package com.byb.vidio.utils.countdown;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;

import com.byb.vidio.R;
import com.byb.vidio.utils.ResourceUtils;

/**
 * 注册时获取验证码工具类
 * Created by baoyb on 2016/10/11.
 */
public class CodeCountDownUtils extends CountDownTimer{

    private Context context;
    private TextView tvGetCode;
    private long nSecond = 0;
    /**是否处于获取短信验证码的过程中*/
    public static boolean isGetSmsCode;

    public CodeCountDownUtils(Context context, long millisInFuture, long countDownInterval, TextView tvGetCode) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.tvGetCode = tvGetCode;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvGetCode.setEnabled(false);
        nSecond = millisUntilFinished / 1000;
        tvGetCode.setTextColor(ContextCompat.getColor(context, R.color.text_light_green));
        tvGetCode.setText(nSecond + "s后重发");
    }

    @Override
    public void onFinish() {
        tvGetCode.setText(ResourceUtils.getString(R.string.login_get_code));
        tvGetCode.setTextColor(ContextCompat.getColor(context, R.color.text_green));
        tvGetCode.setEnabled(true);
        nSecond = 0;
        isGetSmsCode = false;
    }
}
