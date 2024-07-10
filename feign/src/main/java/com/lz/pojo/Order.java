package com.lz.pojo;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/09/上午10:49
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lz
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order implements Serializable {

    private Integer id;


    private Integer userId;
    

    private String name;
    
    

    private String orderInfo;
    

    private LocalDateTime orderStart;
    

    private LocalDateTime orderEnd;


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                ", orderStart=" + orderStart +
                ", orderEnd=" + orderEnd +
                '}';
    }
}