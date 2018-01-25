package com.aerozhonghuan.rxretrofitlibrary;

import rx.functions.Func1;

/**
 * Created by zhangyonghui on 2018/1/23.
 */

public class ServerResponseFunc<T> implements Func1<ServerResponse<T>,T> {

    @Override
    public T call(ServerResponse<T> serverResponse) {//获取数据失败时，包装一个Fault 抛给上层处理错误
        if(!serverResponse.isSuccess()){
            throw new ServerException(serverResponse.code, serverResponse.message);
        }
        return serverResponse.data;
    }
}
