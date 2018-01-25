package com.aerozhonghuan.rxretrofitlibrary;


/**
 * 约定好的，固定的返回信息 格式。
 */
public class ServerResponse<T> {
    public int code;
    public String message;
    public T data;

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 200;
    }
}