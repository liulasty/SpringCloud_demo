package com.lz.respositories;

import com.lz.pojo.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

/**
 * 用户仓库
 *
 * @author lz
 * {@code @date} 2024/07/09
 *//*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/09/上午10:25
 * @Description:
 */
@Component
public interface UserRepository extends PagingAndSortingRepository<User,
        Integer> {
}