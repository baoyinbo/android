/**
 * 文 件 名:  BaseListFragment.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/9
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.ui.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.byb.vidio.R;
import com.byb.vidio.ui.commom.ViDefaultLoadingView;
import com.byb.vidio.views.MyPtrFrameLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 只有列表功能的fragment
 *
 */
public abstract class BaseListActivityFragment extends BaseActivityFragment {
    private ViDefaultLoadingView loadingView;
    private MyPtrFrameLayout ptrFrame;
    private RecyclerView recyclerView;
    private BaseQuickAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fra_base_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        loadingView = (ViDefaultLoadingView) findViewById(R.id.loadingView);
        ptrFrame = (MyPtrFrameLayout) findViewById(R.id.swiperefreshLayout);
        initWJPtrFrameLayout();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        swipeRefreshLayout.setColorSchemeResources(R.color.button_red);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                onPullDownRefreshListener();
//            }
//        });
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        onCreateListView(savedInstanceState);
    }

    private void initWJPtrFrameLayout() {
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onPullDownRefreshListener();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });


    }

    /**
     * 获取加载的view
     * @return
     */
    protected ViDefaultLoadingView getLayoutLoadingView() {
        if(loadingView==null){
            loadingView = (ViDefaultLoadingView) findViewById(R.id.loadingView);
        }
        return loadingView;
    }

    /**
     * 获取下拉刷新控件
     *
     * @return
     */
    public MyPtrFrameLayout getSwipeRefreshLayout() {
        return ptrFrame;
    }

    /***
     * 获取 recyclerView
     *
     * @return
     */
    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 设置列表适配器
     *
     * @param adapter
     */
    protected void setAdapter(BaseQuickAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMoreListener();
            }
        });
        this.adapter.openLoadMore(true);
        recyclerView.setAdapter(this.adapter);
    }

    /**
     * 结束下拉刷新
     */
    protected void completePullDownRefresh() {
        ptrFrame.refreshComplete();
    }

    /**
     * 创建列表,程序从这里开始
     *
     * @param savedInstanceState
     */
    protected abstract void onCreateListView(Bundle savedInstanceState);

    /***
     * 下拉刷新回调方法
     */
    protected abstract void onPullDownRefreshListener();

    /***
     * 加载更多回调方法
     */
    protected abstract void onLoadMoreListener();

}
