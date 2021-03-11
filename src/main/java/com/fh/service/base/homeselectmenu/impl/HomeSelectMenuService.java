package com.fh.service.base.homeselectmenu.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.homeselectmenu.HomeSelectMenuManager;

/** 
 * 说明： 首页图标
 * 创建人：df
 * 创建时间：2018-08-13
 * @version
 */
@Service("homeselectmenuService")
public class HomeSelectMenuService implements HomeSelectMenuManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("HomeSelectMenuMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("HomeSelectMenuMapper.delete", pd);
	}
	
	
	/**删除根据用户ID
	 * @param pd
	 * @throws Exception
	 */
	public void deleteByUserID(PageData pd)throws Exception{
		dao.delete("HomeSelectMenuMapper.deleteByUserID", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("HomeSelectMenuMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HomeSelectMenuMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HomeSelectMenuMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HomeSelectMenuMapper.findById", pd);
	}
	
	/**通过用户id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByUserId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HomeSelectMenuMapper.findByUserId", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("HomeSelectMenuMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

