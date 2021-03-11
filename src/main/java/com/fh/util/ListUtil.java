package com.fh.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * List处理
 * @author Ljp
 *
 */
public class ListUtil {
	/**
	 * varList1中将符合条件的放在varList前面
	 * @param varList
	 * @param varList1
	 * @param CLASSIFICATIONNAME
	 * @return
	 */
	public static List<PageData> listToList(List<PageData> varList, List<PageData> varList1 ,String CLASSIFICATIONNAME) {
		int [] aa= new int[varList1.size()];
		int j = 0;
		boolean flag=false;
		for (int i = 0; i < varList1.size(); i++) {
			if(varList1.get(i).getString("CLASSIFICATIONNAME") != null) {
				if(varList1.get(i).getString("CLASSIFICATIONNAME").equals(CLASSIFICATIONNAME)) {
					varList.add(varList1.get(i));
					aa[j++]=i;
					flag=true;
				}
			}
		}
        if (flag) {
        	for (int k  = j-1 ; k > -1; k--) {
        		varList1.remove(aa[k]);
        	}
        } 
        varList.addAll(varList1);        
        return varList;
	}

	/**
	 * 计算list<PageData>中的平均值
	 * @param varList
	 * @return
	 */
	public static String average(List<PageData> varList) {
		Float a = 0f;
		int i = 0;
		if(varList.size() > 0) {
			for (PageData pageData : varList) {
				if(pageData.getString("INSPECTIONITEMRESULT") != null) {
					a = a + Float.valueOf(pageData.getString("INSPECTIONITEMRESULT"));
					i++;
				}
			}
			if (i == 0) {
				return null;
			}
			a = a/i;
			return String.valueOf(a);
		}
		return null;
	} 
	
	/**
	 * 计算list<PageData>中的全矩
	 * @param varList
	 * @return
	 */
	public static String range(List<PageData> varList) {
		Float a = 0f;
		int i = 0;
		Float[] s= new Float[varList.size()];
		if(varList.size() > 0) {
			for (PageData pageData : varList) {
				if(pageData.getString("INSPECTIONITEMRESULT") != null) {
					s[i] = Float.valueOf(pageData.getString("INSPECTIONITEMRESULT"));
					i++;
				}
			}
			if (i == 0) {
				return null;
			}
			//s排序
			s = sort(s, i);
			a = s[i-1] - s[0];
			return String.valueOf(a);
		}
		return null;
	}
	//冒泡排序(改进版)
	public static Float[] sort(Float[] s, int n) {
		int pass,i;
		boolean swapped=true;
		float temp;
		for(pass = n-1; pass >= 0 && swapped;pass--) {
			swapped=false;
			for(i = 0 ; i < pass ; i ++) {
				if(s[i] > s[i + 1]) {
					temp = s[i];
					s[i] = s[i + 1];
					s[i + 1] = temp;
					swapped = true;
				}
			}
		}
		return s;
	}
	/**
	 * 小于等于12时处理CODE
	 * @param i
	 * @param ListPd
	 * @return
	 */
	public static String merge(int i, List<PageData> listPd) {
		String a ="";
		for (int j=17; j <29; j ++) {
			if (!listPd.get(i).getString("var" + j).equals("")) {
				a = a + listPd.get(i).getString("var" + j) + ",";
			}
		}
		if (!a.equals("")) {
			a = a.substring(0, a.length() -1);
		}
		return a;
	}
	
	/**
	 * 大于12时处理CODE
	 * @param i
	 * @param ListPd
	 * @return
	 */
	public static String merge1(int i, List<PageData> listPd) {
		String a ="";
		int k = Integer.parseInt(listPd.get(i).getString("var16"))/12;
		for (int j=17; j <29; j ++) {
			if (!listPd.get(i).getString("var" + j).equals("")) {
				a = a + listPd.get(i).getString("var" + j) + ",";
			}
		}
		for (int n = i +1 ; n <= (i + k) ; n++) {
			if(listPd.get(n).getString("var0").equals("") && listPd.get(n).getString("var4").equals("")) {
				for (int j=17; j <29; j ++) {
					if (!listPd.get(n).getString("var" + j).equals("")) {
						a = a + listPd.get(n).getString("var" + j) + ",";
					}
			}
			}
		}
		if (!a.equals("")) {
			a = a.substring(0, a.length() -1);
		}
		return a;
	}
	
	public static PageData match(List<PageData> varList) {
		PageData pd= new PageData();
		if(varList.size() > 0) {
			for (PageData pageData : varList) {
				switch (pageData.getString("DISPLAYORDERID")) {
				case "15":
					pd.put("A", pageData.getString("INSPECTIONITEMNAME"));
					break;
				case "55":
					pd.put("B", pageData.getString("INSPECTIONITEMNAME"));
					break;
				case "60":
					pd.put("C", pageData.getString("INSPECTIONITEMNAME"));
					break;
				case "75":
					pd.put("D", pageData.getString("INSPECTIONITEMNAME"));
					break;
				case "80":
					pd.put("E", pageData.getString("INSPECTIONITEMNAME"));
					break;
				default :
					break;
				}
			}
			if(pd.getString("A") == null) {
				pd.put("A", "锡炉温度：260±3℃ 设定");
			}
			if(pd.getString("B") == null) {
				pd.put("B", "波峰1频率/转速(HZ) 17±5");
			}
			if(pd.getString("C") == null) {
				pd.put("C", "波峰2频率/转速(HZ) 22±5");
			}
			if(pd.getString("D") == null) {
				pd.put("D", "气压0.4±0.05(Mpa)");
			}
			if(pd.getString("E") == null) {
				pd.put("E", "Flux比重0.805±0.008(g/cm3)");
			}
		} else {
			pd.put("A", "锡炉温度：260±3℃ 设定");
			pd.put("B", "波峰1频率/转速(HZ) 17±5");
			pd.put("C", "波峰2频率/转速(HZ) 22±5");
			pd.put("D", "气压0.4±0.05(Mpa)");
			pd.put("E", "Flux比重0.805±0.008(g/cm3)");
		}
		return pd;		
	}
	/**
	 * 按产线进行统计生产数量，app专用接口
	 * @param varList
	 * @param varList1
	 * @return
	 */
	public static List<PageData> classify(List<PageData> varList, List<PageData> varList1) {
		//创建有序map
		Map<String,Integer> map = new TreeMap<String,Integer>();
		Map<String,Integer> map1 = new HashMap<String,Integer>();
		for (PageData pageData : varList1) {
			if(pageData.getString("NAME") != null) {
				if(!map.containsKey(pageData.getString("NAME"))) {
					//累积数量
					map.put(pageData.getString("NAME"), Integer.valueOf(pageData.getString("PLAN_AMOUNT")));
					map1.put(pageData.getString("NAME"), Integer.valueOf(pageData.getString("PRODUCT_AMOUNT")));
				} else {
					map.put(pageData.getString("NAME"), map.get(pageData.getString("NAME")) + Integer.valueOf(pageData.getString("PLAN_AMOUNT")));
					map1.put(pageData.getString("NAME"), map1.get(pageData.getString("NAME")) + Integer.valueOf(pageData.getString("PRODUCT_AMOUNT")));
				}
			}
		}
		//统计列表
		for (String key : map.keySet()) {
			PageData pd1 = new PageData();
			pd1.put("LINENAME", key);
			pd1.put("PLAN_AMOUNT", String.valueOf(map.get(key)));
			pd1.put("PRODUCT_AMOUNT", String.valueOf(map1.get(key)));
			varList.add(pd1);
			}
		return varList;
	}
	
	/**
	 * 按车间统计生产数量
	 * @param varList
	 * @param varList1
	 * @return
	 */
	public static List<PageData> classify1(List<PageData> varList, List<PageData> varList1) { 
		Map<String,Integer> map = new HashMap<String,Integer>();
		Map<String,Integer> map1 = new HashMap<String,Integer>();
		Map<String,Integer> map2 = new HashMap<String,Integer>();
		map.put("一车间", 0);
		map.put("二车间", 0);
		map.put("三车间", 0);
		map1.put("一车间", 0);
		map1.put("二车间", 0);
		map1.put("三车间", 0);
		map2.put("一车间", 1);
		map2.put("二车间", 2);
		map2.put("三车间", 3);
		for (PageData pageData : varList1) {
			if(pageData.getString("NAME") != null) {
				String WORKSHOP = "";
				if(pageData.getString("NAME").contains("贴片") || pageData.getString("NAME").contains("SMT")) {
					WORKSHOP = "一车间";
				} else if(pageData.getString("NAME").contains("B")) {
					WORKSHOP = "二车间";
				} else if(pageData.getString("NAME").contains("C")) {
					WORKSHOP = "三车间";
				} else {
					continue;
				}
				map.put(WORKSHOP, map.get(WORKSHOP) + Integer.valueOf(pageData.getString("PLAN_AMOUNT")));
				map1.put(WORKSHOP, map1.get(WORKSHOP) + Integer.valueOf(pageData.getString("PRODUCT_AMOUNT")));
			}
		}
		//统计列表
		for (String key : map1.keySet()) {
			PageData pd1 = new PageData();
			pd1.put("WORKSHOP", key);
			pd1.put("PLAN_AMOUNT", String.valueOf(map.get(key)));
			pd1.put("PRODUCT_AMOUNT", String.valueOf(map1.get(key)));
			pd1.put("TYPE", String.valueOf(map2.get(key)));
			varList.add(pd1);
			}
		return varList;
	}
	
	/**返回一个包含有理论数量的计划列表
	 * @param
	 * @throws Exception
	 */
	public static List<PageData> theoryPlanList(List<PageData> planList , String currentTime){
		//循环得到 计算该产线所有计划今天的理论产量
		for(PageData pageData : planList) {
			float theoryNumber = 0;
			String STATE_CHANGETIME = pageData.getString("STATE_CHANGETIME");
			String EFFECTIVE_TIME = pageData.getString("EFFECTIVE_TIME");
			String PRODUCT_BEAT = pageData.getString("PLAN_PRODUCTIONBEAT");
			String REALITY_STARTTIME = pageData.getString("PLAN_STARTTIME");
			String REALITY_ENDTIME = pageData.getString("PLAN_ENDTIME");
			//float product_amount = Float.parseFloat(pageData.getString("PRODUCT_AMOUNT"))+Float.parseFloat(pageData.getString("MODIFY_AMOUNT"));
			//pageData.put("PRODUCT_AMOUNT", (int)product_amount+"");
			//计算理论产量
			float timediff = 0;
			float effectivetime = 0;
			float beat=0;
			if(PRODUCT_BEAT != null) {
				beat=Float.parseFloat(PRODUCT_BEAT);
			}else {
				beat=40;
			}
			if("生产中".equals(pageData.getString("STATUS"))) {
				if(STATE_CHANGETIME != null) {
					timediff = DateUtil.getSecondSub(STATE_CHANGETIME, currentTime);
					effectivetime = Float.parseFloat(EFFECTIVE_TIME);
				}else {
					timediff = DateUtil.getSecondSub(REALITY_STARTTIME, currentTime);
				}
			}else if(pageData.getString("STATUS").contains("休息中")) {
				if(pageData.getString("STATUS").equals("生产中休息中") || pageData.getString("STATUS").equals("停工中休息中")) {
					if(EFFECTIVE_TIME!=null) {
						effectivetime=Float.parseFloat(EFFECTIVE_TIME);
					}
				}else if(pageData.getString("STATUS").equals("计划完成休息中")) {
					//计划已经完成，计算的就是结束的时间（时间节点）和开始的时间
					if(STATE_CHANGETIME != null) {
						timediff = DateUtil.getSecondSub(STATE_CHANGETIME, REALITY_ENDTIME);
					}else {
						timediff = DateUtil.getSecondSub(REALITY_STARTTIME, REALITY_ENDTIME);
					}
					if(EFFECTIVE_TIME != null) {
						effectivetime = Float.parseFloat(EFFECTIVE_TIME);
					}
				}
				pageData.put("STATUS", "休息中");
			}else if(pageData.get("STATUS").equals("停工中")) {
				if(EFFECTIVE_TIME != null) {
					effectivetime = Float.parseFloat(EFFECTIVE_TIME);
				}
			}else if(pageData.get("STATUS").equals("计划完成")) {
				//计划已经完成，计算的就是结束的时间（时间节点）和开始的时间
				if(STATE_CHANGETIME != null) {
					timediff = DateUtil.getSecondSub(STATE_CHANGETIME, REALITY_ENDTIME);
				}else {
					timediff = DateUtil.getSecondSub(REALITY_STARTTIME, REALITY_ENDTIME);
				}
				if(EFFECTIVE_TIME != null) {
					effectivetime = Float.parseFloat(EFFECTIVE_TIME);
				}
				pageData.put("STATUS", "完成");
			}
			float totalTime = timediff + effectivetime;
			theoryNumber = (float) (totalTime/beat);
			pageData.put("THEORY_AMOUNT", (int)theoryNumber);
			DecimalFormat df = new DecimalFormat("######0.00");
//			String ST = "";
			
//			if(theoryNumber == 0 && product_amount == 0) {
//				ST = "0.00%" ;
//			}else if(theoryNumber == 0 && product_amount != 0){
//				ST = "100%" ;
//			}else {
//				ST = df.format(product_amount/theoryNumber*100)+"%";
//			}
//			pageData.put("ST", ST);
			//pageData.put("PREDICT_ENDTIME", pageData.getString("PREDICT_ENDTIME").split(" ")[1]);
		}
		return planList;
	}
}

