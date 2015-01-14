package com.topsem.common.utils;

import java.math.BigDecimal;

public class MathUtil {

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bigDecimal = new BigDecimal(v);
		return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double getMiddlePrice(double price1, double price2) {
		return MathUtil.round((price1 + price2) / 2, 2);
	}
	
	public static boolean greatThanHalf(double firstPrice, double otherPrice){
		if(otherPrice == 0 || otherPrice == 0){
			return false;
		}
		if(firstPrice > otherPrice){
			return (firstPrice/otherPrice) > 2; 
		}else{
			return (otherPrice/firstPrice) > 2; 
		}
	}
}
