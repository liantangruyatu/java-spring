package com.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@MapperScan("com.system.mapper")
public class AgriTradeApplication {

    private static final Logger logger = LoggerFactory.getLogger(AgriTradeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AgriTradeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("\n----------------------------------------------------------\n\t" +
                "应用程序已启动! 访问地址:\n\t" +
                "本地访问: \t\thttp://localhost:8080\n\t" +
                "Vue前端: \t\thttp://localhost:8080/static/vue/index.html\n" +
                "----------------------------------------------------------");
    }
}