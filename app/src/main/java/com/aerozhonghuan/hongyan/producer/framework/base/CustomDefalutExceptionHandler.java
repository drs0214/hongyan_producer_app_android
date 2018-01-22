package com.aerozhonghuan.hongyan.producer.framework.base;

import android.content.Context;
import android.util.Log;

import com.aerozhonghuan.hongyan.producer.modules.common.event.DefalutHttpExceptionAlert;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.oknet2.CommonMessage;
import com.aerozhonghuan.oknet2.DefaultExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;

/**
 * 自定义的默认异常处理器
 * Created by zhangyunfei on 17/6/19.
 */

class CustomDefalutExceptionHandler implements DefaultExceptionHandler {
    private static final String TAG = "DefalutExceptionHandler";

    @Override
    public void handleException(Context context, int httpCode, Exception ex1, CommonMessage commonMessage, String responseString) {
        Log.e(TAG, String.format("进入默认异常处理器： httpCode=[%s], commonMessage=[%s], ex=[%s]", httpCode, commonMessage, ex1));
        String errorMsg = String.format("未知异常(%s)", httpCode);
        if (httpCode == 0) {
            errorMsg = "网络异常";
            if (ex1 instanceof ConnectException) {
                errorMsg = "无法连接到服务器,请检查网络连接是否通畅";
            }
            //遇到 终止http的异常，不显示任何
            if (ex1 instanceof IOException && "Canceled".equals(ex1.getMessage())) {
                return;
            }
        } else {
            if (httpCode == 500) {
                errorMsg = String.format("服务器异常(%s)", httpCode);
            } else if (commonMessage != null) {
                errorMsg = commonMessage.message;
            }
        }
        if (httpCode == 200 && commonMessage != null) {
            Log.e(TAG, "判定为服务异常：" + commonMessage.message);
            if (commonMessage.code == ServerErrorCode.AUTHORIZATION_FAILURE) {//登录失败
                errorMsg = "您的账号已在其它设备登录，如非本人操作，请修改密码！";
                EventBusManager.post(new DefalutHttpExceptionAlert(errorMsg));
                UserInfoManager.logout(context);
                return;
            } else if (commonMessage.code == ServerErrorCode.HOST_ERROR) {
                errorMsg = "系统异常，请联系管理员";
            } else if (commonMessage.code == ServerErrorCode.USER_NOT_EXIST) {
                errorMsg = "用户不存在";
            } else if (commonMessage.code == ServerErrorCode.AUTHORITY_FAILURE) {
                errorMsg = "权限不足";
            }
        }
        EventBusManager.post(new DefalutHttpExceptionAlert(errorMsg));
    }
}
