package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.qo.QueryObject;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    List queryForList(QueryObject qo);

    int queryForCount(QueryObject qo);

    int updateByPrimaryKey(Product record);

    Integer getProductCount(Long id);
}