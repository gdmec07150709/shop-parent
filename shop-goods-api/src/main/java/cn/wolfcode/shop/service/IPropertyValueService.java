package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.PropertyValue;

import java.util.List;

public interface IPropertyValueService {

    /**
     * 通过分类属性的id获取,该分类属性的属性值列表
     */
    List<PropertyValue> getPropertyValue(Long propertyId);

    /**
     * 新增和修改分类属性值
     * @param propertyValueList
     */
    void save(List<PropertyValue> propertyValueList);

    /**
     * 通过分类属性值id删除对应的对象
     * @param id
     */
    void delete(Long id);
}
