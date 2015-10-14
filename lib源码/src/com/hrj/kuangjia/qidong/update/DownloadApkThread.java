package com.hrj.kuangjia.qidong.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.hrj.kuangjia.qidong.myinterface.UpdateDownloadBack;

public class DownloadApkThread extends Thread {

	/* 下载保存路径 */
	private String mSavePath;

	/* 下载网址 */
	private String url;

	/* 文件名 */
	private String name;

	private Context context;

	private UpdateDownloadBack updateDownloadBack;

	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				if (null != updateDownloadBack)
					updateDownloadBack.onProgress(msg.arg1, msg.arg2,
							Float.parseFloat(msg.obj.toString()));
				break;
			case DOWNLOAD_FINISH:
				if (null != updateDownloadBack)
					updateDownloadBack.onEndDownload();
				break;
			default:
				break;
			}
		};
	};

	public DownloadApkThread(Context context, HashMap<String, String> mHashMap,
			UpdateDownloadBack updateDownloadBack) {
		this.updateDownloadBack = updateDownloadBack;
		url = mHashMap.get("url");
		name = mHashMap.get("name");
		this.context = context;
		if (null != updateDownloadBack)
			updateDownloadBack.onStartDownload(context);
	}

	public void run() {
		try {
			// 判断SD卡是否存在，并且是否具有读写权限
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// 获得存储卡的路径
				String sdpath = Environment.getExternalStorageDirectory() + "/";
				mSavePath = sdpath + "download";
				URL url = new URL(this.url);
				// 创建连接
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				// 获取文件大小
				int length = conn.getContentLength();
				// 创建输入流
				InputStream is = conn.getInputStream();

				File file = new File(mSavePath);
				// 判断文件目录是否存在
				if (!file.exists()) {
					file.mkdir();
				}
				File apkFile = new File(mSavePath, name);
				FileOutputStream fos = new FileOutputStream(apkFile);
				int count = 0;
				// 缓存
				byte buf[] = new byte[1024];
				int numread, percent;
				// 写入到文件中
				do {
					numread = is.read(buf);
					count += numread;
					// 计算进度条位置
					percent = (int) (((float) count / length) * 100);
					mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD,
							percent, count, length));
					// 更新进度
					if (numread <= 0) {
						// 下载完成
						mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
						break;
					}
					// 写入文件
					fos.write(buf, 0, numread);
				} while (true);
				fos.close();
				is.close();
				// 安装apk
				installApk();
				// 杀掉自己进程
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, name);
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}
