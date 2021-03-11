package com.fh.service.base.product.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.product.ProductManager;

/** 
 * 说明： 产品管理
 * 创建人：df
 * 创建时间：2018-09-26
 * @version
 */
@Service("productService")
public class ProductService implements ProductManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ProductMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProductMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProductMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProductMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProductMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProductMapper.deleteAll", ArrayDATA_IDS);
	}
	//通过产品编码查询信息
	@Override
	public PageData findByMaterialCode(PageData pd2) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("ProductMapper.findByMaterialCode", pd2);
	}
	
	
	//通过产品编码查询信息
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByMaterialCode2(PageData pd2) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForObject("ProductMapper.findByMaterialCode", pd2);
	}
	/**从ERP导入产品数据
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selectNumFromERP(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProductMapper.selectNumFromERP", pd);
	}
	
	/**从ERP查询产品插入Product产品表
	 * @param
	 * @throws Exception
	 */
	@Override
	public void insertProduct(PageData pd) throws Exception {
		dao.save("ProductMapper.insertProduct", pd);
		
	}
	
	//根据关键词查询物料信息
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByKeywords(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProductMapper.findByKeywords", pd);
	}
	
	//查询出已导入与ERP不一致的产品的产品名称和规格型号
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> checkProduct(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProductMapper.checkProduct", pd);
	}
	
	//已经导入的产品和ERP不一致，进行更新
	@Override
	public void updateProduct(PageData p)throws Exception {
		dao.update("ProductMapper.updateProduct", p);
	}

	//查询出所有的产品
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAllProduct(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProductMapper.findAllProduct", pd);
	}
}

