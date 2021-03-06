/**
 * 文 件 名:  WJRequestParam.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.net;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.byb.lazynetlibrary.logger.LazyLogger;
import com.byb.lazynetlibrary.net.http.core.RequestParam;
import com.byb.vidio.MyApplication;
import com.byb.vidio.config.AppConfing;
import com.byb.vidio.config.UrlDefines;
import com.byb.vidio.utils.AESUtil;
import com.byb.vidio.utils.SHA1;
import com.byb.vidio.utils.StringUtils;
import com.byb.vidio.utils.ViSecurityUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WJRequestParam extends RequestParam {
    /**
     * 是否使用加密
     */
    private boolean isUseEncrypt;

    /**
     * 请唯一标识
     */
    private String unique;

    /**
     * 要发送的数据
     */
    private String sendData;

    public WJRequestParam(@StringRes int urlResId, @NonNull Map headers) {
        super(urlResId, "");
        setConnectTimeOut(10*1000);
        setReadTimeOut(10*1000);
        setUrl(createUrl(urlResId));
        Map header = new HashMap();
        header.putAll(headers);
        header.put("timestamp", StringUtils.getCustomTime());

//        addSendData(headers);
//        addSendData("timestamp", StringUtils.getCustomTime());//请求时间
        if (true) {
            header.put("token", "1234566666666");
//            addSendData("token", "1234566666666");//令牌ID(登录后必传)
        }

        addSendData("header", header);
//        isUseEncrypt = true;
        isUseEncrypt = false;
    }

    private String createUrl(@StringRes int resId) {
        StringBuilder builder = new StringBuilder(UrlDefines.BASE_API_URL_RELEASE);
        builder.append(MyApplication.getApplication().getString(resId));
        return builder.toString();
    }

    @Override
    public String getSendData() {
        String target = sendData;
        if (target == null) {
            target = JSON.toJSONString(getUrlWithPsaram(), SerializerFeature.BrowserCompatible);
            if (AppConfing.IS_DEBUG) {
                LazyLogger.d("发送的数据");
                LazyLogger.json(target);
            }
            if (isUseEncrypt) {
//                String sign = toSHA1(getUrlWithPsaram());
//                target = target + sign;
//                try {
//                    target = XXTEA.encryptWithBase64(target, GSAppConfing.XXTEA_KET.getBytes(), "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    LazyLogger.e(e, "转换错误");
//                }
                try {
                    target = ViSecurityUtils.encrypt(target);
                } catch (Exception e) {
                    LazyLogger.e(e, "转换错误");
                }

//                try {
//                    byte[] datas = AESUtil.encrypt(target.getBytes());
//                    target = AESUtil.base64Encode(datas);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
            sendData = target;
        }
        return target;
    }

    @Override
    public String getUnique() {
        if (unique == null) {
            StringBuilder st = new StringBuilder();
            for (String key : getUrlWithPsaram().keySet()) {
                if ("appVersion".equals(key)
                        || "osVersion".equals(key)
                        || "termTyp".equals(key)
                        || "channelId".equals(key)
                        || "termId".equals(key)
                        || "deviceId".equals(key)
                        || "requestTm".equals(key)) {
                    continue;
                }
                st.append(getUrlWithPsaram().get(key));
            }
            unique = st.toString();
        }
        if (unique == null) {
            return super.getUnique();
        } else {
            return unique;
        }
    }

    @Override
    public void cleanWithPsaram() {
        super.cleanWithPsaram();
        sendData = null;
    }

    @Override
    public void clean() {
        super.clean();
        sendData = null;
        unique=null;
    }

    /**
     * SHA1加密
     *
     * @param paramMap
     * @return
     */
    private String toSHA1(@NonNull Map<String, Object> paramMap) {

        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        String codes = stringBuilder.toString();
        return SHA1.SHA_1(codes);
    }

    /**
     * 获取是否使用加密
     *
     * @return
     */
    public boolean isUseEncrypt() {
        return isUseEncrypt;
    }

    /**
     * 设置是否使用加密
     *
     * @param isUseEncrypt
     */
    public void setIsUseEncrypt(boolean isUseEncrypt) {
        this.isUseEncrypt = isUseEncrypt;
    }
}
