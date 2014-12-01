package com.zzy.threadpooldemo.util;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CommonUtil {
	// 图片处理
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		// 返回为空
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
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
