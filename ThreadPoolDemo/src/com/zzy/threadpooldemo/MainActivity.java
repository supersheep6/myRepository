package com.zzy.threadpooldemo;

import com.zzy.threadpooldemo.fragment.GirdFragment;
import com.zzy.threadpooldemo.fragment.ListFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends FragmentActivity {

	private ViewPager vp_mainVP;
	private String[] pages = new String[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initCtl();
		initData();
	}

	// ��ʼ���ؼ�
	private void initCtl() {
		vp_mainVP = (ViewPager) findViewById(R.id.vp_mainVP);
	}

	// ��ʼ������
	private void initData() {
		vp_mainVP.setAdapter(new PagerAdapter(getSupportFragmentManager()));
	}

	// �ڲ��� fragment pager������
	private class PagerAdapter extends FragmentPagerAdapter {

		Fragment listFragment;
		Fragment gridFragment;

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			listFragment = new ListFragment();
			gridFragment=new GirdFragment();
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return listFragment;
			case 1:
				return gridFragment;
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {

			return pages.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "�б��ҳ��";
			case 1:
				return "���ӵ�ҳ��";
			default:
				break;
			}
			return super.getPageTitle(position);
		}
	}
}
