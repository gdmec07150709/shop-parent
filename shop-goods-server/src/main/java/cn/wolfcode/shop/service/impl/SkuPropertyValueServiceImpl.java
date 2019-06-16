package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.SkuPropertyValue;
import cn.wolfcode.shop.mapper.SkuPropertyValueMapper;
import cn.wolfcode.shop.service.ISkuPropertyValueService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkuPropertyValueServiceImpl implements ISkuPropertyValueService {
    @Autowired
    private SkuPropertyValueMapper propertyValueMapper;
    @Override
    public List<SkuPropertyValue> getPropertyValues(Long id) {
        return propertyValueMapper.getPropertyValues(id);
    }

    @Override
    public void save(List<SkuPropertyValue> SkuPropertyValueList) {
        SkuPropertyValueList.forEach(propertyValue->{
            if(propertyValue.getId() == null){
                propertyValueMapper.insert(propertyValue);
            }else{
                propertyValueMapper.updateByPrimaryKey(propertyValue);
            }
       });
    }

    @Override
    public void delete(Long id) {
        propertyValueMapper.deleteByPrimaryKey(id);
    }
}
