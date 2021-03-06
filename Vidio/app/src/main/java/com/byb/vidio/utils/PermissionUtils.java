/**
 * 文 件 名:  PermissionUtils.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/8/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.byb.vidio.utils;

import android.content.pm.PackageManager;

import com.byb.vidio.MyApplication;


/**
 * 检测是否有当前权限
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/8/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PermissionUtils {

    /**
     * 检查是否有这个权限
     * @param permissionName
     * @return
     */
    public static boolean checkPermission(String permissionName) {
        PackageManager pm = MyApplication.getApplication().getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permissionName, MyApplication.getApplication().getPackageName()));
        if (permission) {
            return true;
        }
        return false;
    }

}
