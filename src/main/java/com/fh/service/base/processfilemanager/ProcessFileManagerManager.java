package com.fh.service.base.processfilemanager;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 工艺管理接口
 * 创建人：df
 * 创建时间：2018-08-13
 * @version
 */
public interface ProcessFileManagerManager{

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
	
	//通过用户编码查找用户姓名
	public PageData findName(PageData pd)throws Exception;
	//查找数据库是否已有该终端编码
	public PageData findByCode(PageData pd)throws Exception;
	//在工艺表里查找该物料是否已有所属工艺
	public PageData findByMaterialCode(PageData pd)throws Exception;
	
}

