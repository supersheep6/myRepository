package com.leslie.filedemo;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapCache {
	private LruCache<String, Bitmap> mCache;

	public BitmapCache() {
		int maxSize = 4 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}
}
