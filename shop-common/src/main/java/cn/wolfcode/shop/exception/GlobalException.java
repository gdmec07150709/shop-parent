package cn.wolfcode.shop.exception;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;
    public GlobalException(){
        super();
    }
    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.getMessage());
        this.codeMsg = codeMsg;
    }
}
