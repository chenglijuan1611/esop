package com.fh.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
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
	
	/**
	 * 分割bom清单中的代号 
	 * @param CODE
	 * @return
	 */
	public static String handle(String CODE){
		String code ="";
		//先判断是否有逗号，无逗号则不进行处理
		if(CODE.contains("，")){
			String[] s =CODE.split("，");
			for(int i=0 ;i < s.length ; i++) {
				if(s[i].contains("-")) {
					code = compute(s[i], code);
					//return d[0];
				} else {
					//无-则原样装配
					code = code + s[i] +",";
				}
			}
		} else if (CODE.contains(",")) {
			String[] s =CODE.split(",");
			for(int i=0 ;i < s.length ; i++) {
				if(s[i].contains("-")) {
					code = compute(s[i], code);
					//return d[0];
				} else {
					//无-则原样装配
					code = code + s[i] +",";
				}
			}
		}  else {
			//单个判断是否存在-
			if(CODE.contains("-")) {
				code = compute(CODE, code);
			} else {
				return CODE;	
			}
		}
		return code.substring(0, code.length()-1);
	}
    /**
     * 分割-符号
     * @param CODE
     * @param code
     * @return
     */
	public static String compute(String CODE,String code) {
		String[] d=CODE.split("-");
		//匹配多个字符或数字
		if (d.length >1) {
			String[] m=d[0].split("\\D+");
			String[] n=d[1].split("\\D+");
			String[] o=d[0].split("\\d+");
			if (m.length >1 && n.length > 1) {
				int a =Integer.parseInt(m[1]);
				int b =Integer.parseInt(n[1]);
				//D1- D3
				if(!m[1].startsWith("0")) {
					for (int j = a; j <= b ; j++) {
						code = code + o[0] + String.valueOf(j) +"," ;
					}	
				} //D01 -D03
				else {					
					for (int j = a; j <= b ; j++) {
						code = code + o[0] + String.format("%0" + m[1].length() +"d", j) +"," ;
					}
				}
				
			} else {
				//装配L1-2
				code = code + CODE +',';
			}
		} else {
			//此分支用于暴露错误的
			code = code + CODE + ",";
		}
		return code;
	}
	
	/**
	 * 用于登记首检代号信息的字符串处理函数
	 * *标记代号 证明元件存在
	 * @param CODE
	 * @param CODE1
	 * @return
	 */
	public static String divide(String CODE, String CODE1) {
			if(CODE.contains("*")) {
					if(!CODE1.contains(CODE)) {
						String[] d =CODE.split("\\*");
						return replace1(CODE1, d[1], CODE);
					} else {
						return CODE1;
					}

				} else if(CODE.contains("(")) {
					String[] m =CODE.split("\\(");
					return replace1(CODE1,"*" + m[1], m[1]);
				} else {
					return CODE1;
				}
	}
	/**
	 * 处理检查值的字符串
	 * @param a
	 * @param b
	 * @param d
	 * @return
	 */
	public static String divide1(String a, String b, String d) {
		String e = "";
		//首次创建记录
			String[] a1= a.split(",");
			if (b.contains("(")) {
				a1[Integer.parseInt(d)-1] = " ";
			} else if (b.contains("*")) {
				a1[Integer.parseInt(d)-1] = b.split("\\*")[1];
			} else {
				a1[Integer.parseInt(d)-1] = b;	
			}
			for (int i = 0; i < a1.length; i++) {
				e += a1[i] + ","; 
			}
			return e.substring(0, e.length()-1);
	}
	
	public static String divide1(String f) {
		String e = "";
		for(int i=1;i<Integer.parseInt(f); i++)
		{
			e +=" ,";
		}
		e +=" ";
		return e;
	}
	/**
	 * 将字符串组装成List类型
	 * @param f
	 * @return
	 */
	public static List<String> divide2(String f) {
		 List<String> list= new ArrayList<String>();
		 String[] s=f.split(",");
		 for (String string : s) {
			list.add(string);
		}
		 return list;
	}
	/**
	 * 将字符串和单个子件物料编码组装成List类型
	 * @param f
	 * @return
	 */
	public static List<String> divide2(String f,String CINVCODE) {
		 List<String> list= new ArrayList<String>();
		 if(f.contains(",")) {
		 String[] s=f.split(",");
		 for (String string : s) {
			list.add(CINVCODE + string);
		}
		 } else {
			 list.add(CINVCODE + f); 
		 }
		 return list;
	}
	/**
	 * list转换字符串
	 */
	public static String listToString(List<String> list) {
		String s ="";
		for (String string : list) {
			s +=string + ",";
		}
		return s.substring(0, s.length()-1);
	}
	/**
	 * 首检平台登记字符串转换
	 */
	public static String divide3(String a, String b, String c) {
		String e="";
		if (b != null) {
			e = b ;
			if (a != null) {
				String[] aa = a.split(",");
				String[] bb = b.split(",");
				for (int i = 0; i < aa.length; i++) {
					for (int j = 0; j < bb.length; j++) {
						if(aa[i].equals(bb[j])) {
							e= replace1(e, bb[j], "*" + bb[j]);
						} 
					}
				}
			}
		} else  {
			if (a != null) {
				e = "*" + c;
			} else {
				e = c;
			}
		}
		return e;
	}
	/**
	 * 用于提取存储中带*的元器件代号
	 * @param a
	 * @return
	 */
	public static String divide4(String a) {
		String e="";
		boolean s=false;
		String[] aa = a.split(",");
		for (int i = 0; i < aa.length; i++) {
			if(aa[i].contains("*")) {
				String[] bb= aa[i].split("\\*");
				e += bb[1] + ",";
				s=true;
			}
		}
		if (s) {
			return e.substring(0, e.length()-1);
		} else  {
		return e;
		}
	}

	/**
	 * 合并CODE与CODESUB
	 * @param CODE
	 * @param CODESUB
	 * @return
	 */
	public static String combine(String CODE, String CODESUB) {
		if (CODE.substring(CODE.length()-1).equals("，")) {
			CODE = CODE.substring(0, CODE.length()-1);
		}
		return CODE + "，" + CODESUB;
	}
	/**
	 * String.replaceFirst 匹配完全相符的字符串
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static String replace1(String a, String b, String c) {
		String s= "";
		String[] aa=a.split(",");
		if (aa.length > 1) {
			 for (int i = 0; i < aa.length; i++) {
				if (!aa[i].equals(b)) {
					s = s + aa[i] + ",";
				} else {
					s = s + c + ",";
				}				
			}
		} else {
			if (a.equals(b)) {
				s = c +",";
			} else {
				s = a + ",";
			}
		}
		return s.substring(0, s.length()-1);
	}
	
	/**
	 * 前台传来的CODE去掉子件物料编码前缀 
	 */
	public static String divide5(String CODE,String CINVCODE) {
		if(CODE != null) {
			String e ="";
			String[] cc = CODE.split(",");
			for (int i = 0; i < cc.length; i++) {
				cc[i] = cc[i].replaceFirst(CINVCODE, "");
				e = e + cc[i] + ",";
			}
			return e.substring(0, e.length()-1);
		} else {
			return CODE;
		}
	}
	//判断该字符串是否为整数
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
}
