package com.baidu.dingding.code.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.baidu.dingding.code.Constants;
import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

@SuppressLint("NewApi")

public class UploadImageModel extends AsyncTask {
	private final String upload_portrait_URL = Consts.TOUXIANGJIEKOU_PATH;                           //头像路径

	private static final int TIME_OUT = 30 * 1000;                                                   // 超时时间

	public  Context context;
	public String headerImage;
	public String userNumber;
	public String Token;
	public Listener listener;
	public UploadImageModel(Context context) {
		this.context = context;
	}
	
	/** 上传头像
	public void uploadPortrait(String userID, File file) {
		this.userID = userID;
		this.file = file;
		this.execute("");
	}*/

	/** 上传头像 */
	public void uploadPortrait(String userNumber,String token ,String headerImage) {
		this.userNumber = userNumber;
		this.Token = token;
		this.headerImage=headerImage;
		this.execute("");
	}

	public void addListener(Listener listener) {
		this.listener = listener;
	}

	/**处理数据*/
	@Override
	protected void onPostExecute(Object result) {
		if (listener == null) {
			return;
		}
		if (!result.equals("0")) {
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

	/**处理耗时操作*/
	@Override
	protected Object doInBackground(Object... p) {
		String request = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			// 设置通信协议版本
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			MultipartEntity mre = new MultipartEntity();
			String url=upload_portrait_URL+"&userNumber="+userNumber+"&token="+Token+"&headerImage="+headerImage;
			if(Constants.DEBUG){
				LogUtil.i("上传图片路径:", url);
				LogUtil.i("上传本地图片路径:", headerImage);
			}
			
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(mre);
			HttpResponse response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				request = EntityUtils.toString(response.getEntity(),"GBK");
			}
			if(Constants.DEBUG){
				Log.i("上传图片返回:request", request);
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
			return e;
		}
		return request;
	}

	public interface Listener{
		void uploadCallBack(ReturnBean bean, int result, String message, Throwable e);
	}
}
