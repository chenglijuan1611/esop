package com.fh.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class ServerFileUtil {
	
	// 从服务器获得一个输入流(本例是指从服务器获得一个文件输入流)
		public static InputStream getInputStream(String urlPath) {
			InputStream inputStream = null;
			HttpURLConnection httpURLConnection = null;
			try {
				URL url = new URL(urlPath);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				// 设置网络连接超时时间
				httpURLConnection.setConnectTimeout(60000);
				// 设置应用程序要从网络连接读取数据
				httpURLConnection.setDoInput(true);
				httpURLConnection.setRequestMethod("GET");
				int responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 200) {
					inputStream = httpURLConnection.getInputStream();// 从服务器返回一个输入流
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return inputStream;
		}
		public static void deleteServerFile(Client client,String fileUrl) {
			WebResource wr = client.resource(fileUrl);
			wr.delete();
		}
		
		public static byte[] toByteArray(InputStream input) throws IOException {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*4];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		}
		
			
		
		

}
