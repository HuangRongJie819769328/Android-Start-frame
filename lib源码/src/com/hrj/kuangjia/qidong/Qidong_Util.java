package com.hrj.kuangjia.qidong;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.hrj.kuangjia.qidong.myinterface.EndIntent;
import com.hrj.kuangjia.qidong.myinterface.UpdateDownloadBack;
import com.hrj.kuangjia.qidong.myinterface.UpdateShowBack;
import com.hrj.kuangjia.qidong.myinterface.UpdateUserChoice;
import com.hrj.kuangjia.qidong.myinterface.WaitingloadDate;
import com.hrj.kuangjia.qidong.update.DownloadApkThread;
import com.hrj.kuangjia.qidong.update.UpdateManager;
import com.hrj.kuangjia.qidong.view.SplashActivity;

/**
 * ������� ��������ҳ������ҳ���Զ�����
 * 
 * @author ���ٽ�
 * 
 */
public class Qidong_Util {

	static Context context;

	// -------����״��---------------------
	public static boolean SplashEnabled = false;
	public static boolean GuideEnabled = false;
	public static boolean UpdateEnabled = false;
	// --------���ִ��---------------------
	public static Integer SplashLayout = 0;
	public static ArrayList<Integer> GuideLayout;
	// ---------���-----------------------
	// �������id
	public static Integer click_r_id;
	// ������ĵ���ص��¼�
	public static EndIntent endIntent;
	// ---------����-------------------------
	// ���µ���ַ
	public static String updateurl;
	// ��ʾ������½��лص�
	public static UpdateShowBack updateShowBack;
	// ���ػص��¼�
	static UpdateDownloadBack updateDownloadBack;
	// �ȴ��û����ݼ���
	public static WaitingloadDate waitingloadDate;

	// �û�ѡ���˸��»��ǲ����»ص�
	public static UpdateUserChoice updateUserChoice = new UpdateUserChoice() {
		public void onYes(Context context) {
			new DownloadApkThread(context, UpdateManager.mHashMap,
					updateDownloadBack).start();
		}

		public void onNo() {
			// ������һ��
			SplashActivity.flagUpdate = true;
		}
	};

	/**
	 * ��ʼ�����
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		Qidong_Util.context = context;
	}

	/**
	 * ��������ҳ�Ƿ����
	 * 
	 * @param flag
	 */
	public static void setSplashEnabled(boolean flag) {
		SplashEnabled = flag;
	}

	/**
	 * ��������ҳ����
	 * 
	 * @param r_layout
	 */
	public static void setSplashLayout(int r_layout) {
		SplashLayout = r_layout;
	}

	/**
	 * ��������ҳ�Ƿ����
	 * 
	 * @param flag
	 */
	public static void setGuideEnabled(boolean flag) {
		GuideEnabled = flag;
	}

	/**
	 * ��������ҳ����
	 * 
	 * @param r_layout
	 */
	public static void setGuideLayout(ArrayList<Integer> map_r_layout) {
		GuideLayout = map_r_layout;
	}

	/**
	 * �����Զ������Ƿ����
	 * 
	 * @param flag
	 */
	public static void setUpdateEnabled(boolean flag) {
		UpdateEnabled = flag;
	}

	/**
	 * ���ø��µ�url
	 * 
	 * @param httppath
	 *            ��ַ
	 * @param updateShowBack
	 *            �Ƿ���µĻص��¼�
	 * @param updateDownloadBack
	 *            ���ػص��¼�
	 */
	public static void setUpdateUrl(String httppath,
			UpdateShowBack updateShowBack, UpdateDownloadBack updateDownloadBack) {
		updateurl = httppath;
		Qidong_Util.updateShowBack = updateShowBack;
		Qidong_Util.updateDownloadBack = updateDownloadBack;
	}

	/**
	 * �������ݼ��ؼ�����һֱ�����ݼ�����ɲ���������
	 * 
	 * @param waitingloadDate
	 */
	public static void setWaitingloadDate(WaitingloadDate waitingloadDate) {
		Qidong_Util.waitingloadDate = waitingloadDate;
	}

	/**
	 * �������
	 * 
	 * @param click_r_id
	 *            ��Ҫ���е���¼������id
	 * @param myGuideEnd
	 *            ����ص���
	 */
	public static void start(Integer click_r_id, EndIntent endIntent) {
		if (null != click_r_id)
			Qidong_Util.click_r_id = click_r_id;
		Qidong_Util.endIntent = endIntent;
	}

}
