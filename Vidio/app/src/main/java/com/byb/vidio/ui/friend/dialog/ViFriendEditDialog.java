package com.byb.vidio.ui.friend.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.byb.vidio.R;
import com.byb.vidio.ui.base.BaseDialogFragment;

/**
 * Created by baoyb on 2017/8/11.
 */

public class ViFriendEditDialog extends BaseDialogFragment implements View.OnClickListener {
    private EditText etEditname;

    @Override
    public int getLayoutId() {
        return R.layout.vi_dia_editname;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etEditname = (EditText) findViewById(R.id.etEditName);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btnConfirm).setOnClickListener(this);
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
                intent.putExtra("editname", etEditname.getText().toString());
                dismissAllowingStateLoss(Activity.RESULT_OK, intent);
                break;
        }
    }
}
