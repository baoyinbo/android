package com.byb.lazynetlibrary.net.http.core;

import com.byb.lazynetlibrary.net.http.core.callback.BytesResponseCallback;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http网络请求文本数据管理者(返回数据类型为bytes类型,最基本的类型)
 * 
 * @author 江钰锋
 * @version [版本号, 2015年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BytesHttpResponseHandler extends HttpResponseHandler
{
    /**
     * 服务器反馈监听
     */
    private BytesResponseCallback responseCallback;
    
    /**
     * 创建TextHttpResponseHandler对象
     */
    public BytesHttpResponseHandler(BytesResponseCallback responseCallback)
    {
        this.responseCallback = responseCallback;
    }
    
    @Override
    public void sendStartMessage(int messageId)
    {
        if (responseCallback != null)
        {
            responseCallback.sendStartMessage(messageId);
        }
    }
    
    @Override
    public void setConnectProperty(HttpURLConnection urlConnection, ConcurrentHashMap<String, String> sendHeaderMap)
    {
        super.setConnectProperty(urlConnection, sendHeaderMap);
    }
    
    @Override
    public void sendProgressMessage(int messageId, long bytesWritten, long bytesTotal)
    {
        
    }
    
    @Override
    public void readProgressMessage(int messageId, long bytesRead, long bytesTotal)
    {
        
    }
    
    @Override
    public void sendSuccessMessage(int messageId, Map<String,List<String>> headers, byte[] responseByteData)
    {
        if (responseCallback != null)
        {
            responseCallback.sendSuccessMessage(messageId, headers,responseByteData);
        }
        super.sendSuccessMessage(messageId, headers, responseByteData);
    }

    @Override
    public void sendFailMessage(int messageId, int statusCode, Map<String,List<String>> headers, byte[] responseErrorByteData)
    {
        if (responseCallback != null)
        {
            responseCallback.sendFailMessage(messageId, statusCode, headers, responseErrorByteData);
        }
        super.sendFailMessage(messageId, statusCode, headers, responseErrorByteData);
    }
    
    @Override
    public void clean()
    {
    	responseCallback = null;
    }

}
