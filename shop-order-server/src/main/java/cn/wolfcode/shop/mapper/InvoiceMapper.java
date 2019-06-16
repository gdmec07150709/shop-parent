package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Invoice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface InvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Invoice record);

    Invoice selectByPrimaryKey(Long id);

    List<Invoice> selectAll();

    int updateByPrimaryKey(Invoice record);
}