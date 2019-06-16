package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/propertyValues")
public class PropertyValueController {
    @Reference
    private IPropertyValueService propertyValueService;

    /**
     * 保存/编辑属性值
     * @param propertyValueList
     * @return
     */
    @PostMapping
    @ResponseBody
    public JSONResult save(@RequestBody List<PropertyValue> propertyValueList){
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
