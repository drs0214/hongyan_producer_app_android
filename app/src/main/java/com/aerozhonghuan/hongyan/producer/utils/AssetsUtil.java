package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zhangyunfei on 17/7/17.
 */

public class AssetsUtil {


    public static String readAllFile(Context context, String fileURL) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileURL));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                sb.append(line);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
