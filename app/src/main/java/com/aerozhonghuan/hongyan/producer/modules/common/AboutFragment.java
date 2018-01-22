package com.aerozhonghuan.hongyan.producer.modules.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.utils.AppUtil;

/**
 * Created by dell on 2017/6/28.
 * 关于我们
 */

public class AboutFragment extends TitlebarFragment {
    private View rootView;
    private TextView tvLaw;
    private TextView tvVersion;
    private RelativeLayout rlTel;
    private TextView tvTel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_about, null);
            tvLaw = (TextView) rootView.findViewById(R.id.tv_law);
            tvVersion = (TextView) rootView.findViewById(R.id.tv_version);
            rlTel = (RelativeLayout) rootView.findViewById(R.id.rl_tel);
            tvTel = (TextView) rootView.findViewById(R.id.tv_tel);
            rlTel.setOnClickListener(OnPhoneClick);
            tvLaw.setOnClickListener(OnLawClick);

            tvVersion.setText(getAppVersionName());
        }

        return rootView;
    }

    public String getAppVersionName() {
        if (BuildConfig.DEBUG)
            return AppUtil.getAppBuildVersion(getActivity());
        else
            return AppUtil.getAppVersionName(getActivity());
    }

    private View.OnClickListener OnPhoneClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String phoneNumber = tvTel.getText().toString();
            //跳转到拨号界面，同时传递电话号码
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        }
    };
    private View.OnClickListener OnLawClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ServiceClauseActivity.class);
            startActivity(intent);
        }
    };
}
