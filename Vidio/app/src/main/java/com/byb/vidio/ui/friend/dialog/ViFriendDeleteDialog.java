package com.byb.vidio.ui.friend.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.byb.vidio.R;
import com.byb.vidio.model.ViFriendItemResponseModel;
import com.byb.vidio.ui.base.BaseDialogFragment;
import com.byb.vidio.utils.GlideUtil;

import java.util.ArrayList;

/**
 * Created by baoyb on 2017/8/11.
 */

public class ViFriendDeleteDialog extends BaseDialogFragment implements View.OnClickListener {
    private ImageView ivHead;
    private TextView ivName;

    private ViFriendItemResponseModel model;
    @Override
    public int getLayoutId() {
        return R.layout.vi_dia_friend_delete;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            model = (ViFriendItemResponseModel)
                    bundle.getSerializable(ViFriendItemResponseModel.class.getName());
        }

        ivHead = (ImageView) findViewById(R.id.ivHead);
        ivName = (TextView) findViewById(R.id.tvName);

        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btnConfirm).setOnClickListener(this);

        GlideUtil.loadRoundImg(model.getUrl(), ivHead);
        ivName.setText("删除好友 " + model.getName());
    }


    @Override
    public void onStart() {
        super.onStart();
        /**
         * 设置对话框宽度
         */
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);

            //点击对话框外区域不消失
            dialog.setCancelable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismissAllowingStateLoss();
                break;
            case R.id.btnConfirm:
                Intent intent = new Intent();
                intent.putExtra("id", model.getId());
                dismissAllowingStateLoss(Activity.RESULT_OK, intent);
                break;
        }
    }
}
