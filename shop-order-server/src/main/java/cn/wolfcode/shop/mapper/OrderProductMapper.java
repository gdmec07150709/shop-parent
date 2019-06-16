package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.OrderProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderProduct record);

    OrderProduct selectByPrimaryKey(Long id);

    List<OrderProduct> selectAll();

    int updateByPrimaryKey(OrderProduct record);
}