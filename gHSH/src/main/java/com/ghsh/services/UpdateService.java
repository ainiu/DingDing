package com.ghsh.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.ghsh.AppManager;
import com.ghsh.Constants;
import com.ghsh.activity.ConductActivity;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.R;

public class UpdateService extends Service {
	
	private boolean isRun=false;
	private String download_apk_url;//下载apk连接地址
	private File localhost_file_path;//本地下载路径
	private float version;//新版本号
	private int apkSize=0;//apk大小
	private String title;//标题
	// 通知栏
	private NotificationManager notificationManager = null;
	private Notification notification = null;

	@Override
	public void onCreate() {
		super.onCreate();
		title=this.getResources().getString(R.string.app_name)+"-";
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.initView();
//		download_apk_url=DVolleyConstans.getServiceHost("/data/apk/"+AppManager.getPackageName(this)+".apk");
		download_apk_url=DVolleyConstans.getServiceHost("/phoneapi/ghsh.apk");
	}
	
	private void initView(){
		notification = new Notification();
		// 设置下载过程中，点击通知栏，回到主界面
		Intent intent = new Intent(this, ConductActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 加入这句，没一个通知都是一个新的task，不会覆盖当前的task
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,0);
//		notification.setLatestEventInfo(this,title,"aaa",contentIntent);
		// 设置通知栏显示内容
		notification.icon = R.drawable.icon;
		notification.contentView = new RemoteViews(getPackageName(),R.layout.view_update_notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(this.isRun){
			return super.onStartCommand(intent, flags, startId);
		}
		if(intent!=null){
			version=intent.getFloatExtra("version", 0);
		}
		this.downloadApk();
		return super.onStartCommand(intent, flags, startId);
	}

	private void downloadApk() {
		this.isRun=true;
		notification.contentView.setTextViewText(R.id.notifiction_text,title+"开始下载");
		notificationManager.notify(0, notification);
		new Thread(new Runnable() {
			@Override
			public void run() {
				InputStream is = null;
				FileOutputStream fos = null;
				try {
					URL url = new URL(download_apk_url);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					is = connection.getInputStream();
					apkSize = connection.getContentLength();
					localhost_file_path=new File(Constants.getAPKDownloadDir(UpdateService.this)+"/"+AppManager.getPackageName(UpdateService.this)+"_"+version+".apk");
					fos = new FileOutputStream(localhost_file_path);
					byte[] buffer = new byte[1024];
					int len=0,totalSize = 0;
					int n = 0;
					while ((len = is.read(buffer,0,buffer.length)) > 0) {
						fos.write(buffer, 0, len);
						totalSize = totalSize + len;
						if (n % 50 == 0) {
							handler.obtainMessage(1, totalSize).sendToTarget();//更新进度条
						}
						n++;
					}
					handler.sendEmptyMessage(100);//安装
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					isRun=false;
				}
			}
		}).start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				int redersize = (Integer) msg.obj;
				notification.contentView.setTextViewText(R.id.notifiction_text,title+"开始下载-"+ (100*redersize/ apkSize) + "%");
				notification.contentView.setProgressBar(R.id.notifiction_progressBar, apkSize, redersize, false);
				notificationManager.notify(0, notification);
				if(Constants.DEBUG){
					Log.i("开始下载-","总大小："+apkSize+" 当前大小："+redersize+" 进度："+(redersize*100/ apkSize));
				}
			}
			if (msg.what == 100) {
				notification.contentView.setTextViewText(R.id.notifiction_text,title+"下载完成");
				notificationManager.notify(0, notification);
				installAPP();
			}
		}
	};

	/** 重新开始植入app **/
	private void installAPP() {
		if(localhost_file_path==null){
			return ;
		}
		Uri uri = Uri.fromFile(localhost_file_path);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		startActivity(intent);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

//	private void setNotification(String message, int count) {
//		
//		notification.setLatestEventInfo(UpdateService.this,title,message+ (((float)length/(1024*1024)+"").substring(0, ((float)length/(1024*1024)+"").indexOf(".")+3)+"M"), updatePendingIntent);
//		notificationManager.notify(0, notification);
//	}
}
