package com.lz.Base;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/下午5:11
 * @Description:处理feign服务调用userService服务
 */

import com.lz.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author lz
 */
@FeignClient(value = "userService",url = "http://localhost:8082")
public interface UserClient {
    @GetMapping("/users/{id}")
    User findUserById(@PathVariable("id") Long id);
    
    @RequestMapping(value = "/user/getUser")
    String getUser();
    
    @GetMapping(value = "/user/now")
    String now();
    
    @GetMapping("/user/getData")
    String getData();
    
    @GetMapping(value = "/user")
    User getAllUser();
    
    @PostMapping(value = "/user")
    String addUser(User user);
    
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable("id") Long id);
    
    @PutMapping(value = "/user/{id}")
    String updateUser(@PathVariable("id") Long id, @RequestBody User user);
    
    @GetMapping("/user/{id}")
    User getUser(@PathVariable("id") Long id);
}