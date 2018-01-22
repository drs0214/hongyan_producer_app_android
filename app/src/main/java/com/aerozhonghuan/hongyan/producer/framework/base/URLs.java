package com.aerozhonghuan.hongyan.producer.framework.base;

import com.aerozhonghuan.hongyan.producer.BuildConfig;

/**
 * URL 常量定义
 * Created by zhangyunfei on 17/6/20.
 */

public class URLs {
    //用户 服务地址
    public static final String HOST_USER_CENTER_RELEASE = "http://jfx.mapbar.com/usercenter";
    public static final String HOST_USER_CENTER_TEST = "http://117.107.204.167:8808";
    public static final String HOST_USER_CENTER_DEVELOP = "http://119.255.37.167:8808";
    //业务 服务地址
    public static final String HOST_BUSINESS_RELEASE = "http://jfx.mapbar.com/api";//线上环境
    public static final String HOST_BUSINESS_TEST = "http://61.161.238.158:8071/api153";//测试环境
    public static final String HOST_BUSINESS_DEVELOP = "http://61.161.238.158:8071/api152";//开发环境
    //文件上传 服务地址
    public static final String HOST_File_UPLOAD_RELEASE = "http://jfx.mapbar.com/fsm";//线上
    public static final String HOST_File_UPLOAD_TEST = "http://61.161.238.158:8057";//测试
    //H5页面服务地址,行程，油耗排行榜等页面的部署地址
    public static final String HOST_HTML5_RELEASE = "http://219.146.249.190:10106/driver";//线上
    public static final String HOST_HTML5_TEST = "http://219.146.249.190:10106/html";//线上测试
    public static final String HOST_HTML5_DEVELOP = "http://192.168.135.89";//内网测试

    public static final String HOST_BUSINESS = BuildConfig.HOST_BUSINESS;//业务服务器
    public static final String HOST_USER_CENTER = BuildConfig.HOST_USER_CENTER;//用户身份授权服务器
    public static final String HOST_FILE_UPLOAD = BuildConfig.HOST_FILE_UPLOAD;//文件上传服务
    public static final String HOST_HTML5 = BuildConfig.HOST_HTML5;//H5 web 页面服务

    //卡友论坛
    public static final String DRIVER_BBS = BuildConfig.HOST_DRIVER_BBS;

    /***********************************************************/
    /**
     * 云信相关接口
     */

    // 获取客服信息
    public static final String SERVER_REFRESH_INFO = HOST_BUSINESS + "/crm/createDialog";
    // 刷新token接口
    public static final String SERVER_REFRESH_TOKEN = HOST_BUSINESS + "/crm/refreshToken";
    //退出登录
    public static final String USER_LOGOUT = HOST_BUSINESS + "/tocapp/logout";
    //司机端-我的车辆列表
    public static final String MYCAR_GET_MY_CAR_LIST = HOST_BUSINESS + "/tocapp/driverCarList";
    //添加
    public static final String MYCAR_ADD_CAR = HOST_BUSINESS + "/tocapp/addCar";
    //删除车辆
    public static final String MYCAR_REMOVE_CAR = HOST_BUSINESS + "/tocapp/unBindCar";
    //设置当前车辆
    public static final String MYCAR_SET_CURRENT_CAR = HOST_BUSINESS + "/tocapp/setCurrentCar";
    //车辆详情查询
    public static final String MYCAR_GET_CAR_INFO = HOST_BUSINESS + "/tocapp/carInfo";
    //车辆参数接口
    public static final String MYCAR_GET_CAR_SPECIFICATION = HOST_BUSINESS + "/tocapp/vehicleParameters";
    //修改车牌号
    public static final String MYCAR_MODIFY_CAR_NUMBER = HOST_BUSINESS + "/tocapp/updateCarNum";
    //上报push token
    public static final String PUSH_BIND_TOKEN = HOST_BUSINESS + "/tocapp/uploadIXT";

    //月行程数据查询
    public static final String TRIP_MONTH = HOST_BUSINESS + "/tripAnalysis/queryTripByMonth";
    //月行程数据查询
    public static final String TRIP_DAY = HOST_BUSINESS + "/tripAnalysis/queryTripByDay";
    //地图轨迹查询
    public static final String TRIP_TRACK = HOST_BUSINESS + "/realTimeMonitor/queryTrack";
    //行程轨迹查询
    public static final String TRIP_TRACK_DETIAL = HOST_BUSINESS + "/tripAnalysis/queryTripInfo";

    //更新提醒次数
    public static final String UPDATE_REMINDER_COUNT = HOST_BUSINESS + "/tocapp/updateReminders";
    //是否初次认证 获取统一认证信息
    public static final String AUTH_HAS_AUTH = HOST_BUSINESS + "/goods/getUnifiedUserInfoForGoodsSource";
    //统一认证保存
    public static final String AUTH_SAVE_COMMON = HOST_BUSINESS + "/goods/saveUnifiedUserInfoForGoodsSource";
    //统一认证提交
    public static final String AUTH_UPLOAD_COMMON = HOST_BUSINESS + "/goods/validateUnifiedUserInfoForGoodsSource";
    //货车帮资料保存
    public static final String AUTH_SAVE_HUOCHE = HOST_BUSINESS + "/tocapp/hcbSaveAuthInfo";
    //货车帮资料获取用户信息
    public static final String AUTH_GET_USERINFO_HUOCHE = HOST_BUSINESS + "/tocapp/hcbGetAuthInfo";
    public static final String AUTH_GET_AUTH_USERINFO = HOST_BUSINESS + "/tocapp/userReviewInfo/";
    //货车帮资料提交
    public static final String AUTH_UPLOAD_HUOCHE = HOST_BUSINESS + "/tocapp/validateUserInfoTruck";
    //陆鲸资料保存
    public static final String AUTH_SAVE_LUJING = HOST_BUSINESS + "/tocapp/saveUserBaseInfo";
    //陆鲸获取用户信息
    public static final String AUTH_GET_USERINFO_LUJING = HOST_BUSINESS + "/tocapp/getUserInfoStatus";
    //陆鲸上报认证
    public static final String AUTH_UPLOAD_LUJING = HOST_BUSINESS + "/tocapp/validateUserInfo";

    /**
     * 服务预约相关
     */
    // 获取省份列表 --
    public static final String SUBSCRIBE_PROVINCE = HOST_BUSINESS + "/tocapp/areaCondition";
    // 获取服务站列表 --
    public static final String SUBSCRIBE_SERVERSTATIONS = HOST_BUSINESS + "/tocapp/queryStation";
    // 获取服务站详情(根据服务站id) --
    public static final String SUBSCRIBE_SERVERSTATIONINFO = HOST_BUSINESS + "/tocapp/stationDetail";
    // 查询我的评价 --
    public static final String SUBSCRIBE_MYRATE = HOST_BUSINESS + "/tocapp/myEvaluation";
    // 查询服务站评价 --
    public static final String SUBSCRIBE_SERVERSTATIONRATE = HOST_BUSINESS + "/tocapp/stationEvaluationList";
    // 获取服务站项目 --
    public static final String SUBSCRIBE_APPOINTMENT_ITEMLIST = HOST_BUSINESS + "/tocapp/queryCommonParamList";
    // TODO: 2017/9/20 0020 重点修改 新建预约
    // 新建预约
    public static final String SUBSCRIBE_NEW_SUBSCRIBE = HOST_BUSINESS + "/tocapp/serviceReservation";
    // 上传文件
    public static final String SUBSCRIBE_POSTFILE = HOST_FILE_UPLOAD + "/fsevice/uploadFile";
    // 预约 - 获得我的预约列表 --
    public static final String SUBSCRIBE_GET_MY_SUBSCRIBE = HOST_BUSINESS + "/tocapp/myReservationList";
    // TODO: 2017/9/20 0020 重点修改 预约详情
    // 预约 - 查看预约详情
    public static final String SUBSCRIBE_GET_SUBSCRIBE_INFO = HOST_BUSINESS + "/tocapp/reservationDetails";
    // 预约 - 确认订单接口
    public static final String SUBSCRIBE_SUBSCRIBE_CONFIRM = HOST_BUSINESS + "/servicestation/wo/confirmWo";
    // 预约 - 添加服务评价 --
    public static final String SUBSCRIBE_SUBSCRIBE_ADDRATE = HOST_BUSINESS + "/tocapp/serviceEvaluation";
    // 预约 - 删除服务评价 --
    public static final String SUBSCRIBE_SUBSCRIBE_DELETERATE = HOST_BUSINESS + "/tocapp/deleteMyEvaluation";
    // 取消预约 --
    public static final String SUBSCRIBE_CANCEL_SUBSCRIBE = HOST_BUSINESS + "/tocapp/cancelReservation";
    // 查询取消原因 --
    public static final String SUBSCRIBE_GETCANCELREASON = HOST_BUSINESS + "/tocapp/getCancleReason";
    // 查询关闭原因 --
    public static final String SUBSCRIBE_GETCLOSEREASON = HOST_BUSINESS + "/tocapp/getCloseReason";

    // TODO: 2017/9/20 0020 核对30分钟
    // 催单
    public static final String SUBSCRIBE_URGE_SUBSCRIBE = HOST_BUSINESS + "/tocapp/urgeReservation";
    // 救援人员位置 --
    public static final String SUBSCRIBE_HELP_LOCATION = HOST_BUSINESS + "/tocapp/getRescuerPosition";
    // 获取车辆位置
    public static final String SUBSCRIBE_SUBSCRIBE_GETPOSITION = HOST_BUSINESS + "/tocapp/getVehiclePosition";


    //货源 - 获得列表
    public static final String GOODS_SOURCE_GET_LIST = HOST_BUSINESS + "/tocapp/goodsSourceList";
    //获得 关注路线
    public static final String GOODS_SOURCE_QUERY_FOLLOW_LINE = HOST_BUSINESS + "/tocapp/queryFollowLine";
    //货源信息始发地目的地选择列表
    public static final String GOODS_SOURCE_GOODS_AREA_LIST = HOST_BUSINESS + "/tocapp/goodsAreaList";
    //货源信息 - 获得车型
    public static final String GOODS_SOURCE_GET_CAR_TYPE_LIST = HOST_BUSINESS + "/operate/common/basedata?code=A026&type=A";
    //货源信息 - 获得车长
    public static final String GOODS_SOURCE_GET_CAR_LENGTH_LIST = HOST_BUSINESS + "/operate/common/basedata?code=A027&type=A";
    //货源信息 - 添加关注路线
    public static final String GOODS_SOURCE_ADD_FOLLOW_LINE = HOST_BUSINESS + "/tocapp/addFollowLine";
    //货源信息 - 删除关注路线
    public static final String GOODS_SOURCE_DELETE_FOLLOW_LINE = HOST_BUSINESS + "/tocapp/delFollowLine";
    //根据关注线路获取货源
    public static final String GOODS_SOURCE_QUERY_GOODS_BY_LINE = HOST_BUSINESS + "/tocapp/queryGoodsByLine";
    //获得货源认证的认证状态（是否认证通过）
    public static final String GET_GOODS_SOURCE_AUTH_STATUS = HOST_BUSINESS + "/tocapp/userAuth";
    //货源消息推送开启关闭
    public static final String SET_GOODSOURCE_PUSH_ALERT_ENABLE = HOST_BUSINESS + "/tocapp/messageOpenOrShut";
    //获得 货源消息推送开启关闭,消息推送开关验证
    public static final String GET_GOODSOURCE_PUSH_ALERT_ENABLE = HOST_BUSINESS + "/tocapp/messageSwitch";
    //路线详情接口
    public static final String MYCAR_GET_FLAG_ROUTE_INFO = HOST_BUSINESS + "/tocapp/routeInfo";
    //标杆详情
    public static final String MYCAR_GET_MODEL_INFO = HOST_BUSINESS + "/tocapp/viewStandard";
    //获取标杆 总数 数据
    public static final String MYCAR_GET_MODEL_SUM_DATA = HOST_BUSINESS + "/realTimeMonitor/queryShareSummary";
    /**
     * 行车管家
     */
    //获取车辆保养信息
    public static final String GET_MAINTAIN_DETAIL = HOST_BUSINESS + "/tocapp/queryMaintainReminder";
    //获取自定义保养列表
    public static final String GET_CUSTOM_MAINTAIN_LIST = HOST_BUSINESS + "/carmachine/maintainList";
    //获取自定义保养详情
    public static final String GET_CUSTOM_MAINTAIN_DETAIL = HOST_BUSINESS + "/tocapp/maintenance/info";
    //新增自定义保养
    public static final String ADD_CUSTOM_MAINTAIN = HOST_BUSINESS + "/tocapp/maintenance/add";
    //删除自定义保养
    public static final String DELETE_CUSTOM_MAINTAIN = HOST_BUSINESS + "/tocapp/maintenance/delete";
    //修改自定义保养
    public static final String UPDATE_CUSTOM_MAINTAIN = HOST_BUSINESS + "/tocapp/maintenance/edit";
    //更改自定义保养状态
    public static final String ALTER_CUSTOM_MAINTAIN_STATE = HOST_BUSINESS + "/tocapp/maintenance/editStatus";
    //获取全部保养项目列表
    public static final String GET_MAINTAIN_LIST_ALL = HOST_BUSINESS + "/tocapp/maintain/queryMaintainItemList";
    //获取保养项
    public static final String GET_MAINTAIN_LIST_BY_ID = HOST_BUSINESS + "/tocapp/maintenance/item/queryByMaintenanceId";
    //删除保养项
    public static final String DELETE_MAINTAIN_LIST_BY_ID = HOST_BUSINESS + "/tocapp/maintenance/item/deleteItemByMaintenanceId";
    //添加保养项
    public static final String ADD_MAINTAIN_LIST_BY_ID = HOST_BUSINESS + "/tocapp/maintenance/item/addItemListByMaintenanceId";
    //获得货源详情
    public static String GET_GOODS_SOURCE_DETAIL = HOST_BUSINESS + "/tocapp/hcbGetCargoDetail";
    /**
     * 用户登录信息相关
     */
    public static String USER_LOGIN = HOST_BUSINESS + "/tocapp/login";
    /**
     * 用户登录信息相关
     */
    public static String USER_LOGIN_PHONE = HOST_BUSINESS + "/tocapp/quickLogin";
    /**
     * 获取用户信息
     */
    public static String USER_GET_USERINFO = HOST_BUSINESS + "/tocapp/getUserInfo";
    /**
     * 获取用户信息
     */
    public static String WANSHANINFO = HOST_BUSINESS + "/tocapp/modifyUserInfnOfHeavy";
    /**
     * 获取用户头像
     */
    public static String USER_GET_USERPHOTO = HOST_USER_CENTER + "/user/queryPic";
    /**
     * 上传用户头像
     */
    public static String USER_UPLOAD_USERPHOTO = HOST_USER_CENTER + "/user/uploadPic";
    /**
     * 获取短信验证码字符串
     */
    public static String GET_SMS_CODE = HOST_USER_CENTER + "/user/getValidateCode";
    /**
     * 修改密碼
     */
    public static String USER_MODIFY_USER_PWD = HOST_USER_CENTER + "/user/updatePassword";
    /**
     * 修改用户信息
     */
    public static String USER_MODIFY_USER = HOST_BUSINESS + "/tocapp/modifyUserInfo";
    /**
     * 注册
     */
    public static String USER_RGIST = HOST_BUSINESS + "/tocapp/register";
    //修改手机绑定-发送短信
    public static String USER_REBIND_SENDSMS = HOST_USER_CENTER + "/user/applyBindMobile";
    public static String USER_REBIND = HOST_BUSINESS + "/tocapp/bindMobile";
    //修改密码-发送短信
    public static String USER_FORGETPWD_SENDSMS = HOST_USER_CENTER + "/user/findPasswordBySms";
    //修改密码-验证验证码
    public static String USER_FORGETPWD_CHECKSMS = HOST_USER_CENTER + "/user/validateFindPasswordSms";
    //修改密码-重置密码
    public static String USER_FORGETPWD_RESETPWD = HOST_USER_CENTER + "/user/resetPassword";
    /**
     * 修改绑定手机号
     */
    public static String USER_REBIND_PHONE = HOST_BUSINESS + "/tocapp/bindMobile";
    /**
     * 短信验证码
     */
    public static String USER_GET_MESSAGE = HOST_USER_CENTER + "/user/sendSms";
    /**
     * 校验短信验证码
     */
    public static String USER_CHECK_MESSAGE = HOST_USER_CENTER + "/user/validateFindPasswordSms";
    /**
     * 忘记密码重置
     */
    public static String USER_RESET_PASSWORD = HOST_USER_CENTER + "/user/resetPassword";
    /**
     * 查询用户头像
     */
    public static String USER_GET_PHOTO = HOST_USER_CENTER + "/user/queryPicById";
    /**
     * 上传用户头像
     */
    public static String USER_REPORT_PHOTO = HOST_USER_CENTER + "/user/uploadPic";
    /**
     * 获取图形验证码
     */
    public static String USER_GET_PICTURE_YZM = HOST_USER_CENTER + "/user/getCaptcha";
    /**
     * 校验图形验证码
     */
    public static String USER_CHECK_PICTURE_YZM = HOST_USER_CENTER + "/user/checkCaptcha";
    /**
     * 校验手机号是否可用
     */
    public static String USER_CHECK_PHONE_NUMBER = HOST_USER_CENTER + "/user/checkMobile";
    /**
     * 首页轮播图 --
     */
    public static String HOME_BANNER = HOST_BUSINESS + "/tocapp/getBannerInfo";
    /**
     * 一键呼救
     */
    // TODO: 2017/9/20 0020 核对入参name,返回的carType字段
    public static String SOS_NUMBERS = HOST_BUSINESS + "/tocapp/queryUrgentCall";
    // 查询优惠券数量 --
    public static String COUPON_GETCOUNT = HOST_BUSINESS + "/tocapp/activity/couponNum";
    // 获取优惠券列表 --
    public static String COUPON_GETCOUPONLIST = HOST_BUSINESS + "/tocapp/activity/couponList";
    // 获取优惠券详情 --
    public static String COUPON_GETCOUPONINFO = HOST_BUSINESS + "/tocapp/activity/couponDetail";
    // 获取服务商城市 --
    public static String COUPON_GETCITY = HOST_BUSINESS + "/tocapp/activity/providerCity";
    // 获取推荐服务商 --
    public static String COUPON_GETRECOMMEND = HOST_BUSINESS + "/tocapp/activity/recommend";
    // 逆地理 --
    public static String COUPON_GETCITYIDBYLOCATION = HOST_BUSINESS + "/tocapp/activity/inverse";
    // 获取服务商列表 --
    public static String COUPON_GETPROVIDERLIST = HOST_BUSINESS + "/tocapp/activity/providerList";
    // 消费结果 --
    public static String COUPON_GETRECORDDETAIL = HOST_BUSINESS + "/tocapp/activity/recordDetail";
    // 消费记录列表 --
    public static String COUPON_GETRECORDLIST = HOST_BUSINESS + "/tocapp/activity/recordList";

    /**
     * 货车导航
     */
    public static String NAVITRUCK_UPLOAD = "http://wdservice.mapbar.com/appstorewsapi/checkexistlist/21?package_name=com.mapbar.android.navitruck&ck=d5ba9b4073c944b6ad55266aab9a7807";

    /**
     * 积分部分
     */
    // 获取未完成的积分数和总积分
    public static String JIFEN_QUERYSCORE = HOST_BUSINESS + "/accumulate/queryUnfinishScoreTaskNum";

    //积分商城
    public static String JIFEN_SHOPPING = HOST_BUSINESS + "/accumulate/duiba/autoLogin";

    /**
     * 消息部分
     */
    // 获取未读消息数量
    public static String MESSAGE_GET_UNREADMESSAGECOUNT = HOST_BUSINESS + "/tocapp/queryUnreadMessageCount";
    public static String MESSAGE_SET_MESSAGEREAD = HOST_BUSINESS + "/tocapp/setMessageRead";
}
