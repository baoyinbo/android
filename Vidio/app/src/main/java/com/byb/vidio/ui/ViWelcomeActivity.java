package com.byb.vidio.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.byb.vidio.R;
import com.byb.vidio.config.AppConfing;
import com.byb.vidio.ui.base.BaseActivity;
import com.byb.vidio.ui.home.ViHomeActivity;
import com.byb.vidio.ui.login.ViLoginFragment;
import com.byb.vidio.ui.main.ViMainActivity;

/**
 * Created by baoyb on 2017/5/25.
 */

public class ViWelcomeActivity extends BaseActivity {
    private static int OVERLAY_PERMISSION_REQ_CODE = 1000;

    private AlphaAnimation alphaAnimation;
    private View contentView;
    private boolean isLoing = false;
    @Override
    public void doCreate(Bundle savedInstanceState) {
        if (AppConfing.IS_DEBUG) {
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (Settings.canDrawOverlays(this)) {
//                    GSDebugActivity.initFloatBtn(getLazyApplication());
//                } else {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//                }
//            } else {
//                GSDebugActivity.initFloatBtn(getLazyApplication());
//            }
//            if (PermissionUtils.checkPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
//                WJDebugActivity.initFloatBtn(getLazyApplication());
//            }
        }
        contentView = View.inflate(this, R.layout.vi_act_welcome, null);
        setContentView(contentView);
        setSwipeBackEnable(false);


    }

    @Override
    public void initView() {
        startAnimation();
    }

    private void startAnimation() {
        // 渐变启动屏
        alphaAnimation = new AlphaAnimation(0.8f, 1.0f);
        alphaAnimation.setDuration(3000);

        alphaAnimation.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        startActivity();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                }

        );
        contentView.startAnimation(alphaAnimation);
    }


    private void startActivity() {
        contentView.clearAnimation();

//        if (WJAppConfing.isShowGuideDone()) {
//            startActivity(new Intent(WJSplashActivity.this,
//                    WJMainActivity.class));
//        } else {
//            startActivity(new Intent(WJSplashActivity.this,
//                    WJGuidePageActivity.class));
//            WJAppConfing.saveShowGuide();
//        }

        if (isLoing) {

            startActivity(new Intent(this, ViMainActivity.class));
        } else {
            startActivity(new Intent(this, ViLoginFragment.class));
        }
        setOpenExitTransition(true);
        finish();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.canDrawOverlays(this)) {
//                    GSDebugActivity.initFloatBtn(getLazyApplication());
                }
            }
        }
    }
}
