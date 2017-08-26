/**
 * 文 件 名:  BaseFragment.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/5
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.byb.applifelibrary.LazyAppCompatActivity;
import com.byb.applifelibrary.LazyFragment;
import com.byb.vidio.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Fragment基类
 *
 */
public abstract class BaseFragment extends LazyFragment {

    /***
     * 开启一个fragment
     * @param launchType 启动类型
     * @param clazz
     */
    public void startFragment(LaunchBody.LaunchType launchType,Class<? extends BaseActivityFragment> clazz) {
        startFragment(launchType,clazz,null);
    }

    /***
     * 开启一个fragment
     * @param clazz
     */
    public void startFragment(Class<? extends BaseActivityFragment> clazz) {
        startFragment(clazz,null);
    }

    /**
     * 开启一个fragment
     * @param launchType 启动类型
     * @param requestCode 请求码
     * @param clazz
     */
    public void startFragmentForResult(int requestCode,LaunchBody.LaunchType launchType,Class<? extends BaseActivityFragment> clazz) {
        startFragmentForResult(requestCode,launchType,clazz,null);
    }

    /**
     * 开启一个fragment
     * @param requestCode 请求码
     * @param clazz
     */
    public void startFragmentForResult(int requestCode,Class<? extends BaseActivityFragment> clazz) {
        startFragmentForResult(requestCode,clazz,null);
    }

    /**
     *
     * @param clazz
     * @param bundle 参数
     */
    public void startFragment(Class<? extends BaseActivityFragment> clazz,Bundle bundle) {
        startFragment(LaunchBody.LaunchType.SINGLE_TOP,clazz,bundle);
    }

    /**
     * @param launchType 启动类型
     * @param clazz
     * @param bundle 参数
     */
    public void startFragment(LaunchBody.LaunchType launchType,Class<? extends BaseActivityFragment> clazz, Bundle bundle) {
        LaunchBody.Builder builder = new LaunchBody.Builder(this, clazz);
        if(bundle!=null){
            builder.bundle(bundle);
        }
        if(launchType==null){
            builder.launchType(LaunchBody.LaunchType.SINGLE_TOP);
        }else{
            builder.launchType(launchType);
        }
        CommonActivity.launch(builder);
    }

    /**
     * 开启一个fragment
     * @param requestCode 请求码
     * @param clazz
     * @param bundle 参数
     */
    public void startFragmentForResult(int requestCode,Class<? extends BaseActivityFragment> clazz,Bundle bundle) {
        startFragmentForResult(requestCode,LaunchBody.LaunchType.SINGLE_TOP,clazz,bundle);
    }

    /**
     * 开启一个fragment
     * @param launchType 启动类型
     * @param requestCode 请求码
     * @param clazz
     * @param bundle 参数
     */
    public void startFragmentForResult(int requestCode,LaunchBody.LaunchType launchType,Class<? extends BaseActivityFragment> clazz,Bundle bundle) {
        LaunchBody.Builder builder = new LaunchBody.Builder(this, clazz);
        if(bundle!=null){
            builder.bundle(bundle);
        }
        if(launchType==null){
            builder.launchType(LaunchBody.LaunchType.SINGLE_TOP);
        }else{
            builder.launchType(launchType);
        }
        builder.launchType(LaunchBody.LaunchType.SINGLE_TOP);
        CommonActivity.launchForResult(requestCode,builder);
    }

    /***
     * 开启退出activity的动画
     */
    protected void openExitTransition() {
        Activity activity = getActivity();
        if (activity instanceof LazyAppCompatActivity) {
            ((LazyAppCompatActivity) activity).setOpenExitTransition(true);
        }
    }

    /**
     * finish掉当前activity
     * @param resultCode 结果码
     * @param data 数据
     */
    public void finishActivity(int resultCode,Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode,data);
        }
        finishActivity();
    }

    /**
     * finish掉当前activity
     * @param resultCode
     */
    public void finishActivity(int resultCode) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode);
        }
        finishActivity();
    }

    /**
     * finish掉当前activity
     */
    public void finishActivity() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().finish();
        }
    }

    /**
     * finish掉当前activity(带动画效果的)
     * @param resultCode 结果码
     * @param data
     */
    public void finishActivityAfterTransition(int resultCode,Intent data) {
        openExitTransition();
        finishActivity(resultCode,data);
    }

    /***
     * finish掉当前activity(带动画效果的)
     * @param resultCode 结果码
     */
    public void finishActivityAfterTransition(int resultCode) {
        openExitTransition();
        finishActivity(resultCode);
    }

    /**
     * finish掉当前activity(带动画效果的)
     */
    public void finishActivityAfterTransition() {
        openExitTransition();
        finishActivity();
    }

    /**
     * 添加StatusBar项的view
     * @param llStatusBar
     * @param colorId 状态栏颜色ColorRes
     * @param resid 状态栏drawableRes
     */
    protected void addStatusBarView(LinearLayout llStatusBar, @ColorRes int colorId, @DrawableRes int resid) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(false);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        View view = new View(getContext());
        if (colorId > 0) {
            view.setBackgroundColor(getResources().getColor(colorId));
        } else if (resid > 0) {
            view.setBackgroundResource(resid);
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.statusColor));
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, config.getStatusBarHeight()));
        llStatusBar.addView(view, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
