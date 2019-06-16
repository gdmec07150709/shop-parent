package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.OrderProductProperty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderProductPropertyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderProductProperty record);

    OrderProductProperty selectByPrimaryKey(Long id);

    List<OrderProductProperty> selectAll();

    int updateByPrimaryKey(OrderProductProperty record);
}