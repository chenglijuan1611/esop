package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PDFReader {
	public static void splitPDFFile(String respdfFile,String savepath, String str) {
        Document document = null;  
        PdfCopy copy = null;          
        try {  
            PdfReader reader = new PdfReader(respdfFile);            
            document = new Document(reader.getPageSize(1));  
            copy = new PdfCopy(document, new FileOutputStream(savepath));  
            document.open();
        	String[] s = str.split(",");
        	for(int i = 0;i<s.length;i++) {
        		document.newPage();   
                PdfImportedPage page = copy.getImportedPage(reader, Integer.parseInt(s[i]));  
                copy.addPage(page); 
		  	}
            document.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch(DocumentException e) {  
            e.printStackTrace();  
        }  
    }
	
	public static int getPdfPages(String filePath) {
		File file = new File(filePath);
		int pages = 0;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			PdfReader pdfReader = new PdfReader(fileInputStream);
			pages = pdfReader.getNumberOfPages();
			fileInputStream.close();
			pdfReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pages;
	}
	
	public static int getPdfPages(MultipartFile processFile) {
		int pages = 0;
		try {
			InputStream inputStream = processFile.getInputStream();
			PdfReader pdfReader = new PdfReader(inputStream);
			pages = pdfReader.getNumberOfPages();
			inputStream.close();
			pdfReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pages;
	}
	
	public static int getPdfPages(InputStream inputStream) {
		int pages = 0;
		try {
			PdfReader pdfReader = new PdfReader(inputStream);
			pages = pdfReader.getNumberOfPages();
			inputStream.close();
			pdfReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pages;
		
	}
	
}
