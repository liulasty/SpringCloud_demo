package com.lz.service.Impl;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/08/下午6:23
 * @Description:
 */

import com.lz.Base.OrderClient;
import com.lz.exception.MyException;
import com.lz.pojo.QUserQuery;
import com.lz.pojo.User;


import com.lz.service.UserService;
import com.lz.util.StringUtils;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import com.lz.respositories.UserRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author lz
 */
@Slf4j
@Service
@RefreshScope
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    
    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private OrderClient orderClient;
   

    @Override
    public String getUser() {
        
        return "good";
    }

    @Override
    public Iterable<User> getAllUser() {


        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        if (user.getAge()<=0 || user.getAge()>100){
            log.error("年龄不合法");
            throw new IllegalArgumentException("年龄不合法");
        }
        if (StringUtils.isEmpty(user.getName())){
            log.error("用户名不能为空");
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getSex())){
            log.error("性别不能为空");
            throw new IllegalArgumentException("性别不能为空");
        }
        userRepository.save(user);
    }

    /**
     * 删除用户。
     * 
     * 本方法通过传入的用户ID，调用userRepository的DeleteById方法，删除对应ID的用户记录。
     * 之所以需要这个方法，是因为在系统中经常需要根据用户ID来定位并删除特定的用户数据。
     * 
     * @param id 用户的唯一标识符。这个参数是必须的，因为删除操作需要明确知道要删除哪个用户。
     */
    @Override
    public void deleteUser(int id) throws MyException {
        // 检查id是否为合法值
        if (id <= 0) {
            log.error("尝试删除非法的用户ID: {}", id);
            throw new IllegalArgumentException("用户ID必须为正数");
        }

        // 检查用户是否存在
        if (!userRepository.existsById(id)) {
            log.error("尝试删除不存在的用户ID: {}", id);
            throw new IllegalStateException("用户ID不存在");
        }

        log.info("开始删除用户ID: {}", id);

        try {
            userRepository.deleteById(id);
            log.info("成功删除用户ID: {}", id);
        } catch (Exception e) {
            log.error("删除用户ID {} 时发生异常: {}", id, e.getMessage());
            throw new MyException( e.getMessage());
        }
    }

    /**
     * 更新用户信息。
     * 通过调用userRepository的save方法来保存更新后的用户对象。
     * 此方法覆盖了父类中的同名方法。
     *
     * @param user 更新后的用户对象，包含需要保存的用户信息。
     */
    @Override
    public void updateUser(User user) throws MyException {
        try {
            if (user.getId()== null || user.getId()<=0){
                log.error("尝试更新非法的用户ID: {}", user.getId());
                throw new IllegalArgumentException("用户ID不合法");
            }
            //检查数据库是否存在指定ID的用户
            Optional<User> optionalUser = userRepository.findById(user.getId());
            if (optionalUser.isPresent()) {
                User u = optionalUser.get();
                // 使用u对象进行后续操作
                if (StringUtils.isEmpty(user.getName())) {
                    u.setName(user.getName());
                }

                if (StringUtils.isEmpty(user.getSex())){
                    u.setSex(user.getSex());
                }

                if (user.getAge()>0 && user.getAge()<100){
                    u.setAge(user.getAge());
                }

                userRepository.save(u);
            } else {
                // 处理用户不存在的情况
                throw new IllegalStateException("用户ID不存在");
            }
            
        } catch (RuntimeException e) {
            log.info("更新用户信息失败");
            throw new MyException(e.getMessage());
        }
    }


    /**
     * 根据ID查找用户。
     *
     * @param id 用户的唯一标识符。
     *
     * @return 如果找到用户，则返回该用户；如果未找到，则返回null。
     */
    @Override
    public User getUserById(int id) {
        /* 检查数据库中是否存在指定ID的用户 */
        if (userRepository.findById(id).isPresent()) {
            
            

            User user = userRepository.findById(id).get();
            
            /* 如果存在，则获取并返回该用户 */
            return user;
        }
        /* 如果不存在，则返回null */
        log.info("用户ID不存在");
        throw new IllegalStateException("用户ID不存在");
    }
    
    @Override
    public User getUserById1(Long id) {
        // 初始化一个布尔条件构建器，用于后续构建查询条件
        Predicate predicate = new BooleanBuilder();
        
        // 创建QUser对象，用于构建JPA查询语句
        QUserQuery qUserQuery = QUserQuery.user;
        // 如果id不为空，则添加id的查询条件
        if (id != null){
            // 将id的查询条件与之前的条件进行合并
            predicate = ExpressionUtils.and(predicate,
                                            qUserQuery.id.gt(Math.toIntExact(id)));
        }else {
            predicate = ExpressionUtils.and(predicate,
                                            qUserQuery.id.gt(0));
        }
        
        // 根据QUser对象构建JPA查询语句，选择id、name、age、sex属性
        JPAQuery<User> jpaQuery = jpaQueryFactory.select(Projections.bean(User.class,
                                                                          qUserQuery.id,
                                                                          qUserQuery.name,
                                                                          qUserQuery.age,
                                                                          qUserQuery.sex)
                )
                .where(predicate)
                .from(qUserQuery);
        // 通过JPA查询获取特定实体的数量
        long count = jpaQuery.fetchCount();
        System.out.println("count:" + count);


        // 执行查询，按照id升序排序，分页查询，每次返回10条记录，从第0条开始
        List<User> users =
                jpaQuery.orderBy(qUserQuery.id.asc())
                        .limit(10)
                        .offset(0)
                        .fetch();



        users.forEach(System.out::println);


        // JPAQuery<Tuple> select = jpaQueryFactory.selectFrom(qUserQuery).select(qUserQuery.id, qUserQuery.name,
        //                                                                   qUserQuery.age, qUserQuery.sex)
        //         .where(qUserQuery.id.eq(Math.toIntExact(id)));
        // select.fetch().forEach(System.out::println);
        

        return users.get(0);
    }


}