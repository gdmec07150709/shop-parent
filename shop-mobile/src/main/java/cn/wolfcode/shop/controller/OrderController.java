package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.UserLogin;
import cn.wolfcode.shop.message.CreateOrderProductMessage;
import cn.wolfcode.shop.service.IOrderInfoService;
import cn.wolfcode.shop.vo.JSONResult;
import cn.wolfcode.shop.vo.OrderStatusChangeVo;
import cn.wolfcode.shop.vo.OrderVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Reference
    private IOrderInfoService         orderInfoService;
    @Autowired
    private CreateOrderProductMessage createOrderMessage;

    @PostMapping
    public JSONResult createOrder(@RequestBody OrderVo orderVo, UserLogin userLogin) {
        JSONResult jsonResult = new JSONResult();
        orderVo.setUserLogin(userLogin);
        createOrderMessage.sendMessage(JSON.toJSONString(orderVo));
        //orderInfoService.createOrder(orderVo);
        return jsonResult;
    }
    /**
     * 确认收货接口
     */
    @PatchMapping("/{id}")
    public JSONResult changeStatus(OrderStatusChangeVo vo,@PathVariable("id") Long orderId,UserLogin  userLogin){
        JSONResult jsonResult = new JSONResult();
        vo.setOrderId(orderId);
        vo.setUserName(userLogin.getUserName());
        vo.setPlace("前台");
        orderInfoService.changeStatus(vo);
        return jsonResult;
    }
}
