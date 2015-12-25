package com.ghsh.view.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import com.ghsh.dialog.DAlertDialog;

public class DAlertUtil {

	
	public static Dialog alertOKAndCel(Context context,int message,OnClickListener confirmListener){
		return DAlertUtil.alertOKAndCel(context, message, 0,confirmListener, null);
	}
	public static Dialog alertOKAndCel(Context context,int message,OnClickListener confirmListener,OnClickListener cancelListener){
		return DAlertUtil.alertOKAndCel(context, message, 0,confirmListener, cancelListener);
	}
	public static Dialog alertOKAndCel(Context context,int message,int okText,OnClickListener confirmListener){
		return DAlertUtil.alertOKAndCel(context, message, okText,confirmListener, null);
	}
	public static Dialog alertOKAndCel(Context context,int message,int okText,OnClickListener confirmListener,OnClickListener cancelListener){
		DAlertDialog alert=new DAlertDialog(context);
		alert.addCancelListener(cancelListener);
		alert.addConfirmListener(confirmListener);
		alert.setMessage(message);
		alert.setOKText(okText);
		return alert;
	}
}
