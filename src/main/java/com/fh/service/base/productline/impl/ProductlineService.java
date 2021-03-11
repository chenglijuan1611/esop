package com.fh.service.base.productline.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.productline.ProductlineManager;

/** 
 * 说明： 生产线管理
 * 创建人：rhz
 * 创建时间：2018-05-21
 * @version
 */
@Service("productlineService")
public class ProductlineService implements ProductlineManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ProductlineMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProductlineMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProductlineMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProductlineMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductlineMapper.listAll", pd);
	}
	/**列表(根据产品名称NAME)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByName(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductlineMapper.listByName", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllExcel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductlineMapper.listAllExcel", pd);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProductlineMapper.findById", pd);
	}
	
	/**通过产线编码CODE获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProductlineMapper.findByCode", pd);

	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProductlineMapper.deleteAll", ArrayDATA_IDS);
	}

	/**批量查找
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> selectAll(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("ProductlineMapper.selectAll", ArrayDATA_IDS);
	}
	/**删除产线时查询是否有终端关联着此产线
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findRelationOfTerminal(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProductlineMapper.findRelationOfTerminal", pd);
	}
	
}

