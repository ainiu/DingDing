package com.fanc.mycar.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	//统一处理错误
	public static void handleException(Exception e){

		String str="";
		StringWriter stringWriter=new StringWriter();
		PrintWriter printWrite=new PrintWriter(stringWriter);
		e.printStackTrace(printWrite);
	}
}
