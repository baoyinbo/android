package com.byb.vidio.config;

import android.widget.Toast;

import com.byb.vidio.utils.ToastShowUtils;

/**
 * 一些公共的定义
 */
public class CommonDefines {
    /***
     * 当前账号被踢的dialog是否是显示状态
     */
    public static boolean isShowKickOutDialog;

    /**
     * 短信验证码发送号后5位
     * 发送号码会更改，但后缀几位不变
     */
    public static final String SMS_SENDER = "19404";

    /**
     * 一页数据的数量
     */
    public static final int PAGE_SIZE = 20;

    public static final int REQUEST_CODE_selectcity = 1001;
    /**房子精确筛选*/
    public static final int REQUEST_CODE_housePreciseSift = 1002;
    /**学校选择*/
    public static final int REQUEST_CODE_selectSchool = 1003;
    /**室友筛选*/
    public static final int REQUEST_CODE_roommateSift = 1004;
    /**账单详情*/
    public static final int REQUEST_CODE_billDetail = 1005;
    /**修改昵称或者真实姓名*/
    public static final int REQUEST_CODE_alterName = 1006;
    /**转租房源*/
    public static final int REQUEST_CODE_subletHouse = 1007;
    /**支付*/
    public static final int REQUEST_CODE_PAYMENT = 1008;
    /**国家选择*/
    public static final int REQUEST_CODE_Country_Select = 1009;
    /**评价*/
    public static final int REQUEST_CODE_evaluate = 1010;
    /**去到相册*/
    public static final int REQUEST_CODE_toAlbum = 1011;
    /**去拍照*/
    public static final int REQUEST_CODE_toPhotograph= 1012;
    /**图片裁切*/
    public static final int REQUEST_CODE_toImageCrop=1013;
    /**上传图片*/
    public static final int REQUEST_CODE_toUploadImage=1014;

    /**
     * 显示toast提示
     *
     * @param text
     * @see [类、类#方法、类#成员]
     */
    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast提示
     *
     * @param text
     * @param millisecond
     * @see [类、类#方法、类#成员]
     */
    public static void showToast(String text, int millisecond) {
        ToastShowUtils.showTextToast(text);
    }

    /**
     * 账号被踢
     *
     * @param context
     */
//    public static void kickLogout(Context context) {
//        if (CommonDefines.isShowKickOutDialog) return;
//        CommonDefines.isShowKickOutDialog = true;
//        Dialog dialog = DialogUtil.getAlertDialog(context, "", "您的账号在其他地方登录，如非本人操作请及时更换密码", "确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                CommonDefines.isShowKickOutDialog = false;
//                WLHMainActivity mainActivity = WLHApplication.getApplication().getActivityStackManager().findActivity(WLHMainActivity.class);
//                if (mainActivity != null) {
//                    mainActivity.backToLogout(true);
//                }
//                WLHUserInfoConfig.deteleUserInfo();
//                WLHLoginStateWrapper.starLogintActivity(WLHApplication.getApplication().getActivityStackManager().topActivity());
//                dialog.dismiss();
//            }
//        });
//        WLHUserInfoConfig.deteleUserInfo();
//        dialog.show();
//    }

}
