package com.zzy.threadpooldemo.fragment;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zzy.threadpooldemo.FileUtil;
import com.zzy.threadpooldemo.MemoCache;
import com.zzy.threadpooldemo.MyListAsyncTask;
import com.zzy.threadpooldemo.PlayerActivity;
import com.zzy.threadpooldemo.R;
import com.zzy.threadpooldemo.util.CommonUtil;

public class ListFragment extends Fragment implements OnItemClickListener {

	private View rootView;
	private ListView lv_list;
	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader/";
	private String vedioRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	

	private  LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10);

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

	/** 创建一个自己的线程池，队列10，核心线程数3，默认线程5的小池子 */
	private  ThreadPoolExecutor exe = new ThreadPoolExecutor(3, 5, 0L, TimeUnit.MILLISECONDS, queue, new ThreadPoolExecutor.DiscardOldestPolicy());

	private MemoCache mc = new MemoCache();
	private FileUtil fu = new FileUtil();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.list_fragment, container, false);
		lv_list = (ListView) rootView.findViewById(R.id.lv_list);
		lv_list.setAdapter(new imageListAdapter());
		lv_list.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ViewHolder holder=(ViewHolder)view.getTag();
		String path=holder.text.getTag(R.string.list_item_video_path_tag).toString();
		Bundle b=new Bundle();
		b.putString("path", path);
		Intent in=new Intent(getActivity(), PlayerActivity.class);
		in.putExtras(b);
		startActivity(in);
	}
	
	private class imageListAdapter extends BaseAdapter {

		private LayoutInflater inflater = LayoutInflater.from(getActivity());

		public imageListAdapter() {

		}

		@Override
		public int getCount() {
			return CommonUtil.images.length;
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
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("text", holder.text);
				map.put("image", holder.image);
				map.put("progess", holder.progess);
				convertView.setTag(R.string.list_item_view_tag, map);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText("图片" + position);
			holder.text.setTag(R.string.list_item_video_path_tag,vedioRootPath+"看看我的腿.mp4");// 给每个条目绑定一个对应的视频地址
			holder.image.setTag(CommonUtil.images[position]);
			// 从内存缓存中读取图片---1级缓存
			Bitmap map = mc.getBitmap(CommonUtil.images[position]);
			if (map != null) {
				holder.image.setImageBitmap(map);
				if (holder.progess.getProgress() != 0)
					holder.progess.setProgress(0);
				if (holder.progess.getVisibility() != View.INVISIBLE)
					holder.progess.setVisibility(View.INVISIBLE);
				return convertView;
			}
			// 从SD卡中读取图片---2级缓存
			File file=new File(rootPath  +CommonUtil.images[position].substring(CommonUtil.images[position].lastIndexOf("/")+1));
			if (fu.isFileExists(file)) {
				map=CommonUtil.convertToBitmap(file.getAbsolutePath(),200,200);
				mc.putBitmap(CommonUtil.images[position],map );// 重新缓存到内存中
				holder.image.setImageBitmap(map);
				if (holder.progess.getProgress() != 0)
					holder.progess.setProgress(0);
				if (holder.progess.getVisibility() != View.INVISIBLE)
					holder.progess.setVisibility(View.INVISIBLE);
				return convertView;
			}
			// 如果队列已满则移除一个任务再添加，保证不显示的条目不必下载
			if(queue.size()==10){
				queue.poll();
			}
			MyListAsyncTask task = new MyListAsyncTask(convertView, mc,fu);
			task.executeOnExecutor(exe, CommonUtil.images[position]);// 往线程池里添加一个任务
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
