package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDetails extends BaseDomain{

    private Long productId;

    private String details;

}