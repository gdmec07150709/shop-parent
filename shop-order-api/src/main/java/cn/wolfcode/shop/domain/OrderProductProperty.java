package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderProductProperty extends BaseDomain{

    private Long orderProductId;

    private String name;

    private String value;

}