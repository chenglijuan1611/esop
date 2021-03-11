package com.fh.service.fhoa.departstaff.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.fhoa.DepartStaff;
import com.fh.util.PageData;
import com.fh.service.fhoa.departstaff.DepartStaffManager;

/** 
 * 说明： 员工树形
 * 创建人：rhz
 * 创建时间：2018-05-15
 * @version
 */
@Service("departstaffService")
public class DepartStaffService implements DepartStaffManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DepartStaffMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DepartStaffMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DepartStaffMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DepartStaffMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DepartStaffMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartStaffMapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DepartStaff> listByParentId(String parentId) throws Exception {
		return (List<DepartStaff>) dao.findForList("DepartStaffMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<DepartStaff> listTree(String parentId) throws Exception {
		List<DepartStaff> valueList = this.listByParentId(parentId);
		for(DepartStaff fhentity : valueList){
			fhentity.setTreeurl("departstaff/list.do?DEPARTSTAFF_ID="+fhentity.getDEPARTSTAFF_ID());
			fhentity.setSubDepartStaff(this.listTree(fhentity.getDEPARTSTAFF_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
		
}

