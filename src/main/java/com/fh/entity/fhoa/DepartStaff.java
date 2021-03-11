package com.fh.entity.fhoa;

import java.util.List;

/** 
 * 说明：员工树形 实体类
 * 创建人：rhz
 * 创建时间：2018-05-15
 */
public class DepartStaff{ 
	
	private String DEPARTSTAFF_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private DepartStaff departstaff;
	private List<DepartStaff> subDepartStaff;
	private boolean hasDepartStaff = false;
	private String treeurl;
	
	private String STAFF_ID;			//员工编号
	public String getFSTAFF_ID() {
		return STAFF_ID;
	}
	public void setFSTAFF_ID(String STAFF_ID) {
		this.STAFF_ID = STAFF_ID;
	}
	private String STAFF_NAME;			//员工姓名
	public String getFSTAFF_NAME() {
		return STAFF_NAME;
	}
	public void setFSTAFF_NAME(String STAFF_NAME) {
		this.STAFF_NAME = STAFF_NAME;
	}
	private String STAFF_NAME_EN;			//英文名字
	public String getFSTAFF_NAME_EN() {
		return STAFF_NAME_EN;
	}
	public void setFSTAFF_NAME_EN(String STAFF_NAME_EN) {
		this.STAFF_NAME_EN = STAFF_NAME_EN;
	}
	private String STAFF_BIANMA;			//员工编码
	public String getFSTAFF_BIANMA() {
		return STAFF_BIANMA;
	}
	public void setFSTAFF_BIANMA(String STAFF_BIANMA) {
		this.STAFF_BIANMA = STAFF_BIANMA;
	}
	private String STAFF_FUNCTIONS;			//员工职能
	public String getFSTAFF_FUNCTIONS() {
		return STAFF_FUNCTIONS;
	}
	public void setFSTAFF_FUNCTIONS(String STAFF_FUNCTIONS) {
		this.STAFF_FUNCTIONS = STAFF_FUNCTIONS;
	}
	private String STAFF_TEL;			//员工电话
	public String getFSTAFF_TEL() {
		return STAFF_TEL;
	}
	public void setFSTAFF_TEL(String STAFF_TEL) {
		this.STAFF_TEL = STAFF_TEL;
	}
	private String STAFF_EMAIL;			//员工邮箱
	public String getFSTAFF_EMAIL() {
		return STAFF_EMAIL;
	}
	public void setFSTAFF_EMAIL(String STAFF_EMAIL) {
		this.STAFF_EMAIL = STAFF_EMAIL;
	}
	private String STAFF_SEX;			//性别
	public String getFSTAFF_SEX() {
		return STAFF_SEX;
	}
	public void setFSTAFF_SEX(String STAFF_SEX) {
		this.STAFF_SEX = STAFF_SEX;
	}
	private String STAFF_BIRTHDAY;			//员工生日
	public String getFSTAFF_BIRTHDAY() {
		return STAFF_BIRTHDAY;
	}
	public void setFSTAFF_BIRTHDAY(String STAFF_BIRTHDAY) {
		this.STAFF_BIRTHDAY = STAFF_BIRTHDAY;
	}
	private String STAFF_NATION;			//民族
	public String getFSTAFF_NATION() {
		return STAFF_NATION;
	}
	public void setFSTAFF_NATION(String STAFF_NATION) {
		this.STAFF_NATION = STAFF_NATION;
	}
	private String STAFF_JOBTYPE;			//工作类别
	public String getFSTAFF_JOBTYPE() {
		return STAFF_JOBTYPE;
	}
	public void setFSTAFF_JOBTYPE(String STAFF_JOBTYPE) {
		this.STAFF_JOBTYPE = STAFF_JOBTYPE;
	}
	private String STAFF_JOBJOINTIME;			//入职时间
	public String getFSTAFF_JOBJOINTIME() {
		return STAFF_JOBJOINTIME;
	}
	public void setFSTAFF_JOBJOINTIME(String STAFF_JOBJOINTIME) {
		this.STAFF_JOBJOINTIME = STAFF_JOBJOINTIME;
	}
	private String STAFF_FADDRESS;			//籍贯
	public String getFSTAFF_FADDRESS() {
		return STAFF_FADDRESS;
	}
	public void setFSTAFF_FADDRESS(String STAFF_FADDRESS) {
		this.STAFF_FADDRESS = STAFF_FADDRESS;
	}
	private String STAFF_POLITICAL;			//政治面貌
	public String getFSTAFF_POLITICAL() {
		return STAFF_POLITICAL;
	}
	public void setFSTAFF_POLITICAL(String STAFF_POLITICAL) {
		this.STAFF_POLITICAL = STAFF_POLITICAL;
	}
	private String STAFF_PJOINTIME;			//工作时间
	public String getFSTAFF_PJOINTIME() {
		return STAFF_PJOINTIME;
	}
	public void setFSTAFF_PJOINTIME(String STAFF_PJOINTIME) {
		this.STAFF_PJOINTIME = STAFF_PJOINTIME;
	}
	private String STAFF_SFID;			//SFID
	public String getFSTAFF_SFID() {
		return STAFF_SFID;
	}
	public void setFSTAFF_SFID(String STAFF_SFID) {
		this.STAFF_SFID = STAFF_SFID;
	}
	private String STAFF_MARITAL;			//婚否
	public String getFSTAFF_MARITAL() {
		return STAFF_MARITAL;
	}
	public void setFSTAFF_MARITAL(String STAFF_MARITAL) {
		this.STAFF_MARITAL = STAFF_MARITAL;
	}
	private String STAFF_DJOINTIME;			//入职时间
	public String getFSTAFF_DJOINTIME() {
		return STAFF_DJOINTIME;
	}
	public void setFSTAFF_DJOINTIME(String STAFF_DJOINTIME) {
		this.STAFF_DJOINTIME = STAFF_DJOINTIME;
	}
	private String STAFF_POST;			//职位
	public String getFSTAFF_POST() {
		return STAFF_POST;
	}
	public void setFSTAFF_POST(String STAFF_POST) {
		this.STAFF_POST = STAFF_POST;
	}
	private String STAFF_POJOINTIME;			//上岗时间
	public String getFSTAFF_POJOINTIME() {
		return STAFF_POJOINTIME;
	}
	public void setFSTAFF_POJOINTIME(String STAFF_POJOINTIME) {
		this.STAFF_POJOINTIME = STAFF_POJOINTIME;
	}
	private String STAFF_EDUCATION;			//最高学历
	public String getFSTAFF_EDUCATION() {
		return STAFF_EDUCATION;
	}
	public void setFSTAFF_EDUCATION(String STAFF_EDUCATION) {
		this.STAFF_EDUCATION = STAFF_EDUCATION;
	}
	private String STAFF_SCHOOL;			//毕业学校
	public String getFSTAFF_SCHOOL() {
		return STAFF_SCHOOL;
	}
	public void setFSTAFF_SCHOOL(String STAFF_SCHOOL) {
		this.STAFF_SCHOOL = STAFF_SCHOOL;
	}
	private String STAFF_MAJOR;			//专业
	public String getFSTAFF_MAJOR() {
		return STAFF_MAJOR;
	}
	public void setFSTAFF_MAJOR(String STAFF_MAJOR) {
		this.STAFF_MAJOR = STAFF_MAJOR;
	}
	private String STAFF_FTITLE;			//职业职称
	public String getFSTAFF_FTITLE() {
		return STAFF_FTITLE;
	}
	public void setFSTAFF_FTITLE(String STAFF_FTITLE) {
		this.STAFF_FTITLE = STAFF_FTITLE;
	}
	private String STAFF_CERTIFICATE;			//资格证书
	public String getFSTAFF_CERTIFICATE() {
		return STAFF_CERTIFICATE;
	}
	public void setFSTAFF_CERTIFICATE(String STAFF_CERTIFICATE) {
		this.STAFF_CERTIFICATE = STAFF_CERTIFICATE;
	}
	private int STAFF_CONTRACTLENGTH;				//合同时长
	public int getFSTAFF_CONTRACTLENGTH() {
		return STAFF_CONTRACTLENGTH;
	}
	public void setFSTAFF_CONTRACTLENGTH(int STAFF_CONTRACTLENGTH) {
		this.STAFF_CONTRACTLENGTH = STAFF_CONTRACTLENGTH;
	}
	private String STAFF_CSTARTTIME;			//签订日期
	public String getFSTAFF_CSTARTTIME() {
		return STAFF_CSTARTTIME;
	}
	public void setFSTAFF_CSTARTTIME(String STAFF_CSTARTTIME) {
		this.STAFF_CSTARTTIME = STAFF_CSTARTTIME;
	}
	private String STAFF_CENDTIME;			//终止日期
	public String getFSTAFF_CENDTIME() {
		return STAFF_CENDTIME;
	}
	public void setFSTAFF_CENDTIME(String STAFF_CENDTIME) {
		this.STAFF_CENDTIME = STAFF_CENDTIME;
	}
	private String STAFF_ADDRESS;			//现住址
	public String getFSTAFF_ADDRESS() {
		return STAFF_ADDRESS;
	}
	public void setFSTAFF_ADDRESS(String STAFF_ADDRESS) {
		this.STAFF_ADDRESS = STAFF_ADDRESS;
	}
	private String DEPART_NAME;			//部门名称
	public String getFDEPART_NAME() {
		return DEPART_NAME;
	}
	public void setFDEPART_NAME(String DEPART_NAME) {
		this.DEPART_NAME = DEPART_NAME;
	}
	private String DEPART_NAME_EN;			//英文名称
	public String getFDEPART_NAME_EN() {
		return DEPART_NAME_EN;
	}
	public void setFDEPART_NAME_EN(String DEPART_NAME_EN) {
		this.DEPART_NAME_EN = DEPART_NAME_EN;
	}
	private String DEPART_BIANMA;			//部门编号
	public String getFDEPART_BIANMA() {
		return DEPART_BIANMA;
	}
	public void setFDEPART_BIANMA(String DEPART_BIANMA) {
		this.DEPART_BIANMA = DEPART_BIANMA;
	}
	private String DEPART_HEADMAN;			//负责人
	public String getFDEPART_HEADMAN() {
		return DEPART_HEADMAN;
	}
	public void setFDEPART_HEADMAN(String DEPART_HEADMAN) {
		this.DEPART_HEADMAN = DEPART_HEADMAN;
	}
	private String DEPART_ADDRESS;			//部门地址
	public String getFDEPART_ADDRESS() {
		return DEPART_ADDRESS;
	}
	public void setFDEPART_ADDRESS(String DEPART_ADDRESS) {
		this.DEPART_ADDRESS = DEPART_ADDRESS;
	}
	private String DEPART_FUNCTIONS;			//部门职能
	public String getFDEPART_FUNCTIONS() {
		return DEPART_FUNCTIONS;
	}
	public void setFDEPART_FUNCTIONS(String DEPART_FUNCTIONS) {
		this.DEPART_FUNCTIONS = DEPART_FUNCTIONS;
	}
	private String DEPART_STARTTIME;			//成立日期
	public String getFDEPART_STARTTIME() {
		return DEPART_STARTTIME;
	}
	public void setFDEPART_STARTTIME(String DEPART_STARTTIME) {
		this.DEPART_STARTTIME = DEPART_STARTTIME;
	}

	public String getDEPARTSTAFF_ID() {
		return DEPARTSTAFF_ID;
	}
	public void setDEPARTSTAFF_ID(String DEPARTSTAFF_ID) {
		this.DEPARTSTAFF_ID = DEPARTSTAFF_ID;
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
	public DepartStaff getDepartStaff() {
		return departstaff;
	}
	public void setDepartStaff(DepartStaff departstaff) {
		this.departstaff = departstaff;
	}
	public List<DepartStaff> getSubDepartStaff() {
		return subDepartStaff;
	}
	public void setSubDepartStaff(List<DepartStaff> subDepartStaff) {
		this.subDepartStaff = subDepartStaff;
	}
	public boolean isHasDepartStaff() {
		return hasDepartStaff;
	}
	public void setHasDepartStaff(boolean hasDepartStaff) {
		this.hasDepartStaff = hasDepartStaff;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
