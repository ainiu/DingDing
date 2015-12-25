package com.baidu.dingding.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.baidu.dingding.R;
import com.baidu.dingding.code.Constants;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.UploadImageModel;
import com.baidu.dingding.entity.User;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.ReadimgBase64;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;
import android.provider.DocumentsContract;
import java.io.File;

/**
 * 上传图片
 * */
public class UploadImageActivity extends BaseActivity implements OnClickListener,UploadImageModel.Listener{
	private LinearLayout albumButton, cameraButton;// 相册 相机
	private ImageView celButton;//取消
	private final int REQUESTCODE_cameraButton = 100;// 拍照
	private final int REQUESTCODE_album = 200;// 相册
	private File imagePath=null;
	private ProgressDialog progressDialog;
	User user=new User();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_upload_image);
		initData();
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = d.getWidth(); // 宽度设置为屏幕
		getWindow().setAttributes(p); // 设置生效
		getWindow().setGravity(Gravity.BOTTOM); // 设置靠右对齐
		
		this.initView();
		this.initListener();
		imagePath=new File(Constants.getSDCachePath(this)+"/temp1.jpg");
	}

	private void initData() {
		user= (User) this.getIntent().getSerializableExtra("user");
	}


	@SuppressLint("NewApi")
	private void initView() {
		this.setFinishOnTouchOutside(false);//单机背景不可以取消，API Level>=11
		progressDialog=new ProgressDialog(this);
		cameraButton = (LinearLayout) this.findViewById(R.id.cameraButton);
		albumButton = (LinearLayout) this.findViewById(R.id.albumButton);
		celButton = (ImageView) this.findViewById(R.id.upload_image_close);
	}

	private void initListener() {
		cameraButton.setOnClickListener(this);
		albumButton.setOnClickListener(this);
		celButton.setOnClickListener(this);
	}
	/**判断用户的选择*/
	@Override
	public void onClick(View v) {
		if(v==albumButton){
			//相册
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("output",Uri.fromFile(imagePath));
			intent.putExtra("outputFormat", "JPEG");
			intent.putExtra("aspectX", 1); // 放大和缩小  
			intent.putExtra("aspectY", 1); // 如果aspectX和aspectY同时设置为相同值的话则为矩形
			intent.putExtra("outputX", 100);
			intent.putExtra("outputY", 100);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true); // no face detection
			this.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUESTCODE_album);
		}else if(v==cameraButton){
			//拍照
			 String SDState = Environment.getExternalStorageState();
			 if(SDState.equals(Environment.MEDIA_MOUNTED)){
				 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 this.startActivityForResult(intent,REQUESTCODE_cameraButton);
			 }else{
				 Toast.makeText(UploadImageActivity.this,"内存卡不存在", Toast.LENGTH_LONG).show();
			 }
		}else if(v==celButton){
			//取消
			this.finish();
			overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out);
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitmap=null;
		if(requestCode==REQUESTCODE_album){
			//相册
			if(data!=null&&data.getData()!=null){
				Uri uri=data.getData();
				if(DocumentsContract.isDocumentUri(this, uri)){
					String wholeID = DocumentsContract.getDocumentId(uri);
					String id = wholeID.split(":")[1];
					String[] column = { MediaStore.Images.Media.DATA };
//					    String sel = MediaStore.Images.Media._ID + =?;
					Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,"_id=?", new String[] { id }, null);
					int columnIndex = cursor.getColumnIndex(column[0]);
					if (cursor.moveToFirst()) {
						String temp_imagePath = cursor.getString(columnIndex);
						imagePath=new File(temp_imagePath);
					}
					cursor.close();
				}else{
					String path=uri.getPath().toLowerCase();
					if(!path.endsWith("jpg")&&!path.endsWith("png")&&!path.endsWith("gif")){
						Toasttool.MyToast(this,"图片格式不正确，请重新选择(jpg,png,gif)");
						this.finish();
						return;
					}
				}
			}
			if(!imagePath.exists()){
//				this.showShortToast("图片不存在，请重新选择");
				this.finish();
				return;
			}
			progressDialog.show();
			UploadImageModel uploadImageModel=new UploadImageModel(this);
			uploadImageModel.addListener(this);
			Bitmap bitmap1= BitmapFactory.decodeFile(imagePath.toString(),null);
			String imageString = ReadimgBase64.imgToBase64(imagePath.toString(), bitmap1, null);
			uploadImageModel.uploadPortrait(user.getUsrNumber(), user.getToken(), imageString);
			LogUtil.i("上传图片","user.getUsrNumber()="+user.getUsrNumber()+"user.getToken()="+user.getToken()+"imagePath="+imagePath);
		}else if(requestCode==REQUESTCODE_cameraButton){
			//拍照
			if(data!=null){
				bitmap = data.getParcelableExtra("data");
				Intent intent = this.getCropImageIntent(bitmap);
				this.startActivityForResult(intent, REQUESTCODE_album);
			}else{
				Toasttool.MyToast(this,"图片不存在，请重新选择");
				this.finish();
				return;
			}
		}
	}

	private Intent getCropImageIntent(Bitmap data) {
	    Intent intent = new Intent("com.android.camera.action.CROP");
	    intent.setType("image/*");
	    intent.putExtra("data", data);
	    intent.putExtra("crop", "true");
	    intent.putExtra("output",Uri.fromFile(imagePath));
	    intent.putExtra("aspectX", 1);
	    intent.putExtra("aspectY", 1);
//	    intent.putExtra("outputX", 300);
//	    intent.putExtra("outputY", 300);
	    intent.putExtra("return-data", true);
	    intent.putExtra("outputFormat", "JPEG");
	    return intent;
	}

	@Override
	public void uploadCallBack(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		imagePath.delete();
		if (error != null) {
			Toasttool.MyToast(this,error.getMessage());
			return;
		}
		if (result == DVolleyConstans.RESULT_OK) {
			Intent intent = new Intent();
			intent.putExtra("imageUrl", bean.getObject()+"");
			setResult(Activity.RESULT_OK, intent);
	        finish();
		}
		Toasttool.MyToast(this,message);
	}
}
