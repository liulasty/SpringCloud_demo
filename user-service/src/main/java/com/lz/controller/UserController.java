package com.lz.controller;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/08/下午6:35
 * @Description:
 */

import com.lz.Base.OrderClient;
import com.lz.pojo.Order;
import com.lz.exception.MyException;
import com.lz.pojo.User;
import com.lz.respositories.RedisRepository;
import com.lz.result.Result;
import com.lz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户控制器
 *
 * @author lz
 * @date 2024/07/11 16:52:41
 */
@RestController
@RequestMapping(value = "/user")
@RefreshScope
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisRepository redisRepository;

    @Value("${spring.redis.enabled}")
    private boolean redisEnabled;

    @Value("${pattern.dateformat}")
    private String dateformat;

    @Value("${pattern.data}")
    private String data;

    @RequestMapping(value = "/getUser")
    public String getUser() {
        return userService.getUser();
    }

    @GetMapping("/now")
    public String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }

    @GetMapping("/getData")
    public String getUser2() {
        return data;
    }

    /**
     * 获取 全部用户
     *
     * @return {@code List<User> }
     */
    @GetMapping
    public List<User> getUser3() {
        ArrayList<User> list = new ArrayList<>();
        userService.getAllUser().forEach(list::add);
        return list;
    }

    /**
     * 添加用户
     *
     * @param user 用户
     */
    @PostMapping
    public String addUser(@RequestBody User user) {
        System.out.println("user:" + user);
        userService.addUser(user);
        if (redisEnabled) {
            extracted();
        }

        return "success";
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) throws MyException {
        userService.deleteUser(id);
        if (redisEnabled) {
            extracted();
        }
        return "success";
    }

    /**
     * 更新用户
     *
     * @param user 用户
     */
    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestBody User user) throws MyException {

        userService.updateUser(user);
        if (redisEnabled) {
            extracted();
        }
        return "success";

    }

    /**
     * 重置redis缓存
     */
    private void extracted() {
        redisRepository.clearObjects("user");
        Iterable<User> allUser = userService.getAllUser();

        // 转换为Map，使用用户ID作为键
        Map<String, User> userMap = new HashMap<>();
        allUser.forEach(item -> userMap.put(String.valueOf(item.getId()),
                                            item));

        redisRepository.setObjectsByHash("user", userMap);
    }

    /**
     * 获取用户
     *
     * @param id 用户id
     *
     * @return 用户
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) throws MyException {
        if (id <= 0){
            throw new MyException("用户不存在");
        }
        
        if (redisEnabled) {
            User user_redis = (User) redisRepository.getObjectById("user",
                                                                   id + "");
            
            if (user_redis != null) {
                return user_redis;
            }
        }
        User userById = userService.getUserById(id);
        if (redisEnabled && userById != null) {
            // 更新缓存
            extracted();
            return userById;
        }else if (userById != null) {
            return userById;
        }
        throw new MyException("用户不存在");
    }

    @Autowired
    private OrderClient orderClient;

    /**
     * 获取 user1
     * @Description 调用feign客户端调用order-service模块
     * @param id 订单id
     *
     * @throws MyException 订单不存在
     */
    @GetMapping("/getOrder/{id}")
    public void getUser1(@PathVariable("id") Long id) throws MyException {
        String s = orderClient.now();
        System.out.println(s);
        Order order = orderClient.findOrderById(id);
        if (order == null) {
            throw new MyException("订单不存在");
        }
        System.out.println(order);

    }


}