package com.lz;


import com.lz.Base.OrderClient;
import com.lz.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户应用程序
 *
 * @author lz
 * &#064;date  2024/07/10
 * &#064;date  2024/07/10
 */

@SpringBootApplication
@EntityScan(basePackages = {"com.lz.pojo"})
@EnableFeignClients(
        clients = {OrderClient.class},defaultConfiguration=
        {DefaultFeignConfiguration.class})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        System.out.println("Hello world!");
    }
}