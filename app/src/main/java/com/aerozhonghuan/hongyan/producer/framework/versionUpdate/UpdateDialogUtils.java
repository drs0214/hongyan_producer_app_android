package com.aerozhonghuan.hongyan.producer.framework.versionUpdate;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.aerozhonghuan.hongyan.producer.utils.NetUtils;
import com.aerozhonghuan.hongyan.producer.utils.SystemUtil;


/**
 * Created by zhangyh on 2016/12/15 0015.
 * 更新对话框
 */

public class UpdateDialogUtils {
    public static Dialog getUpdateDialog(final Context context, final AppInfo appInfo, final FileBreakpointLoadManager fileBreakpointLoadManager, boolean isUpdate) {

        final Dialog dialog = new Dialog(context, R.style.myDialog);
        View view = View.inflate(context, R.layout.layout_dialog_update, null);
        TextView tv_update = (TextView) view.findViewById(R.id.tv_update);
        TextView tv_no = (TextView) view.findViewById(R.id.tv_no);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//        tv_title.setText(appInfo.getDescription());
        tv_content.setText(appInfo.getUpdate_desc());
        //立即更新
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (SystemUtil.isServiceWork(BreakpointLoadService.class.getName(), context)) {
                    if (UpdateAPPConstants.file_flag == UpdateAPPConstants.UPDATE_APP) {
                        Toast.makeText(context, "正在下载中,请稍后", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "货车导航正在下载中,请稍后进行升级", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                //获取当前网络状态
                if (!NetUtils.isConnected(MyAppliation.getApplication())) {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (!NetUtils.isWifi(MyAppliation.getApplication())) {
                    final Dialog changeNetDialog = getNetAlertDialog(context, appInfo, fileBreakpointLoadManager, dialog, false);
                    if (!changeNetDialog.isShowing())
                        changeNetDialog.show();
                } else {
                    dialog.dismiss();
                    UpdateAPPConstants.file_flag = UpdateAPPConstants.UPDATE_APP;
                    fileBreakpointLoadManager.startUpdate(appInfo);
                }
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        return dialog;
    }

    /**
     * 移动网络对话框
     *
     * @return
     */
    public static Dialog getNetAlertDialog(final Context context, final AppInfo appInfo, final FileBreakpointLoadManager fileBreakpointLoadManager, final Dialog dialog, final boolean isReStart) {
        final Dialog changeNetDialog = new Dialog(context, R.style.myDialog);
        View changeNetView = View.inflate(context, R.layout.layout_dialog_choosenet, null);
        TextView tv_yes = (TextView) changeNetView.findViewById(R.id.tv_yes);
        TextView tv_cancel = (TextView) changeNetView.findViewById(R.id.tv_cancel);
        //使用移动网络,下次不提醒
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNetDialog.dismiss();
                if (isReStart) {
                    fileBreakpointLoadManager.continueUpdate(context);
                } else {
                    fileBreakpointLoadManager.startUpdate(appInfo);
                }
            }
        });
        //返回上一对话框
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNetDialog.dismiss();
                if (dialog != null) {
                    dialog.show();
                }
            }
        });
        changeNetDialog.setContentView(changeNetView);
        return changeNetDialog;
    }
}
