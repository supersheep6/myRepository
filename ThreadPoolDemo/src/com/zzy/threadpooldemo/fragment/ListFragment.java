package com.zzy.threadpooldemo.fragment;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import com.zzy.threadpooldemo.R;
import com.zzy.threadpooldemo.util.CommonUtil;

public class ListFragment extends Fragment implements OnItemClickListener {

	private View rootView;
	private ListView lv_list;
	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader/";
	private String vedioRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	String[] images = new String[] { "http://c.hiphotos.baidu.com/image/pic/item/9358d109b3de9c829bc196b56f81800a19d84367.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de80f2d65e97eef01f3a297943.jpg", "http://a.hiphotos.baidu.com/image/pic/item/a9d3fd1f4134970a68d77f1596cad1c8a7865d67.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/5fdf8db1cb1349544a5e875e554e9258d1094a43.jpg", "http://c.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc42b93dbc13adbb6fd52663397.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/11385343fbf2b2119461ea74c98065380cd78eb8.jpg", "http://d.hiphotos.baidu.com/image/pic/item/9213b07eca80653807a5cb2994dda144ad3482b8.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/71cf3bc79f3df8dcb47de0abce11728b47102843.jpg", "http://a.hiphotos.baidu.com/image/pic/item/30adcbef76094b36b4dd74d7a0cc7cd98d109db8.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/00e93901213fb80e5a9e0eed35d12f2eb93894b8.jpg", "http://b.hiphotos.baidu.com/image/pic/item/8601a18b87d6277f27b65d022b381f30e924fc60.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/4d086e061d950a7bc69121fe09d162d9f2d3c960.jpg", "http://h.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc48e3877e53bdbb6fd536633d5.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/c2cec3fdfc039245ef0d460f8594a4c27d1e2571.jpg", "http://f.hiphotos.baidu.com/image/pic/item/3b292df5e0fe992531c456df36a85edf8cb17170.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/80cb39dbb6fd5266007d0317a918972bd407365a.jpg", "http://h.hiphotos.baidu.com/image/pic/item/8326cffc1e178a82b611bba1f403738da977e886.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/ca1349540923dd542ee5ba66d309b3de9c82482d.jpg", "http://e.hiphotos.baidu.com/image/pic/item/c83d70cf3bc79f3d9c628b0ab9a1cd11728b290c.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/a8773912b31bb051bba94207357adab44aede014.jpg", "http://c.hiphotos.baidu.com/image/pic/item/738b4710b912c8fcc15507e2ff039245d688217e.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/caef76094b36acaf813167d37fd98d1000e99cc8.jpg", "http://g.hiphotos.baidu.com/image/pic/item/1e30e924b899a9011a9aaa191e950a7b0208f576.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/c83d70cf3bc79f3d027a151fb9a1cd11728b2902.jpg", "http://g.hiphotos.baidu.com/image/pic/item/1e30e924b899a9011aa7aa191e950a7b0208f50b.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/55e736d12f2eb93827c88bddd6628535e5dd6f7c.jpg", "http://a.hiphotos.baidu.com/image/pic/item/ac4bd11373f082027244601048fbfbedaa641bce.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/8b13632762d0f703f71764f30bfa513d2697c57c.jpg", "http://d.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7863abfbb216409c93d70cf500c.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/d8f9d72a6059252d47a394cb369b033b5ab5b94d.jpg", "http://b.hiphotos.baidu.com/image/pic/item/b3119313b07eca80ccacb028932397dda0448350.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf48239771074094b36adaf9950.jpg", "http://f.hiphotos.baidu.com/image/pic/item/2f738bd4b31c8701f4013b29257f9e2f0608ff4e.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/9f510fb30f2442a7f37a746ad343ad4bd013028c.jpg", "http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c080e8ca16a087b02087af4f44e.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/c995d143ad4bd11389ae720259afa40f4bfb059b.jpg", "http://b.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ec7973edc2249759ee3c6ddb4d.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc03fd458c82d73f8794a4c226ce.jpg", "http://d.hiphotos.baidu.com/image/pic/item/962bd40735fae6cd88a870600db30f2442a70f97.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/aec379310a55b31904bbbe4440a98226cffc1744.jpg", "http://g.hiphotos.baidu.com/image/pic/item/a8773912b31bb05106668f19357adab44aede045.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/f9198618367adab448f3277288d4b31c8701e4db.jpg", "http://a.hiphotos.baidu.com/image/pic/item/8ad4b31c8701a18b8fc82e7e9d2f07082838fedb.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/86d6277f9e2f070824261631ea24b899a901f2db.jpg", "http://e.hiphotos.baidu.com/image/pic/item/cf1b9d16fdfaaf517788bd8e8f5494eef01f7a93.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/0823dd54564e9258495fbadf9f82d158ccbf4e93.jpg", "http://f.hiphotos.baidu.com/image/pic/item/9922720e0cf3d7ca4ed50347f11fbe096b63a9db.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/dbb44aed2e738bd4b6238e00a28b87d6277ff984.jpg", "http://g.hiphotos.baidu.com/image/pic/item/83025aafa40f4bfb1dd2a265004f78f0f7361884.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/08f790529822720e3fecdecb78cb0a46f21fabe4.jpg", "http://e.hiphotos.baidu.com/image/pic/item/b64543a98226cffcea288383ba014a90f603ea85.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c117cd0f8d42a6059252da6b0.jpg", "http://d.hiphotos.baidu.com/image/pic/item/eac4b74543a982263cbc17168982b9014a90eb86.jpg",
			"http://b.hiphotos.baidu.com/image/pic/item/2e2eb9389b504fc2ac118c34e6dde71190ef6db0.jpg", "http://h.hiphotos.baidu.com/image/pic/item/aec379310a55b3197494ee4f40a98226cffc179e.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/d50735fae6cd7b89163556b90c2442a7d9330eaa.jpg", "http://g.hiphotos.baidu.com/image/pic/item/342ac65c10385343fd50eb1b9013b07eca808868.jpg" };

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
		return rootView;
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		
	}
	
	private class imageListAdapter extends BaseAdapter {

		private LayoutInflater inflater = LayoutInflater.from(getActivity());

		public imageListAdapter() {

		}

		@Override
		public int getCount() {
			return images.length;
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
			holder.text.setTag(R.string.list_item_video_path_tag,vedioRootPath+"video.avi");// 给每个条目绑定一个对应的视频地址
			holder.image.setTag(images[position]);
			// 从内存缓存中读取图片---1级缓存
			Bitmap map = mc.getBitmap(images[position]);
			if (map != null) {
				holder.image.setImageBitmap(map);
				if (holder.progess.getProgress() != 0)
					holder.progess.setProgress(0);
				if (holder.progess.getVisibility() != View.INVISIBLE)
					holder.progess.setVisibility(View.INVISIBLE);
				return convertView;
			}
			// 从SD卡中读取图片---2级缓存
			File file=new File(rootPath  +images[position].substring(images[position].lastIndexOf("/")+1));
			if (fu.isFileExists(file)) {
				mc.putBitmap(images[position], CommonUtil.convertToBitmap(file.getAbsolutePath(),200,200));// 重新缓存到内存中
				holder.image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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
			task.executeOnExecutor(exe, images[position]);// 往线程池里添加一个任务
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
