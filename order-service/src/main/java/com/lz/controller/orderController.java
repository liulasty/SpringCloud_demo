package com.lz.controller;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/08/下午6:25
 * @Description:
 */

import com.lz.pojo.Order;
import com.lz.respositories.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.lz.service.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 */
@RestController
@RequestMapping(value = "/orders")
@RefreshScope
public class orderController {
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisRepository redisRepository;

    @Value("${spring.redis.enabled}")
    private boolean redisEnabled;
    
    @Autowired
    private OrderService orderService;
    
    @RequestMapping(value = "/getUser")
    public String getOrder()
    {
        
        String url = "http://userService/users/getUser";
        String user = restTemplate.getForObject(url, String.class);
        System.out.println(user);
        return "Order";
    }

    @Value("${pattern.dateformat}")
    private String dateformat;

    @GetMapping("/now")
    public String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }
    
    
    @GetMapping("/all")
    public Iterable<Order> getOrder3()
    {
        
        return orderService.getAllOrder();
    }
    
    @GetMapping("/set")
    public void setOrder()
    {
        orderService.setOrder();
    }

    /**
     * 重置redis缓存
     */
    private void extracted() {
        redisRepository.clearObjects("user");
        Iterable<Order> allUser = orderService.getAllOrder();

        // 转换为Map，使用用户ID作为键
        Map<String, Order> userMap = new HashMap<>();
        allUser.forEach(item -> userMap.put(String.valueOf(item.getId()),
                                            item));

        redisRepository.setObjectsByHash("order", userMap);
    }

    /**
     * 更新任务
     *
     * @param order 次序
     */
    @PutMapping("/update")
    public String updateOrder(@RequestBody Order order)
    {   
        orderService.updateOrder(order);
        if (redisEnabled) {
            extracted();
        }

        return "success";
        
    }

    /**
     * 添加任务
     *
     * @param order 次序
     */
    @PostMapping("/add")
    public String addOrder(@RequestBody Order order)
    {
        orderService.addOrder(order);
        if (redisEnabled) {
            extracted();
        }

        return "success";
    }

    /**
     * 删除任务
     *
     * @param id 同上
     */
    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable int id)
    {
        orderService.deleteOrder(id);
        if (redisEnabled) {
            extracted();
        }

        return "success";
    }

    /**
     * 获取任务
     *
     * @param id 同上
     *
     * @return 次序
     */
    @GetMapping("/get/{id}")
    public Order getOrder(@PathVariable("id") int id)
    {
        
        if (redisEnabled) {
            /*
             * 从Redis存储中获取对象。
             */
            Order order_redis = (Order)redisRepository.getObjectById(
                    "order", id + "");
            if (order_redis != null){
                return order_redis;
            }
           

        }
        Order order = orderService.getOrder(id).get();
        if(redisEnabled && order != null){
            extracted();
            return order;
        }
        throw new RuntimeException("没有找到该任务");
        
    }
}