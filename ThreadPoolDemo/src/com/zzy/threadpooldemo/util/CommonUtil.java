package com.zzy.threadpooldemo.util;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CommonUtil {
	// ͼƬ����
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// ����Ϊtureֻ��ȡͼƬ��С
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		// ����Ϊ��
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// ����
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		bitmap = BitmapFactory.decodeFile(path, opts);
		if (bitmap != null) {
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(bitmap);
			if (weak != null) {
				Bitmap result = Bitmap.createScaledBitmap(weak.get(), w, h, true);
				bitmap.recycle();
				return result;
			}
		}
		return null;
	}
}
