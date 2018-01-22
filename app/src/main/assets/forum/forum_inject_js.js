/*
卡友论坛 js注入脚本，客户端将本文件注入到页面中。
最后修改日期： 2017-08-24 10:20:02
*/

window.mapbar = window.mapbar || {};

(function (mapbar, window) {

    var _postMessage = function (action, params, callBack) {
        console.log('window.mobileAgent is: '+window.mobileAgent);
        if (typeof window.mobileAgent == "undefined") return;
        if (typeof window.mobileAgent.postMessage == "undefined") return;
        window.mobileAgent.postMessage(JSON.stringify({
            action : action,
            params : params,
            callBack : callBack
        }));
    };

    mapbar.closeWindow = function () {
        _postMessage('close');
    };

    mapbar.goBack = function () {


        _postMessage('back');
    };

    mapbar.sharePage = function (title, content, imagePath, url) {
        var callBackMethodName = 'callBack' + Math.random();
        window.mobileAgent[callBackMethodName] = function (err, rs) {
            callBack(err, rs);
            window.mobileAgent[callBackMethodName] = null;
            callBackMethodName = null;
        };

        _postMessage(
            'share',
            {
                title: title, content: content, imagePath: imagePath, url: url
            },
            callBackMethodName
        )
    };

    mapbar.fetchLocation = function (callBack) {
        var callBackMethodName = 'callBack' + Math.random();
        window.mobileAgent[callBackMethodName] = function (err, rs) {
            console.log("fetchLocation err="+err+", rs="+rs);
            callBack(err, rs);
            window.mobileAgent[callBackMethodName] = null;
            callBackMethodName = null;
        };
        _postMessage(
            'location',
            null,
            callBackMethodName
        )
    };

    mapbar.fetchImage = function (callBack) {
        var callBackMethodName = 'callBack' + Math.random();
        window.mobileAgent[callBackMethodName] = function (err, rs) {
            console.log("fetchLocation err="+err+", rs="+rs);
            callBack(err, rs);
            window.mobileAgent[callBackMethodName] = null;
            callBackMethodName = null;
        };
        _postMessage(
            'image',
            null,
            callBackMethodName
        )
    };

    mapbar.callWindowFun = function (funName, args, callBackMethodName) {
        var rs = null;
        if(window[funName]) {
            rs = window[funName].apply(null, args);
        }
        _postMessage(
            'callWindowFun',
            [rs],
            callBackMethodName
        );
    };


    mapbar.imgSwiper = function (imageArray,selectedIndex) {
        _postMessage(
            'imgSwiper',
            {
                imageArray:imageArray,
                selectedIndex:selectedIndex
            }
        )
    };
})(window.mapbar || {}, window);
