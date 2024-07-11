package com.lz.Base;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/下午5:15
 * @Description:处理feign服务调用orderService服务
 */

import com.lz.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lz
 */
@FeignClient(value = "orderService",url = "http://localhost:8081")
public interface OrderClient {
    @GetMapping(value = {"/order/get/{id}"})
    Order getOrder(@PathVariable("id") Long id);
    
    @GetMapping(value = {"/order/getUser"})
    String getOrder();
}