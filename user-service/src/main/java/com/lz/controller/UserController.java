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
import java.util.HashMap;
import java.util.Map;


/**
 * @author lz
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
    public Result<String> getUser() {
        String user = userService.getUser();
        return Result.success(user);
    }

    @GetMapping("/now")
    public Result<String> now() {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
        return Result.success(format);
    }

    @GetMapping("/getData")
    public String getUser2() {
        return data;
    }

    /**
     * 获取 全部用户
     *
     * @return {@code Iterable<User> }
     */
    @GetMapping
    public Iterable<User> getUser3() {
        return userService.getAllUser();
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
    @PutMapping
    public String updateUser(@RequestBody User user) throws MyException {

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
        if (redisEnabled) {
            User user_redis = (User) redisRepository.getObjectById("user",
                                                                   id + "");
            log.info("user1 {} ", user_redis);
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

    @GetMapping("/getOrder/{id}")
    public void getUser1(@PathVariable("id") Long id) throws MyException {
        String s = orderClient.getOrder();
        System.out.println(s);
        Order order = orderClient.getOrder(id);
        if (order == null) {
            throw new MyException("订单不存在");
        }
        System.out.println(order);

    }


}