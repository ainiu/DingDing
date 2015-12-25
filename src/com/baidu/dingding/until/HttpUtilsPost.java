package com.baidu.dingding.until;

import java.net.HttpURLConnection;
import java.net.URL;

//post«Î«Û
public class HttpUtilsPost {
	public static String getContent(String url_path){
		try {
			URL url=new URL(url_path);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setInstanceFollowRedirects(true);
			int len=connection.getResponseCode();
			if(len==200){
				StringBuilder builder=new StringBuilder();
                
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
