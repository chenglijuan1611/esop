package com.fh.entity.base;

import java.util.List;

import com.fh.entity.Page;


public class Product {
	private String PRODUCT_ID;
	private String PRODUCT_CODE;	
	private String PRODUCT_NAME;	
	private String PRODUCT_SPECIFICATIONS;	
	private String PRODUCT_SWHOURS;
	private String PRODUCT_BODYBARCODEONE;	
	private String PRODUCT_BODYBARCODETWO;	
	private String PRODUCTLINE_ID;
	private Productline productline;

	private String BODY_NUMBER;	
	private String SERIAL_START;
	private String SERIAL_END;
	
	
	
	public Productline getProductline() {
		return productline;
	}
	public void setProductline(Productline productline) {
		this.productline = productline;
	}
	private Page page;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}
	public String getPRODUCT_SPECIFICATIONS() {
		return PRODUCT_SPECIFICATIONS;
	}
	public void setPRODUCT_SPECIFICATIONS(String pRODUCT_SPECIFICATIONS) {
		PRODUCT_SPECIFICATIONS = pRODUCT_SPECIFICATIONS;
	}
	public String getPRODUCT_SWHOURS() {
		return PRODUCT_SWHOURS;
	}
	public void setPRODUCT_SWHOURS(String pRODUCT_SWHOURS) {
		PRODUCT_SWHOURS = pRODUCT_SWHOURS;
	}
	public String getPRODUCT_BODYBARCODEONE() {
		return PRODUCT_BODYBARCODEONE;
	}
	public void setPRODUCT_BODYBARCODEONE(String pRODUCT_BODYBARCODEONE) {
		PRODUCT_BODYBARCODEONE = pRODUCT_BODYBARCODEONE;
	}
	public String getPRODUCT_BODYBARCODETWO() {
		return PRODUCT_BODYBARCODETWO;
	}
	public void setPRODUCT_BODYBARCODETWO(String pRODUCT_BODYBARCODETWO) {
		PRODUCT_BODYBARCODETWO = pRODUCT_BODYBARCODETWO;
	}
	public String getPRODUCTLINE_ID() {
		return PRODUCTLINE_ID;
	}
	public void setPRODUCTLINE_ID(String pRODUCTLINE_ID) {
		PRODUCTLINE_ID = pRODUCTLINE_ID;
	}
	public String getBODY_NUMBER() {
		return BODY_NUMBER;
	}
	public void setBODY_NUMBER(String bODY_NUMBER) {
		BODY_NUMBER = bODY_NUMBER;
	}
	public String getSERIAL_START() {
		return SERIAL_START;
	}
	public void setSERIAL_START(String sERIAL_START) {
		SERIAL_START = sERIAL_START;
	}
	public String getSERIAL_END() {
		return SERIAL_END;
	}
	public void setSERIAL_END(String sERIAL_END) {
		SERIAL_END = sERIAL_END;
	}
	
}
