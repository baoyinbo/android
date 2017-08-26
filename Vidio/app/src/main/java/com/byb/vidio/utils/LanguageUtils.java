/**
 * 文 件 名:  LanguageUtils.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  2017/3/30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.utils;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

import static com.byb.vidio.MyApplication.getApplication;


/**
 * 多语言切换工具
 *
 */
public class LanguageUtils {

    /**
     * 获取当前语言
     * @return
     */
    public static Locale getCurrentLanguage(){
        Locale locale = getApplication().getPreferenceConfig().getConfig(Locale.class);
        if (locale == null) {
            locale = Locale.getDefault();
            if (locale.equals(Locale.CHINESE)|| locale.equals( Locale.CHINA )|| locale .equals( Locale.PRC )|| locale .equals( Locale.TAIWAN)) {
                locale = Locale.CHINESE;
            } else {
                locale = Locale.ENGLISH;
            }
        }
        return locale;
    }
    /**
     * 初始化当前app语言
     */
    public static void initAppLanguage() {
        updateLanguage(getCurrentLanguage());
    }

    /**
     * 更新当前语言
     *
     * @param locale
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void updateLanguage(Locale locale) {
        Resources resources = getApplication().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        config.setLocale(locale);
        resources.updateConfiguration(config, dm);
        getApplication().getPreferenceConfig().setConfig(locale);
    }

    /***
     * 设置语言为英语
     */
    public static void setLanguageIsChinese() {
        updateLanguage(Locale.CHINESE);
        AppUtils.restartApp();
    }

    /**
     * 设置语言为英语
     */
    public static void setLanguageIsEnglish() {
        updateLanguage(Locale.ENGLISH);
        AppUtils.restartApp();
    }

}
