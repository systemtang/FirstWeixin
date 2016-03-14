package com.st.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CheakUtil {
	private static final String token = "st20160312";

	public static boolean cheakSignature(String signature,String timestamp,String nonce){
		//创建数组
		String[] arr = new String[]{token,timestamp,nonce};
		
		//排序
		Arrays.sort(arr);
		
		//生成字符串
		StringBuffer sb = new StringBuffer();
		for (String string : arr) {
			sb.append(string);
		}
		
		String result = getSha1(sb.toString());
		
		return result.equals(signature);
	}
	
	public static String getSha1(String str){
	    if (null == str || 0 == str.length()){
	        return null;
	    }
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	         
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char[] buf = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
}
