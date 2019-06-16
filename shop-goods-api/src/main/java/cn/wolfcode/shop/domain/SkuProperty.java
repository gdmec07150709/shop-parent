package cn.wolfcode.shop.domain;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class SkuProperty extends BaseDomain{

    private Long catalogId;

    private String name;

    private Byte type;

    private Integer sort;

    private List<PropertyValue> propertyValueList;
    public String getJson(){
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id",this.getId());
        hashMap.put("catalogId",catalogId);
        hashMap.put("name",name);
        hashMap.put("sort",sort);
        hashMap.put("type",type);
        return  JSON.toJSONString(hashMap);
    }
}