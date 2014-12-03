package com.zzy.threadpooldemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PlayerActivity extends Activity implements Callback, OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener {

	private float mVideoWidth;
	private float mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String path;
	PowerManager.WakeLock wl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		mPreview = (SurfaceView) findViewById(R.id.surfaceView1);

		/* 得到SurfaceHolder对象 */
		holder = mPreview.getHolder();

		/* 设置回调函数 */
		holder.addCallback(this);

		/* 设置风格 */
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		path = getIntent().getExtras().getString("path");

		// 设置横屏
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

//		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "");
//		wl.acquire();// 申请锁这个里面会调用PowerManagerService里面acquireWakeLock()
	}

	private void playVideo() {
		try {

			/* 构建MediaPlayer对象 */
			mMediaPlayer = new MediaPlayer();

			/* 设置媒体文件路径 */
			mMediaPlayer.setDataSource(path);
			/* 设置通过SurfaceView来显示画面 */
			mMediaPlayer.setDisplay(holder);

			/* 准备 */
			mMediaPlayer.prepare();

			// 设置播放时屏幕常亮
			mMediaPlayer.setScreenOnWhilePlaying(true);

			/* 设置事件监听 */
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		} catch (Exception e) {
			Toast.makeText(this, "播放出错：" + e.getMessage(), Toast.LENGTH_SHORT).show();
			System.out.println("播放出错：" + e.getMessage());
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		playVideo();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mVideoWidth = mMediaPlayer.getVideoWidth();
		mVideoHeight = mMediaPlayer.getVideoHeight();
		float scale = mVideoWidth / mVideoHeight;
		float targath = (float) mPreview.getWidth() / scale;
		if (mVideoWidth != 0 && mVideoHeight != 0) {
			/* 设置视频的宽度和高度 */
			holder.setFixedSize(mPreview.getWidth(), (int) targath);
			mPreview.setLayoutParams(new LinearLayout.LayoutParams(mPreview.getWidth(), (int) targath));
			/* 开始播放 */
			mMediaPlayer.start();

		}

	}

	@Override
	protected void onPause() {
		if (mMediaPlayer != null) {
			mMediaPlayer.pause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mMediaPlayer != null) {
			mMediaPlayer.start();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mMediaPlayer.reset();
		playVideo();
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {

	}

}
