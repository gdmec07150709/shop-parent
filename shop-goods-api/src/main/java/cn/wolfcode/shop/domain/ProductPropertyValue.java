package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPropertyValue extends BaseDomain{
    private Long id;

    private Long productId;

    private String name;

    private String value;

}