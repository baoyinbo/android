package com.byb.vidio.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.byb.vidio.MyApplication;

import java.util.List;

/**
 * 设备系统工具类
 *
 * @author lanyan
 */
public class DeviceUtil {


    /**
     * 获取设备IMEI号
     *
     * @param ctx
     * @return
     */
    public static String getIMEI(Context ctx) {
        if (ctx == null) {
            return "";
        }
        return ((TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取手机IMSI号
     *
     * @param ctx
     * @return
     */
    public static String getIMSI(Context ctx) {
        if (ctx == null) {
            return "";
        }
        return ((TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 返回系统版本号
     *
     * @return
     */
    public static String getSysVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 返回手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 设备序列号 用于游客登录标识
     * @return
     */
    public static String getDevice() {
        return android.os.Build.SERIAL;
    }

    /**
     * 返回手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 隐藏键盘
     *
     * @param et 需要隐藏键盘对于的EditText
     */
    public static void hideKeyboard(Context ctx, EditText et) {
        if (et == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public static void hideSoftInput(Activity activity) {
        hideSoftInput(activity, activity.getCurrentFocus());
    }

    public static void hideSoftInput(Context ctx, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) ctx
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


//    public static boolean checkDeviceHasNavigationBar(Context context) {
//        boolean hasNavigationBar = false;
//        Resources rs = context.getResources();
//        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (id > 0) {
//            hasNavigationBar = rs.getBoolean(id);
//        }
//        try {
//            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
//            Method m = systemPropertiesClass.getMethod("get", String.class);
//            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
//            if ("1".equals(navBarOverride)) {
//                hasNavigationBar = false;
//            } else if ("0".equals(navBarOverride)) {
//                hasNavigationBar = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return hasNavigationBar;
//
//    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    public static boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey && !hasBackKey;
    }


    public static String getWifiSSID(Context activity) {
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ConnectivityManager connec = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                && wifiInfo.getSSID() != null) {
            return wifiInfo.getSSID();
        }
        return "";
    }

    public static boolean isWiFiActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取渠道名
     *
     * @return
     */
    public static String getAppChannelName() {
        String channel = "";
        try {
            ApplicationInfo info = MyApplication.getApplication().getPackageManager()
                    .getApplicationInfo(MyApplication.getApplication().getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                channel = info.metaData.getString("UMENG_CHANNEL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static int getScreenWidthPx(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;// 屏幕宽
    }

    public static int getScreenWidthPx(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;// 屏幕宽
    }
    public static int getScreenHeightPx(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;// 屏幕高
    }

}
