package com.zzy.threadpooldemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Environment;
import android.widget.ProgressBar;


/**
 * AsyncTask 其中第一个泛型是doInBackground方法的参数集合类型
 * 			   第二个泛型是onProgressUpdate方法的参数集合类型
 * 			  第三个泛型是doInBackground方法返回的类型，也是onPostExecute方法的参数集合类型
 * @author jack
 *
 */
public class MyAsyncTask extends AsyncTask<String, Integer, String> {

	private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader/";

	private ProgressBar pb_progress;
	private ImageView iv_image;
	private TextView tv_text;

	public MyAsyncTask(View view) {
		pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
		iv_image = (ImageView) view.findViewById(R.id.image);
		tv_text = (TextView) view.findViewById(R.id.text);
	}

	@Override
	protected String doInBackground(String... params) {
		if (!isCancelled()) {
			File myFilePath = new File(rootPath);
			// 创建文件夹
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			File file = new File(myFilePath.getAbsoluteFile() + File.separator + tv_text.getText() + ".jpg");

			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				int length = conn.getContentLength();
				if(length<0){
					publishProgress(100);
					return file.getAbsolutePath();
				}
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				int total = 0;
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total += len;
					publishProgress(total / length);
					System.out.println("***********     "+(total / length)*100+"      ***********");
				}
				fos.close();
				bis.close();
				is.close();
			} catch (MalformedURLException e) {
				this.cancel(true);
				System.out.println(e.getMessage());
			} catch (IOException e) {
				this.cancel(true);
				System.out.println(e.getMessage());
			} finally {
				publishProgress(101);
			}
			return file.getAbsolutePath();

		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		this.cancel(true);
		iv_image.setImageBitmap(BitmapFactory.decodeFile(result));
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (values[0] < 100)
			pb_progress.setProgress(values[0]);// 更新进度条
		else
			pb_progress.setVisibility(View.GONE);
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}
	
	
}
