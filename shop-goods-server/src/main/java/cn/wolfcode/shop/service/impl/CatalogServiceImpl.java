package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.mapper.CatalogMapper;
import cn.wolfcode.shop.mapper.ProductMapper;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.utils.RedisContants;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements ICatalogService {
    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Catalog> getAllCatalog() {
        //从Redis中查询数据
        List<Catalog> catalogList = (List<Catalog>) redisTemplate.opsForValue().get(RedisContants.CATALOG_ALL);
        //若数据为空则查询数据库数据,并将数据添加到Redis
        if (catalogList == null) {
            catalogList = updateRedis();
        }
        //若不为空,将Redis的值返回
        return catalogList;
    }

    public List<Catalog> updateRedis() {
        List<Catalog> catalogList;
        catalogList = catalogMapper.selectAll();
        redisTemplate.opsForValue().set(RedisContants.CATALOG_ALL, catalogList);
        return catalogList;
    }

    @Override
    public List<Catalog> getChildCatalog(Long id) {
        List<Catalog> childCatalogs = catalogMapper.getChildCatalog(id);
        childCatalogs.forEach(childCatalog -> {
            //先去Redis中查询总数
            Integer propertyCount = (Integer) redisTemplate.opsForValue().get(RedisContants.CATALOG_PROPERTY_COUNT.replace("ID",
                    childCatalog.getId() + ""));
            Integer productCount = (Integer) redisTemplate.opsForValue().get(RedisContants.CATALOG_PRODUCT_COUNT.replace("ID",
                    childCatalog.getId() + ""));
            ///若Redis中为空则查询数据库,并且将数据存入Redis
            if (propertyCount == null) {
                propertyCount = catalogMapper.getPropertyCount(childCatalog.getId());
                redisTemplate.opsForValue().set(RedisContants.CATALOG_PROPERTY_COUNT.replace("ID", childCatalog.getId() + ""), propertyCount);
            } else {
                childCatalog.setPropertyCount(propertyCount);
            }
            if (productCount == null) {
                productCount = productMapper.getProductCount(childCatalog.getId());
                redisTemplate.opsForValue().set(RedisContants.CATALOG_PRODUCT_COUNT.replace("ID", childCatalog.getId() + ""), productCount);
            } else {
                //否则,直接设置到遍历的分类中
                childCatalog.setProductCount(productCount);
            }
        });
        return childCatalogs;
    }

    @Override
    public void catalogSort(List<Long> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Catalog catalog = catalogMapper.selectByPrimaryKey(ids.get(i));
            catalog.setSort(i + 1);
            catalogMapper.updateByPrimaryKey(catalog);
        }
        updateRedis();
    }

    @Override
    public void save(Catalog catalog) {
        if (catalog.getId() != null) {
            catalogMapper.updateByPrimaryKey(catalog);
        } else {
            catalogMapper.insert(catalog);
        }
        updateRedis();
    }

    @Override
    public void delete(Long id) {
        catalogMapper.deleteByPrimaryKey(id);
        updateRedis();
    }

    @Override
    public List<Catalog> getAllParentCatalog(Long catalogId) {
        return catalogMapper.getAllParentCatalog(catalogId);
    }
}
