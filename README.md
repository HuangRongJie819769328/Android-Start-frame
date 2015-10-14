# Android-Start-frame
Android-Start-frame (start page, boot page, auto update)

1.添加jar到lib

2.往自己项目的layout里放guide.xml这个文件

3.往自己项目的drawable里放btn_guide_go.xml这文件，如果没有drawable，可以自己建立一个

4.复制包com.hrj.kuangjia.qidong.view到自己的项目，将R错误修改

5.manifest里面需要声明，你用了启动页声明SplashActivity，并且作为主入口！用了引导页声明GuideActivity

          <activity
            android:name="com.hrj.kuangjia.qidong.view.SplashActivity"
            android:launchMode="singleTop"
            android:theme="@android:style/Theme.NoTitleBar" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.hrj.kuangjia.qidong.view.GuideActivity"
            android:launchMode="singleTop" >
        </activity>
        
6.当你使用了自动更新时添加权限

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
    <uses-permission android:name="android.permission.INTERNET" />
    
7.这是自动更新的version.xml文件格式，请勿修改,放到自己服务器

<update>
	<version>2</version>
	<name>baidu_xinwen_1.1.0</name>
	<description>动态改变更新提示：软件内容</description>
	<url>http://gdown.baidu.com/data/wisegame/f98d235e39e29031/baiduxinwen.apk</url>
</update>

8.详细介绍
		请在自己的application的onCreate里调用
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
		
9.manifest里面需要声明自己自定义的application name，最终预览应该如下

    <application
        android:name="com.hrj.text.qidongkuangjia.MyApplication"
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name="com.hrj.kuangjia.qidong.view.SplashActivity"
            android:launchMode="singleTop"
            android:theme="@android:style/Theme.NoTitleBar" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.hrj.kuangjia.qidong.view.GuideActivity"
            android:launchMode="singleTop" >
        </activity>
        <activity android:name="com.hrj.text.qidongkuangjia.MainActivity" >
        </activity>
    </application>
		
