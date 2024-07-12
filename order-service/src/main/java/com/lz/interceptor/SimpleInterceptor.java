package com.lz.interceptor;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/12/上午9:46
 * @Description:
 */

import com.lz.respositories.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * @author lz
 * @date 2024/07/12 09:58:43
 */
@Component
public class SimpleInterceptor implements HandlerInterceptor {


    
    private final RedisRepository redisRepository;
    
    
    public SimpleInterceptor(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
    // 请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        System.out.println("请求之前SimpleInterceptor preHandle 方法被执行了...");
        
        boolean isIntercept = false;
        // 返回false表示拦截请求，不再继续调用其他的拦截器或处理器
        String queryString = request.getHeader("gateway-token");
        String token_redis = "";
        
        token_redis = (String)redisRepository.get("gateway-token");
        

        if (token_redis == null){
            redisRepository.set("gateway-token", "123456");
        }
        if (queryString != null &&  queryString.equals(token_redis)) {
            System.out.println("这是网关发出的请求");
            isIntercept = true;
        }
        String feignHeader = request.getHeader("X-Feign-Request");
        if (feignHeader != null && feignHeader.equals("1234567")) {
            System.out.println("这是feign发出的请求");
            isIntercept = true;
        }
        
        if (isIntercept) {
            System.out.println("请求通过拦截器");
        } else {
            System.out.println("请求被拦截");
        }


        // 返回true表示继续调用其他的拦截器或处理器
        return isIntercept;
    }

    // 请求处理之后调用，但在视图渲染之前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // System.out.println("请求处理之后SimpleInterceptor postHandle 方法被执行了...");
    }

    // 在整个请求完成之后调用，也就是在视图渲染之后调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // System.out.println("整个请求完成之后SimpleInterceptor afterCompletion 方法被执行了...");
    }
}