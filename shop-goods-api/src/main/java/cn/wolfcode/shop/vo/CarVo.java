package cn.wolfcode.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CarVo implements Serializable{
    private Long SkuId;
    private Integer number;
}
