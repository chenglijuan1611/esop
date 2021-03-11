package com.fh.controller.base.processfilemanagermxmx;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.DealFile;
import com.fh.util.DelAllFile;
import com.fh.util.ServerFileUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PDFReader;
import com.fh.util.PageData;
import com.fh.util.Pdf2Image;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import net.sf.json.JSONObject;
import com.fh.util.Jurisdiction;
import com.fh.service.base.classification.ClassificationManager;
import com.fh.service.base.processfilemanagermx.ProcessFileManagerMxManager;
import com.fh.service.base.processfilemanagermxmx.ProcessFileManagerMxMxManager;
import com.fh.service.base.product.ProductManager;
import com.fh.service.base.productline.ProductlineManager;
import com.fh.service.base.terminal.TerminalManager;

/** 
 * 说明：工艺管理明细(明细)
 *rhz
 * 创建时间：2018-08-14
 */
@Controller
@RequestMapping(value="/processfilemanagermxmx")
public class ProcessFileManagerMxMxController extends BaseController {
	
	String menuUrl = "processfilemanagermxmx/list.do"; //菜单地址(权限用)
	@Resource(name="processfilemanagermxmxService")
	private ProcessFileManagerMxMxManager processfilemanagermxmxService;
	@Resource(name="processfilemanagermxService")
	private ProcessFileManagerMxManager processfilemanagermxService;
	@Resource(name="classificationService")
	private ClassificationManager classificationService;
	@Resource(name="productlineService")
	private ProductlineManager productlineService;
	@Resource(name="productService")
	private ProductManager productService;
	@Resource(name="terminalService")
	private TerminalManager terminalService;
	private  FTPClient ftp;
	/*
	 * 上传视频uploadVideo
	 * */
	@RequestMapping(value="/uploadVideo")
	@ResponseBody
	public JSONObject uploadvideo(@RequestParam("file") MultipartFile file,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 //获取前端传过来的参数
		PageData pd = this.getPageData();
		JSONObject jsonObject=new JSONObject();
		String material = pd.getString("MATERIAL_CODING");
		String terminalCode = pd.getString("terminalCode");
		String suffix = "mp4";
		//获得文件后缀名
		if(file != null) {
			int begin = file.getOriginalFilename().indexOf(".");
			int last = file.getOriginalFilename().length();
			suffix = file.getOriginalFilename().substring(begin, last);
		}else {
			jsonObject.put("result", "fileIsNull");
			return jsonObject;
		}
		Properties properties = new Properties();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			//从配置文件中读取服务器的IP和端口号 
			fileReader = new FileReader("E:/serverconfig.properties");
			bufferedReader = new BufferedReader(fileReader);
			properties.load(bufferedReader);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "propertiesIsNotExist");
			return jsonObject;
		}finally {
			bufferedReader.close();
			fileReader.close();
		}
		String addr = properties.getProperty("addr");//FTP服务器地址
		int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
		String username = properties.getProperty("username");//FTP服务器登录用户名
		String password = properties.getProperty("password");//FTP服务器登录密码
		String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
		String cunchuPath = material+  "/video/" +material+"R"+stamp+suffix;//定义文件数据库需存储的路径
		PageData pd2 = processfilemanagermxmxService.findVideoSavePath(pd);//查找第一次视频上传存放的路径
		if(null != pd2) {
			String path = pd2.getString("PATH");
			Boolean boolean1 = deleteFile(addr,port,username,password, path);//删除服务器上的视频
			if(true == boolean1) {
				processfilemanagermxmxService.delVideoSavePath(pd);//删除数据库中存放的路径
			}else {
				jsonObject.put("result", "firseVideoDeleFail");
				return jsonObject;
			}
		}
		//FTP服务器创建PDF文件所在的文件夹，完成图片上传
		InputStream in = file.getInputStream();
		Boolean b = true;
		try {
			b = connect("", addr, port, username, password,"/"+material+"/video",cunchuPath,in);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "FtpConnectionFail");
			return jsonObject;
		}
		if(false == b) {
			jsonObject.put("result", "uploadFail");
			return jsonObject;
		}
		in.close();
		pd.put("PATH", cunchuPath);
		pd.put("PROCESSFILEVIDEO_ID", this.get32UUID());
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String UPLOAD_DATE = dateFormat.format(date);
		pd.put("UPLOAD_DATE", UPLOAD_DATE);
		processfilemanagermxmxService.videoSave(pd);//将上传视频存储的路径存储到数据库中
		jsonObject.put("result", "success");
		logBefore(logger, Jurisdiction.getUsername()+"上传了"+material+"的"+terminalCode+"终端的视频");
		return jsonObject;
	}
	//预览视频前校验视频是否已上传
	@RequestMapping(value="/checkVideo")
	@ResponseBody
	public JSONObject checkVideo() throws Exception{
		JSONObject jsonObject = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		String remark = "false";
		PageData p = processfilemanagermxmxService.findVideoSavePath(pd);
		if(null != p) {
			//从配置文件中读取服务器的IP和端口号 
			Properties properties = new Properties();
			BufferedReader bufferedReader = null;
			FileReader fileReader = null;
			try {
				fileReader = new FileReader("E:/serverconfig.properties");
				bufferedReader = new BufferedReader(fileReader);
				properties.load(bufferedReader);
			}catch(Exception e) {
				e.printStackTrace();
				jsonObject.put("remark", "propertiesIsNotExist");
				return jsonObject;
			}finally {
				bufferedReader.close();
				fileReader.close();
			}
			String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
			String path = p.getString("PATH");
			String videoPath = serverPath + "/img/"+path;//复制物料的存放路径
			jsonObject .put("path", videoPath);
			remark = "success";
		}
		jsonObject.put("remark", remark);
		return jsonObject;
	}
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveStationMess")
	@ResponseBody
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"保存工位信息");
		PageData pd = new PageData();
		pd = this.getPageData();
		String page = pd.getString("PAGE").replace(" ", "");
		String str[] = page.split(",");
		String newStr[] = new String[str.length];
		for(int i = 0;i < str.length;i++) {
			String str1 = str[i];
			newStr[i] = str1.replaceAll("^(0+)", "");
		}
		String pages =  ArrayUtils.toString(newStr, ",").replace("{", "").replace("}", "");
		pd.put("PAGE", pages);
		
		//判断是否已经有不为0的页码
		PageData p = processfilemanagermxmxService.pageStationExist(pd);
		if(null != p) {
			//更新工位页码显示
			processfilemanagermxmxService.pageStationUpdate(pd);
		}else {
			pd.put("STATIONPAGESAVE_ID", this.get32UUID());	//主键
			//插入工位页码显示
			processfilemanagermxmxService.pageStationInsert(pd);
		}
		logBefore(logger, Jurisdiction.getUsername()+"保存了"+pd.getString("PRODUCT_CODE")+pd.getString("TERMINAL")+"终端的显示页码");
		mv = messShow(pd);
		return mv;
	}
	/**一次保存表单所有的数据
	 * @param
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/makeAllSave")
	public ModelAndView makeAllSave() throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"一次保存表单所有的数据");
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");//1%B01-01,2%B01-02,3%B01-03,4%B01-04,5%B01-05
		System.out.println("1111111222"+DATA_IDS);
		String[] sourceStrArray = DATA_IDS.split("W");
        for (int i = 0; i < sourceStrArray.length; i++) {
        	String source = sourceStrArray[i];
        	String[] messArray = source.split("H");
        	String flag = "first";
        	String PAGE = "";
        	String TERMINAL_EQUIPMENT = "";
        	for(int j = 0; j<messArray.length; j++) {
        		if("first".equals(flag)) {
        			String str1 = messArray[j]; 
        			PAGE = str1.replace(" ", "");
        			flag = "second";
        			continue;
        		}else if("second".equals(flag)) {
        			String string = messArray[j];
        			TERMINAL_EQUIPMENT = string.trim();
        		}
        	}
//        	String str[] = PAGE.split(",");
//        	String newStr[] = new String[str.length];
//    		for(int j = 0;j < str.length;j++) {
//    			String str1 = str[j];
//    			newStr[j] = str1.replaceAll("^(0+)", "");
//    		}
//    		String pages =  ArrayUtils.toString(newStr, ",").replace("{", "").replace("}", "");
    		pd.put("PAGE", PAGE);
    		pd.put("TERMINAL_EQUIPMENT", TERMINAL_EQUIPMENT);
    		pd.put("PROCESSFILEMANAGERMXMX_ID", this.get32UUID());	//主键
    		//processfilemanagermxmxService.save(pd);
        }
        logBefore(logger, Jurisdiction.getUsername()+"批量保存了"+pd.getString("MATERIAL_CODING")+"在"+pd.getString("PRODUCTLINE_NAME")+"终端的显示页码");
		mv = messShow(pd);
		return mv;
	}
	/**选择产线之后触发查询上传文件保存路径的信息的函数
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/search")
	@ResponseBody
	public JSONObject search() throws Exception{
		//获取传递的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		String materialId = pd.getString("PROCESSFILEMANAGERMX_ID");
		JSONObject jsonObject=new JSONObject();
		//产线信息查询出终端设备信息
		List<PageData>	terminalMessList = processfilemanagermxmxService.findTerminalMess(pd);
		for(PageData terminal : terminalMessList) {
			//根据终端编码，在工位信息表里查询出要显示的页码
			String terminalCode = terminal.getString("TERMINAL_CODE");
			PageData pd2 = new PageData();
			pd2.put("TERMINAL_CODE", terminalCode);
			pd2.put("materialId", materialId);
			PageData pd3 = processfilemanagermxmxService.findPageByTerminal(pd2);
			String page ="0";
			if(pd3 != null) {
				page = pd3.getString("PAGE");
			}
			terminal.put("page", page);
		}
		jsonObject.put("terminalMessList", terminalMessList);
		return jsonObject;
	}
	 /**去复制工位页面
	 * @param
	 * @throws Exception   
	 */
	@RequestMapping(value="/goCopyStation")
	public ModelAndView goCopyStation(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		//查询出MX表中所有的已配置过工位信息的物料Id（得到的是对应物料的id）
		List<PageData> materialList2=processfilemanagermxService.findMaterStationMess(page);
		mv.addObject("pd", pd);
		mv.addObject("varList", materialList2);
		mv.addObject("msg", "copyByLine");
		mv.setViewName("base/processfilemanagermxmx/processfilemxmx_copyStation");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	 /**通过表格展示终端工艺信息
		 * @param
		 * @throws Exception   
		 */
	@RequestMapping(value="/terminalShowByTable")
	public ModelAndView terminalShowByTable(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("materialId", pd.getString("PROCESSFILEMANAGERMX_ID"));
		mv = messShow(pd);
		return mv;
	}
	/**复制某个物料的工位，测试跨域
	 * FTP + Nginx
	 
	@RequestMapping(value="/copy4")
	public ModelAndView copy4(Page page,HttpServletRequest request) throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
		PageData pd = new PageData();
		pd = this.getPageData();
		//Client client = Client.create();
		pd.put("PROCESSFILEMANAGERMX_ID", pd.getString("PROCESSFILEMANAGERMX_ID"));
		//工位信息复制之前，先删除该物料自己本身已有的工位配置
		processfilemanagermxmxService.deleteBeforeCopy(pd);
		String MX_Main_ID = pd.getString("PROCESSFILEMANAGERMX_ID");//main物料的id
		pd.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);
		String materialCoding = pd.getString("MATERIAL_CODING");//main物料编码
		String materialCopy = pd.getString("MATERIAL_COPY2");//要复制的物料的id
		String copyMaterial = pd.getString("copyMaterial");//要复制的物料的编码
		PageData fileUrl = processfilemanagermxmxService.findFilePathByMxIdAndLine(pd);//通过物料编码和mxId查询要要复制物料的文件的存放的路径
		String url = fileUrl.getString("FILE_SAVEPATH");
		// 从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("E:/serverconfig.properties");
			bufferedReader = new BufferedReader(fileReader);
			properties.load(bufferedReader);
		}catch(Exception e) {
			//e.printStackTrace();
			mv.addObject("msg","propertiesIsNotExist");
			System.out.println("----properties文件未找到---");
			mv.setViewName("save_result3");
			return mv;
		}finally {
			fileReader.close();
			bufferedReader.close();
		}
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		String addr = properties.getProperty("addr");//FTP服务器地址
		int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
		String username = properties.getProperty("username");//FTP服务器登录用户名
		String password = properties.getProperty("password");//FTP服务器登录密码
		//先查询该main物料是否已经上传过工艺文件
		PageData pageData2 = processfilemanagermxmxService.findFileSavePath2(pd);
		if(pageData2 != null) {
			String  cunchuPath = pageData2.getString("FILE_SAVEPATH");
			//删除第一次上传的PDF文件
			Boolean boolean1 = true;
			try {
				boolean1 = deleteFile(addr,port,username,password, cunchuPath);
			}catch(Exception e) {
				e.printStackTrace();
				mv.addObject("msg","ftpConnFail");
				mv.setViewName("save_result3");
				return mv;
			}
			if(false == boolean1) {
				mv.addObject("msg","pdfDeleFail");
				mv.setViewName("save_result3");
				return mv;
			}
			//删除第一次上传的工艺文件对应的PNG
			//得到该物料第一次上传所有的PNG路径
			List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList(pd);
			if(contrastList.size() > 0) {
				for(PageData pageData : contrastList) {
					String pngName = pageData.getString("FILENAME");
					Boolean boolean2 = true;
					try {
						boolean2 = deleteFile(addr,port,username,password, materialCoding + "/display/" + pngName+".png");
					}catch(Exception e) {
						e.printStackTrace();
						mv.addObject("msg","ftpConnFail");
						mv.setViewName("save_result3");
						return mv;
					}
					if(false == boolean2) {
						mv.addObject("msg","pngDeleFail");
						mv.setViewName("save_result3");
						return mv;
					}
				}
			}
			processfilemanagermxmxService.delFile(pd);//删除工艺文件
			processfilemanagermxmxService.delMxMXMess(pd);//删除工位信息
			processfilemanagermxmxService.deleteContrastMess(pd);//删除对照表的内容
		}
		//定义数据库存储的文件名
		String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
		String cunchuPath = materialCoding+  "/" +materialCoding+"R"+stamp+".pdf";
		 //复制总的PDF文件
		String firstPath = serverPath + "/img/"+url;//复制物料的存放路径
		InputStream inputStream = ServerFileUtil.getInputStream(firstPath);
		//FTP服务器创建PDF文件所在的文件夹，完成文件上传
		if(null != inputStream ) {
			Boolean boolean3 = true;
			try {
				boolean3 = connect("", addr, port, username, password,"/"+materialCoding,cunchuPath,inputStream);
			}catch(Exception e) {
				e.printStackTrace();
				mv.addObject("msg","ftpConnFail");
				mv.setViewName("save_result3");
				return mv;
			}
			if(false == boolean3) {
				mv.addObject("msg","pdfUploadFail");
				mv.setViewName("save_result3");
				return mv;
			}
			inputStream.close();
		}else {
			mv.addObject("msg","copyMaterPdfNotExist");
			mv.setViewName("save_result3");
			return mv;
		}
		
		//将主物料存储的路径存储到数据库中
		pd.put("FILE_SAVEPATH", cunchuPath);
		processfilemanagermxmxService.fileInsert(pd);
		//得到复制物料所有的PNG路径
		List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList2(pd);
		String timeStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		if(contrastList.size() > 0) {
			int i= 1;
			for(PageData pageData : contrastList) {
				String pngName = pageData.getString("FILENAME");
				String pngCopyPath = serverPath + "/img/" + copyMaterial + "/display/" + pngName+".png";
				//获取复制物料的png文件流
				InputStream in = ServerFileUtil.getInputStream(pngCopyPath);
				if(null != in) {
					Boolean boolean4 = true;
					try {
						boolean4 = connect("", addr, port, username, password,"/"+materialCoding+"/display",materialCoding+"/display/"+i+"R"+timeStamp+".png",in);
					}catch(Exception e) {
						e.printStackTrace();
						mv.addObject("msg","ftpConnFail");
						mv.setViewName("save_result3");
						return mv;
					}
					
					in.close();
					if(false == boolean4) {
						mv.addObject("msg","pngUploadFail");
						mv.setViewName("save_result3");
						return mv;
					}
				}else {
					mv.addObject("msg","copyMaterPngNotExist");
					mv.setViewName("save_result3");
					return mv;
				}
				//将png信息存储到对照表
				//创建一个新的PageData对象，存储放入对照表的信息
				PageData pContrast = new PageData();
				pContrast.put("CONTRAST_ID", this.get32UUID());
				pContrast.put("material_coding", materialCoding);
				pContrast.put("page", i);
				pContrast.put("filename", i+"R"+timeStamp);
				processfilemanagermxmxService.saveContrastMess(pContrast);
				i++;
			}
		}
		
		/*
		 * 复制每个终端要显示的页码并进行数据库的存储
				//第一步：找到要复制的物料在哪些产线有配置的终端信息
		List<PageData> lineList = processfilemanagermxmxService.findLinesOnlyByMxId(pd);//查询是否配置过工位信息的产线
		//第二步：遍历集合中的每条产线，查询每条产线下的工位信息
		for(PageData pData : lineList) {
			String lineCode = pData.getString("LINE_CODE");
			PageData p = new PageData();
			p.put("PRODUCTLINE_NAME", lineCode);
			p.put("PROCESSFILEMANAGERMX_ID", materialCopy);
			//根据mxId和产线编码查询工位信息
			List<PageData> mList = processfilemanagermxmxService.findStationMessOnlyByMxId(p);//查询是否配置过工位信息
			//根据产线编码和mxId查询出来终端信息
			for(PageData data  : mList) {
				String terminalCode = data.getString("TERMINAL_EQUIPMENT");
				String p2 = data.getString("PAGE");
				//各个终端显示页码信息进行数据库的存储
				PageData stationPd = new PageData();
				stationPd.put("PROCESSFILEMANAGERMXMX_ID",this.get32UUID());//mxmx表里的主键
				stationPd.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);//mx的ID
				stationPd.put("PAGE", p2);
				stationPd.put("TERMINAL_EQUIPMENT", terminalCode);
				stationPd.put("PRODUCTLINE_NAME", lineCode);
				processfilemanagermxmxService.save(stationPd);
			}
		}
		logBefore(logger, Jurisdiction.getUsername()+"为"+materialCoding+"产品复制了"+copyMaterial+"产品的工艺配置");
		mv.addObject("msg","success");
		mv.setViewName("save_result3");
		return mv;
	}
	*/
	/**复制某个物料的工位，测试跨域
	 * FTP+Tomcat+jersey
	 
	@RequestMapping(value="/copy3")
	public ModelAndView copy3(Page page,HttpServletRequest request) throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
		PageData pd = new PageData();
		pd = this.getPageData();
		Client client = Client.create();
		pd.put("PROCESSFILEMANAGERMX_ID", pd.getString("PROCESSFILEMANAGERMX_ID"));
		//工位信息复制之前，先删除该物料自己本身已有的工位配置
		processfilemanagermxmxService.deleteBeforeCopy(pd);
		String MX_Main_ID = pd.getString("PROCESSFILEMANAGERMX_ID");//main物料的id
		pd.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);
		String materialCoding = pd.getString("MATERIAL_CODING");//main物料编码
		String materialCopy = pd.getString("MATERIAL_COPY2");//要复制的物料的id
		String copyMaterial = pd.getString("copyMaterial");//要复制的物料的编码
		PageData fileUrl = processfilemanagermxmxService.findFilePathByMxIdAndLine(pd);//通过物料编码和mxId查询要要复制物料的文件的存放的路径
		String url = fileUrl.getString("FILE_SAVEPATH");
		 //从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		BufferedReader bufferedReader = new BufferedReader(new FileReader("E:/serverconfig.properties"));
		properties.load(bufferedReader);
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		String addr = properties.getProperty("addr");//FTP服务器地址
		int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
		String username = properties.getProperty("username");//FTP服务器登录用户名
		String password = properties.getProperty("password");//FTP服务器登录密码
		//先查询该main物料是否已经上传过工艺文件
		PageData pageData2 = processfilemanagermxmxService.findFileSavePath2(pd);
		if(pageData2 != null) {
			String  cunchuPath = pageData2.getString("FILE_SAVEPATH");
			String newFPath = serverPath + "/img/"+cunchuPath;
			//删除第一次上传的PDF文件
			ServerFileUtil.deleteServerFile(client,newFPath);
			//删除第一次上传的工艺文件对应的PNG
			//得到该物料第一次上传所有的PNG路径
			List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList(pd);
			if(contrastList.size() > 0) {
				for(PageData pageData : contrastList) {
					String pngName = pageData.getString("FILENAME");
					String pngPath = serverPath + "/img/" + materialCoding + "/display/" + pngName+".png";
					ServerFileUtil.deleteServerFile(client,pngPath);
				}
			}
			//删除工艺文件
			processfilemanagermxmxService.delFile(pd);
			//删除工位信息
			processfilemanagermxmxService.delMxMXMess(pd);
			//删除对照表的内容
			processfilemanagermxmxService.deleteContrastMess(pd);
		}
		/*
		 * 创建物料PDF文件的存放路径
		 *
		//定义数据库存储的文件名
		String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
		String cunchuPath = materialCoding+  "/" +materialCoding+"R"+stamp+".pdf";
		 //复制总的PDF文件
		String firstPath = serverPath + "/img/"+url;//复制物料的存放路径
		InputStream inputStream = ServerFileUtil.getInputStream(firstPath);
		byte[] pdfBytes = ServerFileUtil.toByteArray(inputStream);
		String newFPath = serverPath +"/img/"+cunchuPath;
		WebResource wr = client.resource(newFPath);
        wr.put(String.class, pdfBytes); //将文件对象的字节码上传
		//将主物料存储的路径存储到数据库中
		pd.put("FILE_SAVEPATH", cunchuPath);
		processfilemanagermxmxService.fileInsert(pd);
		//得到复制物料所有的PNG路径
		List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList2(pd);
		String timeStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		if(contrastList.size() > 0) {
			int i= 1;
			for(PageData pageData : contrastList) {
				String pngName = pageData.getString("FILENAME");
				String pngCopyPath = serverPath + "/img/" + copyMaterial + "/display/" + pngName+".png";
				//获取复制物料的png文件流
				InputStream in = ServerFileUtil.getInputStream(pngCopyPath);
				//获取复制文件的字节数组
				byte[] pngBytes = ServerFileUtil.toByteArray(in);
				//定义主物料的路径
				String pngPath = serverPath + "/img/" + materialCoding + "/display/" + i+"R"+timeStamp+".png";
				//png进行复制
				WebResource webResource = client.resource(pngPath);
				webResource.put(String.class, pngBytes); //将文件对象的字节码上传
				//将png信息存储到对照表
				//创建一个新的PageData对象，存储放入对照表的信息
				PageData pContrast = new PageData();
				pContrast.put("CONTRAST_ID", this.get32UUID());
				pContrast.put("material_coding", materialCoding);
				pContrast.put("page", i);
				pContrast.put("filename", i+"R"+timeStamp);
				processfilemanagermxmxService.saveContrastMess(pContrast);
				i++;
			}
		}
		
		/*
		 * 复制每个终端要显示的页码并进行数据库的存储
		 *
		//第一步：找到要复制的物料在哪些产线有配置的终端信息
		List<PageData> lineList = processfilemanagermxmxService.findLinesOnlyByMxId(pd);//查询是否配置过工位信息的产线
		//第二步：遍历集合中的每条产线，查询每条产线下的工位信息
		for(PageData pData : lineList) {
			String lineCode = pData.getString("LINE_CODE");
			PageData p = new PageData();
			p.put("PRODUCTLINE_NAME", lineCode);
			p.put("PROCESSFILEMANAGERMX_ID", materialCopy);
			//根据mxId和产线编码查询工位信息
			List<PageData> mList = processfilemanagermxmxService.findStationMessOnlyByMxId(p);//查询是否配置过工位信息
			//根据产线编码和mxId查询出来终端信息
			for(PageData data  : mList) {
				String terminalCode = data.getString("TERMINAL_EQUIPMENT");
				String p2 = data.getString("PAGE");
				//各个终端显示页码信息进行数据库的存储
				PageData stationPd = new PageData();
				stationPd.put("PROCESSFILEMANAGERMXMX_ID",this.get32UUID());//mxmx表里的主键
				stationPd.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);//mx的ID
				stationPd.put("PAGE", p2);
				stationPd.put("TERMINAL_EQUIPMENT", terminalCode);
				stationPd.put("PRODUCTLINE_NAME", lineCode);
				processfilemanagermxmxService.save(stationPd);
			}
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 复制某个物料的工位
	
	@RequestMapping(value="/copy")
	public ModelAndView copy(Page page,HttpServletRequest request) throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
		PageData pd = new PageData();
		pd = this.getPageData();
		String pageIsZero = "yes";//mxmx表里有数据，判断页码是否全为0的标识
		pd.put("PROCESSFILEMANAGERMX_ID", pd.getString("PROCESSFILEMANAGERMX_ID"));
		//工位信息复制之前，先删除该物料自己本身已有的工位配置
		processfilemanagermxmxService.deleteBeforeCopy(pd);
		String MX_Main_ID = pd.getString("PROCESSFILEMANAGERMX_ID");//main物料的id
		pd.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);
		String materialCoding = pd.getString("MATERIAL_CODING");//main物料编码
		String materialCopy = pd.getString("MATERIAL_COPY2");//要复制的物料的id
		//通过materialMxId在PROCESSFILESAVE表里查询到要复制物料的工艺文件的保存路径
		PageData fileUrl = processfilemanagermxmxService.findFilePathByMxIdAndLine(pd);//通过物料编码和mxId查询要要复制物料的文件的存放的路径
		String url = fileUrl.getString("FILE_SAVEPATH");
		String oldPath = "D:/processFile/"+url;//复制物料的存放路径
		//删除第一次上传的文件
		//先查询该main物料是否已经上传过工艺文件
		PageData pageData2 = processfilemanagermxmxService.findFileSavePath2(pd);
		if(pageData2 != null) {
			String  FILE_SAVEPATH = pageData2.getString("FILE_SAVEPATH");
			String havePath = "D:/processFile/"+FILE_SAVEPATH;
			File file = new File(havePath);
			file.deleteOnExit();
			//拼接图片所在的文件夹的路径
			String pngFolderFPath = "D:"+File.separator+"processFile"+File.separator+materialCoding;
			//删除该路径下的所有图片文件
			DelAllFile.delFolder(pngFolderFPath);
			//如果已经有相应的对照表的信息，先进行删除操作
			processfilemanagermxmxService.deleteContrastMess(pd);
		}
		//创建新上传文件的存储路径
		String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
		String cunchuPath = materialCoding+  "/" +materialCoding+"R"+stamp+".pdf";
		String newFPath = "D:/processFile/" +cunchuPath;
		pd.put("FILE_SAVEPATH", cunchuPath);
		processfilemanagermxmxService.fileInsert(pd);//将上传文件存储的路径存储到数据库中
		File f = new File(newFPath);
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		DealFile.copyFile(oldPath, newFPath);
		String timeStamp = Pdf2Image.pdf2Image(newFPath, "D:\\processFile\\"+materialCoding+"\\display", 131);
		int uploadFileNum = PDFReader.getPdfPages(newFPath);//得到上传的PDF的页数
		for(int i=1;i<=uploadFileNum;i++) {
			//创建一个新的PageData对象，存储放入对照表的信息
			PageData pContrast = new PageData();
			pContrast.put("CONTRAST_ID", this.get32UUID());
			pContrast.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID); 
			pContrast.put("material_coding", materialCoding);
			pContrast.put("page", i);
			pContrast.put("filename", i+"R"+timeStamp);
			processfilemanagermxmxService.saveContrastMess(pContrast);
		}
		/*
		 * 复制每个终端要显示的页码并进行数据库的存储
		 
		//第一步：找到要复制的物料在哪些产线有配置的终端信息
		List<PageData> lineList = processfilemanagermxmxService.findLinesOnlyByMxId(pd);//查询是否配置过工位信息的产线
		//第二步：遍历集合中的每条产线，查询每条产线下的工位信息
		for(PageData pData : lineList) {
			String lineCode = pData.getString("LINE_CODE");
			PageData p = new PageData();
			p.put("PRODUCTLINE_NAME", lineCode);
			p.put("PROCESSFILEMANAGERMX_ID", materialCopy);
			//根据mxId和产线编码查询工位信息
			List<PageData> mList = processfilemanagermxmxService.findStationMessOnlyByMxId(p);//查询是否配置过工位信息
			for(PageData pageData  : mList) {
				String p1 = pageData.getString("PAGE");
				if(!"0".equals(p1)) {
					pageIsZero = "no";
					break;
				}
			}
			if("no".equals(pageIsZero)) {
				//根据产线编码和mxId查询出来终端信息
				for(PageData data  : mList) {
					String terminalCode = data.getString("TERMINAL_EQUIPMENT");
					String p2 = data.getString("PAGE");
					//各个终端显示页码信息进行数据库的存储
					PageData stationPd = new PageData();
					stationPd.put("PROCESSFILEMANAGERMXMX_ID",this.get32UUID());//mxmx表里的主键
					stationPd.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);//mx的ID
					stationPd.put("PAGE", p2);
					stationPd.put("TERMINAL_EQUIPMENT", terminalCode);
					stationPd.put("PRODUCTLINE_NAME", lineCode);
					processfilemanagermxmxService.save(stationPd);
				}
			}
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
 */
	//清除工位要显示的文件的页码（将工位要显示的页码置为0）
	@RequestMapping(value="/clearStationMess")
	@ResponseBody
	public ModelAndView clearStationMess() throws Exception{
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManagerMxMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//清除操作之前，先查询数据库是否已经有保存的记录，若有则更新，否则不做处理
		PageData mess = processfilemanagermxmxService.pageStationExist(pd);
		if(null != mess) {
			pd.put("PAGE", "0");
			processfilemanagermxmxService.clearStationMess(pd);
		}
		mv = messShow(pd);
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws ExceptionPrintWriter out
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ProcessFileManagerMxMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		processfilemanagermxmxService.delete(pd);
		out.write("success");
		out.close();
	}
	/**上传工艺文件3（上传到另一服务器）(FTP + Nginx)
	 * @param out
	 * @throws Exception
	 
	@RequestMapping(value="/uploadFile3",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadFile3(@RequestParam("processFile") MultipartFile processFile,HttpServletResponse response,HttpServletRequest request) throws Exception{
		//设置响应的编码格式
		response.setContentType("text/text;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PageData pd = this.getPageData();
		JSONObject jsonObject=new JSONObject();
		if(null == processFile) {
			jsonObject.put("result", "fileIsNull");
			return jsonObject;
		}
		pd.put("MATERIAL_COPY2", pd.getString("PROCESSFILEMANAGERMX_ID"));
		String PROCESSFILEMANAGERMX_ID = pd.getString("PROCESSFILEMANAGERMX_ID");
		String material = pd.getString("MATERIAL_CODING");
		String pageIsZero = "yes";//mxmx表里有数据，判断页码是否全为0的标识
		//从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("E:/serverconfig.properties");
			bufferedReader = new BufferedReader(fileReader);
			properties.load(bufferedReader);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "propertiesIsNotExist");
			return jsonObject;
		}finally {
			bufferedReader.close();
			fileReader.close();
		}
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		String addr = properties.getProperty("addr");//FTP服务器地址
		int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
		String username = properties.getProperty("username");//FTP服务器登录用户名
		String password = properties.getProperty("password");//FTP服务器登录密码
		//获取上传PDF的总的页数
		int uploadFileNum = PDFReader.getPdfPages(processFile);
		//查找第一次文件上传存放的路径
		PageData pd2 = processfilemanagermxmxService.findFileSavePath2(pd);
		//定义文件数据库需存储的路径
		String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
		String cunchuPath = material+  "/" +material+"R"+stamp+".pdf";
		if(null != pd2){//上传过工艺文件
			List<PageData> messList = processfilemanagermxmxService.findStationMessOnlyByMxId2(pd);//查询是否配置过工位信息
			//判断是否有不为0的页码
			for(PageData mess : messList) {
				String page = mess.getString("PAGE");
				if(!"0".equals(page)) {
					pageIsZero = "no";
					break;
				}
			}
			if("no".equals(pageIsZero)){//产线终端有不为0的页码
				String path = pd2.getString("FILE_SAVEPATH");
				String firstPath = serverPath + "/img/"+path;//拼接出第一次上传文件存放的http访问的路径
				//得到第一次上传文件的流文件
				InputStream inputStream = ServerFileUtil.getInputStream(firstPath);
				//得到第一次上传文件的页数
				int truePathNum = PDFReader.getPdfPages(inputStream);
				if(truePathNum != uploadFileNum) {//两次文件的总页数不相同
					//删除mxmx表里配置的工位信息
					processfilemanagermxmxService.delMxMXMess(pd);
				}
				inputStream.close();
			}
			/*
			 * 删除服务器上第一次上传的总PDF
			 * 
			String path = pd2.getString("FILE_SAVEPATH");
			Boolean boolean1 = deleteFile(addr,port,username,password, path);
			if(false == boolean1) {
				jsonObject.put("result", "pdfDeleFail");
				return jsonObject;
			}
			/*
			 * 删除服务器上转换的PNG图片
			 * 
			List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList(pd);
			if(contrastList.size() > 0) {
				for(PageData pageData : contrastList) {
					String pngName = pageData.getString("FILENAME");
					Boolean boolean2 = deleteFile(addr,port,username,password, material + "/display/" + pngName+".png");
					if(false == boolean2) {
						jsonObject.put("result", "pngDeleFail");
						return jsonObject;
					}
				}
			}
			//删除页码对照表里的数据
			processfilemanagermxmxService.deleteContrastMess(pd);
		}
		//FTP服务器创建PDF文件所在的文件夹，完成图片上传
		InputStream in = processFile.getInputStream();
		Boolean boolean3 = true;
		try {
			boolean3 = connect("", addr, port, username, password,"/"+material,cunchuPath,in);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "ftpConnFail");
			return jsonObject;
		}
		if(false == boolean3) {
			jsonObject.put("result", "pdfUploadFail");
			return jsonObject;
		}
		in.close();
		/*
		 * 将上传的文件存储一份在本地,并将文件路径存储在本地
		 * 
		String localPath = "E:/processFile/" +cunchuPath;
		//如果文件的上传路径不存在，创建出相应的文件夹
		File f = new File(localPath); 
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		processFile.transferTo(f);
		pd.put("FILE_SAVEPATH", cunchuPath);
		processfilemanagermxmxService.fileInsert(pd);//将上传文件存储的路径存储到数据库中
		/*
		 * 将PDF转成PNG,并上传至图片服务器
		 * 
		String timeStamp = Pdf2Image.pdf2Image(localPath, "E:\\processFile\\"+material+"\\display", 131);
		//如果已经有相应的对照表的信息，先进行删除操作
		processfilemanagermxmxService.deleteContrastMess(pd);
		for(int i=1;i<=uploadFileNum;i++) {
			//创建一个新的PageData对象，存储放入对照表的信息
			PageData pContrast = new PageData();
			pContrast.put("CONTRAST_ID", this.get32UUID());
			pContrast.put("PROCESSFILEMANAGERMX_ID", PROCESSFILEMANAGERMX_ID); 
			pContrast.put("material_coding", material);
			pContrast.put("page", i);
			pContrast.put("filename", i+"R"+timeStamp);
			processfilemanagermxmxService.saveContrastMess(pContrast);
			/*
			 * 将图片上传至图片服务器
			 * 
			InputStream inputStream;
			try {
				inputStream = new FileInputStream("E:/processFile/"+material+"/display/"+i+"R"+timeStamp+".png");
			}catch(Exception e) {
				e.printStackTrace();
				jsonObject.put("result", "localPngNotExist");
				return jsonObject;
			}
			Boolean boolean4;
			try {
				boolean4 = connect("", addr, port, username, password,"/"+material+"/display",material+"/display/"+i+"R"+timeStamp+".png",inputStream);
			}catch(Exception e) {
				e.printStackTrace();
				jsonObject.put("result", "ftpConnFail");
				return jsonObject;
			}
			if(false == boolean4) {
				jsonObject.put("result", "pngUploadFail");
				return jsonObject;
			}
			inputStream.close();
		}
		//删除该路径下的所有图片文件
		DelAllFile.delFolder("E:/processFile");
		logBefore(logger, Jurisdiction.getUsername()+"为"+material+"产品上传了工艺文件");
		jsonObject.put("result","success");
		return jsonObject;
		}
	/**上传工艺文件2（上传到另一服务器）(FTP+TOMCAT+ jersey)
	 * @param out
	 * @throws Exception
	 
	@RequestMapping(value="/uploadFile2",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadFile2(@RequestParam("processFile") MultipartFile processFile,HttpServletResponse response,HttpServletRequest request) throws Exception{
		//设置响应的编码格式
		response.setContentType("text/text;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		//获取前端传过来的参数
		PageData pd = this.getPageData();
		pd.put("MATERIAL_COPY2", pd.getString("PROCESSFILEMANAGERMX_ID"));
		String PROCESSFILEMANAGERMX_ID = pd.getString("PROCESSFILEMANAGERMX_ID");
		String material = pd.getString("MATERIAL_CODING");
		JSONObject jsonObject=new JSONObject();
		 //定义标识
		String  result = "success";
		String pageIsZero = "yes";//mxmx表里有数据，判断页码是否全为0的标识
		 //从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		BufferedReader bufferedReader = new BufferedReader(new FileReader("E:/serverconfig.properties"));
		properties.load(bufferedReader);
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		String addr = properties.getProperty("addr");//FTP服务器地址
		int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
		String username = properties.getProperty("username");//FTP服务器登录用户名
		String password = properties.getProperty("password");//FTP服务器登录密码
		//获取上传PDF的总的页数
		int uploadFileNum = PDFReader.getPdfPages(processFile);
		 //将上传的文件转成字节数组
		byte[] fileBytes = processFile.getBytes(); 
		// 创建jesy服务器，进行跨服务器上传文件
		Client client = Client.create();
		 //查找第一次文件上传存放的路径
		PageData pd2 = processfilemanagermxmxService.findFileSavePath2(pd);
		//定义文件数据库需存储的路径
		String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
		String cunchuPath = material+  "/" +material+"R"+stamp+".pdf";
		String newFPath = serverPath + "/img/"+cunchuPath;
		if(null != pd2){//上传过工艺文件
			List<PageData> messList = processfilemanagermxmxService.findStationMessOnlyByMxId2(pd);//查询是否配置过工位信息
			//判断是否有不为0的页码
			for(PageData mess : messList) {
				String page = mess.getString("PAGE");
				if(!"0".equals(page)) {
					pageIsZero = "no";
					break;
				}
			}
			if("no".equals(pageIsZero)){//产线终端有不为0的页码
				String path = pd2.getString("FILE_SAVEPATH");
				String firstPath = serverPath + "/img/"+path;//拼接出第一次上传文件存放的http访问的路径
				//得到第一次上传文件的流文件
				InputStream inputStream = ServerFileUtil.getInputStream(firstPath);
				//得到第一次上传文件的页数
				int truePathNum = PDFReader.getPdfPages(inputStream);
				if(truePathNum != uploadFileNum) {//两次文件的总页数不相同
					//删除mxmx表里配置的工位信息
					processfilemanagermxmxService.delMxMXMess(pd);
				}
			}
			//删除服务器上第一次上传的总PDF
			//数据库查找第一次上传文件的存放路径
			String path = pd2.getString("FILE_SAVEPATH");
			String firstPath = serverPath + "/img/"+path;//拼接出第一次上传文件存放的http访问的路径
			ServerFileUtil.deleteServerFile(client,firstPath);
			 //删除服务器上转换的PNG图片
			//得到该物料第一次上传所有的PNG路径
			List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList(pd);
			if(contrastList.size() > 0) {
				for(PageData pageData : contrastList) {
					String pngName = pageData.getString("FILENAME");
					String pngPath = serverPath + "/img/" + material + "/display/" + pngName+".png";
					ServerFileUtil.deleteServerFile(client,pngPath);
				}
			}
			//删除页码对照表里的数据
			processfilemanagermxmxService.deleteContrastMess(pd);
		}
		 //将上传的文件存储一份在本地,并将文件路径存储在本地
		String localPath = "E:/processFile/" +cunchuPath;
		//如果文件的上传路径不存在，创建出相应的文件夹
		File f = new File(localPath); 
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		processFile.transferTo(f);
		pd.put("FILE_SAVEPATH", cunchuPath);
		processfilemanagermxmxService.fileInsert(pd);//将上传文件存储的路径存储到数据库中
		WebResource wr = client.resource(newFPath);
        wr.put(String.class, fileBytes); //将文件对象的字节码上传
		/*
		 * 将PDF转成PNG,并上传至图片服务器
		 *
		String timeStamp = Pdf2Image.pdf2Image(localPath, "E:\\processFile\\"+material+"\\display", 131);
		//如果已经有相应的对照表的信息，先进行删除操作
		processfilemanagermxmxService.deleteContrastMess(pd);
		for(int i=1;i<=uploadFileNum;i++) {
			//创建一个新的PageData对象，存储放入对照表的信息
			PageData pContrast = new PageData();
			pContrast.put("CONTRAST_ID", this.get32UUID());
			pContrast.put("PROCESSFILEMANAGERMX_ID", PROCESSFILEMANAGERMX_ID); 
			pContrast.put("material_coding", material);
			pContrast.put("page", i);
			pContrast.put("filename", i+"R"+timeStamp);
			processfilemanagermxmxService.saveContrastMess(pContrast);
			 //将图片上传至图片服务器
			//图片在服务器上的访问路径
			String PNGPath = serverPath + "/img/" + material + "/display/" + i+"R"+timeStamp+".png";
			InputStream inputStream = new FileInputStream("E:/processFile/"+material+"/display/"+i+"R"+timeStamp+".png");
			byte[] pngBytes = ServerFileUtil.toByteArray(inputStream);
			WebResource wr2 = client.resource(PNGPath);
	        wr2.put(String.class, pngBytes); //将文件对象的字节码上传
		}
		//删除该路径下的所有图片文件
		DelAllFile.delFolder("E:/processFile");
		jsonObject.put("result", result);
		return jsonObject;
		}
	/**上传工艺文件(上传到服务器本地),上传的工艺文件和物料关联，不和产线关联
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadFile(@RequestParam("processFile") MultipartFile processFile,HttpServletResponse response,HttpServletRequest request) throws Exception{
		//设置响应的编码格式
		System.out.println("进入了uploadFile方法");
		response.setContentType("text/text;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject jsonObject=new JSONObject();
		String requestUrl = request.getScheme() +"://" + request.getServerName()+ ":" + request.getServerPort();//获取当前连接使用的协议：//服务器IP地址：端口号
		//String  result = "success";//判断是一般上传还是需要分割PDF文件
		int uploadFileNum = PDFReader.getPdfPages(processFile);//得到上传的PDF的页数
		//pd.put("MATERIAL_COPY2", pd.getString("PROCESSFILEMANAGERMX_ID"));
		//String PROCESSFILEMANAGERMX_ID = pd.getString("PROCESSFILEMANAGERMX_ID");
		String productCode = pd.getString("PRODUCT_CODE");//产品编码
		PageData pd2 = processfilemanagermxmxService.findFileSavePath(pd);//查找第一次文件上传存放的路径
		try{
			if(null != pd2) {//有上传过文件
				//删除contrast表里的数据
				processfilemanagermxmxService.deleteContrastMess(pd);
				String path = pd2.getString("FILE_SAVEPATH");
				String firstPath = "D:/processFile/"+path;//拼接出第一次上传文件存放的绝对路径 
				int truePathNum = PDFReader.getPdfPages(firstPath);//得到第一次上传的文件的页数
				if(truePathNum != uploadFileNum) {//两次文件的总页数不相同
					//删除mxmx表里配置的工位信息
					processfilemanagermxmxService.delMxMXMess(pd);
				}
			}
			String tStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
			String stamp = tStamp.substring(tStamp.length()-5, tStamp.length());
			String cunchuPath = productCode+  "/" +productCode+"R"+stamp+".pdf";
			String newFPath = "D:/processFile/" +cunchuPath;
			File f = new File(newFPath);
			pd.put("FILE_SAVEPATH", cunchuPath);
			//判断数据库是否已上传过工艺文件，数据库是否已有记录
			PageData pag = processfilemanagermxmxService.filesaveExist(pd);
			if(pag == null) {
				processfilemanagermxmxService.fileInsert(pd);//数据库插入工艺文件访问路径
			}else {
				processfilemanagermxmxService.fileUpdate(pd);//数据库更新工艺文件访问路径
			}
			
			String filePath = requestUrl+"/processFile/"+cunchuPath;//拼接出文件的访问路径，返回给前端
			//拼接出第一次文件上传的图片所在的文件夹
			String pngFolderFPath = "D:"+File.separator+"processFile"+File.separator+productCode;
			File f1 = new File(pngFolderFPath);
			if(f1.exists() && f1.isDirectory()) {
				DelAllFile.delFolder(pngFolderFPath);
			}
			//如果文件的上传路径不存在，创建出相应的文件夹
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			processFile.transferTo(f);
			//如果已经有相应的对照表的信息，先进行删除操作
			processfilemanagermxmxService.deleteContrastMess(pd);
			String timeStamp = Pdf2Image.pdf2Image(newFPath, "D:\\processFile\\"+productCode+"\\display", 131);
			for(int i=1;i<=uploadFileNum;i++) {
				//创建一个新的PageData对象，存储放入对照表的信息
				PageData pContrast = new PageData();
				pContrast.put("CONTRAST_ID", this.get32UUID());
				//pContrast.put("PROCESSFILEMANAGERMX_ID", PROCESSFILEMANAGERMX_ID); 
				pContrast.put("productCode", productCode);
				pContrast.put("page", i);
				pContrast.put("filename", i+"R"+timeStamp);
				processfilemanagermxmxService.saveContrastMess(pContrast);
			}
			jsonObject.put("filePath", filePath);
			jsonObject.put("result", "success");
			
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "fail");
		}
		return jsonObject;
		}
	/**通过产品查看工艺文件
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/lookProcessfile")
	@ResponseBody
	public JSONObject lookProcessfile(HttpServletRequest request) throws Exception{
		System.out.println("进入了/lookFile方法");
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		//获取当前连接使用的协议：//服务器IP地址：端口号
		String requestUrl = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
		PageData pd2 = processfilemanagermxmxService.findFileSavePath(pd);
		String filePath;
		if(null != pd2) {
			String savePath = pd2.getString("FILE_SAVEPATH");
			filePath = requestUrl + "/processFile/"+savePath;
		}else {
			filePath = "fileNotExist";
		}
		jsonObject.put("filePath", filePath);
		return jsonObject;
	}
	
	/**查询所有图片的接口
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/findAllProcessfile")
	@ResponseBody
	public JSONObject findAllProcessfile(HttpServletRequest request) throws Exception{
		System.out.println("进入了/findAllProcessfile方法");
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		//获取当前连接使用的协议：//服务器IP地址：端口号
		String requestUrl = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
		PageData pd2 = processfilemanagermxmxService.findFileSavePath(pd);
		String filePath;
		if(null != pd2) {
			String savePath = pd2.getString("FILE_SAVEPATH");
			filePath = requestUrl + "/processFile/"+savePath;
		}else {
			filePath = "fileNotExist";
		}
		jsonObject.put("filePath", filePath);
		System.out.println("----"+filePath);
		return jsonObject;
	}
	
	
	
	
	
	/**仅通过物料查看工艺文件，不和产线关联，测试跨域查看文件
	 * @param out
	 * @throws Exception
	 
	@RequestMapping(value="/lookFile2")
	@ResponseBody
	public JSONObject lookFile2(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		 //从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("E:/serverconfig.properties");
			bufferedReader = new BufferedReader(fileReader);
			properties.load(bufferedReader);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "propertiesIsNotExist");
			return jsonObject;
		}finally {
			bufferedReader.close();
			fileReader.close();
		}
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		//获取当前连接使用的协议：//服务器IP地址：端口号
		String requestUrl = serverPath;
		PageData pd2 = processfilemanagermxmxService.findFileSavePath2(pd);
		String filePath2 = "";
		if(null != pd2) {
			String savePath = pd2.getString("FILE_SAVEPATH");
			filePath2 = requestUrl + "/img/"+savePath;
		}
		jsonObject.put("filePath", filePath2);
		return jsonObject;
	}
	/**仅通过物料查看工艺文件，不和产线关联
	 * @param out
	 * @throws Exception
	 
	@RequestMapping(value="/lookFile")
	@ResponseBody
	public JSONObject lookFile(HttpServletRequest request) throws Exception{
		System.out.println("进入了/lookFile方法");
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		//获取当前连接使用的协议：//服务器IP地址：端口号
		String requestUrl = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
		PageData pd2 = processfilemanagermxmxService.findFileSavePath2(pd);
		String filePath2 = "";
		if(null != pd2) {
			String savePath = pd2.getString("FILE_SAVEPATH");
			filePath2 = requestUrl + "/processFile/"+savePath;
		}
		jsonObject.put("filePath", filePath2);
		System.out.println("----"+filePath2);
		return jsonObject;
	}
	
	//保存工位信息之前，查询工艺文件是否已经上传，并得到文件的页数,测试跨域
		@RequestMapping(value="/checkFileNum2")
		@ResponseBody
		public JSONObject checkFileNum2() throws Exception{
			JSONObject jsonObject=new JSONObject();
			//获取传递的参数
			PageData pd = new PageData();
			pd = this.getPageData();
			 //从配置文件中读取服务器的IP和端口号 
			Properties properties = new Properties();
			BufferedReader bufferedReader = null;
			FileReader fileReader = null;
			try {
				fileReader = new FileReader("E:/serverconfig.properties");
				bufferedReader = new BufferedReader(fileReader);
				properties.load(bufferedReader);
			}catch(Exception e) {
				e.printStackTrace();
				jsonObject.put("remark", "propertiesIsNotExist");
				return jsonObject;
			}finally {
				bufferedReader.close();
				fileReader.close();
			}
			String addr = properties.getProperty("addr");//FTP服务器地址
			int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
			String username = properties.getProperty("username");//FTP服务器登录用户名
			String password = properties.getProperty("password");//FTP服务器登录密码
			//查询出文件的保存路径信息
			PageData pd2 = processfilemanagermxmxService.findFileSavePath2(pd);
			//若文件保存路径不为空往前端返回一个字符串
			if(pd2 != null) {
				//remark = "success";
				String filePath = pd2.getString("FILE_SAVEPATH");//获取文件数据库保存的路径
				InputStream in;
				try {
					in = connectAndgetFileIn("", addr, port, username, password,filePath);
				}catch(Exception e) {
					e.printStackTrace();
					jsonObject.put("remark", "ftpConnFail");
					return jsonObject;
				}
				if(null == in) {
					jsonObject.put("remark", "false");
					return jsonObject;
				}
				int truePathNum = PDFReader.getPdfPages(in);//得到文件1的页数
				//将完整路径放到JSon中
				jsonObject.put("fileNum", truePathNum);
			}else{
				jsonObject.put("remark", "false");
				return jsonObject;
			}
			jsonObject.put("remark", "success");
			return jsonObject;
		}
	*/
	//保存工位信息之前，查询工艺文件是否已经上传，并得到文件的页数
	@RequestMapping(value="/checkFileNum")
	@ResponseBody
	public JSONObject checkFileNum() throws Exception{
		JSONObject jsonObject=new JSONObject();
		//获取传递的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询出文件的保存路径信息
		PageData pd2 = processfilemanagermxmxService.findFileSavePath(pd);
		String remark = "false";
		//若文件保存路径不为空往前端返回一个字符串
		if(pd2 != null) {
			remark = "success";
			String filePath = pd2.getString("FILE_SAVEPATH");//获取文件数据库保存的路径
			String firstPath = "D:/processFile/"+filePath;//拼接出第一次上传文件存放的路径
			int truePathNum = PDFReader.getPdfPages(firstPath);//得到文件1的页数
			//将完整路径放到JSon中
			jsonObject.put("fileNum", truePathNum);
		}
		jsonObject.put("remark", remark);
		return jsonObject;
	}
	
	
	/**搜索
	 */
	@RequestMapping(value="/search2")
	public ModelAndView search2(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		//查询出MX表中所有的已配置过工位信息的物料Id（得到的是对应物料的id）
		List<PageData> materialMxIdList=processfilemanagermxService.findMaterStationMess(page);
		mv.setViewName("base/processfilemanagermxmx/processfilemxmx_copyStation");
		mv.addObject("varList", materialMxIdList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**选择产线之后触发查询上传文件保存路径的信息的函数
	 * @param out
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value="/searchPath")
	@ResponseBody
	public JSONObject searchPath(HttpServletRequest request) throws Exception{
		//获取传递的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询出文件的保存路径信息
		PageData pd2 = processfilemanagermxmxService.findFileSavePath(pd);
		JSONObject jsonObject=new JSONObject();
		Boolean remark=false;
		//若文件保存路径不为空往前端返回一个字符串
		if(pd2 != null) {
			remark=true;
			//拼接出访问文件的完整路径
			String filePath = pd2.getString("FILE_SAVEPATH");
			//获取当前连接使用的协议：//服务器IP地址：端口号
			String requestUrl = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
			String filePath2 = requestUrl + "/processFile/" + filePath;
			//将完整路径放到JSon中
			jsonObject.put("filePath", filePath2);
		}
		jsonObject.put("remark", remark);
		return jsonObject;
	}
	
	
	//查看某个终端的工艺文件
	@RequestMapping(value="/lookTerminalFile")
	@ResponseBody
	public JSONObject lookTerminalFile(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String page = pd.getString("page");
		String terCode = pd.getString("terCode");
		String materialCode = processfilemanagermxService.findById(pd).getString("MATERIAL_CODING");
		String lineName = terCode.split("-")[0];
		//获取当前连接使用的协议：//服务器IP地址：端口号
		String requestUrl = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
		String filePath = requestUrl + "/processFile/default.pdf";
		if(!"0".equals(page)) {
			filePath = requestUrl + "/processFile/"+materialCode+"/"+lineName+"/display/"+terCode+".pdf";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("filePath", filePath);
		return jsonObject;
	}
	//查看某个终端的工艺文件通过png的方式查看,测试跨域查看png
	@RequestMapping(value="/lookTerminalFileByPng2")
	@ResponseBody
	public JSONObject lookTerminalFileByPng2(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String page = pd.getString("page");//页码
		JSONObject jsonObject = new JSONObject();
		//从配置文件中读取服务器的IP和端口号
		Properties properties = new Properties();
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("E:/serverconfig.properties");
			bufferedReader = new BufferedReader(fileReader);
			properties.load(bufferedReader);
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "propertiesIsNotExist");
			return jsonObject;
		}finally {
			bufferedReader.close();
			fileReader.close();
		}
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		String imgFilePath = "";
		String materialCode = processfilemanagermxService.findById(pd).getString("MATERIAL_CODING");//物料编码
		if(!"0".equals(page)) {
			String[] arr = page.split(",");
			for(int i = 0;i < arr.length;i++) {
				PageData pageData = new PageData();
				pageData.put("MATERIAL_CODING", materialCode);
				pageData.put("p", arr[i]);
				//根据页码和material查询出来png文件名
				PageData pContrast = processfilemanagermxmxService.findContrastFname(pageData);
				String fileName = pContrast.getString("FILENAME");
				if(imgFilePath == null || "".equals(imgFilePath)) {
					imgFilePath = serverPath + "/img/"+materialCode + "/display/"+fileName+".png";
				}else {
					imgFilePath = imgFilePath +","+serverPath + "/img/"+materialCode + "/display/"+fileName+".png";
				}
			}
		}
		jsonObject.put("filePath", imgFilePath);
		return jsonObject;
	}
	
	//查看某个终端的工艺文件通过png的方式查看
		@RequestMapping(value="/lookTerminalFileByPng")
		@ResponseBody
		public JSONObject lookTerminalFileByPng(HttpServletRequest request) throws Exception{
			PageData pd = new PageData();
			pd = this.getPageData();
			String page = pd.getString("page");//页码
			//获取当前连接使用的协议：//服务器IP地址：端口号
			String requestUrl = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort();
			String imgFilePath = "";
			String productCode = pd.getString("PRODUCT_CODE");//产品编码
			if(!"0".equals(page)) {
				String[] arr = page.split(",");
				for(int i = 0;i < arr.length;i++) {
					//PageData pageData = new PageData();
					//pageData.put("MATERIAL_CODING", materialCode);
					pd.put("p", arr[i]);
					//根据页码和material查询出来png文件名
					PageData pContrast = processfilemanagermxmxService.findContrastFname(pd);
					String fileName = pContrast.getString("FILENAME");
					if(imgFilePath == null || "".equals(imgFilePath)) {
						imgFilePath = requestUrl + "/processFile/"+productCode + "/display/"+fileName+".png";
					}else {
						imgFilePath = imgFilePath +","+requestUrl + "/processFile/"+productCode + "/display/"+fileName+".png";
					}
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("filePath", imgFilePath);
			return jsonObject;
		}
		/**删除工艺文件（文件在另一服务器）(FTP + Nginx)
		 * @param out
		 * @throws Exception
		 
		@RequestMapping(value="/delFile3")
		@ResponseBody
		public JSONObject delFile3() throws Exception{
			//ModelAndView mv = this.getModelAndView();
			JSONObject jsonObject = new JSONObject();
			Properties properties = new Properties();
			BufferedReader bufferedReader = null;
			FileReader fileReader = null;
			try {
				fileReader = new FileReader("E:/serverconfig.properties");
				bufferedReader = new BufferedReader(fileReader);
				properties.load(bufferedReader);
			}catch(Exception e) {
				e.printStackTrace();
				jsonObject.put("result", "properitesIsNotExist");
				return jsonObject;
			}finally {
				bufferedReader.close();
				fileReader.close();
			}
			String addr = properties.getProperty("addr");//FTP服务器地址
			int port = Integer.parseInt(properties.getProperty("port"));//FTP服务器端口号
			String username = properties.getProperty("username");//FTP服务器登录用户名
			String password = properties.getProperty("password");//FTP服务器登录密码
			PageData pd = new PageData();
			pd = this.getPageData();
			String material = pd.getString("MATERIAL_CODING");
			/*
			  删除资源服务器上的PDF文件
			 * 
			PageData p = processfilemanagermxmxService.findFileSavePath2(pd);
			if(p != null) {
				String path = p.getString("FILE_SAVEPATH");
				Boolean boolean1 = true;
				try {
					boolean1 = deleteFile(addr,port,username,password, path);
				}catch(Exception e) {
					e.printStackTrace();
					jsonObject.put("result", "ftpConnFail");
					return jsonObject;
				}
				if(false == boolean1) {
					jsonObject.put("result", "pdfDeleFail");
					return jsonObject;
				}
			}
			/*
			 * 删除服务器上转换的PNG图片
			 *
			List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList(pd);
			if(contrastList.size() > 0) {
				for(PageData pageData : contrastList) {
					String pngName = pageData.getString("FILENAME");
					Boolean boolean2 = true;
					try {
						boolean2 = deleteFile(addr,port,username,password, material + "/display/" + pngName+".png");
					}catch(Exception e) {
						e.printStackTrace();
						jsonObject.put("result", "ftpConnFail");
					}
					if(false == boolean2) {
						jsonObject.put("result", "pngDeleFail");
						return jsonObject;
					}
				}
			}
			//删除工艺文件
			processfilemanagermxmxService.delFile(pd);
			//删除工位信息
			processfilemanagermxmxService.delMxMXMess(pd);
			//删除对照表的内容
			processfilemanagermxmxService.deleteContrastMess(pd);
			
			jsonObject.put("result", "success");
			logBefore(logger, Jurisdiction.getUsername()+"删除了"+material+"产品的工艺文件及工位配置信息");
			return jsonObject;
		}
		/**删除工艺文件（文件在另一服务器）(FTP+TOMCAT+ jersey)
		 * @param out
		 * @throws Exception
		 
		@RequestMapping(value="/delFile2")
		public ModelAndView delFile2() throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String material = pd.getString("MATERIAL_CODING");
			Client client = Client.create();
			/*
			  删除资源服务器上的PDF文件
			 * 
			//从配置文件中读取服务器的IP和端口号
			Properties properties = new Properties();
			BufferedReader bufferedReader = new BufferedReader(new FileReader("E:/serverconfig.properties"));
			properties.load(bufferedReader);
			String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
			//数据库查找第一次上传文件的存放路径
			PageData p = processfilemanagermxmxService.findFileSavePath2(pd);
			if(p != null) {
				String path = p.getString("FILE_SAVEPATH");
				String firstPath = serverPath + "/img/"+path;//拼接出第一次上传文件存放的http访问的路径
				ServerFileUtil.deleteServerFile(client,firstPath);
			}
			/*
			 * 删除服务器上转换的PNG图片
			 *
			//得到该物料第一次上传所有的PNG路径
			List<PageData> contrastList = processfilemanagermxmxService.findContrastFnameList(pd);
			if(contrastList.size() > 0) {
				for(PageData pageData : contrastList) {
					String pngName = pageData.getString("FILENAME");
					String pngPath = serverPath + "/img/" + material + "/display/" + pngName+".png";
					ServerFileUtil.deleteServerFile(client,pngPath);
				}
			}
			//删除工艺文件
			processfilemanagermxmxService.delFile(pd);
			//删除工位信息
			processfilemanagermxmxService.delMxMXMess(pd);
			//删除对照表的内容
			processfilemanagermxmxService.deleteContrastMess(pd);
			//拼接图片所在的文件夹的路径
			//String pngFolderFPath = "D:"+File.separator+"processFile"+File.separator+material+File.separator+"display";
			//删除该路径下的所有图片文件
			//DelAllFile.delFolder(pngFolderFPath);
			mv = messShow(pd);
			return mv;
		}
	
	
	
	/**删除工艺文件
	 * @param out
	 * @throws Exception
	 
	@RequestMapping(value="/delFile")
	public ModelAndView delFile() throws Exception{
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, Jurisdiction.getUsername()+"删除ProcessFileManagerMxMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//删除工艺文件的配置信息时，同时删除本地文件
		PageData p = processfilemanagermxmxService.findFileSavePath2(pd);
		if(p != null) {
			String filePath = p.getString("FILE_SAVEPATH");
			String completeFilePath = "D:/processFile/"+filePath;
			File file = new File(completeFilePath);
			if(file.exists() && file.isFile()) {
				file.delete();
			}
		}
		String material = pd.getString("MATERIAL_CODING");
		//删除工艺文件
		processfilemanagermxmxService.delFile(pd);
		//删除工位信息
		processfilemanagermxmxService.delMxMXMess(pd);
		//删除对照表的内容
		processfilemanagermxmxService.deleteContrastMess(pd);
		//拼接图片所在的文件夹的路径
		String pngFolderFPath = "D:"+File.separator+"processFile"+File.separator+material+File.separator+"display";
		//删除该路径下的所有图片文件
		DelAllFile.delFolder(pngFolderFPath);
		mv = messShow(pd);
		return mv;
	}
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ProcessFileManagerMxMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		processfilemanagermxmxService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list2")
	public ModelAndView list2(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMxMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = processfilemanagermxmxService.list(page);	//列出ProcessFileManagerMxMx列表
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMxMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = processfilemanagermxmxService.list(page);	//列出ProcessFileManagerMxMx列表
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_list_22");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(String checkadd)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_edit");
		mv.addObject("checkadd",checkadd);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = processfilemanagermxmxService.findById(pd);	//根据ID读取
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	public ModelAndView messShow(PageData pd)throws Exception {
		ModelAndView mv = new ModelAndView();
		//产线信息查询出终端设备信息
		List<PageData>	terminaList = terminalService.listTerAndPage(pd);
//		List<PageData> terminalShowByTableMess = new ArrayList<>();
		/*
		for(PageData mess : terminalMessList) {
			PageData pd2 = new PageData();
			String TERMINAL_CODE = mess.getString("TERMINAL_CODE");
			pd.put("TERMINAL_CODE", TERMINAL_CODE);
			PageData pPageData = processfilemanagermxmxService.findPageByTerminal(pd);
			pd2.put("TERMINAL_EQUIPMENT", TERMINAL_CODE);
			if(pPageData == null) {
				pd2.put("PAGE", "");
			}else {
				pd2.put("PAGE", pPageData.getString("PAGE"));
			}
			terminalShowByTableMess.add(pd2);
		}
		*/
		//mv.addObject("pd", pd);
		mv.addObject("PRODUCT_CODE", pd.getString("PRODUCT_CODE"));
		mv.addObject("terminaList",terminaList);
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_list22");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ProcessFileManagerMxMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			processfilemanagermxmxService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出ProcessFileManagerMxMx到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("工位名称");	//1
		titles.add("所属分类");	//2
		titles.add("对应页码");	//3
		titles.add("包装前必检工序");	//4
		titles.add("喷胶、灌胶标志");	//5
		titles.add("终端");	//6
		titles.add("对应工艺文件");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = processfilemanagermxmxService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STATION_NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("CLASSIFICATION"));	    //2
			vpd.put("var3", varOList.get(i).getString("PAGE"));	    //3
			vpd.put("var4", varOList.get(i).getString("PACKAGE_CHECK"));	    //4
			vpd.put("var5", varOList.get(i).getString("GLUE_SYMBOL"));	    //5
			vpd.put("var6", varOList.get(i).getString("TERMINAL_EQUIPMENT"));	    //6
			vpd.put("var7", varOList.get(i).getString("PROCESS_FILE"));	    //7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	@RequestMapping(value="/processDialog")
	public ModelAndView processDialog(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"弹出文件选择对话框");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("base/processfilemanagermxmx/processfileDialog");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	//通过FTP删除资源服务器上的文件
	public boolean deleteFile(String addr,int port,String username,String password,String filename){ 
        boolean flag = false; 
        try { 
            ftp = new FTPClient();      
            ftp.connect(addr,port);      
            ftp.login(username,password); 
            //切换FTP目录 
            //ftp.changeWorkingDirectory(pathname); 
            ftp.dele(filename); 
            ftp.logout();
            flag = true; 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally {
            if(ftp.isConnected()){ 
                try{
                	ftp.disconnect();
                }catch(Exception e){
                    e.printStackTrace();
                }
            } 
        }
        return flag; 
    }
	//连接FTP,完成文件夹的创建，文件的上传
	private boolean connect(String path,String addr,int port,String username,String password,String folder,String fileName,InputStream in) throws Exception {      
        boolean result = true;      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();
            result = false;
        }else{
        	String[] arr = folder.split("/");
    		String temFolder = "";
    		for(int i =1;i<arr.length;i++) {
    			temFolder = temFolder + "/" + arr[i];
    			try {
    				ftp.makeDirectory(temFolder);
    			}catch(Exception e) {
    				e.printStackTrace();
    				ftp.logout();
    				ftp.disconnect();
    				result = false;
    			}
        	}
    		if(result) {
    			ftp.setFileType(ftp.BINARY_FILE_TYPE);
            	Boolean boolean2 = ftp.storeFile(fileName, in);
            	result = boolean2;
    		}
        }
        in.close();
        ftp.logout();
        ftp.disconnect();  
        return result;  
    }
	
	private boolean ftpCreateFolder(String path,String addr,int port,String username,String password,String folder) throws Exception {      
		boolean result = true;      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect(); 
            result = false;
        }else {
        	String[] arr = folder.split("/");
    		String temFolder = "";
    		for(int i =1;i<arr.length;i++) {
    			temFolder = temFolder + "/" + arr[i];
    			try {
    				Boolean boolean1 = ftp.makeDirectory(temFolder);
            		if(!boolean1) {
            			result = false;
            			break;
            		}
    			}catch(Exception e) {
    				e.printStackTrace();
    				ftp.logout();
    				ftp.disconnect();
    				result = false;
    			}
        	}
        }
        ftp.logout();
		ftp.disconnect();
        return result;   
    }
	
	//获取FTP连接
	private FTPClient connect(String path,String addr,int port,String username,String password) throws Exception {      
        //boolean result = false;      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect(); 
        }
        return ftp;      
    }
	//获取FTP上的流文件
	private InputStream connectAndgetFileIn(String path,String addr,int port,String username,String password,String fileName) throws Exception {      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect(); 
        }
        InputStream in = ftp.retrieveFileStream(fileName);
        ftp.disconnect();
        return in;      
    }
}
