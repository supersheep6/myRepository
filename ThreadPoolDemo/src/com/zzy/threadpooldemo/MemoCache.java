package com.zzy.threadpooldemo;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoCache {

	private LruCache<String, Bitmap> cache;

	public MemoCache() {
		cache = new LruCache<String, Bitmap>(5 * 1024 * 1024){
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}
	
	public Bitmap getBitmap(String key){
		return cache.get(key);
	}
	
	public void putBitmap(String key,Bitmap bitmap){
		cache.put(key, bitmap);
	}
}
