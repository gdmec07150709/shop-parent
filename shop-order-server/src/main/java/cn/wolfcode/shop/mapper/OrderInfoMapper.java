package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.qo.OrderQueryObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    List<OrderInfo> selectAll();

    int updateByPrimaryKey(OrderInfo record);

    int queryCount(OrderQueryObject qo);

    List<OrderInfo> queryList(OrderQueryObject qo);
}