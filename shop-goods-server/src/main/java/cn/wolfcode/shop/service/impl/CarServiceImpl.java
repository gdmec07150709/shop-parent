package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.UserLogin;
import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.mapper.ProductMapper;
import cn.wolfcode.shop.mapper.ProductSkuMapper;
import cn.wolfcode.shop.service.ICarService;
import cn.wolfcode.shop.utils.RedisContants;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CarServiceImpl implements ICarService {
    @Autowired
    private RedisTemplate    redisTemplate;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductMapper    productMapper;

    @Override
    public void addCar(Long skuId, Integer number, UserLogin userLogin) {
        //拼接一个key,并且通过该key去redis中获取对应的数据,如果有数据,那就在之前的数量基础上,添加这次数量
        ProductSku productSku = productSkuMapper.selectByPrimaryKey(skuId);
        Product product = productMapper.selectByPrimaryKey(productSku.getProductId());
        String carKey = RedisContants.USER_CAR
                .replace("USERID", userLogin.getId() + "")
                .replace("PRODUCTID", productSku.getProductId() + "")
                .replace("SKUID", productSku.getId() + "");
        Map<String, Object> car = redisTemplate.opsForHash().entries(carKey);
        if (car != null && car.size() > 0) {
            Integer oldNumber = (Integer) redisTemplate.opsForHash().get(carKey, "number");
            redisTemplate.opsForHash().put(carKey, "number", oldNumber + number);
        }
        //否则,在redis中新增一个购物车数据
        car = new HashMap<>();
        car.put("productId", productSku.getProductId());
        car.put("skuId", productSku.getId());
        car.put("number", number);
        car.put("price", productSku.getPrice());
        car.put("status", 1);
        car.put("productName", product.getName());
        car.put("productImg", product.getImage());
        Map<String, String> propertys = new HashMap<>();
            productSku.getProductSkuPropertyList().forEach(productSkuProperty -> {
                propertys.put(productSkuProperty.getSkuProperty().getName(), productSkuProperty.getValue());
            });
        car.put("propertys", propertys);
        car.put("select", true);
        redisTemplate.opsForHash().putAll(carKey, car);
    }
}
