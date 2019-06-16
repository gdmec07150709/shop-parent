package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.qo.ProductQueryObject;
import cn.wolfcode.shop.service.*;
import cn.wolfcode.shop.util.UploadUtil;
import cn.wolfcode.shop.vo.JSONResult;
import cn.wolfcode.shop.vo.ProductVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Value("${file.path}")
    private String filePath;
    @Reference
    IProductService          productService;
    @Reference
    ICatalogService          catalogService;
    @Reference
    IBrandService            brandService;
    @Reference
    IPropertyService         propertyService;
    @Reference
    ISkuPropertyService      skuPropertyService;
    @Reference
    ISkuPropertyValueService skuPropertyValueService;
    @Reference
    private IProductSkuService productSkuService;

    /**
     * 商品列表界面
     */
    @GetMapping("/view")
    public String product() {
        return "product/product";
    }

    @GetMapping
    public String getAllProduct(@ModelAttribute("qo") ProductQueryObject qo, Map map) {
        PageResult pageResult = productService.getAllProduct(qo);
        map.put("pageResult", pageResult);
        return "product/product_list";
    }

    @RequestMapping("/add")
    public String add(Map map) {
        List<Catalog> catalogList = catalogService.getAllCatalog();
        List<Brand> brandList = brandService.getAllBrand();
        map.put("catalogList", catalogList);
        map.put("brandList", brandList);
        return "product/product_add";
    }

    @PostMapping
    @ResponseBody
    public JSONResult save(ProductVo productVo) {
        JSONResult jsonResult = new JSONResult();
        productService.save(productVo);
        return jsonResult;

    }

    /**
     * @param upload   ckedit默认找upload变量名
     * @param request  ckedit上传图片需要用到
     * @param response ckedit上传图片需要用到
     */
    @RequestMapping(value = "/ckeditUploadImg")
    public void ckeditUploadImg(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) {
        try {
            //上传文件并返回文件名
            String fileName = UploadUtil.upload(upload, filePath);
            // 结合ckeditor功能
            // imageContextPath为图片在服务器地址，如/upload/123.jpg,非绝对路径
            String imageContextPath = "/upload/" + fileName;
            response.setContentType("text/html;charset=UTF-8");
            String callback = request.getParameter("CKEditorFuncNum");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageContextPath + "',''" + ")");
            out.println("</script>");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/uploadImage")
    @ResponseBody
    public String uploadImage(MultipartFile img) {
        String fileName = UploadUtil.upload(img, filePath);
        return "/upload/" + fileName;
    }

    /**
     * 生成Sku
     */
    @GetMapping("/{productId}/skus")
    public String generateSkuUI(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        List<ProductSku> skuList = productSkuService.selectAll(productId);
        model.addAttribute("product", product);
        if (skuList.size() != 0 && skuList != null) {
            ProductSku productSku = skuList.get(0);
            model.addAttribute("skuList", skuList);
            List<ProductSkuProperty> productSkuPropertyList = productSku.getProductSkuPropertyList();
            model.addAttribute("productSkuPropertyList", productSkuPropertyList);
            return "product_sku/sku_list";
        } else {
            List<SkuProperty> skuPropertyList = skuPropertyService.getCatalogProperty(product.getCatalog().getId());
            model.addAttribute("skuPropertyList", skuPropertyList);
            return "product_sku/generate_sku";
        }
    }
}
