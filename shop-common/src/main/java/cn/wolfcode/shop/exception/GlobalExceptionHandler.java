package cn.wolfcode.shop.exception;

import cn.wolfcode.shop.vo.JSONResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public JSONResult exceptionHandler(Exception e) {
        e.printStackTrace();
        JSONResult jsonResult = new JSONResult();
        if (e instanceof GlobalException) {
            GlobalException ge = (GlobalException) e;
            CodeMsg codeMsg = ge.getCodeMsg();
            jsonResult.setExceptionMsg(codeMsg);
        }else{
            jsonResult.setExceptionMsg(CodeMsg.SERVER_EXPTION);
        }
        return jsonResult;
    }
}
