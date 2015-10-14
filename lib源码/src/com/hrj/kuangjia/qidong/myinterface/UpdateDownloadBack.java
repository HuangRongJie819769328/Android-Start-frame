package com.hrj.kuangjia.qidong.myinterface;

import android.content.Context;

public interface UpdateDownloadBack {

	public void onStartDownload(Context context);

	/**
	 * 下载进度
	 * 
	 * @param percent
	 *            下载百分比
	 * @param progress
	 *            已经下载的
	 * @param count
	 *            总的大小
	 */
	public void onProgress(float percent, float progress, float count);

	public void onEndDownload();
}
