package com.aerozhonghuan.hongyan.producer.widget;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.foundation.base.BaseActivity;
import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.utils.BitmapUtils;
import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

/**
 * 弹出dialog，选择 拍照或者从相册选择
 * Created by zhangyunfei on 17/7/19.
 */

public class SelectImageDialog {
    public static final int REQUEST_CODE_TAKE_PICTURE = 1234;
    public static final int REQUEST_CODE_ALBUM = 1235;
    // 相机临时文件路径
    public static final String FILEPATH_CAMERA = SimpleSettings.getCacheDir().getPath() + "/image.jpg";
    // 文件保存路径
    public static final String FILEPATH_UPLOAD = SimpleSettings.getCacheDir().getPath() + "/uploadFiles";

    private static final String TAG = "SelectImageDialog";
    private static final int REQUEST_PERMISSIONS_CAMERA = 1110119;
    private Dialog photoDialog;
    private WeakReference<BaseFragment> fragmentWeakReference;
    private WeakReference<BaseActivity> activityWeakReference;
    private OnSelectCallback onSelectCallback;
    //   dialog取消
    private View.OnClickListener OnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismissDialog();
            if (onSelectCallback != null)
                onSelectCallback.onCancel();
        }
    };
    //    dialog相册
    private View.OnClickListener OnAlbumClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismissDialog();

            Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            if (getFragment() != null)
                getFragment().startActivityForResult(albumIntent, REQUEST_CODE_ALBUM);
            else if (getActivity() != null)
                getActivity().startActivityForResult(albumIntent, REQUEST_CODE_ALBUM);
        }
    };
    //   dialog相机
    private View.OnClickListener OnOpenCameraClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismissDialog();
            //检测权限

            Activity activity = getActivity() != null ? getActivity() : getFragment().getActivity();
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                //注：如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                // 如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。如果设备规范禁止应用具有该权限，此方法也会返回 false。
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        android.Manifest.permission.CAMERA)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.CAMERA},
                            REQUEST_PERMISSIONS_CAMERA);
                }
            } else {
                startIntentCamera();
            }
        }
    };

    /**
     * 启动照相 intent
     */
    private void startIntentCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FILEPATH_CAMERA)));
        if (getFragment() != null)
            getFragment().startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        else if (getActivity() != null)
            getActivity().startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    public SelectImageDialog(BaseFragment fragment, OnSelectCallback onSelectCallback) {
        fragmentWeakReference = new WeakReference<>(fragment);
        this.onSelectCallback = onSelectCallback;
    }

    public SelectImageDialog(BaseActivity activity, OnSelectCallback onSelectCallback) {
        activityWeakReference = new WeakReference<>(activity);
        this.onSelectCallback = onSelectCallback;
    }

    public void dismissDialog() {
        if (photoDialog != null && photoDialog.isShowing()) {
            photoDialog.dismiss();
            photoDialog = null;
        }
    }

    private BaseFragment getFragment() {
        return fragmentWeakReference == null ? null : fragmentWeakReference.get();
    }

    private Activity getActivity() {
        return activityWeakReference == null ? null : activityWeakReference.get();
    }

    protected Context getContext() {
        return getActivity() != null ? getActivity() : (getFragment() == null ? null : getFragment().getContext());
    }

    public void showDialog() {
        Context context = getContext();
        if (context == null) return;
        if (photoDialog != null) {
            photoDialog.show();
            return;
        }
        photoDialog = new Dialog(context, R.style.style_dialog);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_account_bottom_layout, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(params);
        //初始化控件
        Button btCamera = (Button) inflate.findViewById(R.id.btn_camera);
        Button btAlbum = (Button) inflate.findViewById(R.id.btn_album);
        Button btCancel = (Button) inflate.findViewById(R.id.btn_cancel);
        btCamera.setOnClickListener(OnOpenCameraClick);
        btAlbum.setOnClickListener(OnAlbumClick);
        btCancel.setOnClickListener(OnCancelClick);

        //将布局设置给Dialog
        photoDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = photoDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = d.getWidth();
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        photoDialog.show();//显示对话框
    }

    public File saveFile(String imagePath, String path, String fileName) {
        // 工具类,先对图片进行压缩再返回bitmap,减少内存
        Bitmap bitmap = BitmapUtils.getimage(imagePath);
        try {
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File myCaptureFile = new File(path, fileName);
            myCaptureFile.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            return myCaptureFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理 发起 拍照或者选择图片后的 intent的回调
     * 注意：要在发起方（fragment or activty的 onActivityResult方法中调用这个方法）
     * 注意：要在发起方（fragment or activty的 onActivityResult方法中调用这个方法）
     * 注意：要在发起方（fragment or activty的 onActivityResult方法中调用这个方法）
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //相机
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i(TAG, "SD card is not avaiable/writeable right now.");
                return;
            }
            // 创建一个路径用来保存照相的原图,正常通过getData获取的为缩略图,参数一为照片路径,二为保存路径,三位保存的文件名
            File albumFile = saveFile(FILEPATH_CAMERA, FILEPATH_UPLOAD, System.currentTimeMillis() + ".jpg");
            if (onSelectCallback != null)
                onSelectCallback.onTakePicture(albumFile);
        }
        // 相册
        else if (requestCode == REQUEST_CODE_ALBUM && resultCode == Activity.RESULT_OK) {
            if (getContext() == null) return;
            /*Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            // 好像是android多媒体数据库的封装接口，具体的看Android文档````````````````````````````````````````````````````````````
            Cursor cursor = null;
            if (getActivity() != null) {
                cursor = getActivity().managedQuery(uri, proj, null, null, null);
            } else if (getFragment() != null) {
                cursor = getFragment().getActivity().managedQuery(uri, proj, null, null, null);
            }
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            String path = cursor.getString(column_index);*/
            String path;
            if (data != null) {
                Uri uri = data.getData();
                if (!TextUtils.isEmpty(uri.getAuthority())) {
                    Cursor cursor = getContext().getContentResolver().query(uri,
                            new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                    if (null == cursor) {
                        Toast.makeText(getContext(), "图片没找到", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cursor.moveToFirst();
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();
                } else {
                    path = uri.getPath();
                }
            } else {
                Toast.makeText(getContext(), "图片没找到", Toast.LENGTH_SHORT).show();
                return;
            }
            // 不持有原图bitmap,减少内存消耗
            File cameraFile = saveFile(path, FILEPATH_UPLOAD, System.currentTimeMillis() + ".jpg");
            if (onSelectCallback != null)
                onSelectCallback.onTakeAlbum(cameraFile);
        }
    }


    public void release() {
        if (fragmentWeakReference != null) {
            fragmentWeakReference.clear();
            fragmentWeakReference = null;
        }
        dismissDialog();
        photoDialog = null;
    }

    public interface OnSelectCallback {
        void onTakeAlbum(File file);

        void onTakePicture(File file);

        void onCancel();
    }
}
