package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.content.Context;
import android.text.TextUtils;

import com.aerozhonghuan.hongyan.producer.dao.beans.UserBean;
import com.aerozhonghuan.hongyan.producer.framework.base.URLs;
import com.aerozhonghuan.hongyan.producer.modules.common.event.UserInfoChangedEvent;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.CommonMessage;
import com.aerozhonghuan.oknet2.JsonBodyBuilder;
import com.aerozhonghuan.oknet2.RequestBuilder;

import org.json.JSONObject;

/**
 * 用户信息
 * Created by zhangyunfei on 17/6/29.
 */

public class UserInfoWebRequest {
    private static final String TAG = "UserInfoWebRequest";

    //获取用户
    public static void getUserInfoFromWeb(final Context context, final OnGetUserInfoCallback onGetUserInfoCallback) {
        if (UserInfoManager.getCurrentUserBaseInfo() == null) return;

        String token = UserInfoManager.getCurrentUserBaseInfo().getToken();
        LogUtil.i(TAG, "setting pwd token : " + token);
        String json = new JsonBodyBuilder()
                .put("token", token)
                .build();
        String url = URLs.USER_GET_USERINFO;
        RequestBuilder.with(context).URL(url).body(json)
                .onSuccess(new CommonCallback<UserBean>(UserBean.class) {
                    @Override
                    public void onSuccess(UserBean userBean, CommonMessage commonMessage, String allResponseString) {
                        LogUtil.i(TAG, "获取用户信息成功 ");
                        if (userBean != null) {
                            UserInfoManager.saveUserDetail(userBean);
                            //本地数据库保存后，发出 用户信息变化事件
                            EventBusManager.post(new UserInfoChangedEvent(userBean));
                            if (onGetUserInfoCallback != null)
                                onGetUserInfoCallback.onSuccess(userBean);
                        }
                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                        LogUtil.i(TAG, "错误消息" + allResponseString);
                        try {
                            if (!TextUtils.isEmpty(allResponseString)) {
                                JSONObject obj = new JSONObject(allResponseString);
                                String message = obj.getString("message");
                                if (onGetUserInfoCallback != null)
                                    onGetUserInfoCallback.onFailure(message);
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return super.onFailure(httpCode, exception, responseMessage, allResponseString);
                    }

                }).excute();
    }

    /**
     * 退出登录
     *
     * @param context
     * @param token
     * @param callback
     */
    public static void logout(Context context, String token, CommonCallback<Object> callback) {
        RequestBuilder.with(context).URL(URLs.USER_LOGOUT)
                .para("token", token)
                .onSuccess(callback).excute();
    }

    public interface OnGetUserInfoCallback {
        void onSuccess(UserBean userBean);

        void onFailure(String err);
    }
}
