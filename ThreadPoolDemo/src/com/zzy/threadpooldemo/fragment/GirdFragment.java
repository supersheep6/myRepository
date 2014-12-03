package com.zzy.threadpooldemo.fragment;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zzy.threadpooldemo.FileUtil;
import com.zzy.threadpooldemo.MemoCache;
import com.zzy.threadpooldemo.MyListAsyncTask;
import com.zzy.threadpooldemo.PlayerActivity;
import com.zzy.threadpooldemo.R;
import com.zzy.threadpooldemo.util.CommonUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GirdFragment extends Fragment implements OnItemClickListener {

	private GridView gv_grid;
	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader/";
	private String vedioRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	
	private  LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10);
	/** 创建一个自己的线程池，队列10，核心线程数3，默认线程5的小池子 */
	private  ThreadPoolExecutor exe = new ThreadPoolExecutor(3, 5, 0L, TimeUnit.MILLISECONDS, queue, new ThreadPoolExecutor.DiscardOldestPolicy());
	
	private MemoCache mc = new MemoCache();
	private FileUtil fu = new FileUtil();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.grid_fragment, container,false);
		gv_grid=(GridView)rootView.findViewById(R.id.gv_grid);
		gv_grid.setAdapter(new GridAdapter());
		gv_grid.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ViewHolder holder=(ViewHolder)view.getTag();
		String path=holder.imageView.getTag(R.string.gird_item_video_path_tag).toString();
		Bundle b=new Bundle();
		b.putString("path", path);
		Intent in=new Intent(getActivity(), PlayerActivity.class);
		in.putExtras(b);
		startActivity(in);
		
	}
	
	private class GridAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		
		public GridAdapter(){
			inflater=LayoutInflater.from(getActivity());
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
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_grid_image, null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("image", holder.imageView);
				convertView.setTag(R.string.grid_item_view_tag, map);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.imageView.setTag(R.string.gird_item_video_path_tag,vedioRootPath+"看看我的腿.mp4");// 给每个条目绑定一个对应的视频地址
			holder.imageView.setTag(CommonUtil.images[position]);
			// 从内存缓存中读取图片---1级缓存
			Bitmap map = mc.getBitmap(CommonUtil.images[position]);
			if (map != null) {
				holder.imageView.setImageBitmap(map);
				return convertView;
			}
			// 从SD卡中读取图片---2级缓存
			File file=new File(rootPath  +CommonUtil.images[position].substring(CommonUtil.images[position].lastIndexOf("/")+1));
			if (fu.isFileExists(file)) {
				map=CommonUtil.convertToBitmap(file.getAbsolutePath(),200,200);
				mc.putBitmap(CommonUtil.images[position],map );// 重新缓存到内存中
				holder.imageView.setImageBitmap(map);
				return convertView;
			}
			// 如果队列已满则移除一个任务再添加，保证不显示的条目不必下载
			if(queue.size()==10){
				queue.poll();
			}
			MyListAsyncTask task = new MyListAsyncTask(convertView, mc,fu);
			task.executeOnExecutor(exe, CommonUtil.images[position]);// 往线程池里添加一个任务
			holder.imageView.setImageResource(R.drawable.ic_launcher);
			return convertView;
		}
		
	}
	
	private static class ViewHolder{
		ImageView imageView;
	}
	
}
