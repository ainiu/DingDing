package com.fanc.mycar.code.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageUtil {

    /**保存文件到本地路径*/
    public static void saveBitmapToLocalFile(String localImagePath,Bitmap bitmap){
        if(bitmap==null){
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(localImagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            bitmap = null;
        }
    }
    /**
     * 从内存卡中读取bitmap
     */
    public static Bitmap getBitmapFromFile(String localImagePath) {
        Bitmap bitmap = null;
        try {
            File file=new File(localImagePath);
            if(file.exists()){
                FileInputStream fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
            }
        } catch (FileNotFoundException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (IOException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        }catch (OutOfMemoryError ex) {
            bitmap = null;
            System.gc();
        }
        return bitmap;
    }

    /**
     * 从网络中获取bitmap
     */
    public static Bitmap getBitmapFromURL(String imageUrl, String localImagePath) {
        Bitmap bitmap =null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            FileOutputStream fos = new FileOutputStream(localImagePath);
            if(bitmap!=null){
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (FileNotFoundException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (IOException e) {
            bitmap = null;
            //AppViewException.onViewException(e);
        } catch (OutOfMemoryError ex) {
            bitmap = null;
            System.gc();
        }
        return bitmap;
    }

    /** 图片颜色叠加 */
    public static BitmapDrawable getColorOverlay(Context context, int drawable,int color) {
        Resources resources=context.getResources();
        TypedValue value = new TypedValue();
        resources.openRawResource(drawable, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
//		Bitmap mBitmap = BitmapFactory.decodeResource(resources, drawable,opts).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap mBitmap = BitmapFactory.decodeResource(resources, drawable,opts).copy(Bitmap.Config.ARGB_8888, true);
        Canvas mCanvas = new Canvas(mBitmap);
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        // 从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        // 在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);
        return new BitmapDrawable(mBitmap);
    }
}
