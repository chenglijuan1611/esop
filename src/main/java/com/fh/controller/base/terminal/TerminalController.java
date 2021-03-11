package com.fh.controller.base.terminal;

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
import com.fh.service.base.productline.ProductlineManager;
import com.fh.service.base.terminal.TerminalManager;

/** 
 * 说明：终端管理
 * 创建人：df
 * 创建时间：2018-09-03
 */
@Controller
@RequestMapping(value="/terminal")
public class TerminalController extends BaseController {
	
	String menuUrl = "terminal/list.do"; //菜单地址(权限用)
	@Resource(name="terminalService")
	private TerminalManager terminalService;
	@Resource(name="productlineService")
	private ProductlineManager productlineService;
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save(Model model,String checkadd) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Terminal");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TERMINAL_ID", this.get32UUID());	//主键
		terminalService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Terminal");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		terminalService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Terminal");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		terminalService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Terminal");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	lineList = productlineService.listAll(pd);//查询出已有的产线
		String keywords = pd.getString("keywords");				//关键词检索条件
		String lineName = pd.getString("lineName");
		System.out.println("lllline:"+lineName);
		String lastStart = pd.getString("lastStart");
		String lastEnd = pd.getString("lastEnd");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != lineName && !"".equals(lineName)) {
			pd.put("lineName", lineName);
		}
		if(null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart);
		}
		if(null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd);
		}
		page.setPd(pd);
		List<PageData>	varList = terminalService.list(page);	//列出Terminal列表
		mv.setViewName("base/terminal/terminal_list");
		mv.addObject("varList", varList);
		mv.addObject("lineList", lineList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	//lineTrigger
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/lineTrigger")
	public void lineTrigger(PrintWriter out) throws Exception{
		System.out.println("进入了查询当前产线终端应该显示的页码查询方法");
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd2 = terminalService.findPageByLine(pd);
		String num = "0";
		if(pd2.getString("maxShowNum").length()>0 ) {
			num = pd2.getString("maxShowNum");
		}
		Integer maxShowNum = Integer.parseInt(num) + 1;
		String maxString2 = maxShowNum.toString();
		result = maxString2;
		out.write(result);
		out.close();
	}
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	   @RequestMapping(value = "/listBy")
	public ModelAndView listBy(Page page, HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Product");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("CLASSIFICATION_CODE") != null || pd.getString("classifi") != null) {
			request.getSession().setAttribute("CLASSIFICATION_CODE", pd.getString("CLASSIFICATION_CODE"));
		}
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);

		List<PageData> productlineList = productlineService.listAll(pd);// 列出ProductLine列表
		mv.addObject("productlineList", productlineList);
		List<PageData> classificationList = classificationService.listAll(pd);
		mv.addObject("classificationList", classificationList);
		if (pd.getString("listYESOrNO") == null) {
			page.getPd().put("CLASSIFICATION_CODE", request.getSession().getAttribute("CLASSIFICATION_CODE"));
		}
		List<PageData> varList = productService.list(page); // 列出Product列表
		for (PageData i : varList) {
			String ids = i.getString("PRODUCTLINE_ID");
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
		JSONArray jsonvarList = new JSONArray();
		for (PageData pageData : varList) {
			jsonvarList.add(pageData);
		}
		mv.setViewName("base/product/product_list");
		mv.addObject("varList", varList);
		mv.addObject("jsonvarList", jsonvarList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	 */
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(String checkadd)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//列出Productline列表
		List<PageData>	lineList = productlineService.listAll(pd);	
		mv.addObject("lineList",lineList);
		//查询出已有的终端类型信息
		List<PageData> terminalTypeList = terminalService.listTerminalType(pd);
		mv.addObject("terminalTypeList", terminalTypeList);
		mv.setViewName("base/terminal/terminal_edit");
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
		//列出Productline列表
		List<PageData>	lineList = productlineService.listAll(pd);	
		mv.addObject("lineList",lineList);
		//查询出已有的终端类型信息
		List<PageData> terminalTypeList = terminalService.listTerminalType(pd);
		mv.addObject("terminalTypeList", terminalTypeList);
		//根据ID读取
		pd = terminalService.findById(pd);	
		mv.setViewName("base/terminal/terminal_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Terminal");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			terminalService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Terminal到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pageData = terminalService.listAllAgo(pd);
		String count = pageData.getString("zs");
		System.out.println("countcountcountcount:::"+count);
		if("0".equals(count)) {
			mv.addObject("msg","terResult_0");
			mv.setViewName("save_result2");
		}else {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("终端编码");	//1
			titles.add("终端名称");	//2
			titles.add("位置");	//3
			titles.add("部署产线");	//4
			titles.add("终端类型");	//5
			titles.add("注册日期");	//6
			titles.add("屏幕宽");	//7
			titles.add("屏幕高");	//8
			titles.add("显示序号");	//9
			titles.add("显示页码");	//10
			dataMap.put("titles", titles);
			List<PageData> varOList = terminalService.listAll(pd);
			
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TERMINAL_CODE"));	    //1
				vpd.put("var2", varOList.get(i).getString("TERMINAL_NAME"));	    //2
				vpd.put("var3", varOList.get(i).getString("LOCATION"));	    //3
				vpd.put("var4", varOList.get(i).getString("DEPLOY_LINE"));	    //4
				vpd.put("var5", varOList.get(i).getString("TERMINAL_TYPE"));	    //5
				vpd.put("var6", varOList.get(i).getString("REGISTER_DATE"));	    //6
				vpd.put("var7", varOList.get(i).getString("SCREEN_WIDE"));	    //7
				vpd.put("var8", varOList.get(i).getString("SCREEN_HEIGHT"));	    //8
				vpd.put("var9", varOList.get(i).getString("SHOW_NUM"));	    //9
				vpd.put("var10", varOList.get(i).getString("FILE_PAGENUM"));	    //10
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		}
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**判断终端Code是否存在
	 */
	@RequestMapping(value="/hasCode")
	@ResponseBody
	public void hasCode(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(terminalService.findByCode(pd)!= null){
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
	
	
	/**判断分类Code是否存在
	 */
	@RequestMapping(value="/hasName")
	@ResponseBody
	public void hasName(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(terminalService.findByName(pd)!= null){
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
	
	/**判断该产线是否存在该显示序号的终端
	 */
	@RequestMapping(value="/hasNum")
	@ResponseBody
	public void hasNum(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(terminalService.findByNum(pd)!= null){
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
	
}
