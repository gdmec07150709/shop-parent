package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.UserAddress;

public interface IUserAddressService {
    /**
     * 通过主键id获取userAddress对象
     */
    UserAddress getById(Long id);
}
