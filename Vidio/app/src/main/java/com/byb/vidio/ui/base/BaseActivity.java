/**
 * 文 件 名:  BaseActivity.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/4
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.byb.applifelibrary.LazyAppCompatActivity;
import com.byb.vidio.R;
import com.byb.vidio.utils.StatusBarUtil;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeBackPage;
import com.jude.swipbackhelper.SwipeListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * activity基类
 */
public abstract class BaseActivity extends LazyAppCompatActivity {

    /***
     * 滑动页
     */
    private SwipeBackPage swipeBackPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.statusBarLightMode(this);
        SwipeBackHelper.onCreate(this);
        swipeBackPage = initSwipeBackHelper();
        setActivityAnimations(R.anim.push_right_in,
                R.anim.push_left_out,
                R.anim.push_right_in,
                R.anim.push_left_out);
        super.onCreate(savedInstanceState);
    }

    /***
     * 初始化SwipeBackHelper
     *
     * @return
     */
    private SwipeBackPage initSwipeBackHelper() {
        return SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
//                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
//                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
//                .setScrimColor(0x7F000000)//底层阴影颜色
//                .setClosePercent(0.5f)//触发关闭Activity百分比
//                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(300)//activity联动时的偏移量。默认500px。
                /*.setDisallowInterceptTouchEvent(true)*/;//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）;
    }

    /**
     * 设置是否抢占view事件
     *
     * @param bool
     */
    protected void setDisallowInterceptTouchEvent(boolean bool) {
        getCurrentPage().setDisallowInterceptTouchEvent(bool);
    }

    /**
     * 添加滑动监听器
     *
     * @param listener
     */
    protected void addSwipeListener(SwipeListener listener) {
        getCurrentPage().addListener(listener);
    }

    /***
     * 设置是否能够滑动退出activity
     *
     * @param enable
     */
    protected void setSwipeBackEnable(boolean enable) {
        getCurrentPage().setSwipeBackEnable(enable);
    }

    /**
     * 获取当前SwipeBack页面
     *
     * @return
     */
    protected SwipeBackPage getCurrentPage() {
        return swipeBackPage;
    }

    /***
     * 设置状态栏的颜色
     *
     * @param resId
     */
    protected void setStatusBarTintResource(@DrawableRes int resId) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(resId);
    }

    /***
     * 设置状态栏的颜色
     *
     * @param color 颜色值
     */
    protected void setStatusBarTintColor(@ColorInt int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    /***
     * 设置状态栏是否透明的
     *
     * @param on 透明状态开关
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 添加StatusBar项的view
     * @param llStatusBar
     */
    protected void addStatusBarView(LinearLayout llStatusBar) {
        addStatusBarView(llStatusBar,R.color.statusColor,0);
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
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(false);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        View view = new View(this);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        swipeBackPage = null;
        SwipeBackHelper.onDestroy(this);
    }

}
