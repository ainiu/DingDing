package com.baidu.dingding.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.Constants;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MyGerenxinxiModel;
import com.baidu.dingding.code.model.MyGerenxinxibaocuunModel;
import com.baidu.dingding.code.model.UploadImageModel;
import com.baidu.dingding.entity.Gerenxinxi;
import com.baidu.dingding.until.BitmapUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.ReadimgBase64;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.CircleImageView;
import com.baidu.dingding.view.MyView.ClearEditText;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import java.io.File;

public class PersonalProfileActivity extends BaseActivity implements UploadImageModel.Listener, DResponseListener {

    LinearLayout touxiang;
    ProgressDialog progressDialog;
    MyGerenxinxiModel mygerenxinxiModel;
    MyGerenxinxibaocuunModel myGerenxinxibaocuunModel;
    RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout4, sex_child_one, sex_child_two;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    ImageView imageView1, imageView2, imageView3, imageView4;
    TextView textView1, textView2, textView3, textView4;
    ClearEditText edtext1, edtext2, edtext4;
    ImageView img_grild, img_body;
    Button baocun;
    String opertionType = 1 + "";
    CircleImageView circleImageView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String getResult1=(String)msg.obj;
            try {
                circleImageView.setImageBitmap(BitmapUtil.getBitmapFromBytes(BitmapUtil.getImage(getResult1)));
            } catch (Exception e) {
                Toasttool.MyToast(PersonalProfileActivity.this,"图片请求错误");
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        initModel();
        initData();
        intView();
        initLener();

    }

    String userNumber="", token="";

    private void initData() {
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
        token = (String) SharedPreferencesUtils.get(this, "token", "");
        mygerenxinxiModel.findConsultList(userNumber);
    }

    private void initModel() {
        mygerenxinxiModel = new MyGerenxinxiModel(this);
        mygerenxinxiModel.addResponseListener(this);
        myGerenxinxibaocuunModel = new MyGerenxinxibaocuunModel(this);
        myGerenxinxibaocuunModel.addResponseListener(this);
    }

    private Boolean isChild1 = false;
    private Boolean isChild2 = false;
    private Boolean isChild3 = false;
    private Boolean isChild4 = false;

    private String save_sex = "";

    private void initLener() {
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtext1.getText().toString().trim();
                String truename=edtext2.getText().toString().trim();
                String shenfenzhenghaoma=edtext4.getText().toString().trim();
                String str="";
                if(save_sex.equals("女")){
                    str=0+"";
                }else if(save_sex.equals("男")){
                    str=1+"";
                }else{
                    str="";
                }
                myGerenxinxibaocuunModel.findConsultList(userNumber, token, opertionType, str,name,truename,shenfenzhenghaoma);
                progressDialog.show();
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChild4) {
                    linearLayout4.setVisibility(View.GONE);
                    isChild4 = false;
                    imageView4.setBackgroundResource(R.drawable.ding_153);
                } else {
                    linearLayout4.setVisibility(View.VISIBLE);
                    isChild4 = true;
                    imageView4.setBackgroundResource(R.drawable.ding_150);
                }
            }
        });
        //点击gril
        sex_child_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_grild.setVisibility(View.VISIBLE);
                img_body.setVisibility(View.GONE);
                save_sex = "女";
                LogUtil.i("性别选择", "save_sex=" + save_sex);
            }
        });
        sex_child_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_grild.setVisibility(View.GONE);
                img_body.setVisibility(View.VISIBLE);
                save_sex = "男";
                LogUtil.i("性别选择", "save_sex=" + save_sex);
            }
        });
        //sex
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChild3) {
                    linearLayout3.setVisibility(View.GONE);
                    isChild3 = false;
                    imageView3.setBackgroundResource(R.drawable.ding_153);
                } else {
                    linearLayout3.setVisibility(View.VISIBLE);
                    isChild3 = true;
                    imageView3.setBackgroundResource(R.drawable.ding_150);
                }

            }
        });
        //真实姓名
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChild2) {
                    linearLayout2.setVisibility(View.GONE);
                    isChild2 = false;
                    imageView2.setBackgroundResource(R.drawable.ding_153);
                } else {
                    linearLayout2.setVisibility(View.VISIBLE);
                    isChild2 = true;
                    imageView2.setBackgroundResource(R.drawable.ding_150);
                }
            }
        });
        //名称
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChild1) {
                    linearLayout1.setVisibility(View.GONE);
                    isChild1 = false;
                    imageView1.setBackgroundResource(R.drawable.ding_153);
                } else {
                    linearLayout1.setVisibility(View.VISIBLE);
                    isChild1 = true;
                    imageView1.setBackgroundResource(R.drawable.ding_150);
                }
            }
        });

    }

    public void doClick(View view) {
        finish();
    }

    private void intView() {
        touxiang = (LinearLayout) this.findViewById(R.id.personal_profile_linearLayout_01);
        progressDialog = new ProgressDialog(this);
        circleImageView= (CircleImageView) this.findViewById(R.id.personal_profile_image_03);

        //图片路径
        imagePath = new File(Constants.getSDCachePath(this) + "/temp1.jpg");
        //名称
        relativeLayout1 = (RelativeLayout) this.findViewById(R.id.geren_group_item);
        linearLayout1 = (LinearLayout) this.findViewById(R.id.linea1);
        textView1 = (TextView) this.findViewById(R.id.personal_profile_text_imgtv);
        imageView1 = (ImageView) this.findViewById(R.id.personal_profile_xiala);
        edtext1 = (ClearEditText) this.findViewById(R.id.clearview1);
        //真实姓名
        relativeLayout2 = (RelativeLayout) this.findViewById(R.id.truename);
        linearLayout2 = (LinearLayout) this.findViewById(R.id.linea2);
        imageView2 = (ImageView) this.findViewById(R.id.personal_profile_xiala1);
        textView2 = (TextView) this.findViewById(R.id.personal_profile_text_imgtv1);
        edtext2 = (ClearEditText) this.findViewById(R.id.clearview);
        //性别
        relativeLayout3 = (RelativeLayout) this.findViewById(R.id.sex);
        linearLayout3 = (LinearLayout) this.findViewById(R.id.linea3);
        imageView3 = (ImageView) this.findViewById(R.id.personal_profile_xiala2);
        textView3 = (TextView) this.findViewById(R.id.personal_profile_text_imgtv2);
        img_grild = (ImageView) this.findViewById(R.id.img_gild);
        img_body = (ImageView) this.findViewById(R.id.img_body);
        sex_child_one = (RelativeLayout) this.findViewById(R.id.geren_child_one);
        sex_child_two = (RelativeLayout) this.findViewById(R.id.geren_child_two);
        //身份证号码
        relativeLayout4 = (RelativeLayout) this.findViewById(R.id.geren_group_item3);
        linearLayout4 = (LinearLayout) this.findViewById(R.id.linea4);
        imageView4 = (ImageView) this.findViewById(R.id.personal_profile_xiala3);
        textView4 = (TextView) this.findViewById(R.id.personal_profile_text_imgtv3);
        edtext4 = (ClearEditText) this.findViewById(R.id.clearview3);
        baocun = (Button) this.findViewById(R.id.baocun);
    }


    static final int REQUESTCODE_cameraButton = 100;                 // 拍照
    static final int REQUESTCODE_album = 200;                       // 相册
    File imagePath = null;
    Dialog dialog;

    private void show() {
        dialog = new AlertDialog.Builder(this)
                .create();
        dialog.setCanceledOnTouchOutside(true);                   //点击diolong外面是否取消
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.orde_crema);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        Button albumButton = (Button) window.findViewById(R.id.btn_paizhao);
        Button cameraButton = (Button) window.findViewById(R.id.btn_phone);
        Button celButton = (Button) window.findViewById(R.id.btn_cacel);

        celButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toasttool.MyToast(getContext(),"相册");
                LogUtil.i("相册", "");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("output", Uri.fromFile(imagePath));
                intent.putExtra("outputFormat", "JPEG");
                intent.putExtra("aspectX", 1); // 放大和缩小
                intent.putExtra("aspectY", 1); // 如果aspectX和aspectY同时设置为相同值的话则为矩形
                intent.putExtra("outputX", 100);
                intent.putExtra("outputY", 100);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true); // no face detection
                startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUESTCODE_album);
            }
        });
        //拍照
        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toasttool.MyToast(getContext(),"paizhao");
                String SDState = Environment.getExternalStorageState();
                if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUESTCODE_cameraButton);
                } else {
                    Toasttool.MyToast(PersonalProfileActivity.this, "内存卡不存在");
                    // Toast.makeText(getContext(), "内存卡不存在", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        if (requestCode == PersonalProfileActivity.REQUESTCODE_album && resultCode == Activity.RESULT_OK) {
            //相册
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                LogUtil.i("相册", "uri=" + uri);
                if (DocumentsContract.isDocumentUri(this, uri)) {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String[] column = {MediaStore.Images.Media.DATA};
//					    String sel = MediaStore.Images.Media._ID + =?;
                    Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, "_id=?", new String[]{id}, null);
                    int columnIndex = cursor.getColumnIndex(column[0]);
                    if (cursor.moveToFirst()) {
                        String temp_imagePath = cursor.getString(columnIndex);
                        imagePath = new File(temp_imagePath);
                    }
                    cursor.close();
                } else {
                    String path = uri.getPath().toLowerCase();
                    if (!path.endsWith("jpg") && !path.endsWith("png") && !path.endsWith("gif")) {
                        Toasttool.MyToast(this, "图片格式不正确，请重新选择(jpg,png,gif)");
                        this.finish();
                        return;
                    }
                }
            }
            if (!imagePath.exists()) {
//				this.showShortToast("图片不存在，请重新选择");
              //  this.finish();
                return;
            }

            UploadImageModel uploadImageModel = new UploadImageModel(this);
            //uploadImageModel.addListener(PersonalProfileActivity.this);
            String imageString = ReadimgBase64.imgzhuanBase16(imagePath.toString());
            String userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
            String token = (String) SharedPreferencesUtils.get(this, "token", "");
            uploadImageModel.uploadPortrait(userNumber, token, imageString);
            LogUtil.i("上传图片", userNumber + "token" + token + "imagePath=" + imagePath);
            dialog.cancel();

        } else if (requestCode == REQUESTCODE_cameraButton && resultCode == Activity.RESULT_OK) {
            //拍照
            if (data != null) {
                bitmap = data.getParcelableExtra("data");
                Intent intent = this.getCropImageIntent(bitmap);
                startActivityForResult(intent, REQUESTCODE_album);
            } else {
                Toasttool.MyToast(this, "图片不存在，请重新选择");
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
        intent.putExtra("output", Uri.fromFile(imagePath));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
//	    intent.putExtra("outputX", 300);
//	    intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", "JPEG");
        return intent;
    }

    @Override
    public void uploadCallBack(ReturnBean bean, int result, String message, Throwable error) {
        progressDialog.dismiss();
        imagePath.delete();

        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra("imageUrl", bean.getObject() + "");
            this.setResult(Activity.RESULT_OK, intent);
            // getActivity().finish();
        }
        Toasttool.MyToast(this, message);
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        progressDialog.dismiss();
        //错误处理
        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if(result == DVolleyConstans.RESULT_OK){
            if(bean.getType()==DVolleyConstans.GERENXINXIBAOCUN){
                Toasttool.MyToast(this,"修改成功");
            }
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.GERENXINXI) {
                //查询所有
                try {
                    Gerenxinxi gerenxinxi = (Gerenxinxi) bean.getObject();
                    String path=gerenxinxi.getLogpath();
                    Message msg=Message.obtain();
                    msg.obj=path;
                    handler.sendMessage(msg);
                    //circleImageView.setImageBitmap(BitmapUtil.getBitmapFromBytes(BitmapUtil.getImage(path)));
                    LogUtil.i("个人信息请求数据", "gerenxinxi=" + gerenxinxi.toString());
                    if (gerenxinxi.getSex().equals("0")) {
                        img_body.setVisibility(View.GONE);
                        textView3.setText("女");
                    } else if (gerenxinxi.getSex().equals("1")) {
                        img_grild.setVisibility(View.GONE);
                        textView3.setText("男");
                    } else if ("".equals(gerenxinxi.getSex())) {
                        img_grild.setVisibility(View.GONE);
                        img_body.setVisibility(View.GONE);
                    }

                    textView1.setText(gerenxinxi.getUserName());
                    textView2.setText(gerenxinxi.getRealityName());
                    textView4.setText(gerenxinxi.getIdCard());
                    edtext1.setText(gerenxinxi.getUserName());
                    edtext2.setText(gerenxinxi.getRealityName());
                    edtext4.setText(gerenxinxi.getIdCard());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
