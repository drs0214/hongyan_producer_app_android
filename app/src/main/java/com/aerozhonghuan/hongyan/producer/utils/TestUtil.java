package com.aerozhonghuan.hongyan.producer.utils;

import android.widget.EditText;

import com.aerozhonghuan.hongyan.producer.BuildConfig;

import java.lang.reflect.Field;

/**
 * 临时测试sh
 * Created by zhangyunfei on 16/12/29.
 */

public class TestUtil {

    /**
     * 测试用：自动输入账户密码文字到输入框
     *
     * @param editTexts
     */
    public static void testLoginPage(EditText[] editTexts) {
        if (editTexts == null || editTexts.length != 2) return;
        if ("release".equals(BuildConfig.BUILD_TYPE)) return;
        String val = getStaticFieldValueString(BuildConfig.class, "AUTO_LOGIN_BY_ZYF");
        if ("null".equals(val) || val == null) return;
        //格式为： 15910622863,456789
        String[] arr = val.split(",");
        //开发时测试环境
        if (arr.length > 0 && editTexts[0] != null) {
            editTexts[0].setText(arr[0]);
        }
        if (arr.length > 1 && editTexts[1] != null) {
            editTexts[1].setText(arr[1]);
        }
    }


    private static String getStaticFieldValueString(Class clazz, String fieldName) {
        if (clazz == null) throw new NullPointerException();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            Object value = field.get(clazz);
            return value == null ? null : value.toString();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
