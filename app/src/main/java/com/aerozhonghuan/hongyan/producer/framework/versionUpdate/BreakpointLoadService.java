package com.aerozhonghuan.hongyan.producer.framework.versionUpdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.StartLoadAppEvent;
import com.aerozhonghuan.hongyan.producer.utils.NetUtils;
import com.aerozhonghuan.hongyan.producer.utils.SafeHandler;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.breakpoint.BreakpointSPUtil;
import com.aerozhonghuan.oknet2.breakpoint.DownloadTask;
import com.aerozhonghuan.oknet2.breakpoint.FileBreakpointLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import static com.aerozhonghuan.hongyan.producer.framework.versionUpdate.UpdateAPPConstants.UPDATE_FOLDER;
import static com.aerozhonghuan.oknet2.breakpoint.FileBreakpointLoader.ispause;


/**
 * Created by zhangyh on 2016/12/2 0031.
 * 断点下载服务
 */
public class BreakpointLoadService extends Service {
    // 通知栏按钮点击事件对应的ACTION
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    //暂停按钮id
    public final static int BUTTON_PAUSE_ID = 101;
    //取消按钮id
    public final static int BUTTON_CANCEL_ID = 102;
    //下载状态
    private final static int DOWNLOAD_FAIL = 103;
    private final static int DOWNLOAD_START = 104;
    private final static int DOWNLOAD_SUCCESS = 105;
    private final static int DOWNLOAD_SUCCESS_MD5FAIL = 106;
    private final static int DOWNLOAD_PROGRESS = 107;
    private static final String TAG = "UpdateApp";
    private static final int NOTIFY_ID = (int) System.currentTimeMillis();
    private File updateFile = null;
    private NotificationManager notificationManager;
    private Notification notification;
    //文件存储
    private File updateDir = null;
    private AppInfo info;
    private RemoteViews contentView;
    private NotificationCompat.Builder builder;
    private int downLoadProgress;
    private MyHandler updateHandler = new MyHandler(this);

    @Override
    public void onCreate() {
        super.onCreate();
        ispause = false;
        EventBusManager.register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusManager.unregister(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initNotify();
        info = (AppInfo) intent.getSerializableExtra("app_info");
        if (info == null)
            return Service.START_NOT_STICKY;
        //创建文件
        if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
            updateDir = new File(UPDATE_FOLDER);
            updateFile = new File(UPDATE_FOLDER, String.format("%s%s.apk", info.getName(),info.getVersion_no()));
        }
        //判断本地是否已经下载过最新版本,并且可用
        checkNativeApp(info);
        return Service.START_NOT_STICKY;
    }

    private Context getContext() {
        return this;
    }

    /**
     * 检测本地是否有已经下载过的版本,并对照MD5
     * 如果有直接进行安装,如果没有开启下载,如果有但是没有下载完进行断点下载
     *
     * @param info
     */
    private void checkNativeApp(AppInfo info) {
        //请求详细信息获取MD5
        String netMD5 = info.getMd5();
        Log.d(TAG, "$$ 网络请求的MD5::" + netMD5);
        boolean isStartLoad = true;
        //如果文件存在
        if (updateFile.exists()) {
            //解析本地文件MD5
            String fileMD5 = MD5Helper.getFileMD5(updateFile);
            Log.d(TAG, "$$ 本地文件md5::" + fileMD5);
            DownloadTask task = BreakpointSPUtil.getTask(getContext());
            //如果md5相同直接安装
            if (netMD5.equalsIgnoreCase(fileMD5)) {
                isStartLoad = false;
                Log.d(TAG, "$$ MD5相同,直接安装");
                installApp();
            }
            //如果连接相同,但是任务未完成,继续下载
            else if (task != null && task.getUrl().equals(info.getApk_path()) && !task.isCompelete()) {
                //继续下载
                Log.d(TAG, "$$ 继续下载");
                startLoad(task);
            }
            //其他情况,重新下载
            else {
                Log.d(TAG, "$$ 本地文件与下载md5不同，删除本地文件");
                updateFile.delete();
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //重新下载,创建一个新的task,并进行存储,下载
                DownloadTask downloadTask = getNewTask(info.getApk_path(), updateFile.getAbsolutePath(), netMD5);
                BreakpointSPUtil.setTask(getContext(), downloadTask);
                startLoad(downloadTask);
            }
        } else {
            //如果文件不存在,创建文件,开启任务,下载
            String url = info.getApk_path();
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            try {
                updateFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DownloadTask downloadTask = getNewTask(url, updateFile.getAbsolutePath(), netMD5);
            BreakpointSPUtil.setTask(getContext(), downloadTask);
            Log.d(TAG, "$$ 开启线程下载apk:");
            startLoad(downloadTask);
        }
        if (isStartLoad && UpdateAPPConstants.file_flag == UpdateAPPConstants.UPDATE_NAV) {
            EventBusManager.post(new StartLoadAppEvent(true));
        }
    }

    /**
     * 获取一个新的下载任务
     *
     * @param url
     * @param filePath
     * @param MD5
     * @return
     */
    private DownloadTask getNewTask(String url, String filePath, String MD5) {
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.setCompelete(false);
        downloadTask.setBreakpoint(0L);
        downloadTask.setFilePath(filePath);
        downloadTask.setMD5(MD5);
        downloadTask.setUrl(url);
        return downloadTask;
    }

    /**
     * 开启下载
     *
     * @param downloadTask
     */
    private void startLoad(final DownloadTask downloadTask) {
//        gettask
        updateHandler.sendEmptyMessage(DOWNLOAD_START);
        downLoadProgress = BreakpointSPUtil.getTask(getContext()).getProgress();
        FileBreakpointLoader.download(getContext(), downloadTask, new FileBreakpointLoader.FileDownloadCallback() {
            @Override
            public void onStart() {
                showCustomProgressNotify("开始下载", downLoadProgress);
            }

            @Override
            public void onProgress(int percent) {
                Log.d(TAG, "下载进度:" + percent);
                downLoadProgress = percent;
                Message message = new Message();
                message.what = DOWNLOAD_PROGRESS;
                message.arg1 = percent;
                updateHandler.sendMessage(message);
            }

            @Override
            public void onError(Exception ex) {
                showCustomProgressNotify("下载中断", downLoadProgress);
                Log.d(TAG, "$$ " + ex.toString());
                //下载失败
                updateHandler.sendEmptyMessage(DOWNLOAD_FAIL);
            }

            @Override
            public void onFinish(File binFile) {
                Log.d(TAG, "service接收到完成回调");
                DownloadTask task = BreakpointSPUtil.getTask(getContext());
                String fileMD5 = MD5Helper.getFileMD5(new File(task.getFilePath()));
                if (fileMD5.equalsIgnoreCase(task.getMD5())) {
                    updateHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                } else {
                    updateHandler.sendEmptyMessage(DOWNLOAD_SUCCESS_MD5FAIL);
                }
                notificationManager.cancel(NOTIFY_ID);
            }

            @Override
            public void onPause() {
                showCustomProgressNotify("暂停", downLoadProgress);
            }

            @Override
            public void onRestart() {
                showCustomProgressNotify("继续下载", downLoadProgress);
            }
        });
    }

    /**
     * 显示自定义的带进度条通知栏
     */
    private void showCustomProgressNotify(String status, int progress) {
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notify_item);
        mRemoteViews.setImageViewResource(R.id.notify_icon, R.mipmap.ic_launcher);
        mRemoteViews.setTextViewText(R.id.notify_title, info.getName());
        mRemoteViews.setTextViewText(R.id.notify_content, progress + "%");
        mRemoteViews.setProgressBar(R.id.notify_pb, 100, progress, false);
        if (ispause) {
            mRemoteViews.setImageViewResource(R.id.btn_pause, R.drawable.btn_notify_play);
        } else {
            mRemoteViews.setImageViewResource(R.id.btn_pause, R.drawable.btn_notify_pause);
        }
        //点击的事件处理
        Intent buttonIntent = new Intent();
        buttonIntent.setAction(ACTION_BUTTON);
        //暂停按钮
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PAUSE_ID);
        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_pause = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_pause, intent_pause);
        //取消按钮
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_CANCEL_ID);
        PendingIntent intent_cancel = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_cancel, intent_cancel);
        builder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(0))
                .setTicker("软件更新");
        notification = builder.build();
        notification.contentView = mRemoteViews;
        notificationManager.notify(NOTIFY_ID, notification);
    }

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_MAX) //设置该通知优先级
                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * 安装app的方法
     */
    private void installApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(updateFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        stopSelf();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeleteOrStopLoadEvent deleteOrStopLoadEvent) {
        LogUtil.d(TAG, "关闭下载服务");
        String msg = deleteOrStopLoadEvent.getMsg();
        if ("deleteDownLoad".equals(msg)) {
            FileBreakpointLoader.pause();
            notificationManager.cancel(NOTIFY_ID);
            if (updateFile.exists()) {
                updateFile.delete();
            }
            stopSelf();
        } else if ("stopDownload".equals(msg)) {
            FileBreakpointLoader.pause();
            notificationManager.cancel(NOTIFY_ID);
            stopSelf();
        }
    }

    private static class MyHandler extends SafeHandler<BreakpointLoadService> {

        public MyHandler(BreakpointLoadService var1) {
            super(var1);
        }

        @Override
        public void handleMessage(Message var1) {
            super.handleMessage(var1);
            BreakpointLoadService innerObject = getInnerObject();
            switch (var1.what) {
                case DOWNLOAD_START: {
//                    Toast.makeText(getContext(), "后台下载中,请稍后...", Toast.LENGTH_LONG).show();
                    break;
                }
                case DOWNLOAD_SUCCESS: {
//                    Log.d(TAG, "$$ 下载完成::判断MD5是否相同");
//                    DownloadTask task = BreakpointSPUtil.getTask(innerObject.getContext());
//                    String fileMD5 = MD5Helper.fileToMD5(new File(task.getFilePath()));
                        Log.d(TAG, "$$ MD5相同,开始安装");
                        innerObject.installApp();

                    break;
                }
                case DOWNLOAD_SUCCESS_MD5FAIL:
                    Log.d(TAG, "$$ 本地文件与下载md5不同，删除本地文件");
                    innerObject.updateFile.delete();
                    try {
                        innerObject.updateFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DownloadTask task = BreakpointSPUtil.getTask(innerObject.getContext());
                    task.setBreakpoint(0L);
                    task.setCompelete(false);
                    innerObject.startLoad(task);
                    break;
                case DOWNLOAD_FAIL:
                    Toast.makeText(innerObject.getContext(), "网络异常", Toast.LENGTH_LONG).show();
                    break;
                case DOWNLOAD_PROGRESS:
                    innerObject.showCustomProgressNotify("下载中...", var1.arg1);
                    break;
            }
        }
    }

    /**
     * 广播监听按钮点击事件
     */
    public static class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_PAUSE_ID:
                        //暂停
                        if (!ispause) {
                            FileBreakpointLoader.pause();
                        }
                        //如果用户确认过移动网络,则继续用移动网络下载
                        else {
                            if (!NetUtils.isConnected(MyAppliation.getApplication())) {
                                Toast.makeText(context, "当前网络未连接", Toast.LENGTH_SHORT).show();
                            } else {
                                FileBreakpointLoader.continueLoad(MyAppliation.getApplication());
                            }
                        }
                        break;
                    case BUTTON_CANCEL_ID:
                        Log.d(TAG, "取消");
                        EventBusManager.post(new DeleteOrStopLoadEvent("deleteDownLoad"));
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
