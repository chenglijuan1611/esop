package com.fh.service.base.terminal.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.terminal.TerminalManager;

/** 
 * 说明： 终端管理
 * 创建人：df
 * 创建时间：2018-09-03
 * @version
 */
@Service("terminalService")
public class TerminalService implements TerminalManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TerminalMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TerminalMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TerminalMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TerminalMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TerminalMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TerminalMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TerminalMapper.deleteAll", ArrayDATA_IDS);
	}

	/**通过终端Code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TerminalMapper.findByCode", pd);
	}

	/**通过终端Code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TerminalMapper.findByName", pd);
	}

	/**通过产线和终端显示序号获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNum(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TerminalMapper.findByNum", pd);
	}

	/**查询出已有的终端类型信息
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listTerminalType(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TerminalMapper.listTerminalType", pd);
	}

	/**根据产线查询出该产线最大的显示序号
	 * @param pd
	 * @throws Exception
	 */
	public PageData findPageByLine(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TerminalMapper.findPageByLine", pd);
	}

	/**根据关键字，先查询数据库是否有数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData listAllAgo(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TerminalMapper.listAllAgo", pd);
	}
	
	//根据产品查询在所有终端和页码
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listTerAndPage(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TerminalMapper.listTerAndPage", pd);
	}
	
}

