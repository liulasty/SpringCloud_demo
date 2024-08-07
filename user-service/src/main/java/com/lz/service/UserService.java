package com.lz.service;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/08/下午6:23
 * @Description:
 */

import com.lz.exception.MyException;
import com.lz.pojo.User;

/**
 * 用户服务
 *
 * @author lz
 * @date 2024/07/11 16:49:35
 */

public interface UserService {
    
    String getUser();
    
    Iterable<User> getAllUser();

    void addUser(User user);

    void deleteUser(int id) throws MyException;

    void updateUser(User user) throws MyException;

    User getUserById(int id);

    User getUserById1(Long id);
}