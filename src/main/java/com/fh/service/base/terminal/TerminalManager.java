package com.fh.service.base.terminal;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 终端管理接口
 * 创建人：df
 * 创建时间：2018-09-03
 * @version
 */
public interface TerminalManager{

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

	
	/**通过终端Code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception;

	/**通过终端Name获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**通过产线和终端显示序号获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNum(PageData pd)throws Exception;

	/**查询出所有的终端类型
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listTerminalType(PageData pd)throws Exception;
	/**根据产线查询出该产线最大的显示序号
	 * @param pd
	 * @throws Exception
	 */
	public PageData findPageByLine(PageData pd)throws Exception;
	/**根据关键字，先查询数据库是否有数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData listAllAgo(PageData pd)throws Exception;
	
	
	//根据产品查询在所有终端和页码
	public List<PageData> listTerAndPage(PageData pd)throws Exception;

	
}

