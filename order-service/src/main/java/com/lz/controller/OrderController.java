package com.lz.controller;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/08/下午6:25
 * @Description:
 */

import com.lz.annotation.NoReturnHandle;
import com.lz.pojo.Order;
import com.lz.respositories.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.lz.service.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lz
 */
@RestController
@RequestMapping(value = "/order")
@RefreshScope
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisRepository redisRepository;

    @Value("${spring.redis.enabled}")
    private boolean redisEnabled;
    
    @Autowired
    private OrderService orderService;

    @Value("${pattern.dateformat}")
    private String dateformat;

    /**
     * 获取现在时间
     *
     * @return {@code String }
     */
    @GetMapping("/now")
    public String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }


    /**
     * 获取 全部任务
     *
     * @return {@code Iterable<Order> }
     */
    @GetMapping
    public List<Order> getALLOrder()
    {
        Iterable<Order> allOrder = orderService.getAllOrder();
        ArrayList<Order> list = new ArrayList<>();
        allOrder.forEach(list::add);
        return list;
    }

    /**
     * 设置任务
     */
    @GetMapping("/set")
    public void setOrder()
    {
        orderService.setOrder();
    }

    

    /**
     * 更新任务
     *
     * @param order 次序
     */
    @PutMapping("/{id}")
    public String updateOrder(@PathVariable("id") Long id,@RequestBody Order order)
    {   
        orderService.updateOrder(id,order);
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
    @PostMapping
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
     * @param id 任务id
     */
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") int id)
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
     * @param id 任务id
     *
     * @return 次序
     */
    @NoReturnHandle
    @GetMapping("/{id}")
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
        Order order = orderService.getOrder(id);
        
        if(redisEnabled){
            log.info("将对象 {} 存储到Redis中", order);
            extracted();
            return order;
        }else if (order != null){
            return order;
        }
        throw new RuntimeException("没有找到该任务");
        
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
}