package com.byb.vidio.net.api;

import com.byb.lazynetlibrary.net.http.HttpRequestManager;
import com.byb.lazynetlibrary.net.http.LoadingViewInterface;
import com.byb.lazynetlibrary.net.http.RequestLifecycleContext;
import com.byb.lazynetlibrary.net.http.TextResponseListener;
import com.byb.lazynetlibrary.net.http.cache.HttpCacheLoadType;
import com.byb.vidio.MyApplication;
import com.byb.vidio.R;
import com.byb.vidio.model.ViTestModel;
import com.byb.vidio.model.ViUserInfoModel;
import com.byb.vidio.model.ViZBTestModel;
import com.byb.vidio.net.WJRequestParam;
import com.byb.vidio.net.WJResponseListener;
import com.byb.vidio.net.WrapResponseListener;
import com.byb.vidio.net.http.cache.WJCacheResponseListener;
import com.byb.vidio.net.http.cache.WrapCacheResponseListener;
import com.byb.vidio.ui.commom.DefaultLoadingView;
import com.byb.vidio.ui.commom.LoadingView;
import com.byb.vidio.ui.commom.ViDefaultLoadingView;
import com.byb.vidio.utils.DeviceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baoyb on 2017/7/26.
 */

public class ApiManager {

    private static final long maxCacheAge = 60 * 24 * 7L;
    private static ApiManager apiManager = new ApiManager();

    private HttpRequestManager requestManager;

    /***
     * 请求头,公共部分,所有接口都需要传的字段(其实也是放在请求体中)
     */
    private Map<String, String> headers;

    public static ApiManager instance() {
        return apiManager;
    }

    private ApiManager() {
        requestManager = MyApplication.getApplication().getHttpDataManager();
        headers = new HashMap<String, String>();
        headers.putAll(initHeader());
    }

    /***
     * 初始化请求头
     *
     * @return
     */
    private Map initHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("devicetype", "android");//客户端类型(IOS，ANDROID)
        map.put("version", "1.0.0");    //app版本
        map.put("deviceid", DeviceUtil.getDevice());    //序列号
        map.put("sys", DeviceUtil.getPhoneModel());     //手机型号
        map.put("sysversion", DeviceUtil.getSysVersion());  //android系统版本
        return map;
    }

    /**
     * 得到公共参数
     *
     * @return
     */
    public Map<String, String> getHeaders() {
        return headers;
    }


    /**
     * 无缓存例子
     * @param lifecycleContext
     * @param mblNo            手机号
     * @param clientId         客户端授权编号
     * @param listener
     */
    public void test(RequestLifecycleContext lifecycleContext, String mblNo,
                     String clientId, WJResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_lizi, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
        param.addSendData("cate_id", "1");
        param.addSendData("page", "1");
        requestManager.sendHttpPostRequest(lifecycleContext,
                param,
                new DefaultLoadingView(lifecycleContext.getCurrContext()),
                new WrapResponseListener<ViTestModel>(listener) {
                });
    }

    /**
     * 有缓存例子
     *
     * @param lifecycleContext
     * @param loadingView
     * @param listener
     */
    public void getIndexMsg(RequestLifecycleContext lifecycleContext, ViDefaultLoadingView loadingView,
                            WJCacheResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_lizi, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
        HttpCacheLoadType loadType = HttpCacheLoadType.NOT_USE_CACHE_UPDATE_CACHE;
        LoadingViewInterface loadingViewInterface = null;
        if (loadingView != null) {
            loadType = HttpCacheLoadType.USE_CACHE_AND_NET_UPDATE_CHCHE;
            loadingViewInterface = new LoadingView(loadingView);
        }
        requestManager.sendCacheHttpPostRequest(lifecycleContext,
                param,
                loadingViewInterface,
                new WrapCacheResponseListener<ViTestModel>(listener) {
                }, loadType, maxCacheAge);
    }

    /**
     * 登录
     * @param lifecycleContext
     * @param mobile
     * @param code
     * @param listener
     */
    public void login(RequestLifecycleContext lifecycleContext, String mobile,
                      String code, WJResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_login, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
//        param.addSendData("mobile", mobile);
//        param.addSendData("code", code);
        param.addSendData("username", mobile);
        requestManager.sendHttpPostRequest(lifecycleContext,
                param,
                new DefaultLoadingView(lifecycleContext.getCurrContext()),
                new WrapResponseListener<ViUserInfoModel>(listener) {
                });

    }


    /**
     * 志冰Post测试
     * @param lifecycleContext
     * @param listener
     */
    public void zbPostTest(RequestLifecycleContext lifecycleContext, WJResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_zb_test, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
//        param.addSendData("username", "13600000000");
//        param.addSendData("password", "111111");
        Map body = new HashMap();
        body.put("username", "13600000000");
        body.put("password", "111111");
        param.addSendData("body", body);

        requestManager.sendHttpPostRequest(lifecycleContext,
                param,
                new DefaultLoadingView(lifecycleContext.getCurrContext()),
                new WrapResponseListener<ViZBTestModel>(listener) {
                });
    }

    /**
     * 志冰Get测试
     * @param lifecycleContext
     * @param listener
     */
    public void zbGetTest(RequestLifecycleContext lifecycleContext, TextResponseListener listener) {
        WJRequestParam param = new WJRequestParam(R.string.url_zb_test, headers);
        if (requestManager.isExistTask(param.getMessageId())) return;
        param.addSendData("username", "13600000000");
//        param.addSendData("password", "111111");
        requestManager.sendHttpGetRequest(lifecycleContext,
                param,
                new DefaultLoadingView(lifecycleContext.getCurrContext()), listener
        );

    }
}
