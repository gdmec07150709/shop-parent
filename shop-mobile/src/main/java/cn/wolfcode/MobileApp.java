package cn.wolfcode;


import cn.wolfcode.shop.utils.UserLoginArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@SpringBootApplication
@PropertySource({"zookeeper.properties","redis.properties","activemq.properties"})
public class MobileApp  extends WebMvcConfigurerAdapter {

    @Autowired
    private UserLoginArgumentResolver userLoginArgumentResolver;

    public static void main(String[] args) {
        SpringApplication.run(MobileApp.class,args);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userLoginArgumentResolver);
        super.addArgumentResolvers(argumentResolvers);
    }
}
