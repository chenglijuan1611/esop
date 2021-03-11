package com.fh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.fh.controller.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
/**
 * @author zhangrui
 * 
 * 说明：设备数据解析
 */
public class DeviceDataAnalysis extends BaseController {
	
	/*回流焊Error.log数据解析，按行读取txt文本，并将读取到的内容存到lineTxtList集合中*/
	public List<String> readErrorTxt(String filePath) {
		 List<String> lineTxtList = null;
		 try {
			 File file = new File(filePath);//文件路径
			 if(file.isFile() && file.exists()) {//判断文件是否存在
				 InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
				 BufferedReader  bufferedReader = new BufferedReader(read);
				 String lineTxt = null;
				 lineTxtList = new ArrayList<String>();
				 for (int i = 0; i < 5; i++) {//跳过前面5行
					 if((lineTxt = bufferedReader.readLine())!= null) {
						 continue;
					 }
				 }
				 while ((lineTxt = bufferedReader.readLine())!= null) {
					 lineTxt = lineTxt.trim();
					 lineTxtList.add(lineTxt);//按行读取出的字符串存在集合中
				}
				 read.close(); 
			 }else {
				logBefore(logger, "找不到指定的txt文件了", 1);
				return null;
			}
		} catch (IOException e) {
			logBefore(logger, "读取txt文件内容出错", 1);
			e.printStackTrace();
		}
		return lineTxtList;
	}
	
	/*解析readErrorTxt方法返回的集合所读取到的每一行的前几列*/
	public List<PageData> dataAnalysis(List<String> list) {//此处把上述方法的返回值lineTxtList传进来
		List<PageData> listPageData= new ArrayList<>();
		for (int i = 0; i <list.size(); i++) {
			PageData pagedata = new PageData();
			String str = list.get(i);//str代表集合的一个元素，即一行字符串
			String TIME = "20" + StringUtils.substring(str, 0, 17);//获取字符串str中的时间列
			String STATUS = StringUtils.substringBetween(str, "<", ">");//获取到<>这两字符所夹的字符串
			int a = StringUtils.ordinalIndexOf(str, ">", 1);//返回字符串>在字符串中第1次出现的位置。
			int b = StringUtils.ordinalIndexOf(str, "[", 2);//返回字符串[在字符串中第2次出现的位置。
			String PROCESS = StringUtils.substring(str, a+1, b-1).replaceAll("。", "");
			pagedata.put("TIME", TIME );
			pagedata.put("STATUS", STATUS);
			pagedata.put("PROCESS", PROCESS);
			pagedata.put("ID", this.get32UUID());
			listPageData.add(pagedata);
			/*System.out.println(time+status+process);*/
		}
		return listPageData;
	}
	
	/*SMT贴片机数据解析，按行读取txt文本，并将读取到的内容存到lineTxtList集合中*/
	public List<String> readSmtTxt(String filePath) {
		 List<String> lineTxtList = null;
		 try {
			 File file = new File(filePath);//文件路径
			 if(file.isFile() && file.exists()) {//判断文件是否存在
				 InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
				 BufferedReader  bufferedReader = new BufferedReader(read);
				 String lineTxt = null;
				 lineTxtList = new ArrayList<String>();
				 /*for (int i = 0; i < 1; i++) {//跳过前面1行
					 if((lineTxt = bufferedReader.readLine())!= null) {
						 continue;
					 }
				 }*/
				 while ((lineTxt = bufferedReader.readLine())!= null) {
					 lineTxt = lineTxt.trim();
					 lineTxtList.add(lineTxt);//按行读取出的字符串存在集合中
				}
				 read.close(); 
			 }else {
				logBefore(logger, "找不到指定的txt文件了", 1);
				return null;
			}
		} catch (IOException e) {
			logBefore(logger, "读取txt文件内容出错", 1);
			e.printStackTrace();
		}
		return lineTxtList;
	}
	
	/*解析readSmtTxt方法返回的集合所读取到各列*/
	public List<PageData> dataAnalysis2(List<String> list) {//此处把上述方法的返回值lineTxtList传进来
		List<PageData> listPageData= new ArrayList<>();
		/*文本字段:49个*/
		String[] arr = {"TIME","PROJECT_NAME","COL1_SETTEMP","COL1_ACTUTEMP","TOP1_SETTEMP","TOP2_SETTEMP","TOP3_SETTEMP","TOP4_SETTEMP","TOP5_SETTEMP","TOP6_SETTEMP","TOP7_SETTEMP","TOP8_SETTEMP","TOP9_SETTEMP","TOP10_SETTEMP","TOP1_ACTUTEMP","TOP2_ACTUTEMP","TOP3_ACTUTEMP","TOP4_ACTUTEMP","TOP5_ACTUTEMP","TOP6_ACTUTEMP","TOP7_ACTUTEMP","TOP8_ACTUTEMP","TOP9_ACTUTEMP","TOP10_ACTUTEMP","SET_SPEED","ACTL_SPEED","FAN1_SPEED","BOTTOM1_SETTEMP","BOTTOM2_SETTEMP","BOTTOM3_SETTEMP","BOTTOM4_SETTEMP","BOTTOM5_SETTEMP","BOTTOM6_SETTEMP","BOTTOM7_SETTEMP","BOTTOM8_SETTEMP","BOTTOM9_SETTEMP","BOTTOM10_SETTEMP","BOTTOM1_ACTUTEMP","BOTTOM2_ACTUTEMP","BOTTOM3_ACTUTEMP","BOTTOM4_ACTUTEMP","BOTTOM5_ACTUTEMP","BOTTOM6_ACTUTEMP","BOTTOM7_ACTUTEMP","BOTTOM8_ACTUTEMP","BOTTOM9_ACTUTEMP","BOTTOM10_ACTUTEMP","SET_SPEED2","ACTL_SPEED2"};
		for (int i = 0; i <list.size(); i++) {
			PageData pagedata = new PageData();
			String[] rows = list.get(i).trim().replaceAll(",,,,,,,", "").split(",");//取到的一行切割为数组
			//System.out.println(rows.length);//47,有些待读取的文档前三行有格式错误?	
			for (int j = 0; j < rows.length; j++) {//切分后的字段值分别赋给arr数组字段，并封装在pagadata对象中
				//pagedata.put("\""+arr[j]+"\"", rows[j]);
				pagedata.put(arr[j], rows[j]);
			}
			for(int j = 0;j<arr.length - rows.length;j++) {//arr中比rows多余的字段值置为null
				pagedata.put(arr[rows.length+j], null);	
			}
			pagedata.put("ID", this.get32UUID());
			//System.out.println(pagedata);
			listPageData.add(pagedata);
		}
		return listPageData;
	}
}



