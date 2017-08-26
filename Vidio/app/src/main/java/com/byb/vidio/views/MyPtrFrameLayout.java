package com.byb.vidio.views;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class MyPtrFrameLayout extends PtrFrameLayout {
    private PtrClassicDefaultHeader mPtrClassicHeader;

    public MyPtrFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public MyPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public MyPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);

        setResistance(1.7f);
        setRatioOfHeaderHeightToRefresh(1.0f);
        setDurationToClose(200);
        setDurationToCloseHeader(1000);
        // default is false
        setPullToRefresh(false);
        // default is true
        setKeepHeaderWhenRefresh(true);

        // scroll then refresh
        // comment in base fragment
//        ptrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // ptrFrame.autoRefresh();
//            }
//        }, 150);
    }
    
    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

//    /**
//     * Specify the last update time by this key string
//     *
//     * @param key
//     */
//    public void setLastUpdateTimeKey(String key) {
//        if (mPtrClassicHeader != null) {
//            mPtrClassicHeader.setLastUpdateTimeKey(key);
//        }
//    }
//
//    /**
//     * Using an object to specify the last update time.
//     *
//     * @param object
//     */
//    public void setLastUpdateTimeRelateObject(Object object) {
//        if (mPtrClassicHeader != null) {
//            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
//        }
//    }
}
