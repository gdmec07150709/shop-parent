package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.domain.SkuPropertyValue;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GenerateSkuVo implements Serializable{
    private Product product;
    private List<SkuProperty> skuPropertyList;
    private List<SkuPropertyValue> skuPropertyValueList;


}
