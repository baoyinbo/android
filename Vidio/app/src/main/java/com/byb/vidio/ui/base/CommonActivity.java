/**
 * 文 件 名:  CommonActivity.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/4
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.LinearLayout;


import com.byb.vidio.R;

import java.lang.reflect.Method;

/**
 * 公用的activity
 *
 */
public class CommonActivity extends BaseActivity {
    private int contentId;
    private String fragmentClassName;

    @Override
    public void doCreate(Bundle savedInstanceState) {
        contentId = savedInstanceState == null ? R.layout.common_ui_fra_container
                : savedInstanceState.getInt("contentId");
        fragmentClassName = savedInstanceState == null ? null : savedInstanceState.getString("className");
        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                fragmentClassName = getIntent().getStringExtra("className");
                if (TextUtils.isEmpty(fragmentClassName)) {
                    finish();
                    return;
                }

                Bundle values = getIntent().getBundleExtra("args");
                Class clazz = Class.forName(fragmentClassName);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[]{Bundle.class});
                        method.invoke(fragment, values);
                    } catch (Exception e) {
                    }
                }
                // 重写Activity的contentView
                try {
                    Method method = clazz.getMethod("inflateActivityContentView");
                    if (method != null) {
                        int fragmentConfigId = Integer.parseInt(method.invoke(fragment).toString());
                        if (fragmentConfigId > 0) {
                            contentId = fragmentConfigId;
                        }
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                finish();
                return;
            }
        }
        setContentView(contentId);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragmentContainer, fragment, fragmentClassName).
                    commitAllowingStateLoss();
        }
    }

    @Override
    public void initView() {
        if (contentId == R.layout.common_ui_fra_container) {
            addStatusBarView((LinearLayout) findViewById(R.id.llStatusBar), 0, 0);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            if (findViewById(R.id.llStatusBar) != null) {
                addStatusBarView((LinearLayout) findViewById(R.id.llStatusBar));
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if(toolbar!=null){
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            contentId = savedInstanceState.getInt("contentId");
            fragmentClassName = savedInstanceState.getString("className");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contentId", contentId);
        outState.putString("className", fragmentClassName);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClassName);
        if (fragment != null && fragment instanceof BaseActivityFragment) {
            ((BaseActivityFragment) fragment).onNewIntent(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /***
     * activity获取根Fragment
     */
    public String getRootFragmentClassName() {
        return fragmentClassName;
    }

    /***
     * 开启一个activity(fragment作为内容,activity作为容器)
     *
     * @param builder
     */
    public static void launch(@NonNull LaunchBody.Builder builder) {
        LaunchBody launchBody = builder.build();
        launchBody.launchActivity(CommonActivity.class);
    }

    /***
     * 开启一个activity(fragment作为内容,activity作为容器)
     *
     * @param requestCode
     * @param builder
     */
    public static void launchForResult(int requestCode, @NonNull LaunchBody.Builder builder) {
        LaunchBody launchBody = builder.build();
        launchBody.launchActivityForResult(requestCode, CommonActivity.class);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClassName);
        if (fragment != null && fragment instanceof BaseActivityFragment) {
            if (!((BaseActivityFragment) fragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }


}
