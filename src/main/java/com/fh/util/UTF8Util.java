package com.fh.util;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class UTF8Util {
	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
	      String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
	/**获取字符串编码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public static PageData utf(PageData pd,PageData pd2) throws UnsupportedEncodingException {      
		  Set<String> set = pd.keySet();
		  for (String key : set) {
			// 根据键去找值
			String value = pd.getString(key);
			System.out.println(key + "----" + value);
			pd2.put(key, new String(value.getBytes("ISO-8859-1"),"utf-8").toString());
			}
	      return pd2;      
    } 
}
