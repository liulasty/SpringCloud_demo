package com.lz.service.Impl;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/09/上午10:55
 * @Description:
 */

import com.lz.pojo.Order;
import com.lz.respositories.OrderRepository;
import com.lz.respositories.RedisRepository;
import com.lz.service.OrderService;
import com.lz.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author lz
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    


    @Override
    public Iterable<Order> getAllOrder() {

        return orderRepository.findAll();
    }

    @Override
    public void setOrder() {
        
        for (int i = 0; i <10; i++) {
            Order order_new = new Order();
            order_new.setUserId(i);
            order_new.setName(i+"day");
            order_new.setOrderInfo("01day");
            order_new.setOrderStart(LocalDateTime.now());
            //设置一天后的时间
            order_new.setOrderEnd(LocalDateTime.now().plusDays(1));
            orderRepository.save(order_new);
        }
    }

    @Override
    public Optional<Order> getOrder(int id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (!byId.isPresent()){
            log.info("order is null");
            throw new RuntimeException("order is null");
        }else {
            Order order = byId.get();
        }

        return byId;
    }

    /**
     * 更新任务
     *
     * @param order 次序
     */
    @Override
    public void updateOrder(Order order) {
        if ( order == null || order.getId() == null){
            log.error("order is null");
            throw new RuntimeException("order is null");
        }

        Optional<Order> byId = orderRepository.findById(order.getId());
        /* 检查是否存在指定ID的元素 */
        if (byId.isPresent()){
            Order order1 = byId.get();
            
            if (order1.getId()!=null){
                if (order1.getId()<=0){
                    log.error("id is null");
                    throw new RuntimeException("id is null");
                }
                order1.setId(order.getId());
            }
            if (!StringUtils.isEmpty(order.getName())){
                order1.setName(order.getName());
            }
            
            if (!StringUtils.isEmpty(order.getOrderInfo())){
                order1.setOrderInfo(order.getOrderInfo());
            }
            
            if (order1.getOrderStart()!=null){
                order1.setOrderStart(order.getOrderStart());
            }
            
            if (order1.getOrderEnd()!=null){
                order1.setOrderEnd(order.getOrderEnd());
            }
            
            orderRepository.save(order1);
        }else {
            log.error("order is null");
            throw new RuntimeException("order is null");
        }


    }

    /**
     * 添加任务
     *
     * @param order 次序
     */
    @Override
    public void addOrder(Order order) {
        orderRepository.save(order);


    }

    /**
     * 删除任务
     *
     * @param id 同上
     */
    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateOrder(Long id, Order order) {
        orderRepository.findById(Math.toIntExact(id)).map(order_db -> {
            if (order.getId() != null) {
                order_db.setId(order.getId());
            }
            if (!StringUtils.isEmpty(order.getName())) {
                order_db.setName(order.getName());
            }
            if (!StringUtils.isEmpty(order.getOrderInfo())) {
                order_db.setOrderInfo(order.getOrderInfo());
            }
            if (order.getOrderStart() != null) {
                order_db.setOrderStart(order.getOrderStart());
            }
            if (order.getOrderEnd() != null) {
                order_db.setOrderEnd(order.getOrderEnd());
            }
            return orderRepository.save(order_db);
            
                                         }
        ).orElseThrow(() -> new RuntimeException("order is null"));
    }


}