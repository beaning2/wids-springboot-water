package com.wids;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
// SpringBoot启动的时候自动扫描指定包下面所有的Mapper接口，将其注入到Spring容器
@MapperScan("com.wids.mapper")
public class SpringBootWaterApplication8093 {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWaterApplication8093.class,args);
    }
}
