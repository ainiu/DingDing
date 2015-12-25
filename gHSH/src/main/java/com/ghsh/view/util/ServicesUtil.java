package com.ghsh.view.util;

import android.content.Context;
import android.content.Intent;

public class ServicesUtil {

	
	public static void startMessageServices(Context context){
		Intent intent=new Intent(context,com.ghsh.services.MessageServices.class);
		context.startService(intent);
	}
	
	public static void stopMessageServices(Context context){
		Intent intent=new Intent(context,com.ghsh.services.MessageServices.class);
		context.stopService(intent);
	}
}
