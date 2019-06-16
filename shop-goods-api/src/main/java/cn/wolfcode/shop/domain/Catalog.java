package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class Catalog extends BaseDomain{

    private static final Long  serialVersionUID=1L;

    private String name;

    private String code;

    private Integer sort;

    private Long pId;

    private Integer isParent;

    private Integer productCount;

    private Integer propertyCount;

    public String getJsonData(){
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id",this.getId());
        hashMap.put("name",name);
        hashMap.put("code",code);
        hashMap.put("sort",sort);
        hashMap.put("pId",pId);
        hashMap.put("isParent",isParent);
        return  JSON.toJSONString(hashMap);
    }
}