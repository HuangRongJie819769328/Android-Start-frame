package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

public interface UpdateDownloadBack {

	public void onStartDownload(Context context);

	/**
	 * ���ؽ���
	 * 
	 * @param percent
	 *            ���ذٷֱ�
	 * @param progress
	 *            �Ѿ����ص�
	 * @param count
	 *            �ܵĴ�С
	 */
	public void onProgress(float percent, float progress, float count);

	public void onEndDownload();
}
