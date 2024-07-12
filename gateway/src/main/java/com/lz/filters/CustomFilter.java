package com.lz.filters;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/12/上午9:24
 * @Description:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 自定义过滤器
 * Order注解用于指定一个组件的顺序，值越小，优先级越高。
 * - 此方法是`GlobalFilter`接口的核心，用于定义过滤逻辑。
 *    - `ServerWebExchange`参数提供对当前交换（请求和响应）的访问。
 *    - `GatewayFilterChain`参数允许调用链中的下一个过滤器。
 *    - 方法应返回一个`Mono<Void>`类型，表示异步操作的结果。
 * @author lz
 * @date 2024/07/12 09:25:28
 */
@Order(-1)
@Component
public class CustomFilter implements GlobalFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 滤波器
     *  通过重写filter方法，可以实现自定义的过滤逻辑。
     *  函数的参数包括一个ServerWebExchange对象和一个GatewayFilterChain对象，
     *  其中ServerWebExchange对象包含了当前的请求和响应信息，可以通过该对象获取和修改请求和响应的内容。
     *  而GatewayFilterChain对象则代表了接下来的过滤链，可以通过调用chain.filter(exchange)方法来继续执行后续的过滤器。
     * @param exchange 交换
     * @param chain    链
     *
     * @return {@code Mono<Void> }
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("进入自定义过滤器");
        // 获取请求对象
        // 1.获取请求参数
        MultiValueMap<String, String> params = exchange.getRequest().getHeaders();
        // 2.获取authorization参数
        String auth = params.getFirst("JWT-token");
        String o = (String)redisTemplate.opsForValue().get("JWT-token");
        if (  o == null ) {
            System.out.println("未找到jwt-token，拦截请求"+ auth + " redis:" + o);
            o = "123456";
            redisTemplate.opsForValue().set("JWT-token", o);
            //拦截这次请求，并返回一个空的响应对象
            return exchange.getResponse().setComplete();


        }
        if ( auth == null ||!auth.equals(o)){
            //拦截这次请求，并返回一个空的响应对象
            System.out.println("未携带正确jwt-token，拦截请求");
            return exchange.getResponse().setComplete();
        }

        String gateway = (String) redisTemplate.opsForValue().get("gateway-token");
        
        if ( gateway == null ) {
            System.out.println("未找到gateway-token，拦截请求");
            gateway = "123456";
            redisTemplate.opsForValue().set("gateway-token", gateway);
            //拦截这次请求，并返回一个空的响应对象
            return exchange.getResponse().setComplete();


        }
        
        exchange.getRequest().mutate().header("gateway-token", gateway).build();
        /*
        调用chain.filter(exchange)方法来继续执行后续的过滤器。
         */
        return chain.filter(exchange);

    }
}