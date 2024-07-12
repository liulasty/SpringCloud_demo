package com.lz.service;

import com.lz.pojo.Order;

import java.util.Optional;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/09/上午10:48
 * @Description:
 */
public interface OrderService {
    Iterable<Order> getAllOrder();
    
    void setOrder();

    Order getOrder(int orderId);
    
    void updateOrder(Order order);

    void addOrder(Order order);

    void deleteOrder(int id);

    void updateOrder(Long id, Order order);
}