package com.lz.exceptionHandling;

import com.lz.exception.MyException;
import com.lz.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lz
 * <p>
 * 通过全局异常处理的方式统一处理异常。
 */
@RestControllerAdvice
@RestController
@Slf4j
public class GlobalControllerAdvice {
    private static final String BAD_REQUEST_MSG = "客户端请求参数错误";


    // <1> 处理 form data方式调用接口校验失败抛出的异常

    @ExceptionHandler(BindException.class)
    public Result<String> bindExceptionHandler(BindException e) {
        log.error("form data方式调用接口校验失败 " , e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> collect = fieldErrors.stream()

                .map(DefaultMessageSourceResolvable::getDefaultMessage)

                .collect(Collectors.toList());

        return Result.error(collect.toString());

    }

    // <2> 处理 json 请求体调用接口校验失败抛出的异常

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("json 请求体调用接口校验失败 " , e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> collect = fieldErrors.stream()

                .map(DefaultMessageSourceResolvable::getDefaultMessage)

                .collect(Collectors.toList());

        return Result.error(collect.toString());

    }

    // <3> 处理单个参数校验失败抛出的异常



    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("SQLIntegrityConstraintViolationException = " , e);

        return Result.error("SQLIntegrityConstraintViolationException");
    }

    /**
     * 异常处理程序
     * 处理SQL异常
     *
     * @param e e
     *
     * @return {@code Result}
     */
    @ExceptionHandler(value = SQLException.class)
    public Result<String> exceptionHandler(SQLException e) {

        log.error("发生SQL异常！原因是{} :", e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Result<String> exceptionHandler(NullPointerException e) {
        log.error("发生系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = MyException.class)
    public Result<String> myException(MyException e) {
        log.error("发生系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(value = IllegalStateException.class)
    public Result<String> illegalStateException(IllegalStateException e) {
        log.error("发生illegalStateException系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<String> illegalArgumentException(IllegalArgumentException e) {
        log.error("发生illegalArgumentException系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(value = RedisConnectionFailureException.class)
    public Result<String> redisConnectionFailureException(RedisConnectionFailureException e) {
        log.error("发生RedisConnectionFailureException系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(value = NoSuchFieldError.class)
    public Result<String> NoSuchFieldError(NoSuchFieldError e) {
        log.error("发生NoSuchFieldError系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(value = RuntimeException.class)
    public Result<String> exceptionHandler(RuntimeException e) {
        log.error("发生 RuntimeException系统异常！原因是:", e);
        return Result.error(e.getMessage());
    }



}