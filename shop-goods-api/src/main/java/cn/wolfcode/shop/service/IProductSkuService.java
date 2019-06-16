package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductVo;

import java.util.List;
import java.util.Map;

public interface IProductSkuService {
    /**
     * 生成Sku
     */
    List<Map<String,Object>> generateSku(GenerateSkuVo vo);

    /**
     * 保存Sku
     * @param vo
     */
    void save(ProductVo vo);


    List<ProductSku> selectAll(Long productId);

    ProductSku getById(Long skuId);
}
