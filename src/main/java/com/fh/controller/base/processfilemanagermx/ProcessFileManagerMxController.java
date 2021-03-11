package com.fh.controller.base.processfilemanagermx;


import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DealFile;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelView;
import com.fh.util.PDFReader;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead3;
import com.fh.service.base.processfilemanagermx.ProcessFileManagerMxManager;
import com.fh.service.base.processfilemanagermxmx.ProcessFileManagerMxMxManager;
import com.fh.service.base.product.ProductManager;
import com.fh.service.base.productline.ProductlineManager;

/** 
 * 说明：工艺管理(明细)
 *rhz
 * 创建时间：2018-08-14
 */
@Controller
@RequestMapping(value="/processfilemanagermx")
public class ProcessFileManagerMxController extends BaseController {
	
	String menuUrl = "processfilemanagermx/list.do"; //菜单地址(权限用)
	@Resource(name="processfilemanagermxService")
	private ProcessFileManagerMxManager processfilemanagermxService;
	@Resource(name="processfilemanagermxmxService")
	private ProcessFileManagerMxMxManager processfilemanagermxmxService;
	@Resource(name="productlineService")
	private ProductlineManager productlineService;
	@Resource(name="productService")
	private ProductManager productService;
	
	/**
	 * 打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView  goUploadExcel() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		System.out.println("***"+pd.getString("material"));
		System.out.println("***"+pd.getString("mxId"));
		ModelAndView mv=new ModelAndView();
		mv.setViewName("base/processfilemanagermx/uploadexcel");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
	@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"将检验模板数据excel表导入数据表中");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;		//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "processfileexcel");							//执行上传
			File target = new File(filePath, fileName);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			int sheetnum=wb.getNumberOfSheets();//获取工作簿中的电子表格数量(序列化后为3个)
			int sheetindex=0;
			for (int i = 0; i <sheetnum; i++) {
				HSSFSheet sheet = wb.getSheetAt(i);//获取给定索引处的HSSFSheet对象。 					//sheet 从0开始
				String sheetName=sheet.getSheetName(); //返回此工作表的名称即工作簿的名字
				if (sheetName.equals("工艺")) {
					sheetindex=i;
				}
			}
			List<PageData> listPd = (List)ObjectExcelRead3.readExcel(filePath, fileName, 5, 0, sheetindex);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			for(PageData pageData :listPd) {
				System.out.println(""+pageData);
			}
			//读取相应的数据保存下来
			List<PageData> materiallist = new ArrayList<>();
			List<PageData> toollist = new ArrayList<>();
			int page1 = 1;
			int page2 = 1;
			for(int i = 0;i < listPd.size();i++) {
				String no = listPd.get(i).getString("var0").replaceAll(" ", "");
				if(!"NO".equals(no) && !"设备及工装治具".equals(no)) {
					continue;
				}else if("NO".equals(no)){
					int j = i;
					while(!"准备工作".equals(listPd.get(j+1).getString("var0").replaceAll(" ", ""))){
						PageData pd1 = new PageData();
						pd1.put("id", this.get32UUID());
						pd1.put("mxId", pd.getString("mxId"));
						pd1.put("page", page1);
						pd1.put("NO", listPd.get(j+1).getString("var0").replaceAll(" ", ""));
						pd1.put("name", listPd.get(j+1).getString("var1").replaceAll(" ", "")); //name:名称
						pd1.put("materialCode", listPd.get(j+1).getString("var2").replaceAll(" ", ""));//materialCode:物料代码
						pd1.put("mark", listPd.get(j+1).getString("var3").replaceAll(" ", ""));//mark:代号
						pd1.put("model", listPd.get(j+1).getString("var4").replaceAll(" ", ""));//model:型号
						pd1.put("count", listPd.get(j+1).getString("var8").replaceAll(" ", ""));//count:数量
						pd1.put("note", listPd.get(j+1).getString("var9").replaceAll(" ", ""));//note:备注
						materiallist.add(pd1);
						if("准备工作".equals(listPd.get(j+2).getString("var0").replaceAll(" ", "")) && (j+2)<listPd.size()) {
							page1++;
						}
						j++;
					}
				}else if("设备及工装治具".equals(no)) {
					int k = i;
					if(k<listPd.size() - 1) {
						while("设备及工装治具".equals(listPd.get(k+1).getString("var0").replaceAll(" ", ""))) {
							PageData pd2 = new PageData();
							pd2.put("id", this.get32UUID());
							pd2.put("mxId", pd.getString("mxId"));
							pd2.put("page", page2);
							pd2.put("name", listPd.get(k+1).getString("var2").replaceAll(" ", ""));//name:名称及型号
							pd2.put("count", listPd.get(k+1).getString("var5").replaceAll(" ", ""));//count:数量
							pd2.put("ingredients", listPd.get(k+1).getString("var7").replaceAll(" ", "") );//ingredients:辅料
							toollist.add(pd2);
							if((k+2)<listPd.size() && !"设备及工装治具".equals(listPd.get(k+2).getString("var0").replaceAll(" ", ""))) {
								page2++;
							}
							if(k<i-2) {
								k++;
							}else {
								break;
							}
						}
					}
				}
			}
			/*存入数据库操作======================================
			// 存入主表的信息
				pd.put("MODEL_ID", this.get32UUID());	//主键
				System.out.println("===========pd="+pd);
				System.out.println("=================+===="+listPd.size());
			
				pd.put("VENDORNAME", listPd.get(0).getString("var2"));				
				pd.put("TESTTYPE", listPd.get(1).getString("var2"));
				
				
				pd.put("MATERIALNAME", listPd.get(0).getString("var7"));				
				pd.put("CATEGORY", listPd.get(1).getString("var7"));
				String var7=listPd.get(2).getString("var7");
				String var10=listPd.get(2).getString("var10");
				if (var10==var7) {
					pd.put("SAMPLINGSTANDARD", var7);
				}else {
					pd.put("SAMPLINGSTANDARD", var7+", "+var10);
				}
												
				pd.put("SPECIFICATIONTYPE", listPd.get(0).getString("var10"));
				pd.put("MATERIALCODE", listPd.get(1).getString("var10"));
								
				pd.put("PRODUCTCATEGORY", listPd.get(0).getString("var18"));
				pd.put("CREATIONDATE", new Date());
				System.out.println("===========pd======"+pd);				
				modelService.save(pd);
				//从表 
				PageData pData=new PageData();
				ArrayList<PageData> list=new ArrayList<PageData>();
			 
				   int count=0;
				   int count2=0;
				   int num=0;
				   for (int i = 5; i <listPd.size()-3; i++) {
					   num=i;
					pData.put("NUM", num);
					pData.put("PROCEDURE1", listPd.get(i).getString("var0"));
				   String procedure2=listPd.get(i).getString("var1");
				   System.out.println("=======p====="+procedure2);
				   if (procedure2.equals("ROHS确认")) {
					   count=i;
						break;
					}else if (procedure2.equals("卤素确认")) {
						 count2=i;
						 break;
					}else {
						 pData.put("PROCEDURE2", procedure2);
					}	   				  
				   pData.put("STANDARDVALUE", listPd.get(i).getString("var2"));
				   pData.put("SAMPLING", listPd.get(i).getString("var4"));
				   pData.put("QUANTITY", listPd.get(i).getString("var5"));
				  
				   pData.put("MODEL_ID", pd.get("MODEL_ID"));
				   pData.put("MODELMX_ID", this.get32UUID());	//主键	
				   
				   modelmxService.save(pData);
				  
				}
				   System.out.println("=====count===="+count);
				   	pData.put("MODEL_ID", pd.get("MODEL_ID"));
				   	pData.put("MODELMX_ID", this.get32UUID());	//主键	
					pData.put("PROCEDURE1", listPd.get(count).getString("var0"));
				
					pData.put("PROCEDURE2", listPd.get(count).getString("var1"));
					pData.put("STANDARDVALUE", listPd.get(count).getString("var2"));
					pData.put("SAMPLING", listPd.get(count).getString("var4"));
					pData.put("QUANTITY", listPd.get(count).getString("var5"));
					pData.put("NUM", count);
							
					if (count2!=0) {
						pData.put("MODEL_ID", pd.get("MODEL_ID"));
					   	pData.put("MODELMX_ID", this.get32UUID());	//主键	
						pData.put("PROCEDURE1", listPd.get(count2).getString("var0"));
					
						pData.put("PROCEDURE2", listPd.get(count2).getString("var1"));
						pData.put("STANDARDVALUE", listPd.get(count2).getString("var2"));
						pData.put("SAMPLING", listPd.get(count2).getString("var4"));
						pData.put("QUANTITY", listPd.get(count2).getString("var5"));
						pData.put("NUM", count2);
					}
					 modelmxService.save(pData);
					 //环保确认表
					 int col=0;
					 for (int column = 7; column < 18; column++) {
							String colstring=listPd.get(count).getString("var"+column);
							int column1=column+1;
							String colstring1=listPd.get(count).getString("var"+column1);
							if (colstring==null||"".equals(colstring)) {
								break;
							}else if (colstring.equals(colstring1)) {
								System.out.println("---"+colstring);
								System.out.println("---"+colstring1);
								break;
							} else {
								col=col+1;									
							}
						}
					 PageData pData2=new PageData();
					 for (int j = count+1; j < listPd.size()-3; j++) {						 
						pData2.put("MODEL_ID", pd.get("MODEL_ID"));
						pData2.put("MODELMXMX_ID", this.get32UUID());	//主键	
						pData2.put("NAME", listPd.get(j).getString("var6"));
						pData2.put("col", col);
						modelmxmxService.save(pData2);
					}
					 					
			fi.close();				
			存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManagerMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PROCESSFILEMANAGERMX_ID", this.get32UUID());	//主键
		processfilemanagermxService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save2")
	public ModelAndView save2() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManagerMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PROCESSFILEMANAGERMX_ID", this.get32UUID());	//主键
		processfilemanagermxService.save2(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**ERP导入物料信息
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/findMessFromERP")
	public void findMessFromERP(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"从ERP导入配置工艺的物料");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//从配置文件中读取链接服务器信息
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/dbconfig.properties"));
		pd.put("LINKSERVER", properties.getProperty("linkServer"));//链接服务器名称
		pd.put("LINKDATABASE", properties.getProperty("linkDatabase"));//链接数据库名
		//执行ERP中的一个存储过程，创建出临时表tempdb.dbo.rhztest
		List<PageData>	varList = processfilemanagermxService.findMessFromERP(pd);
		System.out.println("------"+varList.size());
		if(varList.size() > 0) {
			for(PageData pageData : varList ) {
				PageData pd2 = new PageData();
				pd2.put("PROCESSFILEMANAGERMX_ID", this.get32UUID());	//主键
				pd2.put("MATERIAL_CODING", pageData.getString("parentCode"));
				pd2.put("PROCESSFILEMANAGER_ID", pd.getString("PROCESSFILEMANAGER_ID"));
				if(null == processfilemanagermxService.findCode2(pd2)) {
					processfilemanagermxService.save(pd2);
				}
			}
			out.write("success");
			out.close();
		}else if(varList.size() == 0){
			out.write("false");
			out.close();
		}      
		
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ProcessFileManagerMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		processfilemanagermxService.delete(pd);
		out.write("success");
		out.close();
	}
	
	
	/**删除2(关联删除)
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete2")
	public void delete2(PrintWriter out) throws Exception{
		System.out.println("进入了删除物料时，关联工位信息的查询时删除delete2方法……");
		logBefore(logger, Jurisdiction.getUsername()+"删除ProcessFileManagerMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData count = processfilemanagermxmxService.findCount(pd);
		if(Integer.parseInt(count.get("zs").toString()) > 0){
			out.write("false");
			out.close(); 
		}else {
			processfilemanagermxService.delete(pd);
			out.write("success");
			out.close();
		}
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ProcessFileManagerMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		processfilemanagermxService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = processfilemanagermxService.list(page);	//列出ProcessFileManagerMx列表
		mv.setViewName("base/processfilemanagermx/processfilemanagermx_list");
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
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询出所有的产品
		List<PageData> productList=productService.listAll(pd);
		mv.addObject("productList",productList);
		mv.setViewName("base/processfilemanagermx/processfilemanagermx_edit");
		mv.addObject("msg", "save2");
		mv.addObject("pd", pd);
		return mv;
	}
	 /**去配置工位页面
	 * @param
	 * @throws Exception   
	 */
	@RequestMapping(value="/configStation")
	public ModelAndView configStation(Page page)throws Exception{
		System.out.println("/configStation/configStation/configStation/configStation");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String lineCode = pd.getString("lineCode");
		String MATERIAL_CODING = pd.getString("MATERIAL_CODING");
		//String PROCESSFILEMANAGER_ID = pd.getString("PROCESSFILEMANAGER_ID");
		pd = processfilemanagermxService.findCode(pd);
		String PROCESSFILEMANAGERMX_ID = pd.getString("PROCESSFILEMANAGERMX_ID");
		List<PageData>	lineList = productlineService.listAll(pd);	//列出Productline列表
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_list22");
		mv.addObject("msg", "none");
		mv.addObject("lineList", lineList);
		mv.addObject("PROCESSFILEMANAGERMX_ID", PROCESSFILEMANAGERMX_ID);
		mv.addObject("MATERIAL_CODING", MATERIAL_CODING);
		mv.addObject("pd", pd);
		mv.addObject("lineCode", lineCode);
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	 /**去复制工位页面
		 * @param
		 * @throws Exception   
		 */
		@RequestMapping(value="/copyStation")
		public ModelAndView copyStation(Page page)throws Exception{
			System.out.println("/copyStation复制工位");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			//查询出MX表中所有的已配置过工位信息的物料Id（得到的是对应物料的id）
			List<PageData> materialMxIdList=processfilemanagermxService.findAllMaterStationMess(pd);
			//创建新集合存储查询到的配置过工位信息的物料的物料编码
			List<PageData> materialList2 = new ArrayList<>();
			for(PageData materialMxId : materialMxIdList) {
				System.out.println("$$$$$$$$$$$$$$"+materialMxId);
				//在MX表里通过MX_ID查询出物料的编码
				PageData pData = processfilemanagermxService.findCode4(materialMxId);
				pData.put("materialCode", pData.getString("MATERIAL_CODING"));
				//通过物料的编码在product表里查询出产品名称和规格型号
				PageData wuliao= productService.findByMaterialCode(pData);
				String materialMxIdValue = materialMxId.getString("PROCESSFILEMANAGERMX_ID");
				wuliao.put("materialMxId", materialMxIdValue);
				materialList2.add(wuliao);
			}
			mv.addObject("pd", pd);
			mv.addObject("varList", materialList2);
			mv.setViewName("base/processfilemanagermxmx/processfilemxmx_copyStation");
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		
		
		/**搜索
		 */
		@RequestMapping(value="/search")
		public ModelAndView search(Page page) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			//根据关键词查询物料信息
			List<PageData> list = productService.findByKeywords(pd);
			mv.setViewName("base/processfilemanagermxmx/processfilemxmx_copyStation");
			mv.addObject("varList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			return mv;
		}
		
		/**复制工位
		 */
		@RequestMapping(value="/copy")
		@ResponseBody
		public void copy(Page page,PrintWriter out) throws Exception{
			System.out.println("进入了复制操作方法…………");
			logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManagerMx");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			pd.put("PROCESSFILEMANAGERMX_ID", pd.getString("MX_Main_ID"));
			//通过materialMxId在MX表里查询到MxId的对应物料编码
			PageData pData = processfilemanagermxService.findCodeByMxId(pd);
			String materialCoding = pData.getString("MATERIAL_CODING");
			String MX_Main_ID = pd.getString("MX_Main_ID"); 
			
			//通过materialMxId在PROCESSFILESAVE表里查询到要复制物料的工艺文件的保存路径
			List<PageData> fileUrlList = processfilemanagermxmxService.findFilePathByMxId(pd);
			if(null != fileUrlList) {
				for(PageData fileUrl : fileUrlList) {
					String url = fileUrl.getString("FILE_SAVEPATH");
					String lineCode = fileUrl.getString("LINE_CODE");
					String saveUrl = materialCoding+"/"+lineCode+"/"+materialCoding+"-"+lineCode+".pdf";
					PageData pd2 = new PageData();
					pd2.put("MATERIAL_CODING", materialCoding);
					pd2.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);
					pd2.put("FILE_SAVEPATH", saveUrl);
					pd2.put("PRODUCTLINE_NAME", lineCode);
					String oldPath = "D:\\processFile\\"+url;
					String newPath = "D:\\processFile\\"+materialCoding+"\\"+lineCode+"\\"+materialCoding+"-"+lineCode+".pdf";
					if(url.indexOf("/") != -1) {
						oldPath = "D:/processFile/"+url;
						newPath = "D:/processFile/"+materialCoding+"/"+lineCode+"/"+materialCoding+"-"+lineCode+".pdf";
					}
					//实现文件的拷贝
					File f = new File(newPath);
					if(!f.getParentFile().exists()){
						f.getParentFile().mkdirs();
					}
					DealFile.copyFile(oldPath, newPath);
					//复制工位后实现在数据库保存文件路径
					processfilemanagermxmxService.fileInsert(pd2);
				}
			}
			//通过materialMxId在MXMX表里查询到详细的对应终端配置信息
			List<PageData> stationMesslist = processfilemanagermxmxService.findStationMessByMxId(pd);
			if(null != stationMesslist ) {
				for(PageData stationMess : stationMesslist) {
					//将终端待显示的页码存储到数据库中
					String PROCESSFILEMANAGERMXMX_ID = this.get32UUID();
					System.out.println("this.get32UUID()::"+PROCESSFILEMANAGERMXMX_ID);
					stationMess.put("PROCESSFILEMANAGERMXMX_ID",PROCESSFILEMANAGERMXMX_ID);	//主键
					stationMess.put("PROCESSFILEMANAGERMX_ID", MX_Main_ID);
					stationMess.put("PRODUCTLINE_NAME", stationMess.getString("LINE_CODE"));
					//为了查询完整PDF文件的保存路径多加一个属性
					stationMess.put("MATERIAL_CODING", materialCoding);
					//processfilemanagermxmxService.save(stationMess);
					/*
					 * 分割每个终端要显示的PDF文件
					 * */
					 //找到完整的文件的路径
					PageData pd2 = processfilemanagermxmxService.findFileSavePath(stationMess);
					String file_path = pd2.getString("FILE_SAVEPATH");
					String path = "D:\\processFile\\"+file_path;
					if(file_path.indexOf("/") != -1) {
						path = "D:/processFile/"+file_path;
					}
					//查询要显示的页码信息
					List<PageData> stationMesslist2 = processfilemanagermxmxService.findStationMessByMxId(pd);
					for(PageData pData2 : stationMesslist2) {
						String pageNum = pData2.getString("PAGE");
						String lineName = pData2.getString("LINE_CODE");
						String terminalCode = pData2.getString("TERMINAL_EQUIPMENT");
						String savePath = "D:/processFile/"+materialCoding+"/"+lineName+"/display/"+terminalCode+".pdf";
						File f = new File(savePath); 
						if(!f.getParentFile().exists()) {
							f.getParentFile().mkdirs();
						}
						PDFReader.splitPDFFile(path, savePath, pageNum);
					}
				}
			}
			out.write("success");
			out.close();
		}
	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		System.out.println("进入了/goEdit方法------------");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询出所有的产品
		List<PageData> productList=productService.listAll(pd);
		mv.addObject("productList",productList);
		pd = processfilemanagermxService.findById(pd);	//根据ID读取
		mv.setViewName("base/processfilemanagermx/processfilemanagermx_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ProcessFileManagerMx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			processfilemanagermxService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ProcessFileManagerMx到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("物料编码");	//1
		titles.add("物料名称");	//2
		titles.add("规格型号");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = processfilemanagermxService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("MATERIAL_CODING"));	    //1
			vpd.put("var2", varOList.get(i).getString("PRODUCT_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("PRODUCT_SPECIFICATIONS"));	    //3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
