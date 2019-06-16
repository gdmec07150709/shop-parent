package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Property;

import java.util.List;

public interface IPropertyService {
    List<Property> getCatalogProperty(Long catalogId);

    void save(Property property);

    void delete(Long id);
}
