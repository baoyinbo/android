/**
 * 文 件 名:  WLHUserInfoConfig.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  2017/3/26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.config;

import android.text.TextUtils;

import com.byb.vidio.MyApplication;
import com.byb.vidio.model.ViUserInfoModel;

/**
 * 用户信息配置
 */
public class WLHUserInfoConfig {

    /**
     * 保存用户信息信息
     *
     * @param loginResponseData
     */
    public static void saveUserinfo(ViUserInfoModel loginResponseData) {
        ViUserInfoModel userinfoModel = getUserinfo();
//        if (TextUtils.isEmpty(loginResponseData.getOpenid())) {
//            loginResponseData.setOpenid(userinfoModel.getOpenid());
//        }
        MyApplication.getApplication().setUserinfoModel(loginResponseData);
        MyApplication.getApplication().getPreferenceConfig().setConfig(loginResponseData);
        CommonDefines.isShowKickOutDialog=false;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static ViUserInfoModel getUserinfo() {
        ViUserInfoModel userinfoModel = MyApplication.getApplication().getUserinfoModel();
        if (userinfoModel == null) {
            userinfoModel = MyApplication.getApplication().getPreferenceConfig().getConfig(ViUserInfoModel.class);
            MyApplication.getApplication().setUserinfoModel(userinfoModel);
        }
        return userinfoModel;
    }

    /**
     * 删除用户信息用户信息
     *
     * @return
     */
    public static void deteleUserInfo(){
        MyApplication.getApplication().setUserinfoModel(null);
        MyApplication.getApplication().getPreferenceConfig().remove(ViUserInfoModel.class.getName());
    }

    /**
     * 保存用户账号
     *
     * @param mobile
     */
    public static void setAccount(String mobile) {
        MyApplication.getApplication().getPreferenceConfig().setString("account", mobile);
    }

    /**
     * 获取用户账号
     *
     * @return
     */
    public static String getAccount() {
        return MyApplication.getApplication().getPreferenceConfig().getString("account" ,"");
    }


    /**
     * 判断用户是否登入
     *
     * @return
     */
    public static boolean isLogin() {
        if (getUserinfo() != null) {
            return true;
        }
        return false;
    }
}
