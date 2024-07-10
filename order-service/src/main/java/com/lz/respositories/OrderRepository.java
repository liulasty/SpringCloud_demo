package com.lz.respositories;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/09/上午10:57
 * @Description:
 */

import com.lz.pojo.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

/**
 * @author lz
 */
@Component
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
}