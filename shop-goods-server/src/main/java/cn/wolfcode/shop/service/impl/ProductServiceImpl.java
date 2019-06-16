package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.ProductImage;
import cn.wolfcode.shop.domain.ProductPropertyValue;
import cn.wolfcode.shop.mapper.ProductDetailsMapper;
import cn.wolfcode.shop.mapper.ProductImageMapper;
import cn.wolfcode.shop.mapper.ProductMapper;
import cn.wolfcode.shop.mapper.ProductPropertyValueMapper;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.qo.ProductQueryObject;
import cn.wolfcode.shop.service.IProductService;
import cn.wolfcode.shop.vo.ProductVo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Autowired
    private ProductImageMapper   productImageMapper;
    @Autowired
    private ProductPropertyValueMapper   productPropertyValueMapper;
    @Override
    public PageResult getAllProduct(ProductQueryObject qo) {
        int count = productMapper.queryForCount(qo);
        if(count==0){
            return PageResult.empty();
        }else{
            List list = productMapper.queryForList(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
    }
    @Override
    public void save(ProductVo productVo) {
        //保存商品对象
        productVo.getProduct().setCreatedDate(new Date());
        productVo.getProduct().setLastModifiedDate(new Date());
        int record = productMapper.insert(productVo.getProduct());
        if(record > 0){
            //保存商品详细
            productVo.getProductDetails().setProductId(productVo.getProduct().getId());
            productDetailsMapper.insert(productVo.getProductDetails());
            //保存商品相册
            List<ProductImage> productImagesList = productVo.getProductImagesList();
            productImagesList.forEach(image -> {
                if(image.getImagePath() != null && image.getImagePath().trim().length() > 0){
                    image.setProductId(productVo.getProduct().getId());
                    productImageMapper.insert(image);
                }
            });
            //保存商品属性
            List<ProductPropertyValue> productPropertyValueList = productVo.getProductPropertyValueList();
            productPropertyValueList.forEach(productPropertyValue -> {
                productPropertyValue.setProductId(productVo.getProduct().getId());
                productPropertyValueMapper.insert(productPropertyValue);
            });
        }
    }

    @Override
    public Product getProductById(Long productId) {
        return productMapper.selectByPrimaryKey(productId);
    }
}
