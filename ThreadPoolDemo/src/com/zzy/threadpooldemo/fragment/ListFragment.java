package com.zzy.threadpooldemo.fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zzy.threadpooldemo.MyAsyncTask;
import com.zzy.threadpooldemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListFragment extends Fragment {

	private View rootView;
	private ListView lv_list;
	private String[] imageUrls = new String[100];

	/** 每次只执行一个任务的线程池 */
	private static ExecutorService singleTaskExecutor = null;

	/** 每次执行限定个数个任务的线程池 */
	private static ExecutorService limitedTaskExecutor = null;

	/** 所有任务都一次性开始的线程池 */
	private static ExecutorService allTaskExecutor = null;

	/** 创建一个可在指定时间里执行任务的线程池，亦可重复执行 */
	private static ExecutorService scheduledTaskExecutor = null;

	/** 创建一个可在指定时间里执行任务的线程池，亦可重复执行（不同之处：使用工程模式） */
	private static ExecutorService scheduledTaskFactoryExecutor = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		for (int i = 0; i < imageUrls.length; i++) {
			imageUrls[i] = "http://221.203.108.70:8080/jxzy/UploadFiles_4517/201005/2010052615045178.jpg";
		}
		allTaskExecutor = Executors.newCachedThreadPool();// 所有任务都一次性开始的线程池
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.list_fragment, container, false);
		lv_list = (ListView) rootView.findViewById(R.id.lv_list);
		lv_list.setAdapter(new imageListAdapter());
		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private class imageListAdapter extends BaseAdapter {

		private LayoutInflater inflater = LayoutInflater.from(getActivity());

		public imageListAdapter() {

		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (view == null) {
				view = inflater.inflate(R.layout.item_list_image, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				holder.image = (ImageView) view.findViewById(R.id.image);
				holder.progess = (ProgressBar) view.findViewById(R.id.pb_progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.text.setText("图片" + position);
			MyAsyncTask task = new MyAsyncTask(view);

			task.executeOnExecutor(allTaskExecutor, imageUrls[position]);// 往线程池里添加一个任务
			holder.image.setImageResource(R.drawable.ic_launcher);
			return view;
		}
	}

	private static class ViewHolder {
		TextView text;
		ImageView image;
		ProgressBar progess;
	}

}
