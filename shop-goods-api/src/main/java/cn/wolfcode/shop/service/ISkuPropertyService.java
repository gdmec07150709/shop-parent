package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.SkuProperty;

import java.util.List;

public interface ISkuPropertyService {

    /**
     * 通过分类id,获取该分类下的分类属性
     */
    List<SkuProperty> getCatalogProperty(Long catalogId);

    /**
     * 新增和修改分类属性
     * @param property
     */
    void save(SkuProperty property);

    /**
     * 通过分类属性的id,删除对应的分类属性对象
     * @param id
     */
    void delete(Long id);


    /**
     * 通过sku属性的主键id获取sku属性对象
     * @param skuPropertyId
     * @return
     */
    SkuProperty getById(Long skuPropertyId);
}
