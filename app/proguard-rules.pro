# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangyunfei/chengxu/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#---------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}


#----------------------------------------------------------------------------

#---------------------------------具体业务------------------------------------
#hybrid
-keep class com.aerozhonghuan.hybrid.BaseMobileAgent {
    public <methods>;
}

-keep class * extends com.aerozhonghuan.hybrid.BaseMobileAgent {
    public <methods>;
}

#eventbus
-keep class com.aerozhonghuan.driverapp.foundation.eventbus.EventBusEvent {
    public <methods>;
}

-keep class * extends com.aerozhonghuan.driverapp.foundation.eventbus.EventBusEvent {
    public <methods>;
}

#dao 实体
-keep class com.aerozhonghuan.driverapp.dao.beans.* {*;}
-dontwarn com.aerozhonghuan.driverapp.dao.beans.**

#greendao
#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties


#----------------------------------------------------------------------------


#---------------------------------2.第三方包-------------------------------

#友盟
-keep public class com.aerozhonghuan.driverapp.R$*{
public static final int *;
}

-keep public class com.aerozhonghuan.hongyan.driver.R$*{
public static final int *;
}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#爱心推
-keep class com.ixintui.** {*;}
-keep public interface com.ixintui.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.android.pushagent.** {*;}
-keep class com.meizu.cloud.pushsdk.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#log4j
-libraryjars log4j-1.2.17.jar
-dontwarn org.apache.log4j.**
-keep class  org.apache.log4j.** { *;}

#图吧导航
-keep class com.mapbar.android.* {*;}
-dontwarn com.mapbar.android.**
-keep class com.mapbar.offline.* {*;}
-dontwarn com.mapbar.offline.**
-keep class com.mapbar.bus.* {*;}
-dontwarn com.mapbar.bus.**
-keep class com.mapbar.map.* {*;}
-dontwarn com.mapbar.map.**
-keep class com.mapbar.license.* {*;}
-dontwarn com.mapbar.license.**
-keep class com.mapbar.mapdal.* {*;}
-dontwarn com.mapbar.mapdal.**
-keep class com.mapbar.navi.* {*;}
-dontwarn com.mapbar.navi.**
-keep class com.mapbar.poiquery.* {*;}
-dontwarn com.mapbar.poiquery.**

#okhttp3.x
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#zxing
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**


#gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}

# support-v4
#https://stackoverflow.com/questions/18978706/obfuscate-android-support-v7-widget-gridlayout-issue
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }


# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

# support design
#@link http://stackoverflow.com/a/31028536
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
#-------------------------------------------------------------------------

# picasso
-keep class com.squareup.picasso.** {*; }
-dontwarn com.squareup.picasso.**


#fastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#prt-lib下拉刷新框架不混淆
-keep class in.srain.cube.views.ptr.** { *; }
-dontwarn in.srain.cube.views.ptr.**

#PullToRefreshLibrary下拉刷新框架不混淆
-keep class com.handmark.pulltorefresh.library.** { *; }
-dontwarn com.handmark.pulltorefresh.library.**
#-------------------------------------------------------------------------
