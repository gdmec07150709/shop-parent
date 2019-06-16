package cn.wolfcode.shop.controller;


import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.service.*;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/skuPropertys")
public class SkuPropertyController {
    @Reference
    private ICatalogService          catalogService;
    @Reference
    private ISkuPropertyService      SkuPropertyService;
    @Reference
    private ISkuPropertyValueService SkuPropertyValueService;
    @Reference
    private IProductService          productService;
    @Reference
    private ISkuPropertyService      skuPropertyService;

    /**
     * 商品分类界面树
     * @param model
     * @return
     */
    @GetMapping("/view")
    public String getAllCatalog(Model model) {
        List<Catalog> allCatalog = catalogService.getAllCatalog();
        model.addAttribute("allCatalog", JSON.toJSONString(allCatalog));
        return "sku_property/property";
    }
    /**
     * 通过属性id获取属性值
     */
    @GetMapping("/{propertyId}/skuPropertyValues")
    public String getPropertyValues(@PathVariable("propertyId") Long propertyId,Model model){
        List<SkuPropertyValue> propertyValueList = SkuPropertyValueService.getPropertyValues(propertyId);
        model.addAttribute("propertyValueList",propertyValueList);
        return "sku_property/property_value_list";
    }
    /**
     * 保存/编辑分类属性
     */
    @PostMapping
    @ResponseBody
    public JSONResult save(SkuProperty property){
        JSONResult jsonResult = new JSONResult();
        SkuPropertyService.save(property);
        return jsonResult;
    }
    /**
     * 删除属性
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public JSONResult delete(@PathVariable("id") Long id){
        JSONResult jsonResult = new JSONResult();
        SkuPropertyService.delete(id);
        return jsonResult;
    }
    /**
     * 添加sku表,通过sku的属性id
     */
    @GetMapping("/{skuPropertyId}/skuPropertyValues/table")
    public String getProValTable(@PathVariable Long skuPropertyId, Model model){
        SkuProperty skuProperty = SkuPropertyService.getById(skuPropertyId);
        List<SkuPropertyValue> skuPropertyValueList = SkuPropertyValueService.getPropertyValues(skuPropertyId);
        model.addAttribute("skuProperty",skuProperty);
        model.addAttribute("skuPropertyValueList",skuPropertyValueList);
        return "product_sku/sku_property_value_table";
    }
}
