package com.byb.applifelibrary;

import android.content.Intent;

/**
 * fragemnt结果反馈接口
 *
 */
public interface FragmentResultCallback {

	/**
	 * 结果反馈回调
	 * @param requestCode 请求编码
	 * @param resultCode 结果编码
	 * @param data 
	 * @see [类、类#方法、类#成员]
	 */
	void onFragmentResult(int requestCode, int resultCode, Intent data);
}
