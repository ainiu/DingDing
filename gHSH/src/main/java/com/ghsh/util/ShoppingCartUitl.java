package com.ghsh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShoppingCartUitl {
	
	private static final String config="shopping_cart_config";
	
	public static void addCartNumber(Context context) {
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		int cartNumber=pref.getInt("cartNumber", 0);
		Editor editor = pref.edit();
		editor.putInt("cartNumber", cartNumber+1);
		editor.commit();
	}
	
	public static void setCartNumber(Context context,int cartNumber) {
		if(cartNumber<=0){
			cartNumber=0;
		}
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putInt("cartNumber", cartNumber);
		editor.commit();
	}
	
	public static int getCartNumber(Context context){
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		int  cartNumber=pref.getInt("cartNumber", 0);
		if(cartNumber<=0){
			cartNumber=0;
		}
		return cartNumber;
	}
	
	public static void clearCartNumber(Context context,int number){
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		int cartNumber=pref.getInt("cartNumber", 0);
		cartNumber=cartNumber-number;
		if(cartNumber<=0){
			cartNumber=0;
		}
		Editor editor = pref.edit();
		editor.putInt("cartNumber", cartNumber);
		editor.commit();
	}
}
