package com.fh.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.itextpdf.text.pdf.PdfReader;

public class Pdf2Image {
	public static void main(String[] args) {
		//pdf2Image("C:\\3316273C00.pdf", "C:\\processFile", 131);
	}
	/***
	 * PDF文件转PNG图片，全部页数
	 * 
	 * @param PdfFilePath pdf完整路径
	 * @param imgFilePath 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
	public static String pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) throws Exception{
		File file = new File(PdfFilePath);//获取PDF文件
		PDDocument pdDocument = null;
		PdfReader reader = null;
		String timeStamp = String.valueOf(System.currentTimeMillis());//获取当前的一个时间戳
		try {
			//如果存放图片的路径不存在就创建
			if (createDirectory(dstImgFolder)) {
				pdDocument = PDDocument.load(file);//加载PDF文件
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				reader = new PdfReader(PdfFilePath);
				int pages = reader.getNumberOfPages();//获取PDF文件的总页数
				StringBuffer imgFilePath = null;
				for (int i = 0; i < pages; i++) {//遍历总的PDF的页码
					String imgFilePathPrefix = dstImgFolder + File.separator;//在定义的图片存放路径后面加上“\”
					//定义图片的完整路径
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append("R");
					imgFilePath.append(timeStamp);
					imgFilePath.append(".png");
					File dstFile = new File(imgFilePath.toString());//创建包含图片的文件
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					ImageIO.write(image, "png", dstFile);
					
				}
				reader.close();
				pdDocument.close();
			} else {
				System.out.println("PDF文档转PNG图片失败");
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			pdDocument.close();
			reader.close();
		}
		return timeStamp;
	}
 
	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}


	

}
