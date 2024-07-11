package com.lz;

import com.lz.Base.UserClient;
import com.lz.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/08/下午3:31
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients(
        basePackages = "com.lz.Base",defaultConfiguration=
        {DefaultFeignConfiguration.class})
public class orderApplication {
    public static void main(String[] args) {
        SpringApplication.run(orderApplication.class, args);
        System.out.println("Hello orderApplication!");
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}