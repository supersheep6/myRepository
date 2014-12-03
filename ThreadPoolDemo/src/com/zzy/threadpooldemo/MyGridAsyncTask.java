package com.zzy.threadpooldemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.zzy.threadpooldemo.util.CommonUtil;

import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * AsyncTask 其中第一个泛型是doInBackground方法的参数集合类型 第二个泛型是onProgressUpdate方法的参数集合类型
 * 第三个泛型是doInBackground方法返回的类型，也是onPostExecute方法的参数类型
 * 
 * @author jack
 * 
 */
public class MyGridAsyncTask extends AsyncTask<String, Integer, String> {
	private MemoCache _mc;
	private FileUtil _fu;
	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader/";

	private ImageView iv_image;

	private String url;

	public MyGridAsyncTask(View view, MemoCache mc, FileUtil fu) {
		_mc = mc;
		_fu = fu;
		HashMap<String, Object> map = (HashMap<String, Object>) view.getTag(R.string.list_item_view_tag);
		iv_image = (ImageView) map.get("image");
	}

	@Override
	protected void onCancelled(String result) {
		iv_image.setImageResource(R.drawable.ic_launcher);
		super.onCancelled(result);
	}

	@Override
	protected String doInBackground(String... params) {

		if (!isCancelled()) {
			url = params[0];// 将用于对比错位的Url赋值给全局变量以便在UI线程中进行对比
			File myFilePath = new File(rootPath);
			// 创建文件夹
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			File file = null;
			File newFile = null;
			try {
				file = new File(myFilePath.getAbsoluteFile() + File.separator + url.substring(url.lastIndexOf("/") + 1) + ".tmp");
				newFile = new File(myFilePath.getAbsoluteFile() + File.separator + url.substring(url.lastIndexOf("/") + 1));
				// 从SD卡中读取图片--2级缓存 防止队列中任务重复下载
				if (_fu.isFileExists(newFile)) {
					_mc.putBitmap(params[0], CommonUtil.convertToBitmap(newFile.getAbsolutePath(), 200, 200));// 重新缓存到内存中
					return newFile.getAbsolutePath();
				} else if (_fu.isFileExists(file)) {
					// 如果存在临时文件则直接退出该任务
					return "";
				}
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				float length = conn.getContentLength();
				if (length < 0) {
					return "error";
				}

				// String type = conn.getContentType();
				// file = new File(myFilePath.getAbsoluteFile() + File.separator
				// + tv_text.getText() + "." + type.substring(type.indexOf("/")
				// + 1));

				/*
				 * 防错位对比---只下载队列中当前界面上的条目，如果已经开始下载则不停止，快速滑动时候只控制队列中的任务
				 * 此对比放在获取文件名之后
				 * ，开始下载之前，由于是重用了convertView，所以textview在getView的时候会有变化
				 * 在队列中的任务只有开始执行之后才doInBackground，所以文件名会有变化，会产生实际下载文件与名字错位的情况
				 * --------------此对比已经废除，文件名已经固定，不再由textview的text决定
				 */
				// if (!iv_image.getTag().toString().equals(params[0])) {
				// return "";
				// }
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				bis.close();
				is.close();
				file.renameTo(newFile);
				_mc.putBitmap(params[0], CommonUtil.convertToBitmap(newFile.getAbsolutePath(), 200, 200));// 将下载好的图像放入内存缓存

			} catch (MalformedURLException e) {
				if (file.exists()) {
					// 如果下载过程中出现问题则删除临时文件
					file.delete();
				}
				return "error";
			} catch (IOException e) {
				if (file.exists()) {
					// 如果下载过程中出现问题则删除临时文件
					file.delete();
				}
				return "error";
			} finally {
			}
			return newFile.getAbsolutePath();

		}
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		if (result.equals("error") && url.equals(iv_image.getTag().toString()))// 防错位对比
		{
			iv_image.setImageResource(R.drawable.ic_error);

		} else if (url != null && url.equals(iv_image.getTag().toString())) // 防错位对比
		{
			iv_image.setImageBitmap(CommonUtil.convertToBitmap(result, 200, 200));
		}

		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {

		super.onCancelled();
	}
}
