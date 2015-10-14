package com.hrj.kuangjia.qidong.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.hrj.kuangjia.qidong.Qidong_Util;

public class GuideFragment extends Fragment {

	int r_layout;

	public static GuideFragment getInstance(int r_layout) {
		GuideFragment myFragment = new GuideFragment();
		myFragment.r_layout = r_layout;
		return myFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(r_layout, container, false);
		// ���õ���ر�
		if (null != Qidong_Util.click_r_id) {
			View clickview = v.findViewById(Qidong_Util.click_r_id);
			if (null != clickview)
				clickview.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						Qidong_Util.endIntent.onClick(getActivity());
						getActivity().finish();
					}
				});
		}
		return v;
	}
}
