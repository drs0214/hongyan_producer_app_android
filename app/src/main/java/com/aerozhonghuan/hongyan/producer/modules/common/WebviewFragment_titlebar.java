package com.aerozhonghuan.hongyan.producer.modules.common;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aerozhonghuan.hongyan.producer.framework.base.OnKeyDownAble;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.utils.NetUtils;
import com.aerozhonghuan.hongyan.producer.widget.ErrorView;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hybrid.SimpleInjectJsMobileAgent;
import com.aerozhonghuan.hybrid.XWebView;
import com.aerozhonghuan.hybrid.XWebViewListener;
import com.aerozhonghuan.hybrid.actionhandler.CallWindowFunCallbacActionHandler;
import com.aerozhonghuan.hybrid.actionhandler.OpenNewWindowActionHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;

/**
 * web页面的 fragment
 * 单页应用，仅适用 单页h5页面
 */
public class WebviewFragment_titlebar extends TitlebarFragment implements OnKeyDownAble {
    private static final String TAG = WebviewFragment_titlebar.class.getSimpleName();

    private LinearLayout rootView;
    private XWebView mWebView;
    private ErrorView errorView;
    private String url;
    private SimpleInjectJsMobileAgent theMobileAgent;
    private MyBackkeydownHelper mMyBackkeydownHelper;
    private boolean showProgressBarInWebView = true;
    private TitleBarView titlebar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            if (Build.VERSION.SDK_INT >= 21) {
                getActivity().getWindow().setStatusBarColor(0xFF169ADA);
            }
            titlebar = new TitleBarView(getActivity());
            mWebView = new XWebView(getContext(), null, isShowProgressBarInWebView());
            //没有网络则优先读取本地缓存
            boolean networkConnected = NetUtils.isConnected(getContext());
            if (!networkConnected) {
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.VERTICAL);
            rootView.addView(titlebar, new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            titlebar.setTitle("消息中心");
            titlebar.enableBackButton(false);
            double height = getStatusBarHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titlebar.getLayoutParams();
            params.setMargins(0, (int) height, 0, 0);
            titlebar.setLayoutParams(params);
            //配置 注入js代理对象
            theMobileAgent = onConfigMobildAgentObject();
            if (theMobileAgent != null)
                mWebView.addMobileAgentObject(theMobileAgent, "mobileAgent");
            mWebView.setWebViewListerner(onInitWebviewListener());

            if (getArguments() != null) {//尝试从 参数中读取url字段
                //配置指定的url,如果未指定，则不加载
                String tmpUrl = null;
                tmpUrl = getArguments().getString("url");
                if (!TextUtils.isEmpty(tmpUrl))
                    url = tmpUrl;
                //title
                String title = getArguments().getString("title");
                if (!TextUtils.isEmpty(title)) {
                    getTitlebar().setTitle(title);
                }
                if (!TextUtils.isEmpty(tmpUrl))
                    loadURL(tmpUrl);
            }
            onInitData(getArguments());
        }
        return rootView;
    }

    private double getStatusBarHeight() {
        int statusBarHeight = 0;
        Resources res = getActivity().getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;

    }

    protected void onInitData(Bundle arguments) {
    }

    /**
     * 当 初始化一些callback
     *
     * @return
     */
    @NonNull
    protected XWebViewListener onInitWebviewListener() {
        return new XWebViewListener() {
            @Override
            public void onReceiveError(int errorCode, String description, String failingUrl) {
                showErrorView();
            }

            @Override
            public void onReceiveTitle(String title) {
                super.onReceiveTitle(title);
            }


            @Override
            public void onPageFinished(String url) {
                super.onPageFinished(url);
                getWebView().clearHistory();//每次页面记载成功，清理历史堆栈，适用单页应用
            }
        };
    }


    protected XWebView getWebView() {
        return mWebView;
    }

    public ErrorView getErrorView() {
        return errorView;
    }

    /**
     * 当配置要显示的url时
     */
    protected void loadURL(String url1) {
        LogUtil.d(TAG, "loadURL ulr=" + url1);
        setUrl(url1);
        if (TextUtils.isEmpty(getUrl())) {
            mWebView.loadUrl(XWebView.ABOUT_BLANK);
            return;
        }
        mWebView.loadUrl(getUrl());
    }

    /**
     * 创建 MobildAgentObject
     *
     * @return
     */
    protected SimpleInjectJsMobileAgent onConfigMobildAgentObject() {

        SimpleInjectJsMobileAgent simpleInjectJsMobileAgent = new SimpleInjectJsMobileAgent();
        //打开新窗口的处理
        simpleInjectJsMobileAgent.setOpenNewWindowActionHandler(new OpenNewWindowActionHandler() {
            @Override
            public void onOpenNewWindow(String url, String title) {
                startActivity(new Intent(getContext(), WebviewActivity.class).putExtra("url", url).putExtra("title", title));
            }
        });
        //处理调用js方法后的回调
        mMyBackkeydownHelper = new MyBackkeydownHelper(this);
        simpleInjectJsMobileAgent.addCallWindowFunCallbacActionHandler(mMyBackkeydownHelper);

        return simpleInjectJsMobileAgent;
    }

    protected void showErrorView() {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                if (errorView == null) {
                    errorView = onCreateErrorView();
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                    rootView.addView(errorView, params);
                }
                mWebView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 当创建errorview 时
     *
     * @return
     */
    protected ErrorView onCreateErrorView() {
        ErrorView errorView1 = new ErrorView(getContext());
        errorView1.setNoNetworkState();
        errorView1.setOnConfirmButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissErrorView();
                mWebView.loadUrl(getUrl());
            }
        });
        return errorView1;
    }

    /**
     * 让 webview可见
     */
    protected void showWebViewVisible() {
        mWebView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    protected void dismissErrorView() {
        if (errorView == null) return;
        errorView.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
    }

    /**
     * errorview 是否可见
     *
     * @return
     */
    protected boolean isShowingErrorView() {
        return errorView != null && errorView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        if (rootView != null)
            rootView = null;
        if (theMobileAgent != null) {
            theMobileAgent.release();
            theMobileAgent = null;
        }
        super.onDestroy();
    }

    /**
     * 点击返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShowingErrorView() || mWebView == null || "about:blank".equals(mWebView.getUrl()) || !mWebView.canGoBack()) {
                return goback();
            }
            if (mMyBackkeydownHelper != null && isNotifyBackKeyDownToJs())
                if (mMyBackkeydownHelper.notifyBackKeyDownToJs())//通知backdown给js 成功返回true, 失败，则执行back处理
                    return true;
            return goback();
        }
        return false;
    }

    /**
     * 判断 当前情形，是否做返回处理
     *
     * @return 是否处理过
     */
    protected boolean goback() {
        if (!isShowingErrorView() && mWebView != null && !"about:blank".equals(mWebView.getUrl()) && mWebView.canGoBack()) {
            mWebView.goBack();//返回上一页面
            return true;
        } else {
            if (getActivity() != null && !getActivity().isFinishing())
                getActivity().finish();
            return true;
        }
    }


    /**
     * 是否需要 通知backkey给js
     *
     * @return
     */
    protected boolean isNotifyBackKeyDownToJs() {
        return false;
    }

    /**
     * 控制progressbar显示
     *
     * @return
     */
    public boolean isShowProgressBarInWebView() {
        return showProgressBarInWebView;
    }


    /**
     * 返回键的自定义处理
     */
    public static class MyBackkeydownHelper implements CallWindowFunCallbacActionHandler {
        private int requestCode1 = 0;
        private WeakReference<WebviewFragment_titlebar> webviewFragmentWeakReference;

        public MyBackkeydownHelper(WebviewFragment_titlebar webviewFragmentWebView) {
            this.webviewFragmentWeakReference = new WeakReference<WebviewFragment_titlebar>(webviewFragmentWebView);
        }

        @Override
        public void onCallWindowFunCallback(JSONArray result, String funName, String requestCode, int responseCode) {
            LogUtil.d(TAG, "收到 callWindowFun 的回调 requestCode=" + requestCode);
            if ("onBackKeyDown".equals(funName)) {//如果js没处理  back键事件 则app处理，如果用户做了处理，则app什么也不做
                if (responseCode == CallWindowFunCallbacActionHandler.RESPONSE_CODE_FUNC_NOT_EXIST) {
                    LogUtil.d(TAG, "指定的JS方法不存在 " + funName);
                } else if (result != null && result.length() > 0) {
                    try {
                        if ("true".equals(result.getString(0))) {
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                final WebviewFragment_titlebar webviewFragment = webviewFragmentWeakReference == null ? null : webviewFragmentWeakReference.get();
                //切换到UI线程
                if (webviewFragment != null)
                    webviewFragment.runOnUI(new Runnable() {
                        @Override
                        public void run() {
                            if (webviewFragment != null)
                                webviewFragment.goback();
                        }
                    });
            }
        }

        /**
         * 通知backkey给js
         */
        public boolean notifyBackKeyDownToJs() {
            WebviewFragment_titlebar webviewFragment = webviewFragmentWeakReference == null ? null : webviewFragmentWeakReference.get();
            if (webviewFragment == null) return false;
            if (webviewFragment.isShowingErrorView() || webviewFragment.getWebView() == null || "about:blank".equals(webviewFragment.getWebView().getUrl()) || webviewFragment.getWebView().canGoBack()) {
                return false;
            }
            if (webviewFragment.getWebView().getProgress() != 100) {//正在加载中
                return false;
            }
            //因为要处理 h5也出现的dialog关闭，这里自定义back键盘处理
            requestCode1++;
            LogUtil.d(TAG, "准备发起 callWindowFun 调用，requestCode=" + requestCode1);
            StringBuilder sb = new StringBuilder();
            sb.append("if(typeof window.mobileAgent != 'undefined' && typeof window.mobileAgent.isRunOnApp != 'undefined' && window.mobileAgent.isRunOnApp() && typeof window.onBackKeyDown != 'undefined'){");
            sb.append(String.format("window.mobileAgent.callWindowFun('onBackKeyDown',null,'%s');", requestCode1));
            sb.append("}else{");

            sb.append("};");

            webviewFragment.getWebView().invokeJsScript(sb.toString());
            return true;
        }

    }


    /**
     * 在UI线程执行
     *
     * @param runnable
     */
    protected void runOnUI(Runnable runnable) {
        if (getActivity() == null || getActivity().isFinishing()) return;
        if (rootView == null) return;
        rootView.post(runnable);
    }

    /**
     * 获得 上次的 url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
