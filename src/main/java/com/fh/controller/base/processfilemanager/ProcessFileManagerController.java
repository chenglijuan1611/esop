package com.fh.controller.base.processfilemanager;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.fh.util.Jurisdiction;
import com.fh.service.base.processfilemanager.ProcessFileManagerManager;
import com.fh.service.base.processfilemanagermx.ProcessFileManagerMxManager;
import com.fh.service.base.product.ProductManager;

/** 
 * 说明：工艺管理
 * 创建人：df
 * 创建时间：2018-08-13
 */
@Controller
@RequestMapping(value="/processfilemanager")
public class ProcessFileManagerController extends BaseController {
	
	String menuUrl = "processfilemanager/list.do"; //菜单地址(权限用)
	@Resource(name="processfilemanagerService")
	private ProcessFileManagerManager processfilemanagerService;
	@Resource(name="processfilemanagermxService")
	private ProcessFileManagerMxManager processfilemanagermxService;
	@Resource(name="productService")
	private ProductManager productService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save(Model model,String checkadd) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PROCESSFILEMANAGER_ID", this.get32UUID());	//主键
		processfilemanagerService.save(pd);
		model.addAttribute("msg","success");
		if(checkadd.equals("checked")) {
			model.addAttribute("checkadd",checkadd);
			result = "redirect:goAdd.do";
		}else {
			result = "save_result";
		}
		return result;
	}
	
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		processfilemanagerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**删除2
	 * @param out
	 * @throws Exception
	 */ 
	@ResponseBody
	@RequestMapping(value="/delete2")
	public void delete2(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData count = processfilemanagermxService.findCount(pd);
		if(Integer.parseInt(count.get("zs").toString()) > 0){
			out.write("false");
			out.close(); 
		}else {
			processfilemanagerService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"修改ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		processfilemanagerService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ProcessFileManager");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");	//关键词检索条件
		String lastStart = pd.getString("lastStart");
		String lastEnd = pd.getString("lastEnd");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart.trim());
		}
		if(null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = processfilemanagerService.list(page);	//列出ProcessFileManager列表
		mv.setViewName("base/processfilemanager/processfilemanager_list2");
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
		pd.put("username", Jurisdiction.getUsername());
		PageData pd2 = processfilemanagerService.findName(pd);//通过用户编码查找用户姓名
		pd.put("name", pd2.getString("NAME"));
		//查询出所有的产品
		/*List<PageData> productList=productService.listAll(pd);
		mv.addObject("productList",productList);*/
		mv.setViewName("base/processfilemanager/processfilemanager_edit");
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
		pd = processfilemanagerService.findById(pd);	//根据ID读取
		//查询出所有的产品
		/*List<PageData> productList=productService.listAll(pd);
		mv.addObject("productList",productList);*/
		mv.setViewName("base/processfilemanager/processfilemanager_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去配置工艺页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/configCraft")
	public ModelAndView configCraft(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = new ArrayList<PageData>();
		varList = processfilemanagermxService.findByProcessFileManager_Id(pd);
		PageData pd2 = processfilemanagerService.findById(pd);	//根据ID读取
		page.setPd(pd);
		List<PageData>	varList2 = processfilemanagermxService.list(page);	//列出ProcessFileManagerMx列表
		mv.setViewName("base/processfilemanagermx/processfilemanagermx_list");
		mv.addObject("varList2", varList2);
		mv.addObject("msg", "config");
		mv.addObject("pd", pd2);
		mv.addObject("varList", varList);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ProcessFileManager");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			processfilemanagerService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ProcessFileManager到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		/**/
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("工艺编码");	//1
		titles.add("工艺名称");	//2
		titles.add("产品编码");	//3
		titles.add("产品名称");	//4
		titles.add("规格型号");	//5
		titles.add("操作时间");	//6
		titles.add("操作人");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = processfilemanagerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("PROCESS_CODING"));	    //1
			vpd.put("var2", varOList.get(i).getString("PROCESS_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("PRODUCT_CODING"));	    //3
			vpd.put("var4", varOList.get(i).getString("PRODUCT_NAME"));	    //4
			vpd.put("var5", varOList.get(i).getString("PRODUCT_SPECIFICATIONS"));	    //5
			vpd.put("var6", varOList.get(i).getString("OPERATION_TIME"));	    //6
			vpd.put("var7", varOList.get(i).getString("OPERATION_PERSON"));	    //7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		
		return mv;
	}
	
	
	/**判断工艺Code是否存在
	 */
	@RequestMapping(value="/hasCode")
	@ResponseBody
	public void hasCode(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(processfilemanagerService.findByCode(pd)!= null){
				out.write("false");
				out.close();
			}else {
				out.write("success");
				out.close();
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
