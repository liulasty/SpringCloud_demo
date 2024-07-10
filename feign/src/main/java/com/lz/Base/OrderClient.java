package com.lz.Base;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/下午5:15
 * @Description:
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lz
 */
@FeignClient(value = "order-service")
public interface OrderClient {
    @GetMapping(value = {"/order/{id}"})
    void getOrder(long id);
}