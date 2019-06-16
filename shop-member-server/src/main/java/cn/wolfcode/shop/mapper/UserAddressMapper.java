package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAddress record);

    UserAddress selectByPrimaryKey(Long id);

    List<UserAddress> selectAll();

    int updateByPrimaryKey(UserAddress record);
}