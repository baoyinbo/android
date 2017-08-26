package com.byb.vidio.ui.login;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.byb.vidio.R;
import com.byb.vidio.ui.base.BaseActivity;
import com.byb.vidio.ui.main.ViMainActivity;
import com.byb.vidio.utils.countdown.CodeCountDownUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by baoyb on 2017/8/7.
 */

public class ViLoginFragment extends BaseActivity implements View.OnClickListener {

    private EditText etPhoneAccount;    //手机账号
    private EditText etLoginCode;       //验证码
    private TextView tvGetCode;         //获取验证码


    /**
     * 获取验证码倒计时
     */
    private CodeCountDownUtils countDownUtils;
    @Override
    public void doCreate(Bundle savedInstanceState) {
        setContentView(R.layout.vi_fra_login);
        setSwipeBackEnable(false);
    }

    @Override
    public void initView() {
//        measureSize(getWindow(), (ScrollView) findViewById(R.id.scrollView));
        etPhoneAccount = (EditText) findViewById(R.id.etPhoneAccount);
        etLoginCode = (EditText) findViewById(R.id.etLoginCode);
        tvGetCode = (TextView) findViewById(R.id.tvGetCode);
        findViewById(R.id.btnNext).setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
    }

    /**
     *页面整体上移
     */
    private boolean isShowKeyBoard;
    private void measureSize(@NonNull Window window, @NonNull final ScrollView scrollView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        final View decorView=window.getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect=new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = decorView.getRootView().getHeight();
                final int heightDifference = screenHeight-rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                if(scrollView.getLayoutParams() instanceof FrameLayout.LayoutParams){
                    FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                    layoutParams.setMargins(0,0,0,heightDifference);//设置ScrollView的marginBottom的值为软键盘占有的高度即可
                }else{
                    LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) scrollView.getLayoutParams();
                    layoutParams.setMargins(0,0,0,heightDifference);//设置ScrollView的marginBottom的值为软键盘占有的高度即可
                }
                scrollView.requestLayout();
                //将页面上移
                if (heightDifference > screenHeight / 3 && !isShowKeyBoard) {
                    isShowKeyBoard = true;
                    new CountDownTimer(500, 1) {
                        public void onTick(long millisUntilFinished) {
                            scrollView.smoothScrollTo(0, (int) (heightDifference - heightDifference * millisUntilFinished/500));
                        }

                        public void onFinish() {
                            scrollView.smoothScrollTo(0, heightDifference);
                        }
                    }.start();

                } else if (heightDifference < screenHeight / 3 && isShowKeyBoard) {
                    isShowKeyBoard = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGetCode:
                //获取验证码

                countDownUtils = new CodeCountDownUtils(this, 60000, 1000, tvGetCode);
                countDownUtils.start();
                CodeCountDownUtils.isGetSmsCode = true;
                break;
            case R.id.btnNext:
                //登录

                LoginInfo info = new LoginInfo(); // config...
                RequestCallback<LoginInfo> callback =
                        new RequestCallback<LoginInfo>() {
                            @Override
                            public void onSuccess(LoginInfo loginInfo) {

                            }

                            @Override
                            public void onFailed(int i) {

                            }

                            @Override
                            public void onException(Throwable throwable) {

                            }
                            // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                        };
                NIMClient.getService(AuthService.class).login(info)
                        .setCallback(callback);


                startActivity(new Intent(this, ViMainActivity.class));
                setOpenExitTransition(true);
                finish();
                break;
        }
    }
}
