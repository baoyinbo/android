package com.byb.vidio.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.byb.lazynetlibrary.logger.LazyLogger;
import com.byb.vidio.R;
import com.byb.vidio.model.BaseResponseModel;
import com.byb.vidio.net.WJResponseListener;
import com.byb.vidio.net.api.ApiManager;
import com.byb.vidio.ui.base.BaseActivityFragment;

/**
 * Created by baoyb on 2017/8/4.
 */

public class ViMyFragment extends BaseActivityFragment implements View.OnClickListener, WJResponseListener {


    @Override
    public int getLayoutId() {
        return R.layout.vi_fra_my;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        addStatusBarView((LinearLayout) findViewById(R.id.llStatusBar), 0, 0);
        findViewById(R.id.rlMyInfo).setOnClickListener(this);
        findViewById(R.id.rlAllFeedback).setOnClickListener(this);
        findViewById(R.id.rlFriendFeedback).setOnClickListener(this);
        findViewById(R.id.rlTargetFeedback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlMyInfo:

                break;
            case R.id.rlAllFeedback:

                break;
            case R.id.rlFriendFeedback:
                ApiManager.instance().zbPostTest(this, this);
                break;
            case R.id.rlTargetFeedback:
                ApiManager.instance().test(this,"","",this);
                break;
        }
    }

    @Override
    public void onSuccess(int messageId, BaseResponseModel data) {
        LazyLogger.i("hhhhhh", messageId + "");
    }

    @Override
    public void onFail(int messageId, String statusCode, String error) {
        LazyLogger.i("hhhhhh", messageId + "");
    }
}
