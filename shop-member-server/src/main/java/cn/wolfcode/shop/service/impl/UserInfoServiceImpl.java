package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.UserInfo;
import cn.wolfcode.shop.mapper.UserInfoMapper;
import cn.wolfcode.shop.service.IUserInfoService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by luohaipeng on 2018/3/23.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService{

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }
}
