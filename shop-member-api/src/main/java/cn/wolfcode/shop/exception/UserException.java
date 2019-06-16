package cn.wolfcode.shop.exception;

public class UserException extends GlobalException {

    public UserException(){

    }

    public UserException(CodeMsg codeMsg){
        super(codeMsg);
    }
}
