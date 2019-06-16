package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.SkuPropertyValue;

import java.util.List;

public interface ISkuPropertyValueService {
    List<SkuPropertyValue> getPropertyValues(Long id);

    void save(List<SkuPropertyValue> SkuPropertyValues);

    void delete(Long id);

}
