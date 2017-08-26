/**
 * 文 件 名:  WLHSecurityUtils.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  2017/3/26
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.utils;

import android.support.annotation.NonNull;

import com.byb.lazynetlibrary.logger.LazyLogger;
import com.byb.lazynetlibrary.net.http.core.JSONHttpResponseHandler;
import com.byb.vidio.config.AppConfing;
import com.byb.vidio.utils.base64.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 加解密工具
 *
 * @author 江钰锋 00501
 * @version [版本号, 2017/3/26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ViSecurityUtils {

    private final static String charsetName="UTF-8";

    /***
     * 解密
     * @param data
     * @return
     */
    public static byte[] decrypt(byte[] data) {
        String content=new String(data);
        return AESUtils.decrypt(Base64Utils.decodeFromString(content),Base64Utils.decodeFromString(AppConfing.SECURITY_KET));
    }


    /***
     * 解密
     * @param data
     * @return
     */
    public static byte[] decrypt(String data) {

        byte[] twoStrResult = parseHexStr2Byte(data);

        // 解密
//        byte[] decrypt = decrypt(twoStrResult, AppConfing.SECURITY_KET);

//        String content=new String(data);
        return AESUtils.decrypt(twoStrResult,Base64Utils.decodeFromString(AppConfing.SECURITY_KET));
    }

    /**
     * 加密
     * @param data
     * @return
     */
    public static String encrypt(byte[] data) {
        byte[] target=AESUtils.encrypt(data,Base64Utils.decode(AppConfing.SECURITY_KET.getBytes()));
        String hexStrResult = parseByte2HexStr(target);
//        return toURLEncoded(new String(Base64Utils.encode(target)));
        return hexStrResult;
    }

    /***
     * 加密
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        return encrypt(data.getBytes());
    }

    public static String toURLEncoded(@NonNull String paramString) {
        try
        {
            String str = new String(paramString.getBytes(), charsetName);
            str = URLEncoder.encode(str, charsetName);
            return str;
        }
        catch (Exception localException)
        {
            LazyLogger.e("toURLEncoded error:"+paramString, localException);
        }
        return "";
    }

    public static String toURLDecoded(@NonNull String paramString) {
        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
            LazyLogger.e("toURLDecoded error:"+paramString, localException);
        }

        return "";
    }

    public static void printHexString(byte[] b)
    {
        for (int i = 0; i < b.length; i++)
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }


    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) {
//        System.out.println("AES解密结果:");
//        printHexString(Base64Utils.decode(WLHAppConfing.SECURITY_KET.getBytes()));
//        System.out.println("AES解密结果:" + new String(decrypt("vAacFp1yaWZ1BygxvjUH1slvjJNPBqfl%2FclWRleG60Fti7lsRn9rKXchX8BksGaOjHGRIt8gfJyBjWJOTW7MtSKt68s1IJL05yV%2FVtNlxwyLKVl55ClKmbCv%2F7CAEMNPL2QQ1%2BQo5xu1ANOzf5RlAvAg4ktVrrjUOmYA7wboQaAQ9WPwftSmLGIhGrW1vP94UphcQK2XQbwXb9pLfocGzxWjf1zvRVBA%2FT0O0xTIln4%3D\n")));
        System.out.println("AES加密结果:" + encrypt("{'totalCardCount':'0','userId':'63AFD0C85532B63252A31E27B505A5FE'}"));

        byte[] twoStrResult = parseHexStr2Byte("0D00AA30736476F7804FDB86FD7DB51BFE768496070C24911678808029AE82479A93BEB4A3E8804CF2342129D34066B9A3CF6B2271A82A54CD52A92B1EB2FC6EC2FBF9C799D2C2F57F7015EC8BD99F83");

        // 解密
        byte[] decrypt = decrypt(twoStrResult);
        System.out.println("AES解密结果:" + new String(decrypt));
//        System.out.println("AES解密结果:" + AESUtils.decrypt("666109B9FA0B1B53B00D1B4EAB9B2D7C688517D652A7BC00DB96AB54D85C2E1217721C04813D30B58A767DCE89B2EF78FD992ACC682C7CD999C302B8A630FA57",Base64Utils.decodeFromString(AppConfing.SECURITY_KET)));



//        System.out.println("AES解密结果:" + decrypt("38clO0mDyhjx13Vd%2BxQVPv0Jlmrbn5bLDgTjfE7lknY65dUfTUGJi6JCCV2vr7RQXNDxGW1faePv791aN5xte1ypPa1dA0tcazVspZYB0HRHbp%2B8Hmu4uLUvXYq0AXk0".getBytes()));

//        System.out.println("AES解密结果:" + AESUtils.decrypt(Base64Utils.decodeFromString("DQCqMHNkdveAT9uG%2FX21G%2F52hJYHDCSRFniAgCmugkeak760o%2BiATPI0ISnTQGa5o89rInGoKlTNUqkrHrL8bsL7%2BceZ0sL1f3AV7IvZn4M%3D"),Base64Utils.decodeFromString(AppConfing.SECURITY_KET)));

//        String context="{\n" +
//                "    \"ResultCode\": 0,\n" +
//                "    \"reason\": \"请求成功\"\n" +
//                "}";
//        System.out.println("AES加密结果:" + AESUtils.encrypt(cc.getBytes(),Base64Utils.decodeFromString(AppConfing.SECURITY_KET)));
    }
}
