package com.fh.controller.base.product;

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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import net.sf.json.JSONObject;
import com.fh.util.Jurisdiction;
//import com.fh.service.base.pieceprocess.PieceProcessManager;
import com.fh.service.base.processfilemanager.ProcessFileManagerManager;
import com.fh.service.base.processfilemanagermx.ProcessFileManagerMxManager;
import com.fh.service.base.product.ProductManager;
import com.fh.service.base.productline.ProductlineManager;
import com.fh.service.base.terminal.TerminalManager;

/** 
 * 说明：产品管理
 * 创建人：df
 * 创建时间：2018-09-26
 */
@Controller
@RequestMapping(value="/product")
public class ProductController extends BaseController {
	
	String menuUrl = "product/list.do"; //菜单地址(权限用)
	@Resource(name="productService")
	private ProductManager productService;
	@Resource(name = "productlineService")
	private ProductlineManager productlineService;
//	@Resource(name="pieceprocessService")
//	private PieceProcessManager pieceprocessService;
	@Resource(name="processfilemanagerService")
	private ProcessFileManagerManager processfilemanagerService;
	@Resource(name="processfilemanagermxService")
	private ProcessFileManagerMxManager processfilemanagermxService;
//	@Resource(name="packingscan_infoService")
//	private PackingScan_InfoManager packingscan_infoService;
	@Resource(name="terminalService")
	private TerminalManager terminalService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save(Model model,String checkadd) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRODUCT_ID", this.get32UUID());	//主键
		productService.save(pd);
		model.addAttribute("msg","success");
		if(checkadd.equals("checked")) {
			model.addAttribute("checkadd",checkadd);
			result = "redirect:goAdd.do";
		}else {
			result = "save_result";
		}
		return result;
	}
	
	
	/**去配置工位页面
	 * @param
	 * @throws Exception   
	 */
	@RequestMapping(value="/configStation")
	public ModelAndView configStation(Page page)throws Exception{
		System.out.println("进入了configStation方法");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd = processfilemanagermxService.findCode(pd);
		//String PROCESSFILEMANAGERMX_ID = pd.getString("PROCESSFILEMANAGERMX_ID");
		//List<PageData>	lineList = productlineService.listAll(pd);	//列出Productline列表
		List<PageData>	terminaList = terminalService.listTerAndPage(pd);	////根据产品查询在所有终端和页码
		mv.setViewName("base/processfilemanagermxmx/processfilemanagermxmx_list22");
		mv.addObject("terminaList", terminaList);
		mv.addObject("PRODUCT_CODE", pd.getString("PRODUCT_CODE"));
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
		/**从ERP导入产品数据
		 * @param
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping(value="/importfromErp")
		public JSONObject importfromErp(Model model,String checkadd,HttpServletRequest request) throws Exception{
			//logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManager");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
			JSONObject jsonObject = new JSONObject();
			PageData pd = new PageData();
			pd =  this.getPageData();
			//从配置文件中读取链接服务器信息
			List<PageData>	varList = null;
			Properties properties = null;
			try {
				properties = new Properties();
				properties.load(getClass().getResourceAsStream("/dbconfig.properties"));
			}catch(Exception e) {
				jsonObject.put("res", "dbconfigReadFail");
				return jsonObject;
				//e.printStackTrace();
			}
			String linkServer = properties.getProperty("linkServer");
			String linkDatabase = properties.getProperty("linkDatabase");
			if(linkServer != null && linkDatabase != null) {
				pd.put("LINKSERVER",linkServer);//链接服务器名称
				pd.put("LINKDATABASE",linkDatabase);//链接数据库名
			}else {
				jsonObject.put("res", "propertiesOfValueNotFind");
				return jsonObject;
			}
			//查询已经导入的和ERP不一致的产品的名称、规格型号
			List<PageData> list = productService.checkProduct(pd);
			if(list.size() > 0) {
				for(PageData p : list) {
					productService.updateProduct(p);
				}
			}
			jsonObject.put("checknum", list.size());
			varList = productService.selectNumFromERP(pd);
			if(varList.size() > 0) {
				productService.insertProduct(pd);
				jsonObject.put("num", varList.size());
				jsonObject.put("res", "success");
				logBefore(logger, Jurisdiction.getUsername()+"插入了"+varList.size()+"条数据");
			}else {
				jsonObject.put("res", "dataSame");
			}
			return jsonObject;
		}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		productService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		productService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Product");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = productService.list(page);	//列出Product列表
		List<PageData> productlineList = productlineService.listAll(pd);// 列出ProductLine列表
		for (PageData i : varList) {
			String ids = i.getString("PRODUCTLINE_ID");
			if(ids == null) {
				ids = "";
			}
			String name = i.getString("NAME");
			String[] id = ids.split(",");
			if (name == null) {
				name = "";
				for (String j : id) {
					for (PageData productline : productlineList) {
						if (j.equals(productline.getString("PRODUCTLINE_ID"))) {
							name += productline.getString("NAME") + " ";
						}
					}
				}
				i.put("NAME", name);
			}
		}
		mv.setViewName("base/product/product_list");
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
		List<PageData> productlineList = productlineService.listAll(pd);
		mv.addObject("productlineList", productlineList);
		pd = this.getPageData();
		mv.setViewName("base/product/product_edit");
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
		pd = productService.findById(pd); // 根据ID读取
		System.out.println(pd);
		String PRODUCTLINE_ID = pd.getString("PRODUCTLINE_ID");
		if(PRODUCTLINE_ID == null) {
			PRODUCTLINE_ID = "";
		}
		String[] productline_ids = PRODUCTLINE_ID.split(",");
		mv.addObject("productline_ids", productline_ids);
		List<PageData> productlineList = productlineService.listAll(pd);
		mv.addObject("productlineList", productlineList);
//		List<PageData> pieceProcessList = pieceprocessService.listAll(pd);
//		mv.addObject("pieceProcessList", pieceProcessList);
		//pd = productService.findById(pd);	//根据ID读取
		mv.setViewName("base/product/product_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			productService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Product到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("产品编码");	//1
		titles.add("产品名称");	//2
		titles.add("规格型号");	//3
		titles.add("标准工时");	//4
		titles.add("本体条码1");	//5
		titles.add("本体条码2");	//6
		titles.add("生产线编码");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = productService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("PRODUCT_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("PRODUCT_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("PRODUCT_SPECIFICATIONS"));	    //3
			vpd.put("var4", varOList.get(i).getString("PRODUCT_SWHOURS"));	    //4
			vpd.put("var5", varOList.get(i).getString("PRODUCT_BODYBARCODEONE"));	    //5
			vpd.put("var6", varOList.get(i).getString("PRODUCT_BODYBARCODETWO"));	    //6
			vpd.put("var7", varOList.get(i).getString("PRODUCTLINE_ID"));	    //7
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
	
	/**根据编码查找产品信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryProduct")
	@ResponseBody
	public Object queryProduct() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String result = "success";
		String result2 = "no";
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData product = productService.findByMaterialCode(pd);
		String invName = "";
		String invStd = "";
		if(product == null) {
			result = "error";
		}else {
			invName = product.getString("PRODUCT_NAME");
			invStd = product.getString("PRODUCT_SPECIFICATIONS");
		}
		//在工艺表里查找该物料是否已有所属工艺
		PageData materialMap = processfilemanagerService.findByMaterialCode(pd);
		String processCode;
		if(null != materialMap) {
			processCode = materialMap.getString("PROCESS_CODING");
			result2 = "yes";
			map.put("processCode", processCode);
		}
		map.put("invName", invName);
		map.put("invStd", invStd);
		map.put("result", result);
		map.put("result2", result2);
		return AppUtil.returnObject(new PageData(), map);
		
	}
	
	/**根据编码查找产品信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryProduct2")
	@ResponseBody
	public Object queryProduct2() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String result = "success";
		String result2 = "no";
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData product = productService.findByMaterialCode(pd);
		String invName = "";
		String invStd = "";
		if(product == null) {
			result = "error";
		}else {
			invName = product.getString("PRODUCT_NAME");
			invStd = product.getString("PRODUCT_SPECIFICATIONS");
		}
		//在工艺表里查找该物料是否已有所属工艺
		PageData materialMap = processfilemanagermxService.findByMaterialCodeAndManager(pd);
		if(null != materialMap) {
			result2 = "yes";
		}
		map.put("invName", invName);
		map.put("invStd", invStd);
		map.put("result", result);
		map.put("result2", result2);
		return AppUtil.returnObject(new PageData(), map);
		
	}
	
	
}
