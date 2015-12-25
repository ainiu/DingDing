package com.baidu.dingding.biz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SouBiz {
	public static String getContent(String url_path){
		try {
			URL url=new URL(url_path);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			int code=connection.getResponseCode();
			if(code==200){
				return chageInputStream(connection.getInputStream());
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	private static String chageInputStream(InputStream inputStream) {
		// TODO Auto-generated method stub
		String jsonString="";
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		int len=0;
		byte []data=new byte[1024];
		try {
			while((len=inputStream.read(data))!=-1){
				stream.write(data, 0, len);
			}
			jsonString=new String(stream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}


}




