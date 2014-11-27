package com.zzy.threadpooldemo.fragment;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zzy.threadpooldemo.MyListAsyncTask;
import com.zzy.threadpooldemo.R;

import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
	private static ExecutorService limitedTaskExecutor = Executors.newFixedThreadPool(1);

	/** 所有任务都一次性开始的线程池 */
	private static ExecutorService allTaskExecutor = Executors.newCachedThreadPool();// 所有任务都一次性开始的线程池

	/** 创建一个可在指定时间里执行任务的线程池，亦可重复执行 */
	private static ExecutorService scheduledTaskExecutor = null;

	/** 创建一个可在指定时间里执行任务的线程池，亦可重复执行（不同之处：使用工程模式） */
	private static ExecutorService scheduledTaskFactoryExecutor = null;

	/** 创建一个自己的线程池，队列5，核心线程数3，默认线程1的小池子 */
	private static ThreadPoolExecutor exe = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),
			new ThreadPoolExecutor.DiscardOldestPolicy());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		imageUrls[0] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[1] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[2] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[3] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[4] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[5] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[6] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[7] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[8] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[9] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[10] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[11] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[12] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[13] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[14] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[15] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[16] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[17] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[18] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[19] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[20] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[21] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[22] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[23] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[24] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[25] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[26] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[27] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[28] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[29] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[30] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[31] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[32] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[33] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[34] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[35] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[36] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[37] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[38] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[39] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[40] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[41] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[42] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[43] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[44] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[45] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[46] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[47] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[48] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[49] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[50] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[51] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[52] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[53] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[54] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[55] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[56] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[57] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[58] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[59] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[60] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[61] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[62] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[63] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[64] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[65] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[66] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[67] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[68] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[69] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[70] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[71] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[72] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[73] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[74] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[75] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[76] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[77] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[78] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[79] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[80] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[81] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[82] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[83] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[84] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[85] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[86] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[87] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[88] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[89] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[90] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[91] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[92] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[93] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[94] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
		imageUrls[95] = "http://pic1.win4000.com/wallpaper/7/4ff26235763a9.jpg";
		imageUrls[96] = "http://pic1.win4000.com/wallpaper/f/53f2f3633072c.jpg";
		imageUrls[97] = "http://pic1.win4000.com/wallpaper/9/53f4531591dd5.jpg";
		imageUrls[98] = "http://pic1.win4000.com/wallpaper/9/53f4531892540.jpg";
		imageUrls[99] = "http://pic1.win4000.com/wallpaper/9/53f45323967c6.jpg";
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
			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_list_image, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.progess = (ProgressBar) convertView.findViewById(R.id.pb_progress);
				HashMap<String, Object> map=new HashMap<String, Object>();
				map.put("text", holder.text);
				map.put("image", holder.image);
				map.put("progess", holder.progess);
				convertView.setTag(R.string.list_item_view_tag,map);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				MyListAsyncTask task=(MyListAsyncTask)holder.image.getTag();
				if(!task.getStatus().equals(Status.FINISHED)){
					task.cancel(true);
				}
			}

			holder.text.setText("图片" + position);
			MyListAsyncTask task = new MyListAsyncTask(convertView);
			task.executeOnExecutor(exe, imageUrls[position]);// 往线程池里添加一个任务
			holder.image.setTag(task);
			holder.image.setImageResource(R.drawable.ic_launcher);
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView text;
		ImageView image;
		ProgressBar progess;
	}

}
