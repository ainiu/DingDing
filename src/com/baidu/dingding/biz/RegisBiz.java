package com.baidu.dingding.biz;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.baidu.dingding.entity.UserEntity;
import com.baidu.dingding.until.Consts;

public class RegisBiz {
	//连接服务器
	public static String getContent(String path){
		try {
			URL url=new URL(path);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "appliction/www-form-urlcode");
			connection.connect();
			DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
			outputStream.flush();
			outputStream.close();
			int code=connection.getResponseCode();
			if(code==200){
				return chageOutputStream(connection.getOutputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	private static String chageOutputStream(OutputStream outputStream) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		int len=0;
		byte[]data=new byte[1024];
		return null;
	}



}
