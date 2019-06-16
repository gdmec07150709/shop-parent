package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.exception.CodeMsg;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by luohaipeng on 2018/3/1.
 */
@Setter@Getter
public class JSONResult implements Serializable{


    private Boolean success = true;
    private String message = "success";
    private Integer code = 200;
    private Object result;

    public void setExceptionMsg(CodeMsg codeMsg){
        this.message = codeMsg.getMessage();
        this.code = codeMsg.getCode();
        this.success = false;
    }
}
