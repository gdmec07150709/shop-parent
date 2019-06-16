package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.UserInfo;

/**
 * Created by luohaipeng on 2018/3/23.
 */
public interface IUserInfoService {

    UserInfo getById(Long id);
}
