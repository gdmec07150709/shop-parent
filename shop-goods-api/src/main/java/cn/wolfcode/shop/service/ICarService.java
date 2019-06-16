package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.UserLogin;
import cn.wolfcode.shop.exception.UserException;

public interface ICarService {
    /**
     * 添加商品到购物车
     */
    void addCar(Long skuId, Integer number, UserLogin userLogin) throws UserException;
}
