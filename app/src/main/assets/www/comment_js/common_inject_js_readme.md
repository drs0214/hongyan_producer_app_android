##################################
最后修改日期： 2017-07-31 12:56:37
原理： android ,ios在app注入一段脚本js，这个脚本运行后，会有 window.mobileAgent 这个对象，
      我们为这个而对象声明一些方法，供js调用
      这个脚本即是我们的中间层，它是app和js之间的桥梁
##################################



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
//原生
方法签名：mobileAgent.goPage(页面名称，参数)
    
用于跳转到和app约定好的页面， 传入名称和参数
    
    mobileAgent.goPage("renzheng",{para1:"value1"});