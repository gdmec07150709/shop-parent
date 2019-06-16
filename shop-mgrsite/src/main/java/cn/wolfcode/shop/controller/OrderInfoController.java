package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.OrderAction;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.domain.UserInfo;
import cn.wolfcode.shop.qo.OrderQueryObject;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.service.IOrderActionService;
import cn.wolfcode.shop.service.IOrderInfoService;
import cn.wolfcode.shop.service.IUserInfoService;
import cn.wolfcode.shop.vo.JSONResult;
import cn.wolfcode.shop.vo.OrderStatusChangeVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by luohaipeng on 2018/2/23.
 */
@Controller
@RequestMapping(value = "/orders")
public class OrderInfoController {

    @Reference
    IOrderInfoService orderInfoService;
    @Reference
    IUserInfoService userInfoService;
    @Reference
    IOrderActionService orderActionService;

    /**
     * /orders/view
     * GET
     * 进入订单管理界面
     * @return
     */
    @GetMapping(value = "/view")
    public String orderInfo(){

        return "order/order_info";
    }

    /**
     * /orders
     * GET
     * @param map
     * @param qo
     * @return
     */
    @GetMapping(value = "")
    public String page(Map map, OrderQueryObject qo){
        PageResult page = orderInfoService.query(qo);
        map.put("page",page);
        return "order/order_list";
    }

    /**
     * /orders/{id}
     * GET
     * @param id
     * @param map
     * @return
     */
    @GetMapping(value = "/{id}")
    public String detail(@PathVariable("id") Long id, Map map){
        OrderInfo orderInfo = orderInfoService.getById(id);
        UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
        List<OrderAction> orderActionList =  orderActionService.getOrderAction(id);
        map.put("userInfo",userInfo);
        map.put("orderInfo",orderInfo);
        map.put("orderActionList",orderActionList);
        return "order/order_detail";
    }
    @PostMapping("/{id}")
    @ResponseBody
    public JSONResult changeStatus(OrderStatusChangeVo vo,@PathVariable("id") Long orderId){
        JSONResult jsonResult = new JSONResult();
        vo.setOrderId(orderId);
        vo.setUserName("admin");
        vo.setPlace("后台");
        orderInfoService.changeStatus(vo);
        return jsonResult;
    }
}






















