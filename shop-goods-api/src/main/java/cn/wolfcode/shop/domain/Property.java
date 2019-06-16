package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Setter@Getter
public class Property extends BaseDomain{

    private Long catalogId;

    private String name;

    private Integer sort;

    private Byte type;

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