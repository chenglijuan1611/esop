package com.fh.service.base.processfilemanagermxmx.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.base.processfilemanagermxmx.ProcessFileManagerMxMxManager;

/** 
 * 说明： 工艺管理明细(明细)
 *rhz
 * 创建时间：2018-08-14
 * @version
 */
@Service("processfilemanagermxmxService")
public class ProcessFileManagerMxMxService implements ProcessFileManagerMxMxManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProcessFileManagerMxMxMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProcessFileManagerMxMxMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProcessFileManagerMxMxMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**查询明细总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findCount", pd);
	}

	/**根据processFileManageMxId查询数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByProcessFileManagerMxId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findByProcessFileManagerMxId", pd);
	}
	
	//根据物料编码和产线信息查看上传文件
	@Override
	public PageData findFileSavePath(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findFileSavePath", pd);
	}
	
	//根据物料编码和产线信息删除上传的文件
	@Override
	public void delFile(PageData pd) throws Exception {
		dao.delete("ProcessFileManagerMxMxMapper.delFile", pd);		
	}

	//根据产线查询出终端设备信息
	@SuppressWarnings("unchecked")
	public List<PageData> findTerminalMess(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findTerminalMess", pd);
	}

	//根据终端编码，在工位信息表里查询出要显示的页码
	public PageData findPageByTerminal(PageData pd2) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findPageByTerminal", pd2);
	}
	
	//根据终端编码清除该工位要显示文件的页码
	public void clearStationMess(PageData pd) throws Exception {
		dao.findForObject("ProcessFileManagerMxMxMapper.clearStationMess", pd);
		
	}
	
	//删除工艺文件时，查看终端上是否还有渲染着的pdf文件
	public PageData delFileFind(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.delFileFind", pd);
	}
	
	//向终端显示时查询数据
	@SuppressWarnings("unchecked")
	public List<PageData> findTerminalMess2(PageData pd3) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findTerminalMess2", pd3);
	}
	//根据物料的id查询出该物料所在工艺的编码
	public PageData findProcessCodeByMxId(PageData pd)throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findProcessCodeByMxId", pd);
	}
	@Override
	public PageData findPageByTerminal2(PageData pd3) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findPageByTerminal2", pd3);
	}
	
	//通过materialMxId在MXMX表里查询到详细的对应终端配置信息
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findStationMessByMxId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findStationMessByMxId", pd);
	}
	
	//通过materialMxId在PROCESSFILESAVE表里查询到要复制物料的工艺文件的保存路径
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findFilePathByMxId(PageData pd) throws Exception {
		//return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findFilePathByMxId", pd);
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findFilePathByMxId", pd);
	}
	
	//通过materialMxId和lineName在PROCESSFILESAVE表里查询到要复制物料的工艺文件的保存路径
	@Override
	public PageData findFilePathByMxIdAndLine(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findFilePathByMxIdAndLine", pd);
	}
	
	//通过materialMxId和lineName在MXMX表里查询到详细的对应终端配置信息
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findStationMessByMxIdAndLine(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findStationMessByMxIdAndLine", pd);
	}
	
	//通过物料编码和产线查询是否已经配置过该物料的工艺文件显示
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findMess(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findMess", pd);
	}
	
	//工位信息复制之前，先删除该物料自己本身已有的工位配置
	@Override
	public void deleteBeforeCopy(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.delete("ProcessFileManagerMxMxMapper.deleteBeforeCopy", pd);	
	}
	//根据MX_ID查询MXMX表里是否有工位信息findStationMessOnlyByMxId
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findStationMessOnlyByMxId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findStationMessOnlyByMxId", pd);
	}
	//删除工位信息
	@Override
	public void delMxMXMess(PageData pd) throws Exception {
		dao.delete("ProcessFileManagerMxMxMapper.delMxMXMess", pd);
		
	}
	//根据MX_ID查询MXMX表里是否有工位信息
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findStationMessOnlyByMxId2(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findStationMessOnlyByMxId2", pd);
	}
	//保存工艺文件的访问路径
	@Override
	public void fileSave2(PageData pd)throws Exception {
		// TODO Auto-generated method stub
		dao.save("ProcessFileManagerMxMxMapper.fileSave2", pd);
	}
	//查询出某物料在哪条线上配置过终端编码
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findLinesOnlyByMxId(PageData pd)throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findLinesOnlyByMxId", pd);
	}
	//上传的PDF文件转成图片后，保存图片的图片名saveContrastMess
	@Override
	public void saveContrastMess(PageData pContrast) throws Exception {
		dao.save("ProcessFileManagerMxMxMapper.saveContrastMess", pContrast);
	}
	
	//如果已经有相应的对照表的信息，先进行删除操作
	@Override
	public void deleteContrastMess(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.delete("ProcessFileManagerMxMxMapper.deleteContrastMess", pd);
	}
	
	//根据页码和material查询出来对应png的文件名
	@Override
	public PageData findContrastFname(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findContrastFname", pd);
	}
	//根据物料编码查询出所有的页码对照信息
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findContrastFnameList(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findContrastFnameList", pd);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findContrastFnameList2(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findContrastFnameList2", pd);
	}
	@Override
	public void saveContrastMess2(PageData pContrast) throws Exception {
		dao.save("ProcessFileManagerMxMxMapper.saveContrastMess2", pContrast);
		
	}
	
	//查找工艺视频的存放路径
	@Override
	public PageData findVideoSavePath(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.findVideoSavePath", pd);
	}
	//删除视频存放的路径
	@Override
	public void delVideoSavePath(PageData pd) throws Exception {
		dao.delete("ProcessFileManagerMxMxMapper.delVideoSavePath", pd);
	}
	//将上传视频的存放路径存放在数据库
	@Override
	public void videoSave(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.save("ProcessFileManagerMxMxMapper.videoSave", pd);
	}
	//判断数据库是否已上传过工艺文件，数据库是否已有记录
	@Override
	public PageData filesaveExist(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.filesaveExist", pd);
		
	}
	//数据库插入工艺文件访问路径
	@Override
	public void fileInsert(PageData pd) throws Exception {
		dao.save("ProcessFileManagerMxMxMapper.fileInsert", pd);
	}
	
	//数据库更新工艺文件访问路径
	@Override
	public void fileUpdate(PageData pd) throws Exception {
		dao.save("ProcessFileManagerMxMxMapper.fileUpdate", pd);
	}
	
	//判断是否已经有不为0的页码
	@Override
	public PageData pageStationExist(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ProcessFileManagerMxMxMapper.pageStationExist", pd);	
	}
	//更新工位页码显示
	@Override
	public void pageStationUpdate(PageData pd) throws Exception {
		dao.save("ProcessFileManagerMxMxMapper.pageStationUpdate", pd);
	}
	
	//插入工位页码显示
	@Override
	public void pageStationInsert(PageData pd) throws Exception {
		dao.save("ProcessFileManagerMxMxMapper.pageStationInsert", pd);
	}

	//根据产品查询出该产品对应的所有的图片
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findPngsOfProduct(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ProcessFileManagerMxMxMapper.findPngsOfProduct", pd);
	}
	
}

