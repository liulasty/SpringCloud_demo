package com.lz.Base;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/下午5:15
 * @Description:处理feign服务调用orderService服务
 */

import com.lz.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lz
 */
@FeignClient(value = "orderService",url = "http://localhost:8081")
public interface OrderClient {
    
    @GetMapping("/order/now")
    String now();
    
    @GetMapping("/order")
    List<Order> findAllOrder();
    
    @GetMapping("/order/set")
    void setOrder();
    
    @PutMapping("/order/{id}")
    String updateOrder(@PathVariable("id") Long id,@RequestBody Order order);
    
    @PostMapping
    String addOrder(@RequestBody Order order);
    
    @DeleteMapping("/order/{id}")
    String deleteOrder(@PathVariable("id") Long id);
    
    @GetMapping("/order/{id}")
    Order findOrderById(@PathVariable("id") Long id);
    
    
}