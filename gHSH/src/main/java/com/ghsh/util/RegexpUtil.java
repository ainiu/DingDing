package com.ghsh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**正则表达式**/
public class RegexpUtil {
	
	/**邮箱**/
	public static final String EMAIL_MATCHER="^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	/**手机号码**/
	public static final String MOL_NUMBER="^((13)|(14)|(15)|(17)|(18)|(19))\\d{9}$";
	/**邮编**/
	public static final String CODE_NUMBER="^[1-9][0-9]{5}$";
	/**注册名（含中文）*(英文、_、中文、$)*/
	public static final String USER_NAME_CH="^[A-Za-z_$\u4e00-\u9fa5].*";
	/**注册名（不含中文）*(英文、_、$)*/
	public static final String USER_NAME="^[A-Za-z_$].*";
	/**数字**/
	public static final String NUMBER="^[0-9]*$";
	/**字母**/
	public static final String Letter="^[a-zA-Z]*$";
	public static boolean checkEmail(String input){
		 Pattern pattern = Pattern.compile(EMAIL_MATCHER);
		 Matcher matcher = pattern.matcher(input);
		 return  matcher.matches();
	}
	
	public static boolean checkMobNumber(String input){
		Pattern pattern = Pattern.compile(MOL_NUMBER);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	public static boolean checkCodeNumber(String input){
		Pattern pattern = Pattern.compile(CODE_NUMBER);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	//检查是否为数字
	public static boolean checkNumber(String input){
		Pattern pattern = Pattern.compile(NUMBER);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	//检查是否为字母
	public static boolean checkLetter(String input){
		Pattern pattern = Pattern.compile(Letter);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
//	public static boolean checkUser_Name_CH(String input){
//		Pattern pattern = Pattern.compile(USER_NAME_CH);
//		Matcher matcher = pattern.matcher(input);
//		return matcher.matches();
//	}
	
//	public static boolean chekcUser_Name(String input){
//		Pattern pattern = Pattern.compile(USER_NAME);
//		Matcher matcher = pattern.matcher(input);
//		return matcher.matches();
//	}
}
