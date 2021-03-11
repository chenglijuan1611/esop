package com.fh.controller.base.productline;

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
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：生产线管理
 * 创建人：df
 * 创建时间：2018-05-21
 */
@Controller
@RequestMapping(value="/productline")
public class ProductlineController extends BaseController {
	String menuUrl = "productline/list.do"; //菜单地址(权限用)
	@Resource(name="productlineService")
	private ProductlineManager productlineService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save(Model model,String checkadd) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Productline");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		String result = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRODUCTLINE_ID", this.get32UUID());	//主键
		productlineService.save(pd);
		FHLOG.save(Jurisdiction.getUsername(), "添加产线："+pd.getString("NAME"));
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
		System.out.println("进入了删除产线时关联查询的方法");
		logBefore(logger, Jurisdiction.getUsername()+"删除Productline");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//删除产线时查询是否有终端关联着此产线
		List<PageData> list = productlineService.findRelationOfTerminal(pd);
		if(list.size()>0){
			out.write("false");
			out.close();
		}else{
		    PageData pageData =productlineService.findById(pd);//删除之前保存该条产线的记录
			productlineService.delete(pd);
			if(pageData!=null) {
			FHLOG.save(Jurisdiction.getUsername(), "删除产线："+pageData.getString("NAME"));
			}
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
		logBefore(logger, Jurisdiction.getUsername()+"修改Productline");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		productlineService.edit(pd);
		PageData pageData =productlineService.findById(pd);
		FHLOG.save(Jurisdiction.getUsername(), "修改产线："+pageData.getString("NAME"));
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Productline");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = productlineService.list(page);	//列出Productline列表
		mv.setViewName("base/productline/productline_list");
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
	public ModelAndView goAdd(String checkadd,Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("base/productline/productline_edit");
		List<PageData>	userList = userService.listAll(pd);
		mv.addObject("userList", userList);
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
	public ModelAndView goEdit(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = productlineService.findById(pd);	//根据ID读取
		mv.setViewName("base/productline/productline_edit");
		page.setPd(pd);
		List<PageData>	userList = userService.listAll(pd);
		mv.addObject("userList", userList);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Productline");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			productlineService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Productline到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");	
		pd.put("keywords", keywords.trim());
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("生产线名称");	//1
		titles.add("生产线编码");	//2
		titles.add("生产线线长");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = productlineService.listAllExcel(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME"));	    //2
			vpd.put("var2", varOList.get(i).getString("CODE"));	    //3
			vpd.put("var3", varOList.get(i).getString("lineleader"));	    //4
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		FHLOG.save(Jurisdiction.getUsername(), "导出产线表："+varOList.size()+"条数据");
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	/**判断生产线是否存在
	 */
	@RequestMapping(value="/hasName")
	@ResponseBody
	public void hasPS(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(productlineService.listByName(pd).size() ==0){
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
