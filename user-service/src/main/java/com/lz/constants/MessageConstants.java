package com.lz.constants;



/**
 * 消息常量
 *
 * @author lz
 * @date 2024/04/10
 */
public class MessageConstants {
    /**
     前后端交互异常
     */
    public static final String NETWORK_ERROR = "网络连接不稳定，请稍候重试或检查您的网络设置";
    public static final String REQUEST_TIMEOUT = "服务器响应超时，请稍后重试";
    public static final String REQUEST_FORMAT_ERROR = "请求格式错误，请检查并按照规定格式发送数据";
    public static final String PERMISSION_DENIED = "您没有权限执行此操作";

    /**
     后端处理信息异常
     */
    public static final String DATABASE_ERROR = "系统繁忙，请稍后再试";
    public static final String DATA_VALIDATION_ERROR = "提交的数据不符合要求，请检查并修正";
    public static final String RESOURCE_NOT_FOUND = "您请求的资源未找到";
    public static final String BUSINESS_LOGIC_VIOLATION = "当前操作无法执行，原因：{}";
    public static final String CONCURRENCY_ISSUE = "由于并发冲突，操作未能成功完成，请稍后重试";
    public static final String THIRD_PARTY_SERVICE_UNAVAILABLE = "依赖的外部服务暂时不可用，请稍后重试或联系客服";

    /**
     * 其他通用异常信息
     */
    public static final String INTERNAL_SERVER_ERROR = "服务器内部错误，技术人员已收到通知并将尽快处理";
    public static final String UNEXPECTED_EXCEPTION = "发生意外错误，请稍后重试或联系客服";

    public static final String PASSWORD_CANNOT_BE_EMPTY = "密码不能为空";
    public static final String PASSWORD_WRONG = "密码错误，请重新输入";

}