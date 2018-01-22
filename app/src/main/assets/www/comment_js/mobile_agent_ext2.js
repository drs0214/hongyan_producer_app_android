/*
最后修改日期： 2017-08-14 15:57:04 张云飞
使用说明请参考《mobile_agent_使用说明.md》
*/

console.log("start load mobile agent js file: mobile_agent_ext2.js");

window.mobileAgent = window.mobileAgent || {};

(function (mobileAgent, window) {

    var _postMessage = function (action, params, callBack) {

        var json  = JSON.stringify({
                                action : action,
                                params : params,
                                callBack : callBack
                            });
        if(typeof window.mobileAgent != "undefined"
                && typeof window.mobileAgent.postMessage != "undefined"){
            window.mobileAgent.postMessage(json);
        }
        if(typeof window.webkit != "undefined"
                && typeof window.webkit.messageHandlers != "undefined"
                && typeof window.webkit.messageHandlers.iOSAgent != "undefined"){
            window.webkit.messageHandlers.iOSAgent.postMessage(json);
        }
    };

    //是否运行在app环境
    mobileAgent.isRunOnApp = function(){
        if(typeof window.mobileAgent != "undefined"
                        && typeof window.mobileAgent.postMessage != "undefined"){
                    return true;
                }

        if(typeof window.webkit != "undefined"
                        && typeof window.webkit.messageHandlers != "undefined"
                        && typeof window.webkit.messageHandlers.iOSAgent != "undefined"){
                    return true;
                }
        return false;
    };

    mobileAgent.closeWindow = function () {
        _postMessage('close');
    };

    mobileAgent.goBack = function () {
        _postMessage('back');
    };

    mobileAgent.sharePage = function (title, content, imagePath, url) {
        var callBackMethodName = 'callBack' + Math.random();
        mobileAgent[callBackMethodName] = function (err, rs) {
            callBack(err, rs);
            mobileAgent[callBackMethodName] = null;
            callBackMethodName = null;
        };

        _postMessage(
            'share',
            {
                title: title, content: content, imagePath: imagePath, url: url
            },
            callBackMethodName
        );
    };

    mobileAgent.callWindowFun = function (funName, args, requestCode) {
        var rs = null;
        var responseCode = -1;
        if(window[funName]) {
            rs = window[funName].apply(null, args);
            responseCode = 0;
        }else{
            responseCode = -1;
        }
        _postMessage(
            'callWindowFun_callback',
            {
                funName:funName,
                result:[rs],
                requestCode:requestCode,
                responseCode:responseCode
            }
        );
    };

    //已废弃 2017-08-14 张云飞
    mobileAgent.showErrorView = function(errCode,errText){
        _postMessage('showErrorView',{
            errCode:errCode,
            errText:errText
        });
    };

    //发送http错误信息给app 2017-08-14 张云飞添加
    mobileAgent.sendHttpErrorToApp = function(errCode,errText,httpCode){
        _postMessage('sendHttpErrorToApp',{
            errCode:errCode,
            errText:errText,
            httpCode:httpCode
        });
    };

    mobileAgent.openNewWindow = function(url,title){
        _postMessage('openNewWindow',{
            url:url,
            title:title
        });
    };

   mobileAgent.openURL = function (url,para,callBack) {
          var callBackMethodName = 'callBack' + Math.random();
          mobileAgent[callBackMethodName] = function (err, rs) {
              callBack(err, rs);
              mobileAgent[callBackMethodName] = null;
              callBackMethodName = null;
          };
          _postMessage(
              'openURL',
              {
                url:url,
                para:para
              },
              callBackMethodName
          );
      };

    mobileAgent.goPage = function(pageName,arguments){
        _postMessage('goPage',{
            pageName:pageName,
            arguments:arguments
        });
    };

    mobileAgent.onUmengEvent = function(eventName){
        _postMessage('umengEvent',{
            eventName:eventName
        });
    };

    mobileAgent.postMessageToApp = function(action, params){
            _postMessage(''+action,params);
    };



})(window.mobileAgent || {}, window);


