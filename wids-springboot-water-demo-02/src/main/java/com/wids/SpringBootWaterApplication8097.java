package com.wids;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wids.mapper")
public class SpringBootWaterApplication8097 {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWaterApplication8097.class,args);
    }
}
