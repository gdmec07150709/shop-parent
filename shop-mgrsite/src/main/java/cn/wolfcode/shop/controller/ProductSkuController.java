package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.service.IProductSkuService;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.JSONResult;
import cn.wolfcode.shop.vo.ProductVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/skus")
public class ProductSkuController {

    @Reference
    private IProductSkuService productSkuService;


    /**
     * 生成Sku
     * @param generateSkuVo
     * @param map
     * @return
     */
    @PostMapping("/generator")
    public String generate(@RequestBody GenerateSkuVo generateSkuVo, Map map){
        List<Map<String, Object>> skuList = productSkuService.generateSku(generateSkuVo);
        map.put("skuList",skuList);
        map.put("skuProList",generateSkuVo.getSkuPropertyList());
        return "product_sku/sku_form";
    }
    /**
     * 保存save
     */
    @PostMapping
    @ResponseBody
    public JSONResult save(ProductVo vo){
        JSONResult jsonResult = new JSONResult();
        productSkuService.save(vo);
        return jsonResult;
    }
}
