package com.fh.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Decoder.BASE64Encoder;

/**
 * 下载文件
 *rhz
 * 创建时间：2014年12月23日
 * @version
 */
public class FileDownload2 {

	/**
	 * @param response 
	 * @param filePath		//文件完整路径(包括文件名和扩展名)
	 * @param fileName		//下载后看到的文件名
	 * @return  文件名
	 */
	public static void fileDownload(HttpServletRequest request,final HttpServletResponse response, String filePath, String fileName) throws Exception{  
		byte[] data = FileUtil.toByteArray2(filePath);  
		final String userAgent = request.getHeader("USER-AGENT");
		if(userAgent.contains("Firefox")){
           //是火狐浏览器，使用BASE64编码
           fileName = "=?utf-8?b?"+new BASE64Encoder().encode(fileName.getBytes("utf-8"))+"?=";
           }else {	    	   				
    	   fileName = URLEncoder.encode(fileName, "UTF-8");  
    	}
	    response.reset();  
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
	    response.addHeader("Content-Length", "" + data.length);  
	    response.setContentType("application/octet-stream;charset=UTF-8");  
	    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
	    outputStream.write(data);  
	    outputStream.flush();  
	    outputStream.close();
	    response.flushBuffer();
	} 

}
