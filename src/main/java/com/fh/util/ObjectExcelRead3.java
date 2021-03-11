package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.examples.ReadWriteWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * 从EXCEL导入到数据库 其中判断单元格是否合并
 *rhz
 * 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead3 {

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号
			/*System.out.println("======================="+rowNum);*/
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			for (int i = startrow; i < rowNum; i++) {					//行循环开始
							
				PageData varpd = new PageData();
				HSSFRow row = sheet.getRow(i); 							//行
				if (row==null||isBlankRow(row)) {
					continue;
				}
				 
				 for(Cell c : row) {
		              
		               String returnStr = "";
		                boolean isMerge = isMergeRegion(sheet, i, c.getColumnIndex());
		                //判断是否具有合并单元格
		                if(isMerge) {
		                	String rs = getMergeRegionValue(sheet, row.getRowNum(), c.getColumnIndex(),wb);
//		                    System.out.print(rs + "------ ");
		                	 String super1=	superOrSubScript2003(c, wb);
		                	/* System.out.println("==============="+super1);*/
		                    returnStr = rs;
		                    varpd.put("var"+ c.getColumnIndex(), returnStr);
		                }else {
//		                    System.out.print(c.getRichStringCellValue()+"++++ ");
		                    returnStr = superOrSubScript2003(c, wb);
		                    varpd.put("var"+ c.getColumnIndex(), returnStr);
		                }
		                
				 }
				
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return varList;
	}
	
	/**
	 * 去excel中的空格
	 * @param row
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isBlankRow(HSSFRow row){
        if(row == null) return true;
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
            HSSFCell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                //case Cell.CELL_TYPE_BLANK:
                //    break;
                default:
                    break;
                }
                 
                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
         
        return result;
    }
	/**
	 * 判断指定的单元格是否是合并单元格
	 * @param sheet
	 * @param row
	 * @param col
	 * @return
	 */
	private static  boolean isMergeRegion(HSSFSheet sheet,int row,int col) {
		int sheetMergeCount=sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range=sheet.getMergedRegion(i);
		//	System.out.println("============"+range);
			int firstColumn=range.getFirstColumn();
			int lastColumn=range.getLastColumn();
			int firstRow =range.getFirstRow();
			int lastRow=range.getLastRow();
			if (row>=firstRow&&row<=lastRow) {
				if (col>=firstColumn&&col<=lastColumn) {
					return true;
				}
			}
		}
		return false;
		
	}
	/**
	 * 获取合并单元格的值
	 * @param sheet
	 * @param row
	 * @param col
	 * @param book
	 * @return
	 */
	public static String  getMergeRegionValue(HSSFSheet sheet,int row,int col,HSSFWorkbook book) {
		int sheetMergeCount=sheet.getNumMergedRegions();
		int count=0;
		int num=0;
		int num1=0;
		HSSFCell cell=null;
		String value=null;
		for (int i = 0; i <sheetMergeCount; i++) {			
			CellRangeAddress range=sheet.getMergedRegion(i);
			//	System.out.println("============"+range);
				int firstColumn=range.getFirstColumn();
				int lastColumn=range.getLastColumn();
				int firstRow =range.getFirstRow();
				int lastRow=range.getLastRow();		
				
				if (row>=firstRow&&row<=lastRow) {
					if (col>=firstColumn&&col<=lastColumn) {
						Row row2=sheet.getRow(firstRow);
						cell=(HSSFCell) row2.getCell(firstColumn);
/*						num=num+lastColumn-firstColumn+1;
						System.out.println("num--------"+num);	*/					
						return superOrSubScript2003(cell, book); 
					}
					
				}				
		}
		return null;
	}
	
	
	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	public static  String getCellValue(Cell cell){
		
		String cellValue = null;
		if (null != cell) {
			switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
			case 0:
				cellValue = String.valueOf((int) cell.getNumericCellValue());
				break;
			case 1:
				cellValue = cell.getStringCellValue();
				break;
			case 2:
				cellValue = cell.getNumericCellValue() + "";
				// cellValue = String.valueOf(cell.getDateCellValue());
				break;
			case 3:
				cellValue = "";
				break;
			case 4:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case 5:
				cellValue = String.valueOf(cell.getErrorCellValue());
				break;
			}
		} else {
			cellValue = "";
		}
		
		
		return cellValue;
		
	}
	
	
	 /**
     *  判断上下标
     *  2003版
     *  cell 表示传入的单元格对象 book 表示传入的当前工作薄对象 
     **/
    public static String superOrSubScript2003(Cell cell, Workbook book){

              HSSFWorkbook workbook = null;
              HSSFFont font = null; 
              HSSFRichTextString rts = null;
              HSSFCellStyle style = null;
              int fromIndex = 0;
              int toIndex = 0;
              String value = "";
              
              //处理上下标
              workbook = (HSSFWorkbook)book;

              //判断当前单元格的内容是否为数字类型，如果是转换成字符串型
              if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){               	  
/*            	  cell.setCellValue((cell.getNumericCellValue()+"").substring(0, (cell.getNumericCellValue()+"").indexOf(".")));
*/             		cell.setCellValue(String.valueOf((int) cell.getNumericCellValue()));
            	  }
              //获取单元格中的数据
              rts = (HSSFRichTextString) cell.getRichStringCellValue();
              //获取每个单元格数据的style属性
              style = (HSSFCellStyle) cell.getCellStyle();
              font = style.getFont(workbook);             
              if(rts.numFormattingRuns() > 0){
            	  for(int k = 0; k < rts.numFormattingRuns(); k++) {
            		  toIndex = rts.getIndexOfFormattingRun(k);
            		  String temp = rts.toString().substring(fromIndex, toIndex);
            		  System.out.println("\tSubstring [" + temp + "]");
               //判断上标
            	if(font.getTypeOffset() == HSSFFont.SS_SUPER){
            		temp = "<sup>" +temp+"</sup>";
                    System.out.println("\t______________发现上标");
                 }

                //判断下标
            	if(font.getTypeOffset() == HSSFFont.SS_SUB){
                    temp = "<sub>" +temp+"</sub>";
                    System.out.println("\t______________发现下标");
            	}
            	value += temp;
            	if(!value.equals("")){
                    font = workbook.getFontAt(rts.getFontOfFormattingRun(k));
                    }
            	fromIndex = toIndex;
            	}
            	  toIndex = rts.length();
            	  String temp1 = rts.toString().substring(fromIndex, toIndex);
            	  System.out.println("\tSubstring [" + temp1 + "]");
            	  if(font.getTypeOffset() == HSSFFont.SS_SUPER){
            		  temp1 = "<sup>" +temp1+"</sup>";
            		  System.out.println("\t______________发现上标");
            		  }

			       if(font.getTypeOffset() == HSSFFont.SS_SUB){
			        	temp1 = "<sub>" +temp1+"</sub>";
			        	System.out.println("\t______________发现下标");			
			        }			
			        value += temp1;			
			        return value;
              }
              return cell.getRichStringCellValue().getString();
    }

		
	
	
	
	
	
	
}
