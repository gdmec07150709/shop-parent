package cn.wolfcode.shop.controller;


import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.service.IPropertyService;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/propertys")
public class PropertyController {
    @Reference
    private ICatalogService       catalogService;
    @Reference
    private IPropertyService      propertyService;
    @Reference
    private IPropertyValueService propertyValueService;

    /**
     * 商品分类界面树
     * @param model
     * @return
     */
    @GetMapping("/view")
    public String getAllCatalog(Model model) {
        List<Catalog> allCatalog = catalogService.getAllCatalog();
        model.addAttribute("allCatalog", JSON.toJSONString(allCatalog));
        return "property/property";
    }
    /**
     * 通过属性id获取属性值
     */
    @GetMapping("/{propertyId}/propertyValues")
    public String getPropertyValues(@PathVariable("propertyId") Long propertyId,Model model){
        List<PropertyValue> propertyValueList = propertyValueService.getPropertyValue(propertyId);
        model.addAttribute("propertyValueList",propertyValueList);
        return "property/property_value_list";
    }
    /**
     * 保存/编辑分类属性
     */
    @PostMapping
    @ResponseBody
    public JSONResult save(Property property){
        JSONResult jsonResult = new JSONResult();
        propertyService.save(property);
        return jsonResult;
    }
    /**
     * 删除属性
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public JSONResult delete(@PathVariable("id") Long id){
        JSONResult jsonResult = new JSONResult();
        propertyService.delete(id);
        return jsonResult;
    }
}
