package com.hrj.kuangjia.qidong.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.hrj.kuangjia.qidong.Qidong_Util;
import com.hrj.text.qidongkuangjia.R;

/**
 * ��������
 */
public class GuideActivity extends FragmentActivity {

	private ViewPager vp;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide);

		// ��ʼ��ҳ��
		initViews();

		// �����Ѿ�����
		setGuided();

	}

	private void initViews() {

		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			public Fragment getItem(int arg0) {
				return GuideFragment.getInstance(Qidong_Util.GuideLayout
						.get(arg0));
			}

			public int getCount() {
				return Qidong_Util.GuideLayout.size();
			}

		});

	}

	/**
	 * 
	 * method desc�������Ѿ��������ˣ��´����������ٴ�����
	 */
	private void setGuided() {
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// ��������
		editor.putBoolean("isFirstIn", false);
		// �ύ�޸�
		editor.commit();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Qidong_Util.endIntent.onClick(this);
	}

}
