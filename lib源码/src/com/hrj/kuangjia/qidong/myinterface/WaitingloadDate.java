package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

/**
 * 等待用户数据加载
 * 
 * @author 黄荣洁
 * 
 */
public interface WaitingloadDate {
	/**
	 * 用户加载数据
	 * 
	 * @param context
	 *            上下文
	 * @param waitingloadDateEnd
	 */
	public void onLoadDate(Context context,
			WaitingloadDateEnd waitingloadDateEnd);
}
