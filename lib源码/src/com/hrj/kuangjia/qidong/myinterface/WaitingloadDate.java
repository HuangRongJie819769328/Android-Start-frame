package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

/**
 * �ȴ��û����ݼ���
 * 
 * @author ���ٽ�
 * 
 */
public interface WaitingloadDate {
	/**
	 * �û���������
	 * 
	 * @param context
	 *            ������
	 * @param waitingloadDateEnd
	 */
	public void onLoadDate(Context context,
			WaitingloadDateEnd waitingloadDateEnd);
}
