package com.byb.vidio.ui.home;

import android.os.Bundle;

import com.byb.vidio.R;
import com.byb.vidio.ui.base.BaseActivity;

/**
 * Created by baoyb on 2017/8/3.
 */

public class ViHomeActivity extends BaseActivity {

    @Override
    public void doCreate(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        setContentView(R.layout.vi_act_home);
    }

    @Override
    public void initView() {

    }
}
