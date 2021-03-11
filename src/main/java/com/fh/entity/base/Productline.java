package com.fh.entity.base;

import com.fh.entity.Page;

public class Productline {
	private String PRODUCTLINE_ID;
	private String CODE;	
	private String NAME;	
	private String CATEGORY;	
	private String BZ;
	private Page page;
	public String getPRODUCTLINE_ID() {
		return PRODUCTLINE_ID;
	}
	public void setPRODUCTLINE_ID(String pRODUCTLINE_ID) {
		PRODUCTLINE_ID = pRODUCTLINE_ID;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
}
