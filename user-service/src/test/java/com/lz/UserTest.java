package com.lz;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/10/上午11:22
 * @Description:
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.lz.service.userService;

/**
 * @author lz
 */
@SpringBootTest
public class UserTest {
    
    @Autowired
    private userService userService;
    
    @Test
    public void test1() {
        userService.getUserById1(11L);
    }
}