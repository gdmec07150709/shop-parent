package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Catalog;

import java.util.List;

public interface ICatalogService {

    List<Catalog> getAllCatalog();

    List<Catalog> getChildCatalog(Long id);

    void catalogSort(List<Long> ids);

    void save(Catalog catalog);

    void delete(Long id);

    List<Catalog> getAllParentCatalog(Long CatalogId);
}
