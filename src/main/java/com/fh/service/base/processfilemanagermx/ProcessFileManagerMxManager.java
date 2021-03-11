package com.fh.service.base.processfilemanagermx;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 工艺管理(明细)接口
 *rhz
 * 创建时间：2018-08-14
 * @version
 */
public interface ProcessFileManagerMxManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**查询明细总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception;

	/**通过ProcessFileManager_Id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByProcessFileManager_Id(PageData pd)throws Exception;

	
	//根据物料编码查询出物料的id
	public PageData findCode(PageData pd)throws Exception;
	//根据物料编码查询出物料的id
	public List<PageData> findCode3(PageData pd)throws Exception;
	
	//根据物料id查询出物料的编码
	public PageData findCodeByMxId(PageData pd)throws Exception;
	
	//从ERP导入当前工艺所需物料
	public List<PageData> findMessFromERP(PageData pd)throws Exception;
	//根据物料编码和工艺id该工艺下已有该物料
	public PageData findCode2(PageData pd2)throws Exception;
	
	//查询出所有的已配置过工位信息的物料
	public List<PageData> findAllMaterStationMess(PageData pd)throws Exception;
	
	//根据MX_ID在MX表中查出物料编码
	public PageData findCode4(PageData pd)throws Exception;
	
	//复制工位的时候，根据查询的关键字搜索出相应的信息
	public List<PageData> findMaterStationMess(Page page)throws Exception;

	//添加子物料时判断是否已经添加过该子物料
	public PageData findByMaterialCodeAndManager(PageData pd)throws Exception;
	
	//手动增加自物料
	public void save2(PageData pd)throws Exception;
	//从创建出临时表tempdb.dbo.rhztest查询数据
	public List<PageData> findMessFromtempdbo(PageData pd)throws Exception;
	
}

