package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

/**
 * ��ʾ������½��лص��������潻���û�����
 * 
 * @author ���ٽ�
 * 
 */
public interface UpdateShowBack {
	/**
	 * ��Ҫ����
	 *�û�ѡ����yes
	 *updateUserChoice.onYes(); 
	 *�û�ѡ����no
	 *updateUserChoice.onNo();
	 */
	public void onNeedUpdate(Context context, String description,
			UpdateUserChoice updateUserChoice);

	/**
	 * ����Ҫ����
	 */
	public void onNotUpdate();
}
