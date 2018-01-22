package com.aerozhonghuan.hongyan.producer.framework.push;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.framework.base.InitialComponent;
import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.utils.AppUtil;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.ixintui.pushsdk.PushSdkApi;
import com.ixintui.pushsdk.SdkConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 推送继承类
 * 处理消息请在 PushMessageHandler 中
 * <p>
 * Created by zhangyunfei on 17/7/4.
 */

public class PushContext implements InitialComponent {

    private static final int APP_KEY = BuildConfig.PUSH_APPKEY;
    private static final String TAG = "PUSH";
    static List<Short> tags;
    private static boolean hasInit = false;//是否进行过初始化
    private static String mPushToken;
    private static PushContext instance;

    private PushContext() {
    }

    public static final PushContext getInstance() {
        if (instance == null)
            instance = new PushContext();
        return instance;
    }

    private static String getChannel(Context context) {
        String channel = AppUtil.getChannel(context);
        return channel == null ? "未知" : channel;
    }

    private static String getAppVersionName(Context context) {
        return AppUtil.getAppVersionName(context);
    }

    /**
     * 当 执行 ixintui的一些api方法时，接收到的回调
     *
     * @param context
     * @param cmd
     * @param code
     * @param extra
     */
    public static void onPushCommondCallback(Context context, String cmd, int code, String extra) {
        print(String.format("commond = %s,code = %s,pushToken = %s", cmd, code, extra));
        if (SdkConstants.REGISTER.equals(cmd)) {
            if (code != 0) {
                print("注册失败");
                return;
            }
            print("注册成功，获得推送 push token = " + extra);
            mPushToken = extra;
            sendPushTokenToServer();
            onRegistSuccess(context);
        }
    }

    /**
     * 发送 push token给服务端，会尝试3次
     */
    private static void sendPushTokenToServer() {
        if (TextUtils.isEmpty(PushContext.getPushToken())) {
            print("push token 不存在，无法sendPushTokenToServer");
            return;
        }
        UploadPushTokenTask.getInstance().sendPushTokenToServer(PushContext.getPushToken());
    }

    /**
     * 获得push token
     *
     * @return
     */
    private static String getPushToken() {
        return mPushToken;
    }

    /**
     * 当收到透传消息时
     *
     * @param msg
     * @param extra
     */
    public static void onReceiveMessage(Context context, String msg, String extra) {
        PushMessageHandler.handleMessage(context, msg, extra);
    }

    private static void print(String str) {
        LogUtil.d(TAG, str);
    }

    /**
     * 当注册成功
     *
     * @param context
     */
    private static void onRegistSuccess(Context context) {
        //如果您希望在小米设备上使用小米推送，请使用以下注册接口：
        //PushSdkApi.register(this, appKey, channel, appVersion, miAppId, miAppKey);

        if (UserInfoManager.isUserAuthenticated() && MyAppliation.getApplication().getUserInfo() != null) {
            String token = MyAppliation.getApplication().getUserInfo().getToken();
            if (!TextUtils.isEmpty(token)) {
                PushSdkApi.bindAlias(context, token);
                print("绑定别名: " + token);
            }
        }
        tags = new ArrayList<>();
        if (tags.size() > 0)
            PushSdkApi.addTags(context, tags);
        LogUtil.d(TAG, "初始化推送 完成");
        hasInit = true;
    }

    /**
     * 当初始化时
     *
     * @param contextPara
     */
    public void onInit(Activity contextPara) {
        print("初始化推送 onInit");
        if (hasInit) {
            print("初始化推送 has Init break;");
            //如果已经初始化过，则 仅仅发送push token就够了。这适用于 用户 "退出登录后，push已经注册过，我们通知服务端更新push token"
            sendPushTokenToServer();
            return;
        }
//        PushMessageManager.demo();
        if (BuildConfig.DEBUG)
            print(PushSdkApi.getSdkIntegrationInfo(contextPara));

        //请一定确保四个参数都有值
        //参数appKey为应用从ixintui申请的应用唯一标识符
        //参数channel标识应用的分发渠道，用于统计推送信息，
        //参数appVersion标识应用版本信息，用于统计推送信息
        //参数miAppId小米appId
        //参数miAppKey小米appKey
        PushSdkApi.register(contextPara, APP_KEY, getChannel(contextPara), getAppVersionName(contextPara));
    }

    public void clearup(Activity context) {
        LogUtil.d(TAG, "clearup");
        if (tags != null && tags.size() > 0)
            PushSdkApi.deleteTags(context, tags);
        if (UserInfoManager.isUserAuthenticated() && MyAppliation.getApplication().getUserInfo() != null) {
            String token = MyAppliation.getApplication().getUserInfo().getToken();
            if (!TextUtils.isEmpty(token)) {
                PushSdkApi.unbindAlias(context, token);
                print("解绑别名: " + token);
            }
        }
        hasInit = false;
//        PushSdkApi.suspendPush(context);  2017-07-22 张云飞 故意不暂停推送
    }


    /**
     *
     2、增加标签接口
     应用可以通过标签来标记特定的用户群体， 如“北京”用户，“男”用户， “商务”用户等。
     Push系统根据伴随消息下发的标签来对特定人群进行消息发送。
     在push系统中，标签是一个16位的整数，应用将标签值和具体含义相对应。
     请注意，添加标签必须在成功初始化（注册）之后进行。 方法如下：

     PushSdkApi.addTags(context, tags);
     tags: List<Short>类型

     其结果会异步返回到Receiver，通过如下方式获取。
     结果格式为json的整数数组，表示添加成功的tag列表。
     String extra = intent.getStringExtra(SdkConstants.ADDITION);
     注意，由于系统限制一个用户最多设置16个tag，超出部分会被丢弃。返回结果中只有成功添加的tag。
     3、删除标签接口
     和增加标签接口类似，本接口必须在成功注册之后才能调用。
     结果会异步返回到Receiver，只标识成功失败。失败唯一原因是数组中的tag都不存在。
     PushSdkApi.deleteTags(context, tags);
     参数：tags:List<Short>类型
     4、列出标签接口
     和添加标签接口类似，同样必须在成功注册之后才能调用
     PushSdkApi.listTags(context);
     异步结果会返回给Receiver，使用如下方式获取标签，其格式为json整数数组。
     String extra = intent.getStringExtra(SdkConstants.ADDITION);

     5、暂停push接口
     暂停接收push消息。
     必须在成功注册之后才能调用，调用方式如下。异步结果会返回给Receiver。
     PushSdkApi.suspendPush(context);
     6、恢复push接口
     恢复本应用接收push，必须在成功注册之后才能调用，调用格式如下。
     异步结果会返回给Receiver。
     PushSdkApi.resumePush(context);
     7、检查push是否暂停中
     必须在成功注册之后才能调用。
     异步结果会返回给Receiver, 通过如下方式获取。
     PushSdkApi.isSuspended(context);
     String extra = intent.getStringExtra(SdkConstants.ADDITION);
     返回值：“true”标识已经暂停，”false”标识push正在运行
     8、打开或关闭基本信息统计
     PushSdkApi.enableStat(context, isEnable);
     isEnable可以为true或false。
     异步结果会返回给Receiver, 通过如下方式获取。
     String extra = intent.getStringExtra(SdkConstants.ADDITION);
     返回值："true"标识已经开启统计，"false"标识基本信息统计关闭
     9、绑定别名
     PushSdkApi.bindAlias(context, alias);

     alias为String，请注意限制长度为40个字节，这里长度并不是字符数，而是字符转成字节表示后的字节数，比如一个英文字母的UTF-8只占用一个字节，但是一个中文字符要占用二到三个字节。

     异步结果会返回给Receiver, 通过如下方式获取。请注意多次重复调用可能只会一次返回结果，绑定成功的是最后调用的值

     // 返回值，0为成功，否则失败

     int code = intent.getIntExtra(SdkConstants.CODE, 0);

     // 绑定成功的别名

     String aliasBound = intent.getStringExtra(SdkConstants.ADDITION);
     10、解绑别名
     PushSdkApi.unbindAlias(context, alias);


     alias为String，请注意限制长度为40个字节，这里长度并不是字符数，而是字符转成字节表示后的字节数，比如一个英文字母的UTF-8只占用一个字节，但是一个中文字符要占用二到三个字节。

     异步结果会返回给Receiver, 通过如下方式获取。请注意多次重复调用可能只会一次返回结果，解绑成功的是最后调用的值
     // 返回值，0为成功，否则失败

     int code = intent.getIntExtra(SdkConstants.CODE, 0);
     // 解绑成功的别名

     String aliasUnbound = intent.getStringExtra(SdkConstants.ADDITION);
     11、检查集成SDK结果
     PushSdkApi.getSdkIntegrationInfo(context);
     本接口应在注册之前调用，来检查SDK是否被正确完整的集成。同步结果将直接返回给调用者。

     // 返回值

     String result = PushSdkApi.getSdkIntegrationInfo(context);
     // result字符串将集成结果展示出来，可以根据提示的信息做相应的检查和修改。
     * */
}
