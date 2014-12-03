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
		opts.inPurgeable = true;//允许可清除  
		opts.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果
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
	public static String[] images = new String[] { "http://c.hiphotos.baidu.com/image/pic/item/9358d109b3de9c829bc196b56f81800a19d84367.jpg",
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
	
}
