package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.domain.Invoice;
import cn.wolfcode.shop.domain.UserLogin;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderVo implements Serializable{
    private Long userAddressId;
    private List<CarVo> carList;
    private PayVo pay;
    private Invoice invoice;
    private UserLogin userLogin;
}
