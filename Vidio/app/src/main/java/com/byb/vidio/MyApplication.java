package com.byb.vidio;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;


import com.byb.applifelibrary.LazyApplication;
import com.byb.lazynetlibrary.logger.AndroidLogTool;
import com.byb.lazynetlibrary.logger.LazyLogger;
import com.byb.lazynetlibrary.logger.Log4JTool;
import com.byb.lazynetlibrary.logger.LogLevel;
import com.byb.lazynetlibrary.logger.LogTool;
import com.byb.lazynetlibrary.logger.PrinterType;
import com.byb.lazynetlibrary.net.http.HttpRequestManager;
import com.byb.lazynetlibrary.net.state.NetworkStateReceiver;
import com.byb.lazynetlibrary.utils.StringUtils;
import com.byb.lazynetlibrary.utils.config.DataConfig;
import com.byb.vidio.config.AppConfing;
import com.byb.vidio.config.PreferenceConfig;
import com.byb.vidio.model.ViUserInfoModel;
import com.byb.vidio.net.http.LazyHttpRequestManager;
import com.byb.vidio.receiver.UpdatePageBroadcastReceiver;
import com.byb.vidio.ui.ViWelcomeActivity;
import com.byb.vidio.utils.AppUtils;
import com.byb.vidio.utils.PermissionUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

import org.apache.log4j.Level;

/**
 * Application
 *
 */
public class MyApplication extends LazyApplication {
    private static MyApplication application;
    /**
     * activity部分栈管理
     */
    private MyActivityStackManager ssManager;

    /**
     * 配置器
     */
    private DataConfig mCurrentConfig;
    /**
     * 网络数据请求管理
     */
    private HttpRequestManager httpDataManager;

    /**
     * 用户信息
     */
    private ViUserInfoModel userinfoModel;

    /**
     * 获取Application
     *
     * @return
     */
    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        application = this;
        String processName = AppUtils.getProcessName(this,
                android.os.Process.myPid());
        if (!StringUtils.isEmpty(processName)) {
            boolean defaultProcess = processName
                    .equals(AppUtils.getAppPackageName(this));
            if (defaultProcess) {
                //必要的初始化资源操作
                super.onCreate();
                initApp();
            }
        }

        //SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());
    }


    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = ViWelcomeActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 100;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.drawable.avatar_def;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
    }



    /**
     * 初始化app的一些配置
     */
    private void initApp() {
        NetworkStateReceiver.checkNetworkState(this);
    }


    /***
     * 初始化日志系统
     *
     * @throws
     * @see [类、类#方法、类#成员]
     */
    @Override
    protected void initLogger() {
        LogLevel logLevel = LogLevel.OFF;
        LogTool logTool = new AndroidLogTool();
        if (AppConfing.IS_DEBUG) {
            logLevel = LogLevel.ALL;
            if (PermissionUtils.checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && PermissionUtils.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                logTool = new Log4JTool(Level.ALL, "Vidio/log", "vilog");
            }
        }
        LazyLogger.init(/* PrinterType.FORMATTED */PrinterType.ORDINARY) // 打印类型
                .methodCount(3) // default 2
                .hideThreadInfo() // default shown
                .logLevel(logLevel) // default LogLevel.ALL(设置全局日志等级)
                .methodOffset(2) // default 0
                .logTool(logTool); // Log4j中的Level与本框架的LogLevel是分开设置的(Level只用来设置log4j的日志等级)
    }

    public Activity getTopActivity() {
        if (ssManager != null) return ssManager.topActivity();
        return null;
    }

    /***
     * 获取用户信息
     *
     * @return
     */
    public ViUserInfoModel getUserinfoModel() {
        return userinfoModel;
    }

    /**
     * 设置用户信息
     *
     */
    public void setUserinfoModel(ViUserInfoModel userinfoModel) {
        this.userinfoModel = userinfoModel;
    }

    @Override
    protected void registerUncaughtExceptionHandler() {
        if (AppConfing.IS_DEBUG) {
            super.registerUncaughtExceptionHandler();
        }
    }

    @Override
    protected void initCache() {
        if (AppConfing.IS_DEBUG) {
            super.initCache();
        }
    }

    @Override
    public synchronized MyActivityStackManager getActivityStackManager() {
        if (ssManager == null) {
            ssManager = new MyActivityStackManager();
        }
        return ssManager;
    }

    /**
     * 获取数据请求管理者
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Override
    public synchronized HttpRequestManager getHttpDataManager() {
        if (httpDataManager == null) {
            httpDataManager = LazyHttpRequestManager.getInstance(this);
        }
        return httpDataManager;
    }

    /**
     * 根据类型得到对应的配置器
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public DataConfig getPreferenceConfig() {
        if (mCurrentConfig == null) {
            mCurrentConfig = PreferenceConfig.getPreferenceConfig(this);
            if (!mCurrentConfig.isLoadConfig()) {
                mCurrentConfig.loadConfig();
            }
        }
        return mCurrentConfig;
    }

    @Override
    public void exitApp(Boolean isBackground) {
        UpdatePageBroadcastReceiver.unregisterPageRefreshBroadcast(this);
        UpdatePageBroadcastReceiver.close();
        if (mCurrentConfig != null) {
            mCurrentConfig.close();
            mCurrentConfig = null;
        }
        if (ssManager != null) {
            ssManager.appExit(this, isBackground);
            ssManager = null;
        }
        super.exitApp(isBackground);
    }
}
