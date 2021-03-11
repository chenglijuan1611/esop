package com.fh.service.base.processfilemanager.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.processfilemanager.ProcessFileManagerManager;

/** 
 * 说明： 工艺管理
 * 创建人：df
 * 创建时间：2018-08-13
 * @version
 */
@Service("processfilemanagerService")
public class ProcessFileManagerService implements ProcessFileManagerManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ProcessFileManagerMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProcessFileManagerMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProcessFileManagerMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProcessFileManagerMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProcessFileManagerMapper.deleteAll", ArrayDATA_IDS);
	}
	//通过用户编码查找用户姓名
	@Override
	public PageData findName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("ProcessFileManagerMapper.findName", pd);
	}

	//查找数据库是否已有该终端编码
	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMapper.findByCode", pd);
	}
	
	//在工艺表里查找该物料是否已有所属工艺
	@Override
	public PageData findByMaterialCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMapper.findByMaterialCode", pd);
	}
	
}

