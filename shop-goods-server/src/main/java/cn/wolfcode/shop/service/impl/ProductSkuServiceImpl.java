package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.mapper.ProductSkuMapper;
import cn.wolfcode.shop.mapper.ProductSkuPropertyMapper;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.service.IProductSkuService;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductVo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductSkuServiceImpl implements IProductSkuService {
    @Autowired
    private ICatalogService          catalogService;
    @Autowired
    private ProductSkuMapper         productSkuMapper;
    @Autowired
    private ProductSkuPropertyMapper productSkuPropertyMapper;

    @Override
    public List<Map<String, Object>> generateSku(GenerateSkuVo vo) {
        Product product = vo.getProduct();
        List<SkuProperty> skuPropertyList = vo.getSkuPropertyList();
        List<SkuPropertyValue> skuPropertyValueList = vo.getSkuPropertyValueList();
        //生成Sku前缀
        String skuCodePrefix = generateSkuCodePrefix(product);
        Map<String, List<String>> mapList = getMapList(skuPropertyList, skuPropertyValueList);
        //生成需要递归的数据
        List<List<String>> recursiveList = new ArrayList<>();
        skuPropertyList.forEach(skuProperty -> {
            recursiveList.add(mapList.get(skuProperty.getId() + ""));
        });
        List<List<String>> resultList = new ArrayList<>();
        recursive(recursiveList, resultList, 0, new ArrayList<String>());
        List<Map<String, Object>> skuList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            //获取一组Sku组合
            List<String> data = resultList.get(i);
            Map<String, Object> SkuMap = new HashMap<>();
            SkuMap.put("code", skuCodePrefix + (i + 1));
            SkuMap.put("price", product.getBasePrice());
            ///存组合
            for (int k = 0; k < skuPropertyList.size(); k++) {
                //将Sku的属性id作为key
                SkuProperty skuProperty = skuPropertyList.get(k);
                SkuMap.put(skuProperty.getId() + "", data.get(k));
            }
            skuList.add(SkuMap);
        }
        return skuList;
    }

    /**
     * 保存Sku
     *
     * @param vo
     */
    @Override
    public void save(ProductVo vo) {
        List<ProductSku> productSkuList = vo.getProductSkuList();
        productSkuList.forEach(productSku -> {
            productSku.setProductId(vo.getProduct().getId());
            productSkuMapper.insert(productSku);
            productSku.getProductSkuPropertyList().forEach(productSkuProperty -> {
                productSkuProperty.setProductSkuId(productSku.getId());
                productSkuPropertyMapper.insert(productSkuProperty);
            });
        });
    }

    @Override
    public List<ProductSku> selectAll(Long productId) {
        return productSkuMapper.selectAllByProductId(productId);
    }

    @Override
    public ProductSku getById(Long skuId) {
        return productSkuMapper.selectByPrimaryKey(skuId);
    }


    /**
     * 递归操作的方法
     *
     * @param recursiveList 用于递归的基础数据
     * @param resultList    存放所有规则组合的集合
     * @param layer         递归层级
     * @param data          一条规格组合
     */
    private void recursive(List<List<String>> recursiveList, List<List<String>> resultList, int layer, List<String> data) {
        //判断当前层级是否为最后一层
        if (recursiveList.size() - 1 > layer) {
            //不是最后一层,使用递归
            recursiveList.get(layer).forEach(val -> {
                List<String> tempList = new ArrayList<>();
                tempList.addAll(data);
                tempList.add(val);
                recursive(recursiveList, resultList, layer + 1, tempList);
            });
        } else {
            //结束递归,将data数据添加到resultList中
            recursiveList.get(layer).forEach(val -> {
                List<String> tempList = new ArrayList<>();
                tempList.addAll(data);
                tempList.add(val);
                resultList.add(tempList);
            });
        }
    }

    private Map<String, List<String>> getMapList(List<SkuProperty> skuPropertyList, List<SkuPropertyValue> skuPropertyValueList) {
        Map<String, List<String>> mapList = new HashMap<>();
        skuPropertyList.forEach(skuProperty -> {
            mapList.put(skuProperty.getId() + "", new ArrayList<String>());
        });
        skuPropertyValueList.forEach(skuPropertyValue -> {
            List<String> list = mapList.get(skuPropertyValue.getSkuPropertyId() + "");
            list.add(skuPropertyValue.getValue());
        });
        return mapList;
    }

    /**
     * 生成Sku前缀
     *
     * @param product
     * @return
     */
    private String generateSkuCodePrefix(Product product) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Catalog> catalogList = catalogService.getAllParentCatalog(product.getCatalog().getId());
        catalogList.remove(0);
        for (int i = 0; i < catalogList.size(); i++) {
            Catalog catalog = catalogList.get(i);
            if (i == 0) {
                stringBuffer.append(catalog.getCode().length() > 2 ? catalog.getCode().substring(0, 2) : catalog.getCode());
            } else {
                stringBuffer.append(catalog.getSort() > 9 ? catalog.getSort() + "" : "0" + catalog.getSort());
            }
        }
        return stringBuffer.toString() + product.getId();
    }
}
