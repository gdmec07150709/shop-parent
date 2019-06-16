package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);
}