package com.mutest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/3/26 14:15
 * @description 启动类
 * @modify
 */
@SpringBootApplication
@MapperScan(value = "com.mutest.dao")
public class MutestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutestApplication.class, args);
    }
}
