package com.byb.lazynetlibrary.net.http.core.callback;


import com.byb.lazynetlibrary.net.http.core.BytesHttpResponseHandler;
import com.byb.lazynetlibrary.net.http.core.HttpResponseHandler;

/**
 * 请求监听反馈
 * 
 * @author 江钰锋
 * @version [版本号, 2015年6月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BytesResponseCallback implements ResponseCallbackInterface<byte[],byte[]>
{
    
    @Override
    public HttpResponseHandler getHttpResponseHandler()
    {
        return new BytesHttpResponseHandler(this);
    }
    
}
