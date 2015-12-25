package com.ghsh.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

	public static BigDecimal add(String a1, String a2) {
		BigDecimal b1=new BigDecimal(a1);
		BigDecimal b2=new BigDecimal(a2);
		return b1.add(b2).setScale(2);
	}
	
	public static BigDecimal add(BigDecimal a1, BigDecimal a2) {
		return a1.add(a2).setScale(2);
	}
	
	public static BigDecimal subtract(String a1, String a2) {
		BigDecimal b1=new BigDecimal(a1);
		BigDecimal b2=new BigDecimal(a2);
		return b1.subtract(b2).setScale(2);
	}
	
	public static BigDecimal multiply(String a1, String a2) {
		if(a1==null||a1.equals("")){
			a1="0.00";
		}
		if(a2==null||a2.equals("")){
			a2="0.00";
		}
		BigDecimal b1=new BigDecimal(a1);
		BigDecimal b2=new BigDecimal(a2);
		return b1.multiply(b2).setScale(2);
	}
}
