package com.baidu.dingding.until;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static void handleException(Exception e){
		//把异常信息变成字符串,发给开发人员
		String str="";
		StringWriter stringWriter=new StringWriter();
		PrintWriter printWrite=new PrintWriter(stringWriter);
		e.printStackTrace(printWrite);
	}
}
