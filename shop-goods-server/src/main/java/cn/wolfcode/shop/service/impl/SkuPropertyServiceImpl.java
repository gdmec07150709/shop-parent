package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.mapper.SkuPropertyMapper;
import cn.wolfcode.shop.service.ISkuPropertyService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkuPropertyServiceImpl implements ISkuPropertyService {
    @Autowired
    private SkuPropertyMapper propertyMapper;

    @Override
    public List<SkuProperty> getCatalogProperty(Long catalogId) {
        return propertyMapper.getCatalogProperty(catalogId);
    }

    @Override
    public void save(SkuProperty property) {
        if (property.getId() == null) {
            propertyMapper.insert(property);
        } else {
            propertyMapper.updateByPrimaryKey(property);
        }
    }

    @Override
    public void delete(Long id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SkuProperty getById(Long skuPropertyId) {
        return propertyMapper.getById(skuPropertyId);
    }

}
