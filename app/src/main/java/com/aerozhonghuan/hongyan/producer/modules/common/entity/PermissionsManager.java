package com.aerozhonghuan.hongyan.producer.modules.common.entity;

import java.util.ArrayList;

/**
 * Created by zhangyonghui on 2018/1/26.
 * 产销权限管理类
 */

public class PermissionsManager {

    private static ArrayList<String> permissions;
    /**
     * 生产调试模块
     */

    public static void setPermissions(PermissionsBean permissionsBean){
        permissions = permissionsBean.permissions;
    }

    // 生产调试模块菜单，控制是否显示生产调试模块菜单
    public static final String INSPECTION_VIEW = "app:scts:inspection:view";

    public static boolean isShowInspectionView(){
        return permissions.contains(INSPECTION_VIEW);
    }

    // APP初检，控制是否显示初检按钮
    public static final String INSPECTION_FIRSTCHECK = "app:scts:inspection:firstCheck";

    public static boolean isShowInspectionFirstCheck(){
        return permissions.contains(INSPECTION_FIRSTCHECK);
    }


    // APP复检，控制是否显示复检按钮
    public static final String INSPECTION_SECONDCHECK = "app:scts:inspection:secondCheck";

    public static boolean isShowInspectionSecondCheck(){
        return permissions.contains(INSPECTION_SECONDCHECK);
    }


    // 调试强制通过，控制是否显示复检中的强制通过按钮
    public static final String INSPECTION_FORCEPASS = "app:scts:inspection:forcePass";

    public static boolean isShowInspectionForcepass(){
        return permissions.contains(INSPECTION_FORCEPASS);
    }


    // 开关锁车功能权限, 包括开启、关闭、锁车、解锁按钮
    public static final String INSPECTION_CHECK = "app:scts:inspection:check";

    public static boolean isShowInspectionCheck(){
        return permissions.contains(INSPECTION_CHECK);
    }


    // 强制锁车控制权限，若拥有此权限，则无论车辆属性是否带锁车功能，都显示开启、关闭、锁车、解锁按钮。
    public static final String INSPECTION_FORCECHECK = "app:scts:inspection:forceCheck";

    public static boolean isShowInspectionForceCheck(){
        return permissions.contains(INSPECTION_FORCECHECK);
    }

    /**
     * 运输扫描模块
     */

    // 运输管理模块菜单，控制是否显示运输管理模块菜单
    public static final String TRANSPORT_VIEW = "app:scts:transport:view";

    public static boolean isShowTransportView(){
        return permissions.contains(TRANSPORT_VIEW);
    }


    // 批量扫描
    public static final String TRANSPORT_MASSSCAN = "app:scts:transport:massScan";

    public static boolean isShowTransportMasscan(){
        return permissions.contains(TRANSPORT_MASSSCAN);
    }


    // 运输单分组
    public static final String TRANSPORT_ORDERGROUP = "app:scts:transport:orderGroup";

    public static boolean isShowTransportOrdergroup(){
        return permissions.contains(TRANSPORT_ORDERGROUP);
    }


    // 操作查询
    public static final String TRANSPORT_QUERY = "app:scts:transport:query";

    public static boolean isShowTransportquery(){
        return permissions.contains(TRANSPORT_QUERY);
    }


    // 扫描输入
    public static final String TRANSPORT_INPUTSCAN = "app:scts:transport:inputScan";

    public static boolean isShowTransportInputScan(){
        return permissions.contains(TRANSPORT_INPUTSCAN);
    }

    // 更换终端
    public static final String  DEVICE_BIND= "app:scts:device:bind";

    public static boolean isShowDeviceBind(){
        return permissions.contains(DEVICE_BIND);
    }


    public static void clearPermissions() {
        permissions = null;
    }
}
