package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.mapper.PropertyValueMapper;
import cn.wolfcode.shop.service.IPropertyValueService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PropertyValueServiceImpl implements IPropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Override
    public List<PropertyValue> getPropertyValue(Long propertyId) {
        return propertyValueMapper.getPropertyValue(propertyId);
    }

    @Override
    public void save(List<PropertyValue> propertyValueList) {
        propertyValueList.forEach(propertyValue -> {
            if(propertyValue.getId() == null)
                propertyValueMapper.insert(propertyValue);
            else
                propertyValueMapper.updateByPrimaryKey(propertyValue);
        });
    }

    @Override
    public void delete(Long id) {
        propertyValueMapper.deleteByPrimaryKey(id);
    }

}












