package com.innovate.innovatehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InnovateHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnovateHubApplication.class, args);
        System.out.println("\n=======================================");
        System.out.println("创新创业项目申报管理系统已启动！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("点击上面的链接或复制到浏览器访问");
        System.out.println("=======================================\n");
    }

}
