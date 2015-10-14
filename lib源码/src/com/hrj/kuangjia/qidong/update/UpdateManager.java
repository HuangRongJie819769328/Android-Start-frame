package com.hrj.kuangjia.qidong.update;

import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.hrj.kuangjia.qidong.Qidong_Util;

/**
 * 检查更新
 */
public class UpdateManager {

	/* 保存解析的XML信息 */
	public static HashMap<String, String> mHashMap;

	private Context mContext;

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 检测软件更新
	 */
	public boolean checkUpdate(HashMap<String, String> obj) {
		mHashMap = obj;
		boolean flag = isUpdate(obj);
		// 检测更新
		if (flag) {
			// 显示软件更新进行回调
			Qidong_Util.updateShowBack.onNeedUpdate(mContext,
					obj.get("description"), Qidong_Util.updateUserChoice);
		} else
			Qidong_Util.updateShowBack.onNotUpdate();
		return flag;
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate(HashMap<String, String> mHashMap) {
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		if (null != mHashMap) {
			int serviceCode = Integer.valueOf(mHashMap.get("version"));
			// 版本判断
			if (serviceCode > versionCode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

}