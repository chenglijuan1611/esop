package com.fh.entity;

import java.util.List;

/** 
 * 说明：员工树形 实体类
 * 创建人：rhz
 * 创建时间：2018-05-14
 */
public class StaffDepart{ 
	
	private String STAFFDEPART_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private StaffDepart staffdepart;
	private List<StaffDepart> subStaffDepart;
	private boolean hasStaffDepart = false;
	private String treeurl;
	
	private String STAFF_ID;			//员工编号
	public String getFSTAFF_ID() {
		return STAFF_ID;
	}
	public void setFSTAFF_ID(String STAFF_ID) {
		this.STAFF_ID = STAFF_ID;
	}
	private String STAFFNAME;			//员工姓名
	public String getFSTAFFNAME() {
		return STAFFNAME;
	}
	public void setFSTAFFNAME(String STAFFNAME) {
		this.STAFFNAME = STAFFNAME;
	}
	private String NAME_EN;			//NAME_EN
	public String getFNAME_EN() {
		return NAME_EN;
	}
	public void setFNAME_EN(String NAME_EN) {
		this.NAME_EN = NAME_EN;
	}
	private String BIANMA;			//BIANMA
	public String getFBIANMA() {
		return BIANMA;
	}
	public void setFBIANMA(String BIANMA) {
		this.BIANMA = BIANMA;
	}
	private String DEPARTMENT_ID;			//DEPARTMENT_ID
	public String getFDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setFDEPARTMENT_ID(String DEPARTMENT_ID) {
		this.DEPARTMENT_ID = DEPARTMENT_ID;
	}
	private String FUNCTIONS;			//FUNCTIONS
	public String getFFUNCTIONS() {
		return FUNCTIONS;
	}
	public void setFFUNCTIONS(String FUNCTIONS) {
		this.FUNCTIONS = FUNCTIONS;
	}
	private String TEL;			//TEL
	public String getFTEL() {
		return TEL;
	}
	public void setFTEL(String TEL) {
		this.TEL = TEL;
	}
	private String EMAIL;			//EMAIL
	public String getFEMAIL() {
		return EMAIL;
	}
	public void setFEMAIL(String EMAIL) {
		this.EMAIL = EMAIL;
	}
	private String SEX;			//SEX
	public String getFSEX() {
		return SEX;
	}
	public void setFSEX(String SEX) {
		this.SEX = SEX;
	}
	private String BIRTHDAY;			//BIRTHDAY
	public String getFBIRTHDAY() {
		return BIRTHDAY;
	}
	public void setFBIRTHDAY(String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}
	private String NATION;			//NATION
	public String getFNATION() {
		return NATION;
	}
	public void setFNATION(String NATION) {
		this.NATION = NATION;
	}
	private String JOBTYPE;			//JOBTYPE
	public String getFJOBTYPE() {
		return JOBTYPE;
	}
	public void setFJOBTYPE(String JOBTYPE) {
		this.JOBTYPE = JOBTYPE;
	}
	private String JOBJOINTIME;			//JOBJOINTIME
	public String getFJOBJOINTIME() {
		return JOBJOINTIME;
	}
	public void setFJOBJOINTIME(String JOBJOINTIME) {
		this.JOBJOINTIME = JOBJOINTIME;
	}
	private String FADDRESS;			//FADDRESS
	public String getFFADDRESS() {
		return FADDRESS;
	}
	public void setFFADDRESS(String FADDRESS) {
		this.FADDRESS = FADDRESS;
	}
	private String POLITICAL;			//POLITICAL
	public String getFPOLITICAL() {
		return POLITICAL;
	}
	public void setFPOLITICAL(String POLITICAL) {
		this.POLITICAL = POLITICAL;
	}
	private String PJOINTIME;			//PJOINTIME
	public String getFPJOINTIME() {
		return PJOINTIME;
	}
	public void setFPJOINTIME(String PJOINTIME) {
		this.PJOINTIME = PJOINTIME;
	}
	private String SFID;			//SFID
	public String getFSFID() {
		return SFID;
	}
	public void setFSFID(String SFID) {
		this.SFID = SFID;
	}
	private String MARITAL;			//MARITAL
	public String getFMARITAL() {
		return MARITAL;
	}
	public void setFMARITAL(String MARITAL) {
		this.MARITAL = MARITAL;
	}
	private String DJOINTIME;			//DJOINTIME
	public String getFDJOINTIME() {
		return DJOINTIME;
	}
	public void setFDJOINTIME(String DJOINTIME) {
		this.DJOINTIME = DJOINTIME;
	}
	private String POST;			//POST
	public String getFPOST() {
		return POST;
	}
	public void setFPOST(String POST) {
		this.POST = POST;
	}
	private String POJOINTIME;			//POJOINTIME
	public String getFPOJOINTIME() {
		return POJOINTIME;
	}
	public void setFPOJOINTIME(String POJOINTIME) {
		this.POJOINTIME = POJOINTIME;
	}
	private String EDUCATION;			//EDUCATION
	public String getFEDUCATION() {
		return EDUCATION;
	}
	public void setFEDUCATION(String EDUCATION) {
		this.EDUCATION = EDUCATION;
	}
	private String SCHOOL;			//SCHOOL
	public String getFSCHOOL() {
		return SCHOOL;
	}
	public void setFSCHOOL(String SCHOOL) {
		this.SCHOOL = SCHOOL;
	}
	private String MAJOR;			//MAJOR
	public String getFMAJOR() {
		return MAJOR;
	}
	public void setFMAJOR(String MAJOR) {
		this.MAJOR = MAJOR;
	}
	private String FTITLE;			//FTITLE
	public String getFFTITLE() {
		return FTITLE;
	}
	public void setFFTITLE(String FTITLE) {
		this.FTITLE = FTITLE;
	}
	private String CERTIFICATE;			//CERTIFICATE
	public String getFCERTIFICATE() {
		return CERTIFICATE;
	}
	public void setFCERTIFICATE(String CERTIFICATE) {
		this.CERTIFICATE = CERTIFICATE;
	}
	private int CONTRACTLENGTH;				//CONTRACTLENGTH
	public int getFCONTRACTLENGTH() {
		return CONTRACTLENGTH;
	}
	public void setFCONTRACTLENGTH(int CONTRACTLENGTH) {
		this.CONTRACTLENGTH = CONTRACTLENGTH;
	}
	private String CSTARTTIME;			//CSTARTTIME
	public String getFCSTARTTIME() {
		return CSTARTTIME;
	}
	public void setFCSTARTTIME(String CSTARTTIME) {
		this.CSTARTTIME = CSTARTTIME;
	}
	private String CENDTIME;			//CENDTIME
	public String getFCENDTIME() {
		return CENDTIME;
	}
	public void setFCENDTIME(String CENDTIME) {
		this.CENDTIME = CENDTIME;
	}
	private String ADDRESS;			//ADDRESS
	public String getFADDRESS() {
		return ADDRESS;
	}
	public void setFADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}
	private String USER_ID;			//USER_ID
	public String getFUSER_ID() {
		return USER_ID;
	}
	public void setFUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}
	private String BZ;			//BZ
	public String getFBZ() {
		return BZ;
	}
	public void setFBZ(String BZ) {
		this.BZ = BZ;
	}

	public String getSTAFFDEPART_ID() {
		return STAFFDEPART_ID;
	}
	public void setSTAFFDEPART_ID(String STAFFDEPART_ID) {
		this.STAFFDEPART_ID = STAFFDEPART_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public StaffDepart getStaffDepart() {
		return staffdepart;
	}
	public void setStaffDepart(StaffDepart staffdepart) {
		this.staffdepart = staffdepart;
	}
	public List<StaffDepart> getSubStaffDepart() {
		return subStaffDepart;
	}
	public void setSubStaffDepart(List<StaffDepart> subStaffDepart) {
		this.subStaffDepart = subStaffDepart;
	}
	public boolean isHasStaffDepart() {
		return hasStaffDepart;
	}
	public void setHasStaffDepart(boolean hasStaffDepart) {
		this.hasStaffDepart = hasStaffDepart;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
