package com.fh.controller.base.classification;

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
import com.fh.util.UTF8Util;
import com.fh.service.base.classification.ClassificationManager;
import com.fh.service.base.product.ProductManager;
import com.fh.service.system.fhlog.FHlogManager;

/** 
 * 说明：产品分类
 * 创建人：df
 * 创建时间：2018-06-16
 */
@Controller
@RequestMapping(value="/classification")
public class ClassificationController extends BaseController {
	
	String menuUrl = "classification/list.do"; //菜单地址(权限用)
	@Resource(name="classificationService")
	private ClassificationManager classificationService;
	
//	@Resource(name="worktimeService")
//	private WorktimeManager worktimeService;
	
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="productService")
	private ProductManager productService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save(Model model,String checkadd) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Classification");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CLASSIFICATION_ID", this.get32UUID());	//主键
		classificationService.save(pd);
		FHLOG.save(Jurisdiction.getUsername(), "添加分类："+pd.getString("CLASSIFICATION_CODE"));
		model.addAttribute("msg","success");
		result = "save_result";
		return result;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Classification");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//List<PageData> worktimeList=worktimeService.findByClassificationId(pd);
		/*
		if(worktimeList.size()>0 || productList.size()>0){
			out.write("false");
			out.close();
		}else{
			PageData pageData =classificationService.findById(pd);
			classificationService.delete(pd);
			FHLOG.save(Jurisdiction.getUsername(), "删除分类："+pageData.getString("CLASSIFICATION_CODE"));
			out.write("success");
			out.close();
		}
		*/
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Classification");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		classificationService.edit(pd);
		FHLOG.save(Jurisdiction.getUsername(), "修改分类："+classificationService.findById(pd).getString("CLASSIFICATION_CODE"));
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Classification");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = classificationService.list(page);	//列出Classification列表
		mv.setViewName("base/classification/classification_list");
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
		mv.setViewName("base/classification/classification_edit");
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
		pd = classificationService.findById(pd);	//根据ID读取
		mv.setViewName("base/classification/classification_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Classification");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			classificationService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Classification到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UTF8Util.utf(pd, pd);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("分类编码");	//1
		titles.add("分类名");	//2
		dataMap.put("titles", titles);
		List<PageData> varOList = classificationService.listAllExcel(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CLASSIFICATION_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("CLASSIFICATION_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("CLASSIFICATION_RESERVE"));	    //3
			varList.add(vpd);
		}
		FHLOG.save(Jurisdiction.getUsername(), "导出分类表："+varOList.size()+"条数据");
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
	
	
	/**判断分类Code是否存在
	 */
	@RequestMapping(value="/hasCode")
	@ResponseBody
	public void hasCCode(PrintWriter out){
		
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(classificationService.findByCode(pd)!= null){
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
	
	/**判断分类Name是否存在
	 */
	@RequestMapping(value="/hasCName")
	@ResponseBody
	public void hasCName(PrintWriter out){
		
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(classificationService.findByName(pd)!= null){
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
