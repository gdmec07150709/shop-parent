package cn.wolfcode.shop.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLogin extends BaseDomain{

    private String userName;

    private String password;

    private Byte state;

}