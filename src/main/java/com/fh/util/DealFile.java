package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ScatteringByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.i;

public class DealFile {
	public static void main(String[] args) {
//		traverseFolder1("F:\\Upload");
		traverseFolder2("C:\\processFile");
	}
	/*
	 * 将物料在B01产线下的工艺文件转换成该物料下的工艺文件
	 * 
	 * */
	//实现D:/processFile文件下的文件的拷贝//D:\processFile\3311586C01\B01\3311586C01-B01.pdf
	public static void traverseFolder2(String path) {
		File file = new File(path);//D:\processFile
		String newFile = "";
		String oldFile = "";
		String material = "";
		String lineName = "";
		if (file.exists()) {
			File[] files = file.listFiles();
			int i = 1;
			for (File file2 : files) {
				String name = file2.getName();//3311586C01
				String newfilepath = file2.getAbsolutePath();//D:\processFile\3311586C01
				System.out.println("~~~~~~~~~~~~~~~~"+i+"~~~~~~~~~~"+name+"~~~~~~");
				material = name;
				File[] files3 = file2.listFiles();
				for(File file3 : files3) {
					String filename = file3.getName();//B01
					lineName = filename;
					String filepath = file3.getAbsolutePath();//D:\processFile\3311586C01\B01
					String olddispath = filepath+"\\display";
					File[] files4 = file3.listFiles();
					File f = files4[0];
					String savepath = material+"/"+material+".pdf";//  /3242900330/3242900330-B01.pdf
					if("B01".equals(filename)) {
						//实现完整文件的拷贝
						oldFile = f.getAbsolutePath();
						newFile =  "E:\\processFile\\"+material+"\\"+material+".pdf";
						File file5 = new File(newFile);  
						if(!file5.getParentFile().exists()) {
							file5.getParentFile().mkdirs();
						}
						copyFile(oldFile,newFile);
						//实现display文件夹下的文件的拷贝
						File ff = new File(olddispath);
						if(ff.isDirectory()) {
							File[] ffFiles = ff.listFiles();
							for(int k = 0;k < ffFiles.length;k++) {
								File oldpath2 = ffFiles[k];
								String disoldpath = oldpath2.getAbsolutePath();
								String disnewpath = disoldpath.replaceFirst("C", "E");
								File ffFile = new File(disnewpath);
								if(!ffFile.getParentFile().exists()){
									ffFile.getParentFile().mkdirs();
								}
								copyFile(disoldpath,disnewpath);
							}
						}
						//数据库保存物料完整路径的地址及对应工艺文件的拷贝
						processFileUrlSave2(material,lineName,savepath);
						break;
					}else if("B03".equals(filename)) {
						oldFile = f.getAbsolutePath();
						newFile =  "E:\\processFile\\"+material+"\\"+material+".pdf";
						File file5 = new File(newFile);  
						if(!file5.getParentFile().exists()) {
							file5.getParentFile().mkdirs();
						}
						copyFile(oldFile,newFile);
						//实现display文件夹下的文件的拷贝
						File ff = new File(olddispath);
						if(ff.isDirectory()) {
							File[] ffFiles = ff.listFiles();
							for(int k = 0;k < ffFiles.length;k++) {
								File oldpath2 = ffFiles[k];
								String disoldpath = oldpath2.getAbsolutePath();
								String disnewpath = disoldpath.replaceFirst("C", "E");
								File ffFile = new File(disnewpath);
								if(!ffFile.getParentFile().exists()){
									ffFile.getParentFile().mkdirs();
								}
								copyFile(disoldpath,disnewpath);
							}
						}
						//数据库保存物料完整路径的地址及对应工艺文件的拷贝
						processFileUrlSave2(material,lineName,savepath);
						break;
					}else if("C03".equals(filename)) {
						oldFile = f.getAbsolutePath();
						newFile =  "E:\\processFile\\"+material+"\\"+material+".pdf";
						File file5 = new File(newFile);  
						if(!file5.getParentFile().exists()) {
							file5.getParentFile().mkdirs();
						}
						copyFile(oldFile,newFile);
						//实现display文件夹下的文件的拷贝
						File ff = new File(olddispath);
						if(ff.isDirectory()) {
							File[] ffFiles = ff.listFiles();
							for(int k = 0;k < ffFiles.length;k++) {
								File oldpath2 = ffFiles[k];
								String disoldpath = oldpath2.getAbsolutePath();
								String disnewpath = disoldpath.replaceFirst("C", "E");
								File ffFile = new File(disnewpath);
								if(!ffFile.getParentFile().exists()){
									ffFile.getParentFile().mkdirs();
								}
								copyFile(disoldpath,disnewpath);
							}
						}
						//数据库保存物料完整路径的地址及对应工艺文件的拷贝
						processFileUrlSave2(material,lineName,savepath);
						break;
					}else if("C07".equals(filename)) {
						oldFile = f.getAbsolutePath();
						newFile =  "E:\\processFile\\"+material+"\\"+material+".pdf";
						File file5 = new File(newFile);  
						if(!file5.getParentFile().exists()) {
							file5.getParentFile().mkdirs();
						}
						copyFile(oldFile,newFile);
						//实现display文件夹下的文件的拷贝
						File ff = new File(olddispath);
						if(ff.isDirectory()) {
							File[] ffFiles = ff.listFiles();
							for(int k = 0;k < ffFiles.length;k++) {
								File oldpath2 = ffFiles[k];
								String disoldpath = oldpath2.getAbsolutePath();
								String disnewpath = disoldpath.replaceFirst("C", "E");
								File ffFile = new File(disnewpath);
								if(!ffFile.getParentFile().exists()){
									ffFile.getParentFile().mkdirs();
								}
								copyFile(disoldpath,disnewpath);
							}
						}
						//数据库保存物料完整路径的地址及对应工艺文件的拷贝
						processFileUrlSave2(material,lineName,savepath);
						break;
					}else if("C05".equals(filename)){
						oldFile = f.getAbsolutePath();
						newFile =  "E:\\processFile\\"+material+"\\"+material+".pdf";
						File file5 = new File(newFile);  
						if(!file5.getParentFile().exists()) {
							file5.getParentFile().mkdirs();
						}
						copyFile(oldFile,newFile);
						//实现display文件夹下的文件的拷贝
						File ff = new File(olddispath);
						if(ff.isDirectory()) {
							File[] ffFiles = ff.listFiles();
							for(int k = 0;k < ffFiles.length;k++) {
								File oldpath2 = ffFiles[k];
								String disoldpath = oldpath2.getAbsolutePath();
								String disnewpath = disoldpath.replaceFirst("C", "E");
								File ffFile = new File(disnewpath);
								if(!ffFile.getParentFile().exists()){
									ffFile.getParentFile().mkdirs();
								}
								copyFile(disoldpath,disnewpath);
							}
						}
						//数据库保存物料完整路径的地址及对应工艺文件的拷贝
						processFileUrlSave2(material,lineName,savepath);
						break;
					}
					
				}
				i++;
				} 
		}
		System.out.println("----"+material);
		System.out.println("----"+lineName);
	}
	
	
	//实现文件上传路径的保存2
		public static void processFileUrlSave2(String materialCode,String lineNanme,String savePath) {
			//通过物料编码在MX表中找到该物料对应的MX_ID
			Connection con = null;
		    PreparedStatement statement = null;
			ResultSet res = null;
			String mxId = "";
	         try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            con = DriverManager.getConnection("jdbc:sqlserver://192.168.0.9:1433;DatabaseName=DFMES_TEST","sa","ran2003");
	           //查询PROCESSFILEMANAGERMX表
	            String sql = "select PROCESSFILEMANAGERMX_ID from BASE_PROCESSFILEMANAGERMX where MATERIAL_CODING = '"+materialCode+"'";
	            System.out.println("-------sql--------"+sql);
	            statement = con.prepareStatement(sql);
	            res = statement.executeQuery();
	            while(res.next()){
					mxId = res.getString("PROCESSFILEMANAGERMX_ID");//获取PROCESSFILEMANAGERMX_ID列的元素 
					String sql2 = "insert into BASE_PROCESSFILESAVE2(MATERIAL_CODING,FILE_SAVEPATH,PROCESSFILEMANAGERMX_ID) values ('"+materialCode+"','"+savePath+"','"+mxId+"')";
					System.out.println("----------sql2-----"+sql2);
					statement = con.prepareStatement(sql2);
					statement.executeUpdate();//pstmt.executeUpdate();
	            }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }finally{
	             try {
	                 if(res != null) res.close();
	                 if(statement != null) statement.close();
	                 if(con != null) con.close();
	             } catch (Exception e2) {
	                 e2.printStackTrace();
	            }
	        }
	         try{
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            con = DriverManager.getConnection("jdbc:sqlserver://192.168.0.9:1433;DatabaseName=DFMES_TEST","sa","ran2003");
	            //查出来当前物料的在该产线的详细物料信息，插入到另外一张表里
	            String sql3 = "select PROCESSFILEMANAGERMXMX_ID,PAGE,TERMINAL_EQUIPMENT from BASE_PROCESSFILEMANAGERMXMX where LINE_CODE = '"+lineNanme+"'and PROCESSFILEMANAGERMX_ID = '"+mxId+"'";
	            statement = con.prepareStatement(sql3);
	            res = statement.executeQuery();
	            while(res.next()) {
	            	String mxmxId = res.getString("PROCESSFILEMANAGERMXMX_ID");
	            	String page = res.getString("PAGE");
	            	String terCode = res.getString("TERMINAL_EQUIPMENT");
	            	String sql4 = "insert into BASE_PROCESSFILEMANAGERMXMX2(PROCESSFILEMANAGERMXMX_ID,PROCESSFILEMANAGERMX_ID,PAGE,TERMINAL_EQUIPMENT,LINE_CODE) values('"+mxmxId+"','"+mxId+"','"+page+"','"+terCode+"','"+lineNanme+"')";
	            	statement = con.prepareStatement(sql4);
					statement.executeUpdate();		
	            }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }finally{
	             try {
	                 if(res != null) res.close();
	                 if(statement != null) statement.close();
	                 if(con != null) con.close();
	             } catch (Exception e2) {
	                 e2.printStackTrace();
	            }
	        }
		}
	
	
	
	//为了将智慧工厂的工艺文件转到mes系统
	public static void traverseFolder1(String path) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			Map<String, Long> timemap = new HashMap<>();
	        Map<String, String> urlmap = new HashMap<>();
			for (File file2 : files) {
				String fileName = file2.getName();
				String filePath = file2.getAbsolutePath();
				String[] fileNameSplit = fileName.split("-");
				String materialCode = fileNameSplit[1];
		        long time = file2.lastModified(); 
		        if(timemap.containsKey(materialCode)) {
		        	if(timemap.get(materialCode) < time) {
		        		timemap.put(materialCode, time);
		        		 urlmap.put(materialCode, filePath);
		        	}
		        }else {
		        	timemap.put(materialCode, time);
	        		urlmap.put(materialCode, filePath);
		        }
			}
			for (String key : urlmap.keySet()) {
				String filePath2 = urlmap.get(key);
				//将文件保存的路径存储到MXMX表中
//				processFileUrlSave(key,"B01",key+"\\B01\\"+key+"-"+"B01.pdf");
//				processFileUrlSave(key,"B03",key+"\\B03\\"+key+"-"+"B03.pdf");
//				processFileUrlSave(key,"C03",key+"\\C03\\"+key+"-"+"C03.pdf");
				//processFileUrlSave(key,"C05",key+"\\C05\\"+key+"-"+"C05.pdf");
				//实现文件的复制
//				String newfilePath1  = "F:\\processFile\\"+key+"\\B01\\"+key+"-"+"B01.pdf";
//				String newfilePath2  = "F:\\processFile\\"+key+"\\B03\\"+key+"-"+"B03.pdf";
//				String newfilePath3  = "F:\\processFile\\"+key+"\\C03\\"+key+"-"+"C03.pdf";
				String newfilePath4  = "F:\\processFile\\"+key+"\\C05\\"+key+"-"+"C05.pdf";
//				File f1 = new File(newfilePath1);
//				File f2 = new File(newfilePath2);
//				File f3 = new File(newfilePath3);
				File f4 = new File(newfilePath4);
//				if(!f1.getParentFile().exists()){
//					f1.getParentFile().mkdirs();
//				}
//				if(!f2.getParentFile().exists()){
//					f2.getParentFile().mkdirs();
//				}
//				if(!f3.getParentFile().exists()){
//					f3.getParentFile().mkdirs();
//				}
				if(!f4.getParentFile().exists()){
					f4.getParentFile().mkdirs();
				}
//				copyFile(filePath2,newfilePath1);
//				copyFile(filePath2,newfilePath2);
//				copyFile(filePath2,newfilePath3);
				copyFile(filePath2,newfilePath4);
				} 
		}
	}
	
	//实现文件的拷贝
	public static void copyFile(String oldPath,String newPath) {
		int byteread = 0;
		File oldFile = new File(oldPath);
		if(oldFile.exists()) {
			 try {
				InputStream inStream = new FileInputStream(oldPath); //读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				try {
					while((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
					}
					inStream.close();
					fs.close(); 
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	//实现文件上传路径的保存
	public static void processFileUrlSave(String materialCode,String lineNanme,String savePath) {
		//通过物料编码在MX表中找到该物料对应的MX_ID
		Connection con = null;
	    PreparedStatement statement = null;
		ResultSet res = null;
         try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://192.168.0.9:1433;DatabaseName=DFMES_DB","sa","ran2003");
            String sql = "select PROCESSFILEMANAGERMX_ID from BASE_PROCESSFILEMANAGERMX where MATERIAL_CODING = '"+materialCode+"'";//查询test表
            statement = con.prepareStatement(sql);
            res = statement.executeQuery();
            while(res.next()){
				String title = res.getString("PROCESSFILEMANAGERMX_ID");//获取test_name列的元素 
				String sql2 = "insert into BASE_PROCESSFILESAVE(MATERIAL_CODING,LINE_CODE,FILE_SAVEPATH,PROCESSFILEMANAGERMX_ID) values ('"+materialCode+"','"+lineNanme+"','"+savePath+"','"+title+"')";
				statement = con.prepareStatement(sql2);
				statement.executeQuery();
            }
         } catch (Exception e) {
             e.printStackTrace();
         }finally{
             try {
                 if(res != null) res.close();
                 if(statement != null) statement.close();
                 if(con != null) con.close();
             } catch (Exception e2) {
                 e2.printStackTrace();
            }
        }
	}
}
