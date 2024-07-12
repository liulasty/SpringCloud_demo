package com.lz.config;

import com.lz.Interceptor.FeignRequestHeaderInterceptor;
import feign.Logger.Level;
import org.springframework.context.annotation.Bean;

/**
 * 默认的Feign配置类。
 * 该类用于配置Feign客户端的日志级别。
 */
public class DefaultFeignConfiguration {
    
    /**
     * 配置Feign客户端的日志级别。
     * <p>
     * 返回Logger.Level.BASIC，表示Feign将记录请求的基本信息，包括方法名、请求头和响应状态码。
     * 这个配置有助于调试和理解Feign客户端的请求过程，而不会暴露过多敏感信息。
     * </p>
     * 
     * @return Feign客户端的日志级别
     */
    @Bean
    public Level logLevel(){
        return Level.FULL;
    }
    

    //配置feign客服端的拦截器
    @Bean
    public FeignRequestHeaderInterceptor requestInterceptor() {
        
        return new FeignRequestHeaderInterceptor();
    }
}