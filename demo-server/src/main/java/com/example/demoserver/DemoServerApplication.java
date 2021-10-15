package com.example.demoserver;

import com.ywb.TestService;
import com.ywb.config.TestConfig;
import com.ywb.model.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication

@Import(value = {Test.class})
//@MapperScan("com.cn.dao.mysql")

public class DemoServerApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(DemoServerApplication.class, args);

        System.out.println(run.getBean(Test.class));
        System.out.println(run.getBean(TestService.class));
        System.out.println(run.getBean(TestConfig.class));
    }

}
