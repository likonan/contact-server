package com.swpu.userserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
* ComponnentScan注解，默认读取项目的跟目录package下的所有文件 若自定义java文件里没有package下，Spring不会加载
* MapperScan解决羡慕启动找不到mapper接口问题*/
//@MapperScan("com.swpu.userserver.*.mapper")

@SpringBootApplication
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
//        SpringApplication springApplication = new SpringApplication(UserServerApplication.class);
//        springApplication.setBannerMode(Banner.Mode.LOG);
//        springApplication.run(args);

    }

}
