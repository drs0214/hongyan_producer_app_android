##################################
最后修改日期： 2017年08月14日15:56:57 张云飞
原理： android ,ios在app注入一段脚本js，这个脚本运行后，会有 window.mobileAgent 这个对象，
      我们为这个而对象声明一些方法，供js调用
      这个脚本即是我们的中间层，它是app和js之间的桥梁
##################################

###########################################################################
下文是供  原生app 调用 js 的方法约定

#基础方法： 调用一个window的 function,并获得返回值回调给原生。使用 callWindowFun
注意：该方法仅用于app原生调用

    mobileAgent.callWindowFun("方法名","参数"，"任意TAG");
    方法执行后 原生会收到一个 callWindowFun_callback 的回调消息，消息里包含执行结果
    
    
#处理 android手机的 返回键
 在js 中定义 一个处理back键事件的方法，返回boolean值类型。
    如果true表示js处理了 back键了，app无需后续处理
    如果false表示，app可以继续处理（比如关闭页面)
        
        function onBackKeyDown()   { return false;}

##back键盘处理 执行步骤：
    1.在 app中处理 onkeydown事件，然后通过  mobileAgent.callWindowFun("onBackKeyDown",null,"TAG-xxxxx") 传递事件给js
    2.这时，将js中的 onBackKeyDown 方法，我们需要在js中实现这个方法。
            如果有dialog在出现，就关闭dialog, 返回true
            如果有其他返回处理，则处理后返回 true
            如果没有处理，返回false
    3.由于是通过  mobileAgent.callWindowFun  发起的调用，调用成功后，会传递结果给 原生app
            在 simpleInjectJsMobileAgent 中的 CallWindowFunCallbacActionHandler 会触发
                    public void onCallWindowFunCallbac(JSONArray result, String funName, String requestCode) {
                    }
                    上面这个方法会被触发。我们判断 funName 如果等于 onBackKeyDown 判断返回值即可决定后续处理（比如关闭页面）

###########################################################################
下文是供 js 调用 原生app的方法约定

#是否运行在app环境, 判断window.mobileAgent存在
//如果在app中运行，注入过js，才有这个对象，android和ios会在app注入这个js

    typeof window.mobileAgent != "undefined"

#3关闭当前窗口接口

    window.mobileAgent.closeWindow();


#app返回上一页

    window.mobileAgent.goBack();


#打开新的页面，传递一个url 和标题
      
        window.mobileAgent.openNewWindow('http://www.baidu.com', '百度一下');

#分享页面接口

     title 分享标题
     content 分享标题
     thumbnailUrl 缩略图url
     href 分享链接
 
    window.mapbar.sharePage(title, content, thumbnailUrl, href);

    window.mapbar.sharePage('点我领金币','这你都信，我就是个广告，哈哈', '可以填可以不填', 'http://mapbar.com/xxx.xxx/xx');

    
#在app中，遇到错误时，显示 错误页
    
        window.mobileAgent.showErrorView(errCode,errText);
    
        参数说明：
            errCode  错误码，可传0表示默认，整数
            errText  汉字描述,字符串
     
#由app发送网络请求
    
        window.mobileAgent.openURL("http://www.baidu.com",{method:"get"},function(err,res){
            //dosometing
        });
    
        参数说明：
            第1个是 网址
            第2个参数是 参数。  json类型，可以自定义参数
            第3个参数是 回调对象，function类型，它的参数 err, res都是json对象。
            
            err 参数的json格式 {status:"xxx",message:"yyyy"}
      
#跳转到指定名称的页面

方法签名：mobileAgent.goPage(页面名称，参数)
    
用于跳转到和app约定好的页面， 传入名称和参数
    
    mobileAgent.goPage("renzheng",{para1:"value1"});

#Umeng按钮点击统计
    window.mobileAgent.onUmengEvent(友盟事件名称);
    
#发送 Http 请求中的错误给app

当服务的http响应出现异常时发送
第1个参数：表示 messageCode
第2个参数：messageText
第3个参数：http的状态码

    mobileAgent.sendHttpErrorToApp(509,"登录失败",200);