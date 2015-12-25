package com.baidu.dingding.biz;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;
public class LoginBiz {

	private static String PATH="http://183.234.117.210:9090/AppServer/user/login?";
	private  static URL url=null;
	public boolean loginActionMethodPost(String name,String pwd){
		boolean login=false;
		String params;
		try {
			params="userNumber="+URLEncoder.encode(name,"GBK")+"&password="+URLEncoder.encode(pwd,"GBK");
			byte[] postDate=params.getBytes();
			url=new URL(PATH);
			HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3*1000);
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestMethod("POST");
			urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencode");
			urlConn.connect();
			DataOutputStream dos=new DataOutputStream(urlConn.getOutputStream());
			dos.write(postDate);
			dos.flush();
			dos.close();
			if(urlConn.getResponseCode()==200){
				byte [] data= readIS(urlConn.getInputStream());
				if(data.length>0){}
				String loginState= new String(data);
				if(loginState.equals("0")){
					login=true;
				}
				Log.i("TAG_POST", "Post请求方式成功,返回数据如下:");
				Log.i("TAG_POST",new String(data, "GBK"));
			}else{
				Log.i("TAG_POST", "POST方式请求失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{

		}
		return login;
	}
	private byte[] readIS(InputStream is)throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int length=0;
		while((length=is.read(buff))!=-1){
			bos.write(buff,0,length);
		}
		bos.flush();
		bos.close();
		return bos.toByteArray();
	}


}
