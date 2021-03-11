package com.fh.controller.app.appuser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fh.controller.base.BaseController;
import com.fh.plugin.websocketInstantMsg.ChatServerPool;
//import com.fh.service.base.plan.PlanManager;
import com.fh.service.base.processfilemanagermx.ProcessFileManagerMxManager;
import com.fh.service.base.processfilemanagermxmx.ProcessFileManagerMxMxManager;
import com.fh.service.base.product.ProductManager;
import com.fh.service.base.productline.ProductlineManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;
import net.sf.json.JSONObject;


/**@author rhz
  * 会员-接口类 
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appuser")
public class IntAppuserController extends BaseController {
    
	@Resource(name="appuserService")
	private AppuserManager appuserService;
//	@Resource(name="planService")
//	private PlanManager planService;
	@Resource(name="productlineService")
	private ProductlineManager productlineService;
	@Resource(name="processfilemanagermxmxService")
	private ProcessFileManagerMxMxManager processfilemanagermxmxService;
	@Resource(name="processfilemanagermxService")
	private ProcessFileManagerMxManager processfilemanagermxService;
	
	@Resource(name="productService")
	private ProductManager productService;
	
	//查询出所有的产品
	@RequestMapping(value="/findAllProduct")
	@ResponseBody
	public JSONObject findAllProduct(HttpServletRequest request) throws Exception{
		System.out.println("9888888进入了findAllProduct");
		//获取当前连接使用的协议：//服务器IP地址：端口号(http://172.16.20.206:8080)
		JSONObject jsonObject  = new JSONObject();
		PageData pd = new PageData();
		//查询出所有的产品
		List<PageData>	productList = productService.findAllProduct(pd);
		jsonObject.put("productList", productList);
		return jsonObject;
	}
	
	//根据产品查询该产品对应的所有的PNG
	@RequestMapping(value="/findPngOfProduct")
	@ResponseBody
	public JSONObject findPngOfProduct(HttpServletRequest request) throws Exception{
		System.out.println("9888888进入了/findMess");
		//获取当前连接使用的协议：//服务器IP地址：端口号(http://172.16.20.206:8080)
		String requestUrl = request.getScheme() +"://" + request.getServerName()+ ":" + request.getServerPort();
		//声明要返回的json
		JSONObject jsonObject  = new JSONObject();
		//获取传来的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		String PRODUCTCODE = pd.getString("PRODUCTCODE");
		//根据产品查询出该产品对应的所有的图片的文件名
		List<PageData>	pngNameList = processfilemanagermxmxService.findPngsOfProduct(pd);
		//声明该产品所有图片的文件访问路径的list集合
		List<PageData> pngPathList = new ArrayList<PageData>();
		if(pngNameList.size() < 1) {
			jsonObject.put("result", "noFile");
		}else {
			String imgFilePath;
			//拼接返回的png的路径
			for(PageData png : pngNameList) {
				//根据终端编码，在工位信息表里查询出要显示的页码
				String pName = png.getString("FILENAME");
				String page = png.getString("PAGE");
				PageData pData = new PageData();
				imgFilePath = requestUrl + "/processFile/"+PRODUCTCODE + "/display/"+pName+".png";
				pData.put("PAGE", page);
				pData.put("pngUrl", imgFilePath);
				pngPathList.add(pData);
			}
			jsonObject.put("result", "success");
			jsonObject.put("pngPathList", pngPathList);
			System.out.println("------");
			System.out.println(pngPathList);
			System.out.println("------");
		}
		return jsonObject;
	}
	
	
	public Boolean beforefindPngOfProduct(String productCode) {
		
		return false;
		
	}
	
	
	
	//下达生产计划之前查询是否有工艺文件配置
	@RequestMapping(value="/beforeFindMess")
	@ResponseBody
	public JSONObject beforeFindMess(HttpServletRequest request) throws Exception{
		System.out.println("922121进入了/beforeFindMess");
		//声明要返回的json
		JSONObject jsonObject  = new JSONObject();
		//获取传来的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		//通过物料编码和产线查询是否已经配置过该物料的工艺文件显示
		String result = "fail";
		List<PageData> messList = processfilemanagermxmxService.findMess(pd);
		if(messList.size() > 0) {
			for(PageData mess : messList) {
				String page = mess.getString("PAGE");
				if(!("0".equals(page))) {
					result = "ok";
					break;
				}
			}
		}
		jsonObject.put("result", result);
		return jsonObject;
	}
	
	
	/**根据前端发来的请求（物料编码+产线），查询出终端对应要显示的工艺文件的信息
	 * (FTP + Nginx)查找资源服务器上的图片访问路径
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/bak_findMess")
	@ResponseBody
	public JSONObject bak_findMess(HttpServletRequest request) throws Exception{
		System.out.println("9888888进入了/findMess4");
		JSONObject jsonObject  = new JSONObject();
		PageData pd = this.getPageData();
		//从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		properties.load(new BufferedReader(new FileReader("E:/serverconfig.properties")));
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		String lineName = pd.getString("PRODUCTLINE_NAME");
		String materialCode = pd.getString("MATERIAL_CODING");
		//根据产线查询出该产线所有的工艺文件显示终端
		List<PageData>	terminalMessList = processfilemanagermxmxService.findTerminalMess2(pd);
		//声明包含终端信息及PDF文件路径的信息的list集合
		List<String> terMessFormatList = new ArrayList<String>();
		//拼接返回的png的路径
		for(PageData terminal : terminalMessList){
			//根据终端编码，在工位信息表里查询出要显示的页码
			String terminalCode = terminal.getString("TERMINAL_CODE");
			pd.put("TERMINAL_CODE", terminalCode);
			PageData pd4 = processfilemanagermxmxService.findPageByTerminal2(pd);
			//根据物料编码、终端编码查询出视频存放路径
			PageData p2 = new PageData();
			p2.put("MATERIAL_CODING", materialCode);
			p2.put("terminalCode", terminalCode);
			PageData pData = processfilemanagermxmxService.findVideoSavePath(p2);
			String videoPath = "vv";
			if(pData != null) {
				String videoP = pData.getString("PATH");
				videoPath = videoPath+serverPath+"/img/"+videoP;
			}else {
				videoPath = "";
			}
			String url = serverPath + "/img/default.png";
			String imgFilePath = "";
			if(null != pd4) {
				String page = pd4.getString("PAGE");
				if(!"0".equals(page)) {
					String[] arr = page.split(",");
					for(int i = 0;i < arr.length;i++) {
						String p = arr[i];
						pd.put("p", p);
						//根据页码和material查询出来png文件名
						PageData pContrast = processfilemanagermxmxService.findContrastFname(pd);
						String fileName = pContrast.getString("FILENAME");
						if(imgFilePath == null || "".equals(imgFilePath)) {
							imgFilePath = serverPath + "/img/"+materialCode + "/display/"+fileName+".png";
						}else {
							imgFilePath = imgFilePath +"&"+serverPath + "/img/"+materialCode + "/display/"+fileName+".png";;
						}
					}
					url = imgFilePath;
					
				}
			}
			//url = url + videoPath;
			String mess = "1_"+terminalCode+"_"+url+videoPath;
			terMessFormatList.add(mess);
		}
		jsonObject.put("terminalMessList", terMessFormatList);
		jsonObject.put("userId", Jurisdiction.getUsername());
		ChatServerPool.sendMessageByLine("cleancache", lineName);
		return jsonObject;
	}
	/**根据前端发来的请求（物料编码+产线），查询出终端对应要显示的工艺文件的信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/findMess")
	@ResponseBody
	public JSONObject findMess(HttpServletRequest request) throws Exception{
		System.out.println("9888888进入了/findMess");
		//获取当前连接使用的协议：//服务器IP地址：端口号(http://172.16.20.206:8080)
		String requestUrl = request.getScheme() +"://" + request.getServerName()+ ":" + request.getServerPort();
		//声明要返回的json
		JSONObject jsonObject  = new JSONObject();
		//获取传来的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		String lineName = pd.getString("PRODUCTLINE_NAME");
		String materialCode = pd.getString("MATERIAL_CODING");
		//根据产线查询出该产线所有的工艺文件显示终端
		List<PageData>	terminalMessList = processfilemanagermxmxService.findTerminalMess2(pd);
		//声明包含终端信息及PDF文件路径的信息的list集合
		List<String> terMessFormatList = new ArrayList<String>();
		String checkPath = "";
		//拼接返回的png的路径
		for(PageData terminal : terminalMessList) {
			//根据终端编码，在工位信息表里查询出要显示的页码
			String terminalCode = terminal.getString("TERMINAL_CODE");
			pd.put("TERMINAL_CODE", terminalCode);
			PageData pd4 = processfilemanagermxmxService.findPageByTerminal2(pd);
			String url = requestUrl + "/processFile/default.png";
			String imgFilePath = "";
			if(null != pd4) {
				String page = pd4.getString("PAGE");
				if(!"0".equals(page)) {
					String[] arr = page.split(",");
					for(int i = 0;i < arr.length;i++) {
						String p = arr[i];
						pd.put("p", p);
						//根据页码和material查询出来png文件名
						PageData pContrast = processfilemanagermxmxService.findContrastFname(pd);
						String fileName = pContrast.getString("FILENAME");
						if(imgFilePath == null || "".equals(imgFilePath)) {
							imgFilePath = requestUrl + "/processFile/"+materialCode + "/display/"+fileName+".png";
							checkPath = "D:/processFile/"+materialCode + "/display/"+fileName+".png"; 
						}else {
							imgFilePath = imgFilePath +"&"+requestUrl + "/processFile/"+materialCode + "/display/"+fileName+".png";;
						}
					}
					url = imgFilePath;
				}
			}
			String mess = "1_"+terminalCode+"_"+url;
			terMessFormatList.add(mess);
		}
		System.out.println("11111"+checkPath);
		if(!"".equals(checkPath)) {
			File file=new File(checkPath);    
			if(!file.exists()) {    
				jsonObject.put("result", "false"); 
			}  
		}else{
			jsonObject.put("result", "success"); 
		}
		jsonObject.put("terminalMessList", terMessFormatList);
		jsonObject.put("userId", Jurisdiction.getUsername());
		ChatServerPool.sendMessageByLine("cleancache", lineName);
//		try {
//			Thread.sleep(1500);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		return jsonObject;
	}
	
	
	
	
	/**终端自定义显示工艺文件
	 * (FTP + Nginx)跨域访问资源图片
	 * @param
	 * @throws Exception
	 
	@RequestMapping(value="/findFileUrlBak")
	@ResponseBody
	public JSONObject findFileUrlBak(HttpServletRequest request) throws Exception{
		System.out.println("433232进入了/findFileUrl4");
		JSONObject jsonObject  = new JSONObject();
		//获取传来的参数
		PageData pd = this.getPageData();
		String CODE = pd.getString("lineName");
		String terminalCode = pd.getString("terminalCode");
		//从配置文件中读取服务器的IP和端口号 
		Properties properties = new Properties();
		properties.load(new BufferedReader(new FileReader("E:/serverconfig.properties")));
		String serverPath = properties.getProperty("serverPath");//存放工艺文件的服务器的IP和端口号
		//拼接好默认返回的pdf访问路径
		String url = serverPath + "/img/default.png";
		String pngUrl = "";
		//通过产线编码查询出产线的Id
		pd.put("CODE", CODE);
		PageData pageData =productlineService.findByCode(pd);
		//将产线的产线的Id放入pd中
		pd.put("PRODUCTLINE_ID", pageData.getString("PRODUCTLINE_ID"));
		//格式化当前日期，放入pd中
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String d = df.format(new Date());
		pd.put("date", d);
		//根据产线、终端编码在查询当天该产线上状态为“执行中”的计划的物料编码
		List<PageData> planList= planService.findMaterialCode(pd);
		if(planList.size() > 0) {
			PageData pd3 = new PageData();
			PageData pd2 = planList.get(0);
			String materialCode = pd2.getString("PRODUCT_ID");
			pd3.put("MATERIAL_CODING", materialCode);
			pd3.put("PRODUCTLINE_NAME", CODE);
			pd3.put("TERMINAL_CODE", terminalCode);
			PageData pd4 = processfilemanagermxmxService.findPageByTerminal2(pd3);
			if(null != pd4) {
				//首先先判断是否已经将PDF转成图片
				String page = pd4.getString("PAGE");
				if(!"0".equals(page)) {
					String[] arr = page.split(",");
					for(int i = 0;i < arr.length;i++) {
						String p = arr[i];
						if(!"0".equals(p)) {
							pd3.put("p", p);
							//根据页码和material查询出来png文件名
							PageData pContrast = processfilemanagermxmxService.findContrastFname(pd3);
							String fileName = pContrast.getString("FILENAME");
							if(pngUrl == null || "".equals(pngUrl)) {
								pngUrl = serverPath + "/img/"+materialCode + "/display/"+fileName+".png";
							}else {
								pngUrl = pngUrl +"&"+serverPath + "/img/"+materialCode + "/display/"+fileName+".png";;
							}
						}
					}
					url = pngUrl;
				}
			}
		}
		jsonObject.put("url", url);
		System.out.println("~~~~~~~~~~~~~~~~");
		System.out.println(jsonObject);
		System.out.println("~~~~~~~~~~~~~~~~");
		return jsonObject;
	}
	*/
	
	
	
	/**终端自定义显示工艺文件
	 * 请求同一个服务器的资源图片
	 * @param
	 * @throws Exception
	 
	@RequestMapping(value="/findFileUrl")
	@ResponseBody
	public JSONObject findFileUrl(HttpServletRequest request) throws Exception{
		System.out.println("433232进入了/findFileUrl");
		//获取当前连接使用的协议：//服务器IP地址：端口号(http://172.16.20.206:8080)
		String requestUrl = request.getScheme() +"://" + request.getServerName()+ ":" + request.getServerPort();
		//声明要返回的Json
		JSONObject jsonObject  = new JSONObject();
		//拼接好默认返回的pdf访问路径
		String url = requestUrl + "/processFile/default.png";
		//png的路径
		String pngUrl = "";
		//获取传来的参数
		PageData pd = new PageData();
		pd = this.getPageData();
		String CODE = pd.getString("lineName");
		String terminalCode = pd.getString("terminalCode");
		//通过产线编码查询出产线的Id
		pd.put("CODE", CODE);
		PageData pageData =productlineService.findByCode(pd);
		//将产线的产线的Id放入pd中
		pd.put("PRODUCTLINE_ID", pageData.getString("PRODUCTLINE_ID"));
		//格式化当前日期，放入pd中
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String d = df.format(new Date());
		pd.put("date", d);
		//根据产线、终端编码在查询当天该产线上状态为“执行中”的计划的物料编码
		List<PageData> planList= planService.findMaterialCode(pd);
		if(planList.size() > 0) {
			PageData pd3 = new PageData();
			PageData pd2 = planList.get(0);
			String materialCode = pd2.getString("PRODUCT_ID");
			pd3.put("MATERIAL_CODING", materialCode);
			pd3.put("PRODUCTLINE_NAME", CODE);
			pd3.put("TERMINAL_CODE", terminalCode);
			PageData pd4 = processfilemanagermxmxService.findPageByTerminal2(pd3);
			if(null != pd4) {
				//首先先判断是否已经将PDF转成图片
				String page = pd4.getString("PAGE");
				if(!"0".equals(page)) {
					String[] arr = page.split(",");
					for(int i = 0;i < arr.length;i++) {
						String p = arr[i];
						if(!"0".equals(p)) {
							pd3.put("p", p);
							//根据页码和material查询出来png文件名
							PageData pContrast = processfilemanagermxmxService.findContrastFname(pd3);
							String fileName = pContrast.getString("FILENAME");
							if(pngUrl == null || "".equals(pngUrl)) {
								pngUrl = requestUrl + "/processFile/"+materialCode + "/display/"+fileName+".png";
							}else {
								pngUrl = pngUrl +"&"+requestUrl + "/processFile/"+materialCode + "/display/"+fileName+".png";;
							}
						}
					}
					url = pngUrl;
				}
			}
		}
		jsonObject.put("url", url);
		System.out.println("~~~~~~~~~~~~~~~~");
		System.out.println(jsonObject);
		System.out.println("~~~~~~~~~~~~~~~~");
		return jsonObject;
	}
	*/
	
	/**根据用户名获取会员信息
	 * @return 
	 */
	@RequestMapping(value="/getAppuserByUm")
	@ResponseBody
	public Object getAppuserByUsernmae(){
		logBefore(logger, "根据用户名获取会员信息");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try{
			if(Tools.checkKey("USERNAME", pd.getString("FKEY"))){	//检验请求key值是否合法
				if(AppUtil.checkParam("getAppuserByUsernmae", pd)){	//检查参数
					pd = appuserService.findByUsername(pd);
					map.put("pd", pd);
					result = (null == pd) ?  "02" :  "01";
				}else {
					result = "03";
				}
			}else{
				result = "05";
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
		}finally{
			map.put("result", result);
			logAfter(logger);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
}
	
 