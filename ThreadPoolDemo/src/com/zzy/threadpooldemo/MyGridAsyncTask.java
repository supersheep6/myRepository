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
 * AsyncTask ���е�һ��������doInBackground�����Ĳ����������� �ڶ���������onProgressUpdate�����Ĳ�����������
 * ������������doInBackground�������ص����ͣ�Ҳ��onPostExecute�����Ĳ�������
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
			url = params[0];// �����ڶԱȴ�λ��Url��ֵ��ȫ�ֱ����Ա���UI�߳��н��жԱ�
			File myFilePath = new File(rootPath);
			// �����ļ���
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			File file = null;
			File newFile = null;
			try {
				file = new File(myFilePath.getAbsoluteFile() + File.separator + url.substring(url.lastIndexOf("/") + 1) + ".tmp");
				newFile = new File(myFilePath.getAbsoluteFile() + File.separator + url.substring(url.lastIndexOf("/") + 1));
				// ��SD���ж�ȡͼƬ--2������ ��ֹ�����������ظ�����
				if (_fu.isFileExists(newFile)) {
					_mc.putBitmap(params[0], CommonUtil.convertToBitmap(newFile.getAbsolutePath(), 200, 200));// ���»��浽�ڴ���
					return newFile.getAbsolutePath();
				} else if (_fu.isFileExists(file)) {
					// ���������ʱ�ļ���ֱ���˳�������
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
				 * ����λ�Ա�---ֻ���ض����е�ǰ�����ϵ���Ŀ������Ѿ���ʼ������ֹͣ�����ٻ���ʱ��ֻ���ƶ����е�����
				 * �˶Աȷ��ڻ�ȡ�ļ���֮��
				 * ����ʼ����֮ǰ��������������convertView������textview��getView��ʱ����б仯
				 * �ڶ����е�����ֻ�п�ʼִ��֮���doInBackground�������ļ������б仯�������ʵ�������ļ������ִ�λ�����
				 * --------------�˶Ա��Ѿ��ϳ����ļ����Ѿ��̶���������textview��text����
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
				_mc.putBitmap(params[0], CommonUtil.convertToBitmap(newFile.getAbsolutePath(), 200, 200));// �����غõ�ͼ������ڴ滺��

			} catch (MalformedURLException e) {
				if (file.exists()) {
					// ������ع����г���������ɾ����ʱ�ļ�
					file.delete();
				}
				return "error";
			} catch (IOException e) {
				if (file.exists()) {
					// ������ع����г���������ɾ����ʱ�ļ�
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
		if (result.equals("error") && url.equals(iv_image.getTag().toString()))// ����λ�Ա�
		{
			iv_image.setImageResource(R.drawable.ic_error);

		} else if (url != null && url.equals(iv_image.getTag().toString())) // ����λ�Ա�
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
