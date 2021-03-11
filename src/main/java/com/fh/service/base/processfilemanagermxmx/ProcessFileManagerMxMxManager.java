package com.fh.service.base.processfilemanagermxmx;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 工艺管理明细(明细)接口
 *rhz
 * 创建时间：2018-08-14
 * @version
 */
public interface ProcessFileManagerMxMxManager{

	
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

	/**根据processFileManageMxId进行查询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByProcessFileManagerMxId(PageData pd)throws Exception;
	
	//根据物料编码和产线信息查看上传文件
	public PageData findFileSavePath(PageData pd)throws Exception;
	
	//根据物料编码和产线信息删除上传的文件
	public void delFile(PageData pd)throws Exception;
	
	//根据产线查询出终端设备信息
	public List<PageData> findTerminalMess(PageData pd)throws Exception;
	
	//根据终端编码，在工位信息表里查询出要显示的页码
	public PageData findPageByTerminal(PageData pd2)throws Exception;
	
	//根据终端编码清除该工位要显示文件的页码
	public void clearStationMess(PageData pd)throws Exception;
	
	//删除工艺文件时，查看终端上是否还有渲染着的pdf文件
	public PageData delFileFind(PageData pd) throws Exception;
	
	//向终端显示时查询数据
	public List<PageData> findTerminalMess2(PageData pd3)throws Exception;
	
	//根据物料的id查询出该物料所在工艺的编码
	public PageData findProcessCodeByMxId(PageData pd)throws Exception;

	public PageData findPageByTerminal2(PageData pd3)throws Exception;
	
	//通过materialMxId在MXMX表里查询到详细的对应终端配置信息
	public List<PageData> findStationMessByMxId(PageData pd)throws Exception;
	
	//通过materialMxId在PROCESSFILESAVE表里查询到要复制物料的工艺文件的保存路径
	public List<PageData> findFilePathByMxId(PageData pd)throws Exception;
	
	//通过materialMxId和LineName在PROCESSFILESAVE表里查询到要复制物料的工艺文件的保存路径
	public PageData findFilePathByMxIdAndLine(PageData pd)throws Exception;
	
	//通过materialMxId和LineName在MXMX表里查询到详细的对应终端配置信息
	public List<PageData> findStationMessByMxIdAndLine(PageData pd)throws Exception;
	
	//通过物料编码和产线查询是否已经配置过该物料的工艺文件显示
	public List<PageData> findMess(PageData pd)throws Exception;
	
	//工位信息复制之前，先删除该物料自己本身已有的工位配置
	public void deleteBeforeCopy(PageData pd)throws Exception;
	
	//根据MX_ID和产线查询MXMX表里是否有工位信息
	public List<PageData> findStationMessOnlyByMxId(PageData pd)throws Exception;
	
	//删除工位信息
	public void delMxMXMess(PageData pd)throws Exception;

	//根据MX_ID查询MXMX表里是否有工位信息
	public List<PageData> findStationMessOnlyByMxId2(PageData pd)throws Exception;
	
	//保存文件的上传路径
	public void fileSave2(PageData pd)throws Exception;
	
	//查询出某物料在哪条线上配置过终端编码  
	public List<PageData> findLinesOnlyByMxId(PageData pd)throws Exception;
    
	//上传的PDF文件转成图片后，保存图片的图片名
	public void saveContrastMess(PageData pContrast)throws Exception;
	
	//如果已经有相应的对照表的信息，先进行删除操作
	public void deleteContrastMess(PageData pd)throws Exception;
	
	//根据页码和material查询出来对应png的文件名
	public PageData findContrastFname(PageData pd)throws Exception;
	
	//根据物料编码查询出所有的页码对照信息
	public List<PageData> findContrastFnameList(PageData pd)throws Exception;
	//根据物料编码查询出所有的页码对照信息
	public List<PageData> findContrastFnameList2(PageData pd)throws Exception;

	public void saveContrastMess2(PageData pContrast)throws Exception;
	
	//查找工艺视频的存放路径
	public PageData findVideoSavePath(PageData pd)throws Exception;
	//删除工艺视频的存放路径
	public void delVideoSavePath(PageData pd)throws Exception;
	
	//将上传视频的存放路径存放在数据库
	public void videoSave(PageData pd)throws Exception;
	
	//判断数据库是否已上传过工艺文件，数据库是否已有记录
	public PageData filesaveExist(PageData pd)throws Exception;
	
	//数据库插入工艺文件访问路径
	public void fileInsert(PageData pd)throws Exception;
	
	//数据库更新工艺文件访问路径
	public void fileUpdate(PageData pd)throws Exception;
	
	//判断是否已经有不为0的页码
	public PageData pageStationExist(PageData pd)throws Exception;
	
	//更新工位页码显示
	public void pageStationUpdate(PageData pd)throws Exception;
	
	//插入工位页码显示
	public void pageStationInsert(PageData pd)throws Exception;
	
	//根据产品查询出该产品对应的所有的图片
	public List<PageData> findPngsOfProduct(PageData pd)throws Exception;
}

