package com.baidu.dingding.until;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static void handleException(Exception e){
		//���쳣��Ϣ����ַ���,����������Ա
		String str="";
		StringWriter stringWriter=new StringWriter();
		PrintWriter printWrite=new PrintWriter(stringWriter);
		e.printStackTrace(printWrite);
	}
}
