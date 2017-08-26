package com.byb.vidio.ui.friend.adapter;

import android.content.Context;

import com.byb.vidio.R;
import com.byb.vidio.model.ViFriendItemResponseModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by baoyb on 2017/8/9.
 */

public class ViFriendsAdapter extends BaseQuickAdapter<ViFriendItemResponseModel> {

    public ViFriendsAdapter(Context context, List<ViFriendItemResponseModel> data) {
        super(R.layout.vi_item_friends, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ViFriendItemResponseModel model) {
//        final View itemView = baseViewHolder.getConvertView();
//        HorizontalScrollView hsv = (HorizontalScrollView)baseViewHolder.getView(R.id.hsv);
//        View llContent = baseViewHolder.getView(R.id.llContent);
//        int w = llContent.getWidth();
//        hsv.smoothScrollTo(100, 0);

        //设置滑动监听事件
//        itemView.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                switch (event.getAction())
//                {
//                    case MotionEvent.ACTION_UP:
//
//                        //获得ViewHolder
//                        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
//                        HorizontalScrollView hsv = (HorizontalScrollView) v.findViewById(R.id.hsv);
//                        View llContent = v.findViewById(R.id.llContent);
//
//                        //获得HorizontalScrollView滑动的水平方向值.
//                        int scrollX = hsv.getScrollX();
//                        scrollX = 100;
//                        //获得操作区域的长度
//                        int actionW = llContent.getWidth();
//
//                        //注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
//                        //如果水平方向的移动值<操作区域的长度的一半,就复原
//                        if (scrollX < 80)
//                        {
//                            hsv.smoothScrollTo(0, 0);
//                        }
//                        else//否则的话显示操作区域
//                        {
//                            hsv.smoothScrollTo(actionW, 0);
//                        }
//                        return true;
//                }
//                return false;
//            }
//        });
    }

}
