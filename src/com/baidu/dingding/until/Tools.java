package com.baidu.dingding.until;

import android.app.Activity;
import android.app.ProgressDialog;

public class Tools {

	private static ProgressDialog progressDialog;

	public static void showProgresssDialog(Activity activity,String msg,boolean isCancel){
		if(progressDialog==null){
			progressDialog=new ProgressDialog(activity)	;
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(isCancel);
			progressDialog.show();
		}
	}

	public static void closeProgressDialog(){
		if(progressDialog!=null){
			progressDialog.cancel();
			progressDialog=null;
		}
	}


}
