package com.ghsh.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	/**读取文件*/
	public static String readStr(String filePath){
		if(filePath==null||filePath.equals("")){
			return null;
		}
		File file=new File(filePath);
		if(!file.exists()){
			return null;
		}
		StringBuffer sb=new StringBuffer();
		FileInputStream fis=null;
		BufferedInputStream bis=null;
		try {
			fis = new FileInputStream(file);
			bis=new BufferedInputStream(fis);
			int len=0;
			byte[] bytes=new byte[1024];
			while((len=bis.read(bytes, 0, bytes.length))!=-1){
				sb.append(new String(bytes,0,len,"UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			FileUtils.closeRead(fis, bis);
		}
		return sb.toString();
	}
	/**写入文件*/
	public static void writeStr(String filePath,String data){
		if(filePath==null){
			return ;
		}
		File file=new File(filePath);
		FileOutputStream fos=null;
		BufferedOutputStream bos=null;
		try {
			fos = new FileOutputStream(file);
			bos=new BufferedOutputStream(fos);
			bos.write(data.getBytes());
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			FileUtils.closeWrite(fos, bos);
		}
	}
	
	private static void closeRead(FileInputStream fis,BufferedInputStream bis){
		try {
			if(bis!=null){
				bis.close();
			}
			if(fis!=null){
				fis.close();
			}
		} catch (IOException e) {
		}
	}
	private static void closeWrite(FileOutputStream fis,BufferedOutputStream bis){
		try {
			if(bis!=null){
				bis.close();
			}
			if(fis!=null){
				fis.close();
			}
		} catch (IOException e) {
		}
	}
}
