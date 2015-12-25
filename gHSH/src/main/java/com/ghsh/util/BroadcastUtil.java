package com.ghsh.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BroadcastUtil {
	
	/**发送广播，添加图片*/
	public static void sendAddPicBroadcast(Context context,String filePath) {
		Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		localIntent.setData(Uri.fromFile(new File(filePath)));
		context.sendBroadcast(localIntent);
	}
}
