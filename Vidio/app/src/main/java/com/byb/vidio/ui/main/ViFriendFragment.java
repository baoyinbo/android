package com.byb.vidio.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.byb.vidio.R;
import com.byb.vidio.model.ViFriendItemResponseModel;
import com.byb.vidio.ui.base.BaseActivityFragment;
import com.byb.vidio.ui.base.BaseListActivityFragment;
import com.byb.vidio.ui.friend.HSVTestFragment;
import com.byb.vidio.ui.friend.adapter.ViFriendListAdapter;
import com.byb.vidio.ui.friend.adapter.ViFriendsAdapter;
import com.byb.vidio.ui.friend.dialog.ViFriendDeleteDialog;
import com.byb.vidio.ui.friend.dialog.ViFriendEditDialog;
import com.byb.vidio.utils.ToastShowUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoyb on 2017/8/4.
 */

public class ViFriendFragment extends BaseListActivityFragment {
    private static final int OPEN_FRIEND_EDIT_DIALOG = 100;
    private static final int OPEN_FRIEND_DELETE_DIALOG = 101;

    private ViFriendListAdapter adapter;
    private List<ViFriendItemResponseModel> friends;
    private RecyclerView recyclerView;
    @Override
    public int getLayoutId() {
        return R.layout.vi_fra_friend;
    }

    @Override
    protected void onCreateListView(Bundle savedInstanceState) {
        addStatusBarView((LinearLayout) findViewById(R.id.llStatusBar), 0, 0);
        friends = new ArrayList<>();
        adapter = new ViFriendListAdapter(this.getContext(), friends);
        recyclerView = getRecyclerView();
        recyclerView.setAdapter(adapter);
        adapter.setCallbackListener(new ViFriendListAdapter.CallbackListener() {
            @Override
            public void itemClick(int position) {
                ToastShowUtils.showTextToast("item");
            }

            @Override
            public void editName(int position) {
                //打开编辑对话框
                ViFriendEditDialog dialog = new ViFriendEditDialog();
                dialog.showAllowingStateLoss(ViFriendFragment.this, OPEN_FRIEND_EDIT_DIALOG);
            }

            @Override
            public void deleteItem(int position) {
                ViFriendDeleteDialog dialog = new ViFriendDeleteDialog();
                dialog.addValues(ViFriendItemResponseModel.class.getName(), friends.get(position));
                dialog.showAllowingStateLoss(ViFriendFragment.this, OPEN_FRIEND_DELETE_DIALOG);
            }
        });
        test();


    }

    private void test() {

        for (int i = 0; i < 3; i++) {
            ViFriendItemResponseModel model = new ViFriendItemResponseModel();
            model.setId("fri " + i);
            model.setName("张三");
            model.setDescription("hahahahhaha");
            friends.add(model);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == OPEN_FRIEND_EDIT_DIALOG ) {
            ToastShowUtils.showTextToast(data.getStringExtra("editname"));
        } else if (requestCode == OPEN_FRIEND_DELETE_DIALOG) {
            ToastShowUtils.showTextToast(data.getStringExtra("id") );
        }
    }

    @Override
    protected void onPullDownRefreshListener() {

    }

    @Override
    protected void onLoadMoreListener() {

    }
}
