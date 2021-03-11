package com.fh.controller.fhoa.departstaff;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.fhoa.departstaff.DepartStaffManager;

/** 
 * 说明：员工树形
 * 创建人：rhz
 * 创建时间：2018-05-15
 */
@Controller
@RequestMapping(value="/departstaff")
public class DepartStaffController extends BaseController {
	
	String menuUrl = "departstaff/list.do"; //菜单地址(权限用)
	@Resource(name="departstaffService")
	private DepartStaffManager departstaffService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增DepartStaff");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DEPARTSTAFF_ID", this.get32UUID());	//主键
		departstaffService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String DEPARTSTAFF_ID) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除DepartStaff");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} 					//校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("DEPARTSTAFF_ID", DEPARTSTAFF_ID);
		String errInfo = "success";
		if(departstaffService.listByParentId(DEPARTSTAFF_ID).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			departstaffService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改DepartStaff");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		departstaffService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表DepartStaff");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 	//校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");								//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DEPARTSTAFF_ID = null == pd.get("DEPARTSTAFF_ID")?"":pd.get("DEPARTSTAFF_ID").toString();
		//System.out.println("==========");
		//System.out.println(DEPARTSTAFF_ID);
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTSTAFF_ID = pd.get("id").toString();
		}
		pd.put("DEPARTSTAFF_ID", DEPARTSTAFF_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = departstaffService.list(page);//列出DepartStaff列表
		//+++++++添加判断条件，看是跳转到哪里
		if(DEPARTSTAFF_ID.equals("0")) {
		mv.setViewName("fhoa/departstaff/departstaff_list2");
		}else{
			mv.setViewName("fhoa/departstaff/departstaff_list");

		}
		mv.addObject("pd", departstaffService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTSTAFF_ID", DEPARTSTAFF_ID);			//上级ID
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());								//按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String DEPARTSTAFF_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(departstaffService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("DEPARTSTAFF_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartStaff", "nodes").replaceAll("hasDepartStaff", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTSTAFF_ID",DEPARTSTAFF_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("fhoa/departstaff/departstaff_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		String DEPARTSTAFF_ID = null == pd.get("DEPARTSTAFF_ID")?"":pd.get("DEPARTSTAFF_ID").toString();
		pd.put("DEPARTSTAFF_ID", DEPARTSTAFF_ID);					//上级ID
		if(DEPARTSTAFF_ID.equals("0")) {
			mv.setViewName("fhoa/departstaff/departstaff_edit2");
			}else{
				mv.setViewName("fhoa/departstaff/departstaff_edit");

			}
		mv.addObject("pds",departstaffService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTSTAFF_ID", DEPARTSTAFF_ID);			//传入ID，作为子级ID用
		mv.addObject("msg", "save");
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
		String DEPARTSTAFF_ID = pd.getString("DEPARTSTAFF_ID");
		pd = departstaffService.findById(pd);							//根据ID读取		
		mv.addObject("pd", pd);													//放入视图容器
		pd.put("DEPARTSTAFF_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",departstaffService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTSTAFF_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTSTAFF_ID",DEPARTSTAFF_ID);					//复原本ID
		pd = departstaffService.findById(pd);							//根据ID读取
		System.out.println("=============");
		System.out.println(pd.get("PARENT_ID").toString());
		if(pd.get("PARENT_ID").toString().equals("0")) {
			mv.setViewName("fhoa/departstaff/departstaff_edit2");
			}else{
				mv.setViewName("fhoa/departstaff/departstaff_edit");

			}
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出DepartStaff到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("员工编号");	//1
		titles.add("员工姓名");	//2
		titles.add("英文名字");	//3
		titles.add("员工编码");	//4
		titles.add("员工职能");	//5
		titles.add("员工电话");	//6
		titles.add("员工邮箱");	//7
		titles.add("性别");	//8
		titles.add("员工生日");	//9
		titles.add("民族");	//10
		titles.add("工作类别");	//11
		titles.add("入职时间");	//12
		titles.add("籍贯");	//13
		titles.add("政治面貌");	//14
		titles.add("工作时间");	//15
		titles.add("SFID");	//16
		titles.add("婚否");	//17
		titles.add("入职时间");	//18
		titles.add("职位");	//19
		titles.add("上岗时间");	//20
		titles.add("最高学历");	//21
		titles.add("毕业学校");	//22
		titles.add("专业");	//23
		titles.add("职业职称");	//24
		titles.add("资格证书");	//25
		titles.add("合同时长");	//26
		titles.add("签订日期");	//27
		titles.add("终止日期");	//28
		titles.add("现住址");	//29
		titles.add("部门名称");	//30
		titles.add("英文名称");	//31
		titles.add("部门编号");	//32
		titles.add("负责人");	//33
		titles.add("部门地址");	//34
		titles.add("部门职能");	//35
		titles.add("成立日期");	//36
		dataMap.put("titles", titles);
		List<PageData> varOList = departstaffService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STAFF_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("STAFF_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("STAFF_NAME_EN"));	    //3
			vpd.put("var4", varOList.get(i).getString("STAFF_BIANMA"));	    //4
			vpd.put("var5", varOList.get(i).getString("STAFF_FUNCTIONS"));	    //5
			vpd.put("var6", varOList.get(i).getString("STAFF_TEL"));	    //6
			vpd.put("var7", varOList.get(i).getString("STAFF_EMAIL"));	    //7
			vpd.put("var8", varOList.get(i).getString("STAFF_SEX"));	    //8
			vpd.put("var9", varOList.get(i).getString("STAFF_BIRTHDAY"));	    //9
			vpd.put("var10", varOList.get(i).getString("STAFF_NATION"));	    //10
			vpd.put("var11", varOList.get(i).getString("STAFF_JOBTYPE"));	    //11
			vpd.put("var12", varOList.get(i).getString("STAFF_JOBJOINTIME"));	    //12
			vpd.put("var13", varOList.get(i).getString("STAFF_FADDRESS"));	    //13
			vpd.put("var14", varOList.get(i).getString("STAFF_POLITICAL"));	    //14
			vpd.put("var15", varOList.get(i).getString("STAFF_PJOINTIME"));	    //15
			vpd.put("var16", varOList.get(i).getString("STAFF_SFID"));	    //16
			vpd.put("var17", varOList.get(i).getString("STAFF_MARITAL"));	    //17
			vpd.put("var18", varOList.get(i).getString("STAFF_DJOINTIME"));	    //18
			vpd.put("var19", varOList.get(i).getString("STAFF_POST"));	    //19
			vpd.put("var20", varOList.get(i).getString("STAFF_POJOINTIME"));	    //20
			vpd.put("var21", varOList.get(i).getString("STAFF_EDUCATION"));	    //21
			vpd.put("var22", varOList.get(i).getString("STAFF_SCHOOL"));	    //22
			vpd.put("var23", varOList.get(i).getString("STAFF_MAJOR"));	    //23
			vpd.put("var24", varOList.get(i).getString("STAFF_FTITLE"));	    //24
			vpd.put("var25", varOList.get(i).getString("STAFF_CERTIFICATE"));	    //25
			vpd.put("var26", varOList.get(i).get("STAFF_CONTRACTLENGTH").toString());	//26
			vpd.put("var27", varOList.get(i).getString("STAFF_CSTARTTIME"));	    //27
			vpd.put("var28", varOList.get(i).getString("STAFF_CENDTIME"));	    //28
			vpd.put("var29", varOList.get(i).getString("STAFF_ADDRESS"));	    //29
			vpd.put("var30", varOList.get(i).getString("DEPART_NAME"));	    //30
			vpd.put("var31", varOList.get(i).getString("DEPART_NAME_EN"));	    //31
			vpd.put("var32", varOList.get(i).getString("DEPART_BIANMA"));	    //32
			vpd.put("var33", varOList.get(i).getString("DEPART_HEADMAN"));	    //33
			vpd.put("var34", varOList.get(i).getString("DEPART_ADDRESS"));	    //34
			vpd.put("var35", varOList.get(i).getString("DEPART_FUNCTIONS"));	    //35
			vpd.put("var36", varOList.get(i).getString("DEPART_STARTTIME"));	    //36
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
