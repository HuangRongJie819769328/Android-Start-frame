package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

/**
 * 显示软件更新进行回调，将界面交给用户设置
 * 
 * @author 黄荣洁
 * 
 */
public interface UpdateShowBack {
	/**
	 * 需要更新
	 *用户选择了yes
	 *updateUserChoice.onYes(); 
	 *用户选择了no
	 *updateUserChoice.onNo();
	 */
	public void onNeedUpdate(Context context, String description,
			UpdateUserChoice updateUserChoice);

	/**
	 * 不需要更新
	 */
	public void onNotUpdate();
}
