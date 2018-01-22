package com.aerozhonghuan.hongyan.producer.modules.user.logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.aerozhonghuan.hongyan.producer.modules.user.beans.GetCaptchaEvent;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.log.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dell on 2017/6/23.
 */

public class LoginLogic {
    private static final String TAG = "LoginLogic";

//    public static OkNetCall getSMSCode(Context context, String mobile, String appType, CommonCallback<String> commonCallback) {
//
//        String baseUrl = URLs.GET_SMS_CODE;
//        String url = baseUrl + "?mobile=" + mobile + "&product=hongyan&smsCode=1&appType=" + appType;
//        return RequestBuilder.with(context).URL(url).useGetMethod()
//                .onSuccess(commonCallback).excute();
//    }

    class GetYZMTask extends AsyncTask<Void, Void, Void> {
        private final String mUrl;
        private Bitmap mBitmap;

        public GetYZMTask(String url) {
            this.mUrl = url;
            this.mBitmap = null;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = getYZMFromWeb(mUrl);
            if (inputStream != null) {
                LogUtil.i(TAG, "mInputStream != null");
                byte[] b = readStream(inputStream);
                mBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            LogUtil.i(TAG, "mInputStream != null");
            if (mBitmap != null) {
                LogUtil.i(TAG, "bm != null");
                GetCaptchaEvent getCaptchaEvent = new GetCaptchaEvent();
                getCaptchaEvent.setCaptcha(mBitmap);
                EventBusManager.post(getCaptchaEvent);
            }

        }
    }

    private InputStream getYZMFromWeb(String urlPath) {
        InputStream inputStream = null;
        try {
            //参数拼接在urlPath中
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("Charsert", "utf-8");
            if (conn.getContentLength() > 0) {
                inputStream = conn.getInputStream();//获取输入流
            }
        } catch (Exception e) {
            LogUtil.i(TAG, "getInputStream : " + e.getMessage());
        }
        return inputStream;
    }

    /*
     * 得到图片字节流 数组大小
     * */
    public byte[] readStream(InputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inStream.close();
            return outStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
