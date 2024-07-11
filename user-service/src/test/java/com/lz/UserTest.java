package com.lz;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/上午11:22
 * @Description:
 */

import com.lz.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author lz
 */
@SpringBootTest
public class UserTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void test1() {
        userService.getUserById1(11L);
    }
}