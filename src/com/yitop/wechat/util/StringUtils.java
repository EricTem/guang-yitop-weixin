/**
 * 
 */
package com.yitop.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author zhaoxl
 *
 */
public class StringUtils {

	public static void main(String[] args) {
		System.out.println(StringUtils.dealSpecialStr("1    a 1_1"));
	}
	
	public static String dealSpecialStr(String str) {
		Pattern charPattern = Pattern.compile("^([A-Za-z]|[\\s])+$"); 
		Pattern numberPattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$"); 
		Pattern wordPattern = Pattern.compile("[\u2E80-\u9fa5]|[\u0800-\u4e00]|[\uAC00-\uD7A3]");
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String item = str.substring(i, i + 1);
			Matcher charMatcher = charPattern.matcher(item); 
			boolean charb = charMatcher.matches();
			Matcher numberMatcher = numberPattern.matcher(item); 
			boolean numberb = numberMatcher.matches();
			Matcher wordMatcher = wordPattern.matcher(item); 
			boolean wordb = wordMatcher.matches();
			if (charb || numberb || wordb) {
				buffer.append(item);
			} else {
				buffer.append("_");
			}
		}
		return buffer.toString();
	}
	
	public static boolean isEmpty(String text) {
		return (text == null || text.trim().isEmpty()) ? true : false;
	}
	
	/**
	 * <br>
	 * StringUtils.isNotEmptyByTrim("") = false <br>
	 * StringUtils.isNotEmptyByTrim("  ") = false <br>
	 * StringUtils.isNotEmptyByTrim(null) = false <br>
	 * StringUtils.isNotEmptyByTrim("123") = true <br>
	 * StringUtils.isNotEmptyByTrim(" 123 ") = true
	 */
	public static boolean isNotEmptyByTrim(String text) {
		if (text == null)
			return false;

		if (text.trim().isEmpty())
			return false;

		return true;
	}

	/**
	 * <br>
	 * StringUtils.isNotEmptyByTrim("") = true <br>
	 * StringUtils.isNotEmptyByTrim("  ") = true <br>
	 * StringUtils.isNotEmptyByTrim(null) = true <br>
	 * StringUtils.isNotEmptyByTrim("123") = false <br>
	 * StringUtils.isNotEmptyByTrim(" 123 ") = false
	 */
	public static boolean isEmptyByTrim(String text) {
		if (text == null)
			return true;

		if (text.trim().isEmpty())
			return true;

		return false;
	}

	/**
	 * <br>
	 * StringUtils.toStringByTrim("") = "" <br>
	 * StringUtils.toStringByTrim("  ") = "" <br>
	 * StringUtils.toStringByTrim(null) = null <br>
	 * StringUtils.toStringByTrim("123") = "123" <br>
	 * StringUtils.toStringByTrim(" 123 ") = "123"
	 */
	public static String toStringByTrim(String text) {
		if (text == null)
			return null;

		return text.trim();
	}

	/**
	 * <br>
	 * StringUtils.toInteger("") = null <br>
	 * StringUtils.toInteger("  ") = null <br>
	 * StringUtils.toInteger(null) = null <br>
	 * StringUtils.toInteger("123") = 123 <br>
	 * StringUtils.toInteger(" 123 ") = 123 <br>
	 * StringUtils.toInteger("ABC") throws NumberFormatException
	 */
	public static Integer toInteger(String text) throws NumberFormatException {
		if (isNotEmptyByTrim(text))
			return Integer.valueOf(text.trim());
		else
			return null;
	}

	public static boolean equals(String text1, String text2) {
		if(text1 == text2)
			return true;
		
		if(text1 == null || text2 == null)
			return false;
		
		return text1.equals(text2);
	}
	
	/**
	 * 判断字符串text是否是身份证号码
	 * @param text
	 * @return
	 */
	public static boolean isShenFenZheng(String text) {
		if(isEmpty(text))
			return false;
		
		text = text.trim();
		int len = text.length();
		
		if((len == 15 || len == 18) && (text.matches("(^[0-9]{15}$)|(^[0-9]{18}$)|(^[0-9]{17}(\\d|X|x)$)"))) {}
		else
			return false;
		
		if(len == 15)
			return true;
		
		//15位身份证号码 = 地址码（6位）+ 出生日期码（6位）+ 顺序码（3位）
		//18位身份证号码 = 地址码（6位）+ 出生日期码（8位）+ 顺序码（3位）+ 校验码（1位）
		//18位身份证号码的顺序码中的最后一位奇数代表性别男，偶数代表性别女
		
		// 加权因子
		int[] ws = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		
		// 校验码
		char[] ys = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
		
		//S = Sum(Ai * Wi), i = 0, ..., 16
		int s = 0;
		for (int i = 0; i < 17 && i < len; i++) {
			s = s + Integer.valueOf(Character.toString(text.charAt(i))) * ws[i];
		}
		
		//Y = mod(S, 11)
		int y = s % 11;
		
		return text.charAt(text.length() - 1) == ys[y];
	}
	
	
	public static String padChar(String text, char fill, int len, boolean leftpad) {
		if(text.length() >= len)
			return text.substring(0, len);
		
		StringBuilder builder = new StringBuilder();
		int size = len - text.length();
		for (int i = 0; i < size; i++) {
			builder.append(fill);
		}
		
		if(!leftpad)
			return builder.insert(0, text).toString();
		else
			return builder.append(text).toString();
	}
	
	
	public static String md5(String str) {
		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
}
