package com.lz.Interceptor;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/07/12/上午9:37
 * @Description:
 */

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign 请求头拦截器
 *
 * @author lz
 * @date 2024/07/12 11:25:18
 */
public class FeignRequestHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header("X-Feign-Request", "1234567");
    }
}