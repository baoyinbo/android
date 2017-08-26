
package com.byb.vidio.config;


import com.byb.vidio.BuildConfig;
import com.byb.vidio.MyApplication;

import static com.byb.vidio.MyApplication.getApplication;

/**
 * app的一些配置
 *
 */
public class AppConfing {
    /***
     * app运行模式,是否debug模式
     */
    public final static boolean IS_DEBUG = BuildConfig.DEBUG;

    /**ApplicationId*/
    public final static String APPLICATION_ID = BuildConfig.APPLICATION_ID;

    /**build时间*/
//    public final static String BUILD_TIME = BuildConfig.BUILD_TIME;

    /**当前打包的主机名称*/
//    public final static String HOST_NAME=BuildConfig.HOST_NAME;

    /**
     * 服务端和app端约定好的密匙
     */
    public final static String SECURITY_KET = "4UTwT4oMgCofZZrDEsmvWQ==";//J58l2qudG8uCeae4JcWBl2en9zv7xvFa

    /**谷歌地图key*/
    public final static String GOOGLE_MAP_KEY="AIzaSyBiOZtXnRzjOZby-xHSeNFhz7JPxmwR2Ao";
    /***
     * 是否有显示过引导
     *
     * @return
     */
    public static boolean isShowGuideDone() {
        return getApplication().getPreferenceConfig().getBoolean("isShowGuideDone", false);
    }

    /***
     * 保存
     */
    public static void saveShowGuide() {
        MyApplication.getApplication().getPreferenceConfig().setBoolean("isShowGuideDone", true);
    }


}
