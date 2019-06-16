package cn.wolfcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("cn.wolfcode.shop.mapper")
@PropertySource({"zookeeper.properties","redis.properties"})
public class GoodsApp {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApp.class,args);
    }
}
