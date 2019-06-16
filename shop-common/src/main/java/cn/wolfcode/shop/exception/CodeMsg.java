package cn.wolfcode.shop.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter@Getter
public class CodeMsg implements Serializable {

    private int code;
    private String message;

    private CodeMsg(){}
    private CodeMsg(int code,String message){
        this.code = code;
        this.message = message;
    }

    //通用的错误码（1xx）
    public static CodeMsg SERVER_EXPTION = new CodeMsg(500100,"服务器繁忙");

    //登录注册错误码（2xx）
    public static CodeMsg USER_NAME_EXISTS = new CodeMsg(500201,"用户名已存在");
    public static CodeMsg USER_NAME_OR_PASSWORD_ERROR = new CodeMsg(500202,"用户名或密码有误");
    public static CodeMsg USER_TOKEN_ERROR = new CodeMsg(500203,"登录状态异常");
    public static CodeMsg USER_TIME_OUT = new CodeMsg(500204,"登录超时，请重新登录");

}
