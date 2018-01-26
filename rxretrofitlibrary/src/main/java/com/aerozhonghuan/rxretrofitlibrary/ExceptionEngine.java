package com.aerozhonghuan.rxretrofitlibrary;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by zhangyonghui on 2018/1/24.
 * 异常驱动类
 */

public class ExceptionEngine {
    //对应HTTP的状态码

    //
    private static final int DATAPARSEERROR = 400;
    public static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int SEND_ERROR_FIELD = 422;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int SERIVCE_ERROR = 510;

    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, httpException.code());
            switch(httpException.code()){
                case DATAPARSEERROR:
                    ex.message = "数据异常";
                    break;
                case UNAUTHORIZED:
                    ex.message = "登录失败";
                    break;
                case SEND_ERROR_FIELD:
                    ex.message = "发送数据异常";
                    break;
                case SERIVCE_ERROR:
                    ex.message = httpException.message();
                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";  //均视为网络错误
                    break;
            }
            return ex;
        } else if (e instanceof ServerException){    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";            //均视为解析错误
            return ex;
        }else if(e instanceof ConnectException || e instanceof SocketTimeoutException){
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = "网络中断，请检查您的网络状态";  //均视为网络错误
            return ex;
        }else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = "未知错误";          //未知错误
            return ex;
        }
    }

    /**
     * 约定异常
     */

    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
    }
}
