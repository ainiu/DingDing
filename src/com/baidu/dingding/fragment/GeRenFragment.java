package com.baidu.dingding.fragment;

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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.biz.BitmapCache;
import com.baidu.dingding.code.Constants;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MyGerenxinxiModel;
import com.baidu.dingding.code.model.UploadImageModel;
import com.baidu.dingding.entity.Gerenxinxi;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.ReadimgBase64;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ChangeActivity;
import com.baidu.dingding.view.LianXiActivity;
import com.baidu.dingding.view.MyCollectionActivity;
import com.baidu.dingding.view.MyOrderActivity;
import com.baidu.dingding.view.PersonalProfileActivity;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;
import com.baidu.dingding.view.SecuritySettingsActivity;
import com.baidu.dingding.view.ShouHuoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 个人中心切记千万不要fragment.getActivity().startForResult，这样会执行activity的onActivityResult。
 */
public class GeRenFragment extends Fragment implements View.OnClickListener, UploadImageModel.Listener, DResponseListener {

    public static final int FRAGMENT_LOGIN_STATE = 1;

    private View view;
    // CircleImageView circleImageView;
    private NetworkImageView networkImageView;
    TextView tv_name, tv_email;
    Button btn_fukuan, btn_fahuo, btn_shouhuo, btn_ok, btn_byde;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;
    ListView listView;
    MyGerenxinxiModel myGerenxinxiModel;
    String[] concent = {"我的订单", "我收藏的商品", "个人资料", "修改密码", "安全设置", "联系方式", "我的收货地址"};

    int[] imagids = {R.drawable.wang_21, R.drawable.wang_22, R.drawable.wang_23, R.drawable.wang_24, R.drawable.wang_25,
            R.drawable.wang_26, R.drawable.wang_27,
    };
    String userNumber = "";

    private void islogin() {
        userNumber = (String) SharedPreferencesUtils.get(getActivity(), "userNumber", "");
        String token = (String) SharedPreferencesUtils.get(getActivity(), "token", "");
        LogUtil.i("用户登录的Token", "token=" + token);
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        islogin();
        view = View.inflate(getActivity(), R.layout.activity_persinal_center, null);
        initView();
        initModel();
        initLinener();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        myGerenxinxiModel.findConsultList(userNumber);
    }

    private void initModel() {
        myGerenxinxiModel = new MyGerenxinxiModel(getActivity());
        myGerenxinxiModel.addResponseListener(this);
    }


    private void initLinener() {
        btn_fukuan.setOnClickListener(this);
        btn_fahuo.setOnClickListener(this);
        btn_shouhuo.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_byde.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toasttool.MyToast(getActivity(), "position=" + position + "id=" + id);

                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(getActivity(), MyOrderActivity.class);                    //我的订单
                        intent0.putExtra("position", "" + position);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), MyCollectionActivity.class);                 //我的收藏
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(), PersonalProfileActivity.class);            //个人资料
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getActivity(), ChangeActivity.class);                  //修改密码
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getActivity(), SecuritySettingsActivity.class);           //安全设置
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(getActivity(), LianXiActivity.class);                   //联系方式
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(getActivity(), ShouHuoActivity.class);                  //我的收货地址
                        startActivity(intent6);
                        break;
                }
            }
        });
    }

    private void initView() {
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <= concent.length - 1; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("title_img", imagids[i]);
            listem.put("tv_concent", concent[i]);
            listems.add(listem);
        }
        SimpleAdapter simplead = new SimpleAdapter(getActivity(), listems,
                R.layout.gerenzhongxin_list_item, new String[]{"title_img", "tv_concent"},
                new int[]{R.id.geren_item_tilte_img, R.id.geren_item_geren_dingdan});
        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(simplead);
        //图片路径
        imagePath = new File(Constants.getSDCachePath(getActivity()) + "/temp1.jpg");
        progressDialog = new ProgressDialog(getActivity());
        tv_name = (TextView) view.findViewById(R.id.geren_name);
        //tv_name.setText(user.getUsrName());
        networkImageView = (NetworkImageView) view.findViewById(R.id.center_photo_01);
        tv_email = (TextView) view.findViewById(R.id.geren_email);
        //tv_email.setText(user.getEmail());
        btn_fukuan = (Button) view.findViewById(R.id.geren_daifukuan);
        btn_fahuo = (Button) view.findViewById(R.id.geren_daifahuo);
        btn_shouhuo = (Button) view.findViewById(R.id.geren_daishouhuo);
        btn_ok = (Button) view.findViewById(R.id.geren_yiwangcheng);
        btn_byde = (Button) view.findViewById(R.id.geren_tuihou);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.center_relativeLayout_02);

    }


    /**
     * 当返回来后执行在,执行OnResum
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (requestCode == GeRenFragment.REQUESTCODE_album && resultCode == Activity.RESULT_OK) {
            //相册
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                LogUtil.i("相册", "uri=" + uri);
                if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String[] column = {MediaStore.Images.Media.DATA};
//					    String sel = MediaStore.Images.Media._ID + =?;
                    Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, "_id=?", new String[]{id}, null);
                    int columnIndex = cursor.getColumnIndex(column[0]);
                    if (cursor.moveToFirst()) {
                        String temp_imagePath = cursor.getString(columnIndex);
                        imagePath = new File(temp_imagePath);
                    }
                    cursor.close();
                } else {
                    String path = uri.getPath().toLowerCase();
                    if (!path.endsWith("jpg") && !path.endsWith("png") && !path.endsWith("gif")) {
                        Toasttool.MyToast(getActivity(), "图片格式不正确，请重新选择(jpg,png,gif)");
                        dialog.cancel();
                        return;
                    }
                }
            }
            if (!imagePath.exists()) {
//		this.showShortToast("图片不存在，请重新选择");

                return;
            }
            UploadImageModel uploadImageModel = new UploadImageModel(getActivity());
            uploadImageModel.addListener(GeRenFragment.this);
            String imageString = ReadimgBase64.imgzhuanBase16(imagePath.toString());
            String userNumber = (String) SharedPreferencesUtils.get(getActivity(), "userNumber", "");
            String token = (String) SharedPreferencesUtils.get(getActivity(), "token", "");
            uploadImageModel.uploadPortrait(userNumber, token, imageString);
            LogUtil.i("上传图片", userNumber + "token" + token + "imagePath=" + imagePath);
            progressDialog.show();
        } else if (requestCode == REQUESTCODE_cameraButton && resultCode == Activity.RESULT_OK) {
            //拍照
            if (data != null) {
                bitmap = data.getParcelableExtra("data");
                Intent intent = getCropImageIntent(bitmap);
                startActivityForResult(intent, REQUESTCODE_album);
            } else {
                Toasttool.MyToast(getActivity(), "图片不存在，请重新选择");
                getActivity().finish();
                return;
            }
        }
    }


    static final int REQUESTCODE_cameraButton = 100;                 // 拍照
    static final int REQUESTCODE_album = 200;                       // 相册
    File imagePath = null;
    Dialog dialog;

    private void show() {
        dialog = new AlertDialog.Builder(getActivity())
                .create();
        dialog.setCanceledOnTouchOutside(true);
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
                dialog.dismiss();
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
        /**不加getActivity表示的是activity*/
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
                    Toasttool.MyToast(getContext(), "内存卡不存在");
                    // Toast.makeText(getContext(), "内存卡不存在", Toast.LENGTH_LONG).show();
                }
            }
        });


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
            Toasttool.MyToast(getActivity(), error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra("imageUrl", bean.getObject() + "");
            getActivity().setResult(Activity.RESULT_OK, intent);
            // getActivity().finish();
        }
        Toasttool.MyToast(getActivity(), message);
    }

    public void intent(int i) {
        Intent intent = new Intent(getContext(), MyOrderActivity.class);
        intent.putExtra("position", "" + i);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_relativeLayout_02:
                show();
                break;
            case R.id.geren_daifukuan:
                intent(1);
                break;
            case R.id.geren_daifahuo:
                intent(2);
                break;
            case R.id.geren_daishouhuo:
                intent(3);
                break;
            case R.id.geren_yiwangcheng:
                intent(4);
                break;
            case R.id.geren_tuihou:
                intent(0);
                break;
        }
    }
    protected RequestQueue mQueue = TApplication.getInstance().getRequestQueue();

    protected ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        progressDialog.dismiss();
        //错误处理
        if (error != null) {
            Toasttool.MyToast(getActivity(), error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.GERENXINXI) {
                Gerenxinxi gerenxinxi = (Gerenxinxi) bean.getObject();
                networkImageView.setImageUrl(gerenxinxi.getLogpath(),imageLoader);
                networkImageView.setDefaultImageResId(R.drawable.ding_149);
                networkImageView.setErrorImageResId(R.drawable.ding_149);
            }
        }

    }
}
