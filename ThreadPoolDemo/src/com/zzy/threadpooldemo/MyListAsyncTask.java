package com.zzy.threadpooldemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.zzy.threadpooldemo.fragment.ListFragment;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Environment;
import android.widget.ProgressBar;

/**
 * AsyncTask 其中第一个泛型是doInBackground方法的参数集合类型 第二个泛型是onProgressUpdate方法的参数集合类型
 * 第三个泛型是doInBackground方法返回的类型，也是onPostExecute方法的参数集合类型
 * 
 * @author jack
 * 
 */
public class MyListAsyncTask extends AsyncTask<String, Integer, String> {

	@Override
	protected void onCancelled(String result) {
		pb_progress.setVisibility(View.INVISIBLE);
		pb_progress.setProgress(0);
		iv_image.setImageResource(R.drawable.ic_error);
		super.onCancelled(result);
	}

	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader/";

	private ProgressBar pb_progress;
	private ImageView iv_image;
	private TextView tv_text;
	private boolean isVisible;

	public MyListAsyncTask(View view) {
		HashMap<String, Object> map = (HashMap<String, Object>) view.getTag(R.string.list_item_view_tag);

		tv_text = (TextView) map.get("text");
		pb_progress = (ProgressBar) map.get("progess");
		iv_image = (ImageView) map.get("image");
	}

	@Override
	protected String doInBackground(String... params) {
		if (!isCancelled()) {
			File myFilePath = new File(rootPath);
			// 创建文件夹
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			File file = null;
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				String type = conn.getContentType();
				file = new File(myFilePath.getAbsoluteFile() + File.separator + tv_text.getText() + "." + type.substring(type.indexOf("/") + 1));
				float length = conn.getContentLength();
				if (length < 0) {
					publishProgress(100);
					return "error";
				}
				InputStream is = conn.getInputStream();

				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				float total = 0f;
				publishProgress(0);
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total += len;
					float percent = total * 100 / length;
					publishProgress((int) percent);
				}
				fos.close();
				bis.close();
				is.close();
			} catch (MalformedURLException e) {
				return "error";
			} catch (IOException e) {
				return "error";
			} finally {
				publishProgress(101);
			}
			return file.getAbsolutePath();

		}
		return "error";
	}

	@Override
	protected void onPostExecute(String result) {
		if (result.equals("error")) {
			iv_image.setImageResource(R.drawable.ic_error);

		} else {
			iv_image.setImageBitmap(BitmapFactory.decodeFile(result));
		}

		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (values[0] < 100 && values[0] > 0) {
			if (!isVisible) {
				pb_progress.setVisibility(View.VISIBLE);
			}
			isVisible = true;
			pb_progress.setProgress(values[0]);// 更新进度条
		} else {
			if (isVisible) {
				pb_progress.setVisibility(View.INVISIBLE);
			}
			isVisible = false;
			pb_progress.setProgress(0);
		}

		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

}
