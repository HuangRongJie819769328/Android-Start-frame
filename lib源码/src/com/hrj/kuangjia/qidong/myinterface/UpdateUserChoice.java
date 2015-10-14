package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

/**
 * 用户选择了更新，还是不更新
 * 
 * @author 黄荣洁
 * 
 */
public interface UpdateUserChoice {

	public void onYes(Context context);

	public void onNo();
}
