/**
 * 文 件 名:  DefaultLoadingView.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.ui.commom;

import android.content.Context;

import com.byb.lazynetlibrary.net.http.LoadingViewInterface;
import com.kaopiz.kprogresshud.KProgressHUD;

public class DefaultLoadingView implements LoadingViewInterface {

    private KProgressHUD progressHUD;

    public DefaultLoadingView(Context context) {
        progressHUD= KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("加载中...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        if(!progressHUD.isShowing()){
            progressHUD.show();
        }
    }

    public DefaultLoadingView(Context context, boolean isCancellable) {
        this(context);
        setCancellable(isCancellable);
    }

    /**
     * 设置是否能够取消
     *
     * @param isCancellable
     */
    public void setCancellable(boolean isCancellable){
        if(progressHUD!=null){
            progressHUD.setCancellable(isCancellable);
        }
    }

    @Override
    public void loadStart(int loadId) {
        if(!progressHUD.isShowing()){
            progressHUD.show();
        }
    }

    @Override
    public void loadSuccess(int loadId, Object responseData) {
        progressHUD.dismiss();
        progressHUD=null;
    }

    @Override
    public void loadFail(int loadId, int failCode, String failMessage) {
        progressHUD.dismiss();
        progressHUD=null;
    }
}
