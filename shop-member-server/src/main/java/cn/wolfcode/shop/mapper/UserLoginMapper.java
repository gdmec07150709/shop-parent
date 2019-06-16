package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserLoginMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLogin record);

    UserLogin selectByPrimaryKey(Long id);

    List<UserLogin> selectAll();

    int updateByPrimaryKey(UserLogin record);

    UserLogin isExist(String username);

    UserLogin login(@Param("username") String username, @Param("password") String password);
}