package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.service.IUserLoginService;
import cn.wolfcode.shop.vo.JSONResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Reference
    private IUserLoginService userLoginService;

    /**
     * 用户注册接口
     * @param username
     * @param password
     * @return
     */
    @PostMapping
    public JSONResult register(String username,String password){
        JSONResult jsonResult = new JSONResult();
        userLoginService.register(username,password);
        return jsonResult;
    }

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/tokens")
    public JSONResult login(String username,String password){
        JSONResult jsonResult = new JSONResult();
        String token = userLoginService.login(username,password);
        Map<String,String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token",token);
        jsonResult.setResult(tokenMap);
        return jsonResult;
    }
    /**
     * 用户注销接口
     */
    @DeleteMapping(value = "/tokens")
    public JSONResult logout(HttpServletRequest request){
        String token = request.getHeader("head_token");
        JSONResult jsonResult = new JSONResult();
        userLoginService.logout(token);
        return jsonResult;
    }
}
