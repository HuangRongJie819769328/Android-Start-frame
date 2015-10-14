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
 * 启动框架 包括启动页，引导页，自动更新
 * 
 * @author 黄荣洁
 * 
 */
public class Qidong_Util {

	static Context context;

	// -------启用状况---------------------
	public static boolean SplashEnabled = false;
	public static boolean GuideEnabled = false;
	public static boolean UpdateEnabled = false;
	// --------布局存放---------------------
	public static Integer SplashLayout = 0;
	public static ArrayList<Integer> GuideLayout;
	// ---------点击-----------------------
	// 该组件的id
	public static Integer click_r_id;
	// 该组件的点击回调事件
	public static EndIntent endIntent;
	// ---------更新-------------------------
	// 更新的网址
	public static String updateurl;
	// 显示软件更新进行回调
	public static UpdateShowBack updateShowBack;
	// 下载回调事件
	static UpdateDownloadBack updateDownloadBack;
	// 等待用户数据加载
	public static WaitingloadDate waitingloadDate;

	// 用户选择了更新还是不更新回调
	public static UpdateUserChoice updateUserChoice = new UpdateUserChoice() {
		public void onYes(Context context) {
			new DownloadApkThread(context, UpdateManager.mHashMap,
					updateDownloadBack).start();
		}

		public void onNo() {
			// 进行下一步
			SplashActivity.flagUpdate = true;
		}
	};

	/**
	 * 初始化框架
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		Qidong_Util.context = context;
	}

	/**
	 * 设置启动页是否可用
	 * 
	 * @param flag
	 */
	public static void setSplashEnabled(boolean flag) {
		SplashEnabled = flag;
	}

	/**
	 * 设置启动页布局
	 * 
	 * @param r_layout
	 */
	public static void setSplashLayout(int r_layout) {
		SplashLayout = r_layout;
	}

	/**
	 * 设置引导页是否可用
	 * 
	 * @param flag
	 */
	public static void setGuideEnabled(boolean flag) {
		GuideEnabled = flag;
	}

	/**
	 * 设置引导页布局
	 * 
	 * @param r_layout
	 */
	public static void setGuideLayout(ArrayList<Integer> map_r_layout) {
		GuideLayout = map_r_layout;
	}

	/**
	 * 设置自动更新是否可用
	 * 
	 * @param flag
	 */
	public static void setUpdateEnabled(boolean flag) {
		UpdateEnabled = flag;
	}

	/**
	 * 设置更新的url
	 * 
	 * @param httppath
	 *            网址
	 * @param updateShowBack
	 *            是否更新的回调事件
	 * @param updateDownloadBack
	 *            下载回调事件
	 */
	public static void setUpdateUrl(String httppath,
			UpdateShowBack updateShowBack, UpdateDownloadBack updateDownloadBack) {
		updateurl = httppath;
		Qidong_Util.updateShowBack = updateShowBack;
		Qidong_Util.updateDownloadBack = updateDownloadBack;
	}

	/**
	 * 设置数据加载监听，一直到数据加载完成才跳过界面
	 * 
	 * @param waitingloadDate
	 */
	public static void setWaitingloadDate(WaitingloadDate waitingloadDate) {
		Qidong_Util.waitingloadDate = waitingloadDate;
	}

	/**
	 * 启动框架
	 * 
	 * @param click_r_id
	 *            需要进行点击事件的组件id
	 * @param myGuideEnd
	 *            点击回调事
	 */
	public static void start(Integer click_r_id, EndIntent endIntent) {
		if (null != click_r_id)
			Qidong_Util.click_r_id = click_r_id;
		Qidong_Util.endIntent = endIntent;
	}

}
