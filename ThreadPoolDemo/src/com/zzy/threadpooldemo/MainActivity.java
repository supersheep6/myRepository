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

	// 初始化控件
	private void initCtl() {
		vp_mainVP = (ViewPager) findViewById(R.id.vp_mainVP);
	}

	// 初始化数据
	private void initData() {
		vp_mainVP.setAdapter(new PagerAdapter(getSupportFragmentManager()));
	}

	// 内部类 fragment pager适配器
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
				return "列表的页面";
			case 1:
				return "格子的页面";
			default:
				break;
			}
			return super.getPageTitle(position);
		}
	}
}
