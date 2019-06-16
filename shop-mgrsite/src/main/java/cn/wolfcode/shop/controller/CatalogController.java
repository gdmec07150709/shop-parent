package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.service.IPropertyService;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.service.ISkuPropertyService;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalogs")
public class CatalogController {
    @Reference
    private ICatalogService       catalogService;
    @Reference
    private IPropertyService      propertyService;
    @Reference
    private IPropertyValueService propertyValueService;
    @Reference
    private ISkuPropertyService   skuPropertyService;
    /**
     * 获取所有的商品分类
     * @return
     */
    @RequestMapping("/getAllCatalog")
    @ResponseBody
    public List<Catalog> getAllCatalog(){
        return catalogService.getAllCatalog();
    }

    /**
     * 进入商品分类管理界面
     * @param model
     * @return
     */
    @GetMapping("/view")
    public String view(Model model){
        List<Catalog> allCatalog = catalogService.getAllCatalog();
        String allCatalogJson = JSON.toJSONString(allCatalog);
        model.addAttribute("allCatalog",allCatalogJson);
        return "catalog/catalog";
    }
    /**
     * 通过分类id获取分类下的子分类
     */
    @GetMapping("/{id}/childs")
    public String getChildCatalog(@PathVariable("id") Long id,Model model){
        List<Catalog> childCatalogList = catalogService.getChildCatalog(id);
        model.addAttribute("catalogList",childCatalogList);
        return "catalog/child_catalog";
    }

    /**
     * 通过拖拽更新排序
     * @param ids
     * @return
     */
    @PatchMapping
    @ResponseBody
    public JSONResult update(@RequestBody List<Long> ids){
        JSONResult jsonResult = new JSONResult();
        catalogService.catalogSort(ids);
        return jsonResult;
    }

    /**
     * 进行分类的保存和编辑
     * @param catalog
     * @return
     */
    @PostMapping
    @ResponseBody
    public JSONResult save(Catalog catalog){
        JSONResult jsonResult = new JSONResult();
        catalogService.save(catalog);
        return jsonResult;
    }
    /**
     * 删除分类
     */
    @DeleteMapping("/{catalogId}")
    @ResponseBody
    public JSONResult delete(@PathVariable("catalogId") Long catalogId){
        JSONResult jsonResult = new JSONResult();
        catalogService.delete(catalogId);
        return jsonResult;
    }
    /**
     * 通过分类id,获取分类下的属性
     */
    @GetMapping("/{catalogId}/propertys")
    public String getCatalogProperty(@PathVariable("catalogId") Long catalogId,Model model){
        List<Property> propertyList = propertyService.getCatalogProperty(catalogId);
        model.addAttribute("propertyList",propertyList);
        return "property/property_list";
    }
    /**
     * 通过分类id,获取该分类下的sku属性
     */
    @GetMapping(value = "/{catalogId}/skuPropertys")
    public String getCatalogSkuProperty(@PathVariable("catalogId") Long catalogId,Model model){
        List<SkuProperty> propertyList = skuPropertyService.getCatalogProperty(catalogId);
        model.addAttribute("propertyList",propertyList);
        return "sku_property/property_list";
    }
    /**
     * 通过分类id获取属性,并且通过type获取相应的属性值
     */
    @GetMapping("/{catalogId}/propertys/propertyValues")
    public String getCatalogPropertyValue(@PathVariable() Long catalogId,Model model){
        List<Property> propertyList = propertyService.getCatalogProperty(catalogId);
        propertyList.forEach(property->{
            if(property.getType()==1){
            List<PropertyValue> propertyValueList = propertyValueService.getPropertyValue(property.getId());
            property.setPropertyValueList(propertyValueList);
            }
        });
        model.addAttribute("propertyList",propertyList);
        return "product/product_property_value";
    }
}
