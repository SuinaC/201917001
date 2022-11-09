package com.jia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jia.mapper")
public class JiaBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiaBlogApplication.class,args);
    }
}
