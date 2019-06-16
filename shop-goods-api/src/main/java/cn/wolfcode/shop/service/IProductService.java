package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.qo.ProductQueryObject;
import cn.wolfcode.shop.vo.ProductVo;

public interface IProductService {
    PageResult getAllProduct(ProductQueryObject qo);

    /**
     * 保存商品
     */
    void save(ProductVo productVo);

    /**
     * 通过商品id获取商品对象
     * @param productId
     * @return
     */
    Product getProductById(Long productId);
}
