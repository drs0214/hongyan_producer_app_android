package com.aerozhonghuan.hongyan.producer.utils;

import android.text.method.ReplacementTransformationMethod;
import android.widget.EditText;

/**
 * eidttext 输入小写变大写
 * Created by zhangyunfei on 17/3/6.
 */

public class EdittextLowToUpperTransformation extends ReplacementTransformationMethod {

    @Override
    protected char[] getOriginal() {
        char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        return aa;
    }

    @Override
    protected char[] getReplacement() {
        char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        return cc;
    }


    /**
     * eidttext 输入小写变大写
     * @param editText eidttext
     */
    public static void setLowerToUpper(EditText editText) {
        if (editText == null)
            throw new NullPointerException();
        editText.setTransformationMethod(new EdittextLowToUpperTransformation());
    }
}