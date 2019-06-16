package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.UserLogin;
import cn.wolfcode.shop.service.ICarService;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Reference
    private ICarService carService;
    /**
     * 添加商品到购物车的接口
     * @return
     */
    @PostMapping
    public JSONResult addCar(Long skuId, Integer number, UserLogin userLogin){
        JSONResult jsonResult = new JSONResult();
        carService.addCar(skuId,number,userLogin);
        return jsonResult;

    }
}
