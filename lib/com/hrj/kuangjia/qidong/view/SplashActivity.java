package com.hrj.kuangjia.qidong.view;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.hrj.kuangjia.qidong.Qidong_Util;
import com.hrj.kuangjia.qidong.myinterface.WaitingloadDateEnd;
import com.hrj.kuangjia.qidong.update.ParseXmlService;
import com.hrj.kuangjia.qidong.update.UpdateManager;

/**
 * 鍚姩鐣岄潰 鍚姩鐢婚潰 (1)鍒ゆ柇鏄惁鏄娆″姞杞藉簲鐢�--閲囧彇璇诲彇SharedPreferences鐨勬柟娉�
 * (2)鏄紝鍒欒繘鍏uideActivity锛涘惁锛屽垯杩涘叆MainActivity (3)3s鍚庢墽琛�(2)鎿嶄綔
 */
public class SplashActivity extends FragmentActivity {
	boolean isFirstIn = false;

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	private static final int UPDATE = 1002;
	// 寤惰繜0绉�
	private static final long SPLASH_DELAY_MILLIS = 0;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	public static boolean flagUpdate;
	public static boolean flagDateEnd;

	// 褰撶敤鎴峰姞杞藉畬鏁版嵁鍚庣殑浜嬩欢
	WaitingloadDateEnd waitingloadDateEnd = new WaitingloadDateEnd() {
		public void onEnd() {
			flagDateEnd = true;
		}
	};

	/**
	 * Handler:璺宠浆鍒颁笉鍚岀晫闈�
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				finish();
				break;
			case GO_GUIDE:
				goGuide();
				finish();
				break;
			case UPDATE:
				UpdateManager updatem = new UpdateManager(SplashActivity.this);
				// 妫�鏌ユ洿鏂�
				if (!updatem.checkUpdate((HashMap<String, String>) msg.obj))
					flagUpdate = true;
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(Qidong_Util.SplashLayout);

		init();

		// 绛夊緟鏁版嵁鍥炶皟
		if (null != Qidong_Util.waitingloadDate)
			Qidong_Util.waitingloadDate.onLoadDate(this, waitingloadDateEnd);
		else
			flagDateEnd = true;
	}

	private void init() {
		flagUpdate = false;
		flagDateEnd = false;
		new Thread() {
			public void run() {

				update();

				// 濡傛灉闇�瑕佹洿鏂帮紝鏆傛椂涓嶈烦杞�
				while (true) {
					// System.out.println(flagUpdate +""+ flagDateEnd);
					if (flagUpdate && flagDateEnd) {
						initSharedPreferences();
						break;
					}
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	private void update() {
		// 寮�鍚嚎绋嬭幏鍙栨枃浠舵祦鐒跺悗瑙ｆ瀽xml
		if (Qidong_Util.UpdateEnabled) {
			try {
				// 鑾峰彇娴�
				HttpURLConnection urlConn = (HttpURLConnection) new URL(
						Qidong_Util.updateurl).openConnection();
				// 瑙ｆ瀽XML鏂囦欢銆� 鐢变簬XML鏂囦欢姣旇緝灏忥紝鍥犳浣跨敤DOM鏂瑰紡杩涜瑙ｆ瀽
				ParseXmlService service = new ParseXmlService();
				HashMap<String, String> mHashMap = null;
				try {
					mHashMap = service.parseXml(urlConn.getInputStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = UPDATE;
				msg.obj = mHashMap;
				mHandler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initSharedPreferences() {
		// 璇诲彇SharedPreferences涓渶瑕佺殑鏁版嵁
		// 浣跨敤SharedPreferences鏉ヨ褰曠▼搴忕殑浣跨敤娆℃暟
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);

		// 鍙栧緱鐩稿簲鐨勫�硷紝濡傛灉娌℃湁璇ュ�硷紝璇存槑杩樻湭鍐欏叆锛岀敤true浣滀负榛樿鍊�
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// 鍒ゆ柇绋嬪簭涓庣鍑犳杩愯锛屽鏋滄槸绗竴娆¤繍琛屽垯璺宠浆鍒板紩瀵肩晫闈紝鍚﹀垯璺宠浆鍒颁富鐣岄潰
		if (!isFirstIn) {
			// 浣跨敤Handler鐨刾ostDelayed鏂规硶锛�3绉掑悗鎵ц璺宠浆鍒癕ainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}

	}

	private void goHome() {
		Qidong_Util.endIntent.onClick(this);
	}

	private void goGuide() {
		if (!Qidong_Util.GuideEnabled) {
			goHome();
			return;
		}
		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		startActivity(intent);
	}
}
