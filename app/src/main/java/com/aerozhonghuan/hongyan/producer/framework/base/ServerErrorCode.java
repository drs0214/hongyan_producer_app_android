package com.aerozhonghuan.hongyan.producer.framework.base;

/**
 * 服务器 错误码 常量
 * Created by zhangyunfei on 16/1/4.
 */
public class ServerErrorCode {
    private ServerErrorCode() {
    }

    public static int SUCCESS = 200;//: "成功",
    public static int HOST_ERROR = 506;//系统异常，请联系管理员
    public static int PRARMETER_ERROR = 507;//参数异常、校验失败、业务异常
    public static int USER_NOT_EXIST = 508;//用户不存在
    public static int AUTHORIZATION_FAILURE = 509;//登录失败
    public static int AUTHORITY_FAILURE = 510;//权限不足


}
