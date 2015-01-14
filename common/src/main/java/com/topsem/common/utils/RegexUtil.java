package com.topsem.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	public static String getMatchData(String pattern, String matcher, int index) {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(matcher);
		if (m.find()) {
			return m.group(index);
		} else {
			return "";
		}
	}

	public static String getMatchData(Pattern pattern, String text, int index) {
		Matcher m = pattern.matcher(text);
		if (m.find()) {
			return m.group(index);
		} else {
			return "";
		}
	}

//	public static void main(String[] args) {
//		System.out.println(RegexUtil.getMatchData("(\\w+:\\/\\/)?([^\\.]+)(\\.[^/:]+)(:\\d*)?([^# ]*)",
//				"w.baidu.com  sdfsd", 3));
//		System.out.println(RegexUtil.getMatchData("[a-zA-Z0-9]$", "手机sssssxxx05", 0));
//		System.out.println(RegexUtil.getMatchData("([0-9]+[,]?)+", "百度为您找到相关结果约17,100,00个", 0));
//
//		String s = StringUtils.substringBetween("aa/dd/account-20130622-","/", "-");
//		String dd = "aa/dd/account-20130622-";
//		//dd = StringUtils.substringAfterLast(dd,"/");
//		//dd = StringUtils.substringBefore(dd,"-");
//		dd = dd.substring(dd.lastIndexOf("/")+1,dd.indexOf("-"));
//		System.out.println(dd);
//
//	}

}
