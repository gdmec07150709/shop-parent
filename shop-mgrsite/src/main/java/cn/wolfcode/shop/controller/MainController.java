package cn.wolfcode.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    /**
     * 后台管理界面的主页
     * @return
     */
    @RequestMapping("main")
    public String main(){
        return "main";
    }
    /**
     * 首页内容
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    /**
        直接访问主页
     */
    @RequestMapping("/")
    public String root(){
        return "forward:/main";
    }
}
