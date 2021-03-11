package com.fh.service.base.product;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 产品管理接口
 * 创建人：df
 * 创建时间：2018-09-26
 * @version
 */
public interface ProductManager{

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
	//通过产品编码查询信息
	public PageData findByMaterialCode(PageData pd2)throws Exception;
	
	//通过产品编码查询信息
	public List<PageData> findByMaterialCode2(PageData pd2)throws Exception;
	/**从ERP查询产品数据
	 * @param
	 * @throws Exception
	 */
	public List<PageData> selectNumFromERP(PageData pd)throws Exception;
	/**从ERP查询产品插入Product产品表
	 * @param
	 * @throws Exception
	 */
	public void insertProduct(PageData pd)throws Exception;
	
	//根据关键词查询物料信息
	public List<PageData> findByKeywords(PageData pd)throws Exception;
	
	//查询出已导入与ERP不一致的产品的产品名称和规格型号
	public List<PageData> checkProduct(PageData pd)throws Exception;
	
	//已经导入的产品和ERP不一致，进行更新
	public void updateProduct(PageData p)throws Exception;
	
	//查询出所有的产品
	public List<PageData> findAllProduct(PageData pd)throws Exception;

	
}

