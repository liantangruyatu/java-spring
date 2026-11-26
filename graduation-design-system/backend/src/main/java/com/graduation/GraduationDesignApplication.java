package com.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 毕业设计管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.graduation.mapper")
public class GraduationDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignApplication.class, args);
        System.out.println("====================================================");
        System.out.println("毕业设计管理系统启动成功！");
        System.out.println("API访问地址：http://localhost:8080/api");
        System.out.println("====================================================");
    }

}
