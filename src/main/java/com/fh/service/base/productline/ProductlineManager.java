package com.fh.service.base.productline;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 生产线管理接口
 * 创建人：rhz
 * 创建时间：2018-05-21
 * @version
 */
public interface ProductlineManager{

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
	
	
	
	/**列表(根据产品名称NAME)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listByName(PageData pd)throws Exception;
	
	
	
	/**导出excel
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllExcel(PageData pd)throws Exception;
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过产线编码CODE获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception;
		
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**批量查找
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public List<PageData> selectAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**删除产线时查询是否有终端关联着此产线
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findRelationOfTerminal(PageData pd)throws Exception;
	
}

