package com.aerozhonghuan.hongyan.producer.modules.user.beans;

import android.graphics.Bitmap;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * Created by dell on 2017/6/27.
 */

public class GetCaptchaEvent extends EventBusEvent {
    private Bitmap captcha;

    public Bitmap getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Bitmap captcha) {
        this.captcha = captcha;
    }
}
