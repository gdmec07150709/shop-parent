package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.SkuPropertyValue;
import cn.wolfcode.shop.service.ISkuPropertyValueService;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/skuPropertyValues")
public class SkuPropertyValueController {
    @Reference
    private ISkuPropertyValueService propertyValueService;

    /**
     * 保存/编辑属性值
     * @param propertyValueList
     * @return
     */
    @PostMapping
    @ResponseBody
    public JSONResult save(@RequestBody List<SkuPropertyValue> propertyValueList){
        JSONResult jsonResult = new JSONResult();
        propertyValueService.save(propertyValueList);
        return jsonResult;
    }
    /**
     * 删除属性值
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public JSONResult delete(@PathVariable("id") Long id){
        JSONResult jsonResult = new JSONResult();
        propertyValueService.delete(id);
        return jsonResult;
    }
}
