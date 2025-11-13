package com.github.musuyin.fuban;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.musuyin.fuban.mapper")
public class FuBanApplication {
    public static void main(String[] args) {
        SpringApplication.run(FuBanApplication.class, args);
    }
}
