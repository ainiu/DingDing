package com.ghsh.code.volley.model;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ghsh.Constants;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;

@SuppressLint("NewApi")
public class UploadImageModel extends AsyncTask {

	private final String upload_portrait_URL = DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=uploadPortrait");

	private static final int TIME_OUT = 30 * 1000; // 超时时间

	private Context context;
	private File file;
	private String userID; 
	private Listener listener;
	public UploadImageModel(Context context) {
		this.context = context;
	}
	
	/** 上传头像 */
	public void uploadPortrait(String userID, File file) {
		this.userID = userID;
		this.file = file;
		this.execute("");
	}

	public void addListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	protected void onPostExecute(Object result) {
		if (listener == null) {
			return;
		}
		if (result == null) {
			listener.uploadCallBack(null, DVolleyConstans.RESULT_FAIL, "上传失败",new Exception("上传失败"));
		} else if (result instanceof Exception) {
			listener.uploadCallBack(null, DVolleyConstans.RESULT_FAIL, "上传失败",(Exception) result);
		} else if (result instanceof String) {
			try {
				DCallResult callResult = new DCallResult();
				callResult.setResponse(result + "");
				ReturnBean bean = new ReturnBean();
				bean.setType(DVolleyConstans.METHOD_ADD);
				bean.setObject(DVolleyConstans.getServiceHost(callResult.getContentString()));
				listener.uploadCallBack(bean, callResult.getResult(),callResult.getMessage(), null);
			} catch (Exception e) {
				listener.uploadCallBack(null, DVolleyConstans.RESULT_FAIL, "上传失败", e);
			}
		}else{
			listener.uploadCallBack(null, DVolleyConstans.RESULT_FAIL, "上传失败",new Exception("上传失败"));
		}
	}

	@Override
	protected Object doInBackground(Object... p) {
		String request = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			// 设置通信协议版本
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			MultipartEntity mre = new MultipartEntity();
			ContentBody fileBody = new FileBody(file);
			mre.addPart("uploadedfile", fileBody);
			
			String url=upload_portrait_URL+"&userID="+userID;
			if(Constants.DEBUG){
				Log.i("上传图片路径:", url);
				Log.i("上传本地图片路径:", file.getAbsolutePath());
			}
			
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(mre);
			HttpResponse response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				request = EntityUtils.toString(response.getEntity(),"UTF-8");
			}
			if(Constants.DEBUG){
				Log.i("上传图片返回:request", request);
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			AppViewException.onViewException(e);
			return e;
		}
		return request;
	}
	public interface Listener{
		 void uploadCallBack(ReturnBean bean, int result, String message,Throwable e);
	}
}
