package com.byb.vidio.ui.friend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.byb.vidio.R;
import com.byb.vidio.model.ViFriendItemResponseModel;
import com.byb.vidio.utils.GlideUtil;
import com.byb.vidio.views.ItemSlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoyb on 2017/8/10.
 */

public class ViFriendListAdapter extends RecyclerView.Adapter<ViFriendListAdapter.MyViewHolder> implements ItemSlideHelper.Callback {
    private Context context;
    private List<ViFriendItemResponseModel> datas = new ArrayList<>();
    private RecyclerView mRecyclerView;

    public ViFriendListAdapter(Context context, List<ViFriendItemResponseModel> datas) {
        this.context = context;
        this.datas = datas;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.vi_item_friends, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(
                new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ViFriendItemResponseModel model = datas.get(position);
        GlideUtil.loadRoundImg(model.getUrl(), holder.ivHead);
        holder.tvName.setText(model.getName());
        holder.tvDescription.setText(model.getDescription());


        holder.rlFriendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callListener.itemClick(position);
            }
        });
        holder.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callListener.editName(position);
            }
        });
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callListener.deleteItem(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            //viewGroup包含3个控件，即消息主item、标记已读、删除，返回为标记已读宽度+删除宽度
            return viewGroup.getChildAt(1).getLayoutParams().width
                    + viewGroup.getChildAt(2).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }


    /**
     * 自定义的ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlFriendItem;
        private LinearLayout llEdit;    //编辑
        private LinearLayout llDelete;  //删除
        private ImageView ivHead;   //头像
        private TextView tvName;    //昵称
        private TextView tvDescription; //说明

        public MyViewHolder(View itemView) {
            super(itemView);
            rlFriendItem = (RelativeLayout) itemView.findViewById(R.id.rlFriendItem);
            llEdit =(LinearLayout) itemView.findViewById(R.id.llEdit);
            llDelete = (LinearLayout) itemView.findViewById(R.id.llDelete);
            ivHead = (ImageView) itemView.findViewById(R.id.ivHead);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }

    public interface CallbackListener{
        void itemClick(int position);
        void editName(int position);
        void deleteItem(int position);
    }

    /**回调 拨打电话 / 隐藏 监听*/
    public CallbackListener callListener;

    public void setCallbackListener (CallbackListener callListener) {
        this.callListener = callListener;
    }
}
