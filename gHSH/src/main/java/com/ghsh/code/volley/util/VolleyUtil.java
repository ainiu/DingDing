package com.ghsh.code.volley.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.preference.PreferenceManager;

import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.util.EncryptionUtil;

public class VolleyUtil {

	public static String getURL(String url, Map<String, String> map) {
		StringBuffer params = new StringBuffer();
		if (map != null && map.size() != 0) {
			for (String key : map.keySet()) {
				params.append(key).append("=").append(map.get(key)).append("&");
			}
			params.deleteCharAt(params.length()-1);
		}
		if (url.lastIndexOf('?') >= 0) {
			url = url + "&" + params;
		} else {
			url = url + "?" + params;
		}
		return url;
	}
	public static String getIdentity(Context context) {
	    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
	    String identity = preference.getString("identity", null);
	    if (identity == null) {
	        identity = java.util.UUID.randomUUID().toString();
	        identity=EncryptionUtil.MD5_32(identity);
	        Editor editor=preference.edit();
	        editor.putString("identity", identity);
	        editor.commit();
	    }
	    return identity;
	}
	
	public static String encode(String text) {
		if(text==null||text.equals("")){
			return "";
		}
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			AppViewException.onViewException(e);
			return text;
		}
	}
	
	/**格式化秒*/
	public static String formatSecond(String time) {
		if(time==null||time.equals("")||time.equals("0")){
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(new Date(Long.parseLong(time)*1000));
	}
	
	  /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
