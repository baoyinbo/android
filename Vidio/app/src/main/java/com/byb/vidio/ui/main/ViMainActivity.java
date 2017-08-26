package com.byb.vidio.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.byb.vidio.R;
import com.byb.vidio.ui.base.BaseActivity;
import com.byb.vidio.utils.ToastShowUtils;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by baoyb on 2017/8/4.
 */

public class ViMainActivity extends BaseActivity {

    private FragmentTabHost fragmentTabHost;
    public static final String TAB_FRIEND = "TAB_FRIEND";   //朋友
    public static final String TAB_VIDEO = "TAB_VIDEO";     //视频
    public static final String TAB_MY = "TAB_MY";           //我的

    private static final String[] tabs = {
            TAB_FRIEND, TAB_VIDEO, TAB_MY
    };
    private static final String[] tabText = {"", "", ""};
    private static final int[] tabImage = {
            R.drawable.vi_tab_friend_selector,
            R.drawable.vi_tab_video_selector,
            R.drawable.vi_tab_my_selector
    };
    private static final Class[] fragments = {
            ViFriendFragment.class, ViVideoFragment.class, ViMyFragment.class,
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void doCreate(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        setContentView(R.layout.vi_act_main);

        //是否需要跳转h5
//        Intent intent = getIntent();
//        if (intent != null) {
//            String sJumpClass = intent.getStringExtra("jump");
//            String url = intent.getStringExtra("url");
//            LazyLogger.d(url);
//            boolean bJumpWebView = !TextUtils.isEmpty(sJumpClass)
//                    && sJumpClass.equals(GSWebViewFragment.class.getSimpleName());
//            if (bJumpWebView) {
//                Bundle bundle = new Bundle();
//                bundle.putString("url", url);
//                LaunchBody.Builder builder = new LaunchBody.Builder(GXBMainActivity.this,
//                        GSWebViewFragment.class);
//                builder.bundle(bundle);
//                builder.launchType(LaunchBody.LaunchType.SINGLE_TOP);
//                CommonActivity.launch(builder);
//            }
//        }
    }

    @Override
    public void initView() {
//        initSystemBarTint(false, R.color.white);

        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.maincontent);
        fragmentTabHost.getTabWidget().setDividerDrawable(R.color.transparent); //去除分割线
        for (int i = 0; i < tabs.length; i ++) {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(tabs[i]).
                    setIndicator(getIndicatorView(i));
            fragmentTabHost.addTab(spec, fragments[i], null);
        }
        //默认启动显示第二个页面
        fragmentTabHost.setCurrentTab(1);
    }

    private View getIndicatorView(int i) {
        View view = View.inflate(this, R.layout.vi_tab_item, null);
        ImageView ivTabItem = (ImageView) view.findViewById(R.id.ivTabItem);
//        TextView tvTabItem = (TextView) view.findViewById(R.id.tvTabItem);
        ivTabItem.setImageResource(tabImage[i]);
//        tvTabItem.setText(tabText[i]);
        return view;
    }

    private int isFirstBack = 1;
    @Override
    public void onBackPressed() {
        if (isFirstBack == 1) {
            ToastShowUtils.showTextToast("再按一次退出");
            isFirstBack = 3;
            //开启一个异步线程，当用户超过两秒没有再次点击返回键，则取消退出状态
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isFirstBack = 1; // 取消退出
                }
            }, 1000);
        } else if (isFirstBack == 3) {//单用户连续点击两次的时候，退出程序
            this.finish();
            System.exit(0);
        }
    }
}
