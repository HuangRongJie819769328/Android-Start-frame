package com.hrj.text.qidongkuangjia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.Toast;

import com.hrj.kuangjia.qidong.Qidong_Util;
import com.hrj.kuangjia.qidong.myinterface.EndIntent;
import com.hrj.kuangjia.qidong.myinterface.UpdateDownloadBack;
import com.hrj.kuangjia.qidong.myinterface.UpdateShowBack;
import com.hrj.kuangjia.qidong.myinterface.UpdateUserChoice;
import com.hrj.kuangjia.qidong.myinterface.WaitingloadDate;
import com.hrj.kuangjia.qidong.myinterface.WaitingloadDateEnd;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 启动框架（启动页，引导页，自动更新）
		initqidong();
	}

	private void initqidong() {
		// 初始化启动框架
		Qidong_Util.init(this);
		// 设置启动页可以用
		Qidong_Util.setSplashEnabled(true);
		// 设置自己的启动页布局
		Qidong_Util.setSplashLayout(R.layout.splash);
		// 设置引导页可以用
		Qidong_Util.setGuideEnabled(true);
		// 设置自己的引导页布局
		ArrayList<Integer> map_r_layout = new ArrayList<Integer>();
		map_r_layout.add(R.layout.what_new_one);
		map_r_layout.add(R.layout.what_new_two);
		map_r_layout.add(R.layout.what_new_three);
		Qidong_Util.setGuideLayout(map_r_layout);
		// 设置自动更新可以用
		Qidong_Util.setUpdateEnabled(true);
		// httppath 更新网址updateShowBack 是否更新的回调事件updateDownloadBack 下载回调事件
		Qidong_Util.setUpdateUrl(
				"http://tashan.wicp.net/gaoxiaobang/version.xml",
				new UpdateShowBack() {
					// 不需要更新
					public void onNotUpdate() {
						Toast.makeText(getApplicationContext(), "不需要更新", 0)
								.show();
					}

					// 需要更新,这里有context传下来
					public void onNeedUpdate(final Context context,
							String description,
							final UpdateUserChoice updateUserChoice) {
						// ---------回调给开发者用户设置自己的界面---------
						// 用户选择了yes
						// updateUserChoice.onYes(context);
						// 用户选择了no
						// updateUserChoice.onNo();
						AlertDialog.Builder ab = new AlertDialog.Builder(
								context).setTitle("更新").setMessage(description);
						ab.setNegativeButton("更新",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										// 用户选择了yes
										updateUserChoice.onYes(context);
									}
								});
						ab.setPositiveButton("不用",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										// 用户选择了no
										updateUserChoice.onNo();
									}
								});
						ab.setCancelable(false);
						ab.show();
					}
				}, new UpdateDownloadBack() {
					AlertDialog dialog;
					SeekBar slider;

					// 开始下载
					public void onStartDownload(Context context) {
						slider = new SeekBar(context);
						slider.setMax(100);
						slider.setProgress(0);
						AlertDialog.Builder db = new AlertDialog.Builder(
								context);
						db.setTitle("下载中");
						db.setView(slider);
						db.setCancelable(false);
						dialog = db.show();
					}

					// 下载进度
					public void onProgress(float percent, float progress,
							float count) {
						slider.setProgress((int) percent);
					}

					// 结束下载
					public void onEndDownload() {
						dialog.cancel();
					}

				});
		// 如果有数据需要加载的，放在这里加载，一直到加载完成才会跳过页面
		Qidong_Util.setWaitingloadDate(new WaitingloadDate() {
			public void onLoadDate(Context context,
					final WaitingloadDateEnd waitingloadDateEnd) {
				// 你可以进行下载需要的数据，到最后调用一下waitingloadDateEnd.onEnd()就好
				// 更新数据
				new Thread() {
					public void run() {
						// 这里是事例，延时2秒在调用
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						waitingloadDateEnd.onEnd();
					}
				}.start();
			}
		});
		// 启用启动框架,第一个参数是引导页需要实现点击跳转的id，没有使用引导页，设置null
		Qidong_Util.start(R.id.iv_start_weibo, new EndIntent() {
			public void onClick(Context context) {
				// 完成回调,最后你打算跳转到那个界面
				Intent intent = new Intent(context, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}
}
