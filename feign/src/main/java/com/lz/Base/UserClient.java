package com.lz.Base;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/下午5:11
 * @Description:处理feign服务调用userService服务
 */

import com.lz.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lz
 */
@FeignClient(value = "userService",url = "http://localhost:8082")
public interface UserClient {
    @GetMapping("/users/{id}")
    User findUserById(@PathVariable("id") Long id);
}