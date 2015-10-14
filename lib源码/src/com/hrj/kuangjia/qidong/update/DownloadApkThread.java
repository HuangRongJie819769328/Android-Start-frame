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

	/* ���ر���·�� */
	private String mSavePath;

	/* ������ַ */
	private String url;

	/* �ļ��� */
	private String name;

	private Context context;

	private UpdateDownloadBack updateDownloadBack;

	/* ������ */
	private static final int DOWNLOAD = 1;
	/* ���ؽ��� */
	private static final int DOWNLOAD_FINISH = 2;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// ��������
			case DOWNLOAD:
				// ���ý�����λ��
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
			// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// ��ô洢����·��
				String sdpath = Environment.getExternalStorageDirectory() + "/";
				mSavePath = sdpath + "download";
				URL url = new URL(this.url);
				// ��������
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				// ��ȡ�ļ���С
				int length = conn.getContentLength();
				// ����������
				InputStream is = conn.getInputStream();

				File file = new File(mSavePath);
				// �ж��ļ�Ŀ¼�Ƿ����
				if (!file.exists()) {
					file.mkdir();
				}
				File apkFile = new File(mSavePath, name);
				FileOutputStream fos = new FileOutputStream(apkFile);
				int count = 0;
				// ����
				byte buf[] = new byte[1024];
				int numread, percent;
				// д�뵽�ļ���
				do {
					numread = is.read(buf);
					count += numread;
					// ���������λ��
					percent = (int) (((float) count / length) * 100);
					mHandler.sendMessage(mHandler.obtainMessage(DOWNLOAD,
							percent, count, length));
					// ���½���
					if (numread <= 0) {
						// �������
						mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
						break;
					}
					// д���ļ�
					fos.write(buf, 0, numread);
				} while (true);
				fos.close();
				is.close();
				// ��װapk
				installApk();
				// ɱ���Լ�����
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, name);
		if (!apkfile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}
