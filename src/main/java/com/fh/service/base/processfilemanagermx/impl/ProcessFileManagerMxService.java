package com.fh.service.base.processfilemanagermx.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.processfilemanagermx.ProcessFileManagerMxManager;

/** 
 * 说明： 工艺管理(明细)
 *rhz
 * 创建时间：2018-08-14
 * @version
 */
@Service("processfilemanagermxService")
public class ProcessFileManagerMxService implements ProcessFileManagerMxManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ProcessFileManagerMxMapper.save", pd);
	}
	/**手动新增子物料
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save2(PageData pd) throws Exception {
		dao.save("ProcessFileManagerMxMapper.save2", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProcessFileManagerMxMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProcessFileManagerMxMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findById", pd);
	}
	/**通过ProcessFileManager_Id获取数据
	 * @param pd
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public List<PageData> findByProcessFileManager_Id(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.findByProcessFileManager_Id", pd);
	}
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProcessFileManagerMxMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**查询明细总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findCount", pd);
	}

	/**通过processFileManageMxId获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByProcessFileManagerId(String processFileManageMxId) throws Exception {
		return null;
	}

	/**根据物料编码查询出物料的id
	  *
	  **/
	public PageData findCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findCode", pd);
	}

	@Override
	public PageData findCodeByMxId(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findCodeByMxId", pd);
	}
	
	/**执行ERP中的一个存储过程，创建出临时表tempdb.dbo.rhztest
	  *
	  **/
	@SuppressWarnings("unchecked")
	public List<PageData> findMessFromERP(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.findMessFromERP", pd);
	}

	@Override
	public PageData findCode2(PageData pd2) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findCode2", pd2);
	}
	/**根据物料编码查询出物料的id
	  *
	  **/
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findCode3(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.findCode3", pd);
	}
	
	
	//查询出所有的已配置过工位信息的物料
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAllMaterStationMess(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.findAllMaterStationMess", pd);
	}

	@Override
	public PageData findCode4(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findCode4", pd);
	}
	
	//复制工位的时候，根据查询的关键字搜索出相应的信息
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findMaterStationMess(Page page)throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.datalistPageMess", page);
	}

	//添加子物料时判断是否已经添加过该子物料
	@Override
	public PageData findByMaterialCodeAndManager(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMapper.findByMaterialCodeAndManager", pd);
	}
	
	//从创建出临时表tempdb.dbo.rhztest查询数据
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findMessFromtempdbo(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMapper.findMessFromtempdbo", pd);
	}

	
	
}

