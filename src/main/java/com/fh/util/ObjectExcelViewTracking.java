package com.fh.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;
/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* @author rhz
* @version 1.0
 */
public class ObjectExcelViewTracking extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		Row row;
		CellRangeAddress region;		
		Cell cell1;
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
				
		sheet = workbook.createSheet("sheet1");
		/**
		 * ict
		 */
			HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
			HSSFCellStyle headerStyle1 = workbook.createCellStyle(); //标题样式			
		 	headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		 	headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );			
		 	HSSFPalette customPalette = workbook.getCustomPalette();
		 	customPalette.setColorAtIndex(IndexedColors.LIGHT_GREEN.getIndex(), (byte) 231, (byte) 238, (byte) 248);						
			headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			
			headerStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			headerStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			HSSFFont headerFont = workbook.createFont();	//标题字体
			headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerFont.setFontHeightInPoints((short)11);
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			HSSFCellStyle  contentStyle = workbook.createCellStyle(); //内容样式
			contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		int varCount;
		
		String varstr;
		short width = 20,height=25*20;
		List<String>icttitles = (List<String>) model.get("icttitles");
		if (icttitles !=null &&! icttitles.isEmpty()) {
			int ictlen = icttitles.size();
			 row = sheet.createRow((short) 0);
			 region = new CellRangeAddress(0, // first row
			        0, // last row
			        0, // first column
			        ictlen-1 // last column
			);				
			sheet.addMergedRegion(region);		
		/*	 headerStyle = workbook.createCellStyle(); //标题样式
			 headerStyle1 = workbook.createCellStyle(); //标题样式*/			
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("ICT测试数据");
			cell1.setCellStyle(headerStyle1);
						
		/*	headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );			
			 customPalette = workbook.getCustomPalette();
			 customPalette.setColorAtIndex(IndexedColors.LIGHT_GREEN.getIndex(), (byte) 231, (byte) 238, (byte) 248);
			*/			
			/*headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);			
			headerStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			headerStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);*/
			/* headerFont = workbook.createFont();	//标题字体
			headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerFont.setFontHeightInPoints((short)11);*/
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);			
			//short width = 20,height=25*20;
			sheet.setDefaultColumnWidth(width);
			for(int i=0; i<ictlen; i++){ //设置标题
				String title = icttitles.get(i);
			
				cell = getCell(sheet, 1, i);
				cell.setCellStyle(headerStyle);
				setText(cell,title);
			}
			
			sheet.getRow(0).setHeight(height);
			sheet.getRow(1).setHeight(height);
			
			/*contentStyle = workbook.createCellStyle(); //内容样式
			contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);*/
			
			List<PageData> ictvarList = (List<PageData>) model.get("ictvarList");
			 varCount = ictvarList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = ictvarList.get(i);
				for(int j=0;j<ictlen;j++){
					 varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+2, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr);
				}				
			}
		}		
		
		/**
		 * aoi
		 */
		List<String>  aoititles = (List<String>) model.get("aoititles");		
		int rowNum=sheet.getLastRowNum();
		System.out.println("=======aoi="+rowNum);
		if (aoititles !=null &&! aoititles.isEmpty()) {		
			if (rowNum==0) {
				rowNum=0;
			}else {
				rowNum=rowNum+6;	
			}
			 		
			int aoilen = aoititles.size();			
			 row = sheet.createRow((short) rowNum);
			 region = new CellRangeAddress(rowNum, // first row
					 rowNum, // last row
			        0, // first column
			        aoilen-1 // last column
			);					
			sheet.addMergedRegion(region);					
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("AOI测试数据");
			cell1.setCellStyle(headerStyle1);				
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			 width = 20;height=25*20;
			sheet.setDefaultColumnWidth(width);
			sheet.getRow(rowNum).setHeight(height);			
			rowNum=sheet.getLastRowNum()+1;
			for(int i=0; i<aoilen; i++){ //设置标题
				String aoititle = aoititles.get(i);
				
				cell = getCell(sheet, rowNum, i);
				cell.setCellStyle(headerStyle);
				setText(cell,aoititle);
			}		
			 rowNum=(sheet.getLastRowNum());
			sheet.getRow(rowNum).setHeight(height);			
			
			List<PageData> aoivarList = (List<PageData>) model.get("aoivarList");
			varCount = aoivarList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = aoivarList.get(i);
				for(int j=0;j<aoilen;j++){
					String varstr1 = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+1+rowNum, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr1);
				}				
			}
		}
		
		/**
		 * caoi
		 */
		List<String> caoititles = (List<String>) model.get("caoititles");
		rowNum=sheet.getLastRowNum();
		if (caoititles !=null &&! caoititles.isEmpty()) {
			if (rowNum==0) {
				rowNum=0;
			}else {
				rowNum=rowNum+6;	
			}
			//rowNum=sheet.getLastRowNum()+6;			
			int caoilen = caoititles.size();			
			 row = sheet.createRow((short) rowNum);
			 region = new CellRangeAddress(rowNum, // first row
					 rowNum, // last row
			        0, // first column
			        caoilen-1 // last column
			);					
			sheet.addMergedRegion(region);					
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("CAOI测试数据");
			cell1.setCellStyle(headerStyle1);	
			
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			 width = 20;height=25*20;
			sheet.setDefaultColumnWidth(width);
			sheet.getRow(rowNum).setHeight(height);
			
			rowNum=sheet.getLastRowNum()+1;
			for(int i=0; i<caoilen; i++){ //设置标题
				String caoititle = caoititles.get(i);				
				cell = getCell(sheet, rowNum, i);
				cell.setCellStyle(headerStyle);
				setText(cell,caoititle);
			}		
			 rowNum=(sheet.getLastRowNum());
			sheet.getRow(rowNum).setHeight(height);		
			
			List<PageData> caoivarList = (List<PageData>) model.get("caoivarList");
			varCount = caoivarList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = caoivarList.get(i);
				for(int j=0;j<caoilen;j++){
					String varstr1 = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+1+rowNum, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr1);
				}			
			}
			
		}
		 		
		/**
		 * fct
		 */
		
		List<String> fcttitles = (List<String>) model.get("fcttitles");
		if (fcttitles !=null &&! fcttitles.isEmpty()) {
			int fctlen = fcttitles.size();
			rowNum=sheet.getLastRowNum();
			if (rowNum==0) {
				rowNum=0;
			}else {
				rowNum=rowNum+6;	
			}
		//	rowNum=sheet.getLastRowNum()+6;
			 row = sheet.createRow((short) rowNum);
			 region = new CellRangeAddress(rowNum, // first row
					 rowNum, // last row
			        0, // first column
			        fctlen-1 // last column
			);					
			sheet.addMergedRegion(region);									
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("FCT测试数据");
			cell1.setCellStyle(headerStyle1);			
			/*headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );*/					
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			 width = 20;height=25*20;
			sheet.setDefaultColumnWidth(width);
			sheet.getRow(rowNum).setHeight(height);
			rowNum=sheet.getLastRowNum()+1;
			for(int i=0; i<fctlen; i++){ //设置标题
				String fcttitle = fcttitles.get(i);				
				cell = getCell(sheet, rowNum, i);
				cell.setCellStyle(headerStyle);
				setText(cell,fcttitle);
			}
			 rowNum=(sheet.getLastRowNum());
			
			sheet.getRow(rowNum).setHeight(height);
		
			List<PageData> fctvarList = (List<PageData>) model.get("fctvarList");
			varCount = fctvarList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = fctvarList.get(i);
				for(int j=0;j<fctlen;j++){
					 varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+1+rowNum, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr);
				}				
			}
		}		

		/**
		 * jzfct
		 */
		
		List<String> titles = (List<String>) model.get("titles");
		if (titles !=null &&! titles.isEmpty()) {
			int len = titles.size();
			rowNum=sheet.getLastRowNum();
			if (rowNum==0) {
				rowNum=0;
			}else {
				rowNum=rowNum+6;	
			}
		//	rowNum=sheet.getLastRowNum()+6;
			 row = sheet.createRow((short) rowNum);
			 region = new CellRangeAddress(rowNum, // first row
					 rowNum, // last row
			        0, // first column
			        len-1 // last column
			);
					
			sheet.addMergedRegion(region);
							
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("机装FCT测试数据");
			cell1.setCellStyle(headerStyle1);
					
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			 width = 20;height=25*20;
			sheet.setDefaultColumnWidth(width);
			sheet.getRow(rowNum).setHeight(height);
			rowNum=sheet.getLastRowNum()+1;
			for(int i=0; i<len; i++){ //设置标题
				String title = titles.get(i);
				
				cell = getCell(sheet, rowNum, i);
				cell.setCellStyle(headerStyle);
				setText(cell,title);
			}
			 rowNum=(sheet.getLastRowNum());
			sheet.getRow(rowNum).setHeight(height);				
			
			List<PageData> varList = (List<PageData>) model.get("varList");
			varCount = varList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = varList.get(i);
				for(int j=0;j<len;j++){
					 varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+1+rowNum, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr);
				}				
			}
		}
		
		/**
		 * 包装扫描数据
		 */
		
		List<String> packtitles = (List<String>) model.get("packtitles");
		if (packtitles !=null &&! packtitles.isEmpty()) {
			int packlen = packtitles.size();
			rowNum=sheet.getLastRowNum();
			if (rowNum==0) {
				rowNum=0;
			}else {
				rowNum=rowNum+6;	
			}
		//	rowNum=sheet.getLastRowNum()+6;
			 row = sheet.createRow((short) rowNum);
			 region = new CellRangeAddress(rowNum, // first row
					 rowNum, // last row
			        0, // first column
			        packlen-1 // last column
			);					
			sheet.addMergedRegion(region);									
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("包装扫描数据");
			cell1.setCellStyle(headerStyle1);			
			/*headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );*/					
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			 width = 20;height=25*20;
			sheet.setDefaultColumnWidth(width);
			sheet.getRow(rowNum).setHeight(height);
			rowNum=sheet.getLastRowNum()+1;
			for(int i=0; i<packlen; i++){ //设置标题
				String packtitle = packtitles.get(i);				
				cell = getCell(sheet, rowNum, i);
				cell.setCellStyle(headerStyle);
				setText(cell,packtitle);
			}
			 rowNum=(sheet.getLastRowNum());
			
			sheet.getRow(rowNum).setHeight(height);
		
			List<PageData> packvarList = (List<PageData>) model.get("packvarList");
			varCount = packvarList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = packvarList.get(i);
				for(int j=0;j<packlen;j++){
					 varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+1+rowNum, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr);
				}				
			}
		}		

		/**
		 * 维修登记数据
		 */
		
		List<String> repairRecordtitles = (List<String>) model.get("repairRecordtitles");
		if (repairRecordtitles !=null &&! repairRecordtitles.isEmpty()) {
			int repairLen = repairRecordtitles.size();
			rowNum=sheet.getLastRowNum();
			if (rowNum==0) {
				rowNum=0;
			}else {
				rowNum=rowNum+6;	
			}
		//	rowNum=sheet.getLastRowNum()+6;
			 row = sheet.createRow((short) rowNum);
			 region = new CellRangeAddress(rowNum, // first row
					 rowNum, // last row
			        0, // first column
			        repairLen-1 // last column
			);					
			sheet.addMergedRegion(region);									
			cell1 = row.createCell((short) 0);
			cell1.setCellValue("维修登记数据");
			cell1.setCellStyle(headerStyle1);			
			/*headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );*/					
			headerStyle.setFont(headerFont);
			headerStyle1.setFont(headerFont);
			 width = 20;height=25*20;
			sheet.setDefaultColumnWidth(width);
			sheet.getRow(rowNum).setHeight(height);
			rowNum=sheet.getLastRowNum()+1;
			for(int i=0; i<repairLen; i++){ //设置标题
				String repairRecordtitle = repairRecordtitles.get(i);				
				cell = getCell(sheet, rowNum, i);
				cell.setCellStyle(headerStyle);
				setText(cell,repairRecordtitle);
			}
			 rowNum=(sheet.getLastRowNum());
			
			sheet.getRow(rowNum).setHeight(height);
		
			List<PageData> repairRecordList = (List<PageData>) model.get("repairRecordList");
			varCount = repairRecordList.size();
			for(int i=0; i<varCount; i++){
				PageData vpd = repairRecordList.get(i);
				for(int j=0;j<repairLen;j++){
					 varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					cell = getCell(sheet, i+1+rowNum, j);
					cell.setCellStyle(contentStyle);
					setText(cell,varstr);
				}				
			}
		}		

		
		
		
	}
}
