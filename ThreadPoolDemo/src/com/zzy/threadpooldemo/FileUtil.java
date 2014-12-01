package com.zzy.threadpooldemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final String TAG = FileUtil.class.getSimpleName();

	public FileUtil() {
		
	}

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * ����ͼƬ���ƶ�·��
	 * 
	 * @param filepath
	 * @param bitmap
	 */
	public void saveBitmap(String filepath, Bitmap bitmap) {
		if (!isExternalStorageWritable()) {
			Log.i(TAG, "SD�������ã�����ʧ��");
			return;
		}

		if (bitmap == null) {
			return;
		}

		try {
			File file = new File(filepath);
			FileOutputStream outputstream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputstream);
			outputstream.flush();
			outputstream.close();
		} catch (FileNotFoundException e) {
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
	}

	/**
	 * ���ص�ǰӦ�� SD ���ľ���·�� like
	 * /storage/sdcard0/Android/data/com.example.test/files
	 */
	public String getAbsolutePath() {
		File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

		if (root != null) {
			return root.getAbsolutePath();
		}

		return null;
	}

	public boolean isFileExists(File file) {

		return file.exists();
	}
}
