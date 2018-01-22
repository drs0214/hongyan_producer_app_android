#一些常用 注解

    标记为不警告"消除对已检查(checked)异常的检查"    @SuppressWarnings("unchecked")

#Activity继承关系
    建议所有的activity继承 BaseActivity
    建议所有的Fragment继承 BaseFragment
    基本视图，一个Actvity至少包含一个Fragment

#记录日志：

        Log.e(TAG, String.format("进入默认异常处理器： httpCode=%s,ex=%s", httpCode, ex1));


#发起网络请求：

      Type t = new TypeToken<List<Demo2Cell>>() {
        }.getType();
        RequestBuilder.with(getActivity())
                .URL(Apis.Cab_Urls.GET_BOX_FREE_NEWS)
                .para("cabinet_code", "1412345678")
                .onSuccess(new CommonCallback<List<Demo2Cell>>(t) {
                    @Override
                    public void onSuccess(List<Demo2Cell> result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.get(0));
                        alert("!!成功" + result.get(0));
                    }
                })
                .excute();

    更多使用方法请参考：https://github.com/vir56k/oknet

#操作数据库
数据库操作我们使用GreenDao,位于 dao Module下，自动生成的代码位于 greendao_gen,切不会被添加到git管理中

具体使用时，请继承自： BaseDao

     DaoSession daoSession = getDaoSession();
     Entity1Dao entity1Dao = daoSession.getEntity1Dao();
     entity1Dao.save(null);

#处理全局 网络请求参数
    请修改：CustomRequestParaInterceptor

#处理全局 网络请求异常
    请修改：CustomDefalutExceptionHandler

#处理 友盟统计
    请使用 UmengAgent

#使用 MVP
    请使用这个package下的类 com.aerozhonghuan.driverapp.foundation.mvp

#使用eventbus
    请使用：EventBusManager

#QuickAdapter 快速实现一个adapter

        listview1 = (ListView) rootview.findViewById(R.id.listview1);
                adapter = new QuickAdapter<MyItem>(getActivity(), R.layout.page_frameworkinfo_page2_item) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, MyItem item) {
                        if (item == null) return;
                        helper.setText(R.id.key, item.key);
                        helper.setText(R.id.value, item.value);
                    }
                };
                listview1.setAdapter(adapter);


#友盟统计事件的常量定义
    写在 UmengEvents 内

#调用系统电话
Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(String.format("tel:%S", numberList.get(position).getTel())));
startActivity(intent);


#爱心推PUSH 正式环境
包名：com.mapbar.qingqi.driver
APP KEY: 2144403187
SECRET: 4zsuTCZUiAaOsaOfJl4eWNff5OjY613j5K6zsiKhVE327B3dmtyzPEqmol6BjTOV

#爱心推PUSH 测试环境
包名：com.mapbar.qingqi.driver
APP KEY: 2014047613
SECRET: OoUjGghfovCpConbJu1gMn1e8MvAUhKla3MkkVBuMJZGdOfMQlvm3FMOddf0fD5A

#消息体的定义
##定义键值对的格式内容，关键字段有：
	消息类别（messageCode）,整数，一个常量来区分不同类型消息
	消息状态（messageStatus）,整数， 比如 0=成功，1=失败，2=添加，3=删除,4=修改等
	标题(title)，字符串，文章的标题
	内容(content)，字符串，文章的文本内容
	推送日期(sevTime)，整数，1499304604000
	附加(extra), 字符串，附加的内容1,扩展json
	是否对用户可见(isUserVisible）,整数，默认0，如果可见则显示在 “消息”列表中。


##消息类别（messageCode）,对应的常量如下：
	1.车辆关联司机
		场景：1. 车主将我添加、取消、变更了某车辆的司机身份，点击消息跳转到我的车辆列表页。
		可选的消息状态有：0=添加、1=取消、2=变更    [可选扩展实现]
		标题为空，内容为json描述的车辆信息		  [可选扩展实现]
	2.车主变更
		场景：2. 我绑定的车辆车主发生变更，点击消息跳转到对应的车辆详情页。
	3.车队路线消息
		场景：3. 车主给我分配、更改、取消了车队线路，点击消息跳转到对应的车辆详情页。
	4.线路提醒
		场景：4. 线路超速（非道路限速），线路详情中设置的最高速度，点击消息跳转到对应的线路详情页。
	5. 车辆故障提醒
		场景：5. 故障（根据运营后台配置的需要提醒给车主的车辆故障信息）：提示车牌号、司机、时间、所在地理位置发生故障、故障码、故障描述，点击该消息不做跳转，在列表页把信息展示全。
	6. 添加车辆提醒
		场景：6. 只有一辆车，即是车辆也是司机，通过司机端添加车辆审请通过\不通过消息提示，点击消息跳转到我的车辆列表页。
	7. 运营消息
		场景：7. 运营消息 标题和内容由运营人员定义，点击跳转到一汽推荐详情页面
	8. 应用升级消息
		场景：8. 应用升级消息 标题和内容由运营人员定义点击跳转到设置页面
	9. 车辆保养到期提醒
		场景：9. 车辆保养到期提醒，点击消息跳转到行车管家页。
	11.预约消息
		场景：11.服务站预约相关推送，请查看“服务站--预约详情页”约束。
    12.优惠券交易结果
        场景: 12.优惠券交易结果推送,
	用不上：
		10. 联系客服有回复信息时（用户当前没有打开联系客户页面时才推送），点击消息跳转到联系客服页。


#android 集成注意事项
##爱心推token
当客户端注册爱心推成功后，可以拿到爱心推的 push token，需用通过网络接口上报给服务器端。
##消息类型
	统一使用透传消息，不是通知消息
##别名
	当用户登录后，绑定别名，使用登录后的user token作为别名
##Tag
	Android 司机版 设备默认加入tag:  APP_EQUAL_DRIVER,DEVICE_TYPE_EQUAL_ANDROID
	更多参考下文：

#Tag定义常量如下：
	public static final short APP_EQUAL_DRIVER = 1001;//app 是 司机版 
	public static final short APP_EQUAL_BOSS = 1002;//app 是 车队版  
	public static final short DEVICE_TYPE_EQUAL_ANDROID = 1101;//android 设备 
	public static final short DEVICE_TYPE_EQUAL_IOS = 1102;//ios 设备


#服务地址定义
	开发联调环境：
      业务服务（沈阳）： http://61.161.238.158:8071/api152/
      用户服务（图吧）： http://119.255.37.167:8808
      预约服务（车厂）： http://61.161.238.158:8071/api152/

    测试环境
      业务服务（沈阳）：http://61.161.238.158:8071/api153/
      用户服务（图吧）：http://119.255.37.167:8808
      预约服务（车厂）：http://61.161.238.158:8071/api153/

    #线上环境
      业务服务（沈阳）：http://jfx.mapbar.com/api
      用户服务（图吧）：http://jfx.mapbar.com/usercenter
      预约服务（车厂）：http://jfx.qdfaw.com:8081/api