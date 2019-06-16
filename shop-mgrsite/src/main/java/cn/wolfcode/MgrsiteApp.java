package cn.wolfcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"zookeeper.properties"})
public class MgrsiteApp {
    public static void main(String[] args) {
        SpringApplication.run(MgrsiteApp.class,args);
    }
}
