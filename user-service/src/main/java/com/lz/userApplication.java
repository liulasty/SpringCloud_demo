package com.lz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 用户应用程序
 *
 * @author lz
 * &#064;date  2024/07/10
 * &#064;date  2024/07/10
 */

@SpringBootApplication
@EntityScan(basePackages = {"com.lz.pojo"})
public class userApplication {
    public static void main(String[] args) {
        SpringApplication.run(userApplication.class, args);
        System.out.println("Hello world!");
    }
}