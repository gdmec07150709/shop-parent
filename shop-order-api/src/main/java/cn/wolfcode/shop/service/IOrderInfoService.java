package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.qo.OrderQueryObject;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.vo.OrderStatusChangeVo;
import cn.wolfcode.shop.vo.OrderVo;

public interface IOrderInfoService {
    /**
     * 生成订单
     */
    void createOrder(OrderVo orderVo);

    PageResult query(OrderQueryObject qo);

    OrderInfo getById(Long id);

    void changeStatus(OrderStatusChangeVo vo);
}
