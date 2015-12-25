package com.ghsh.code.exception;

public class AppViewException {

	public static void onViewException(Throwable error){
		if(error!=null){
			error.printStackTrace();
		}
	}
}
