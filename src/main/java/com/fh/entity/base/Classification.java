package com.fh.entity.base;

import java.util.List;

/**
 * 
* 类名称：组织机构
* 类描述： 
* @author rhz
* 作者单位： 
* 联系方式：
* 修改时间：2015年12月16日
* @version 2.0
 */
public class Classification {

	private String CLASSIFICATION_CODE;			//编码
	private String CLASSIFICATION_NAME;			//名称
	private String CLASSIFICATION_RESERVE;			//备注
	private String CLASSIFICATION_ID;		//ID
	
	
	private String target;
	public String getCLASSIFICATION_CODE() {
		return CLASSIFICATION_CODE;
	}
	public void setCLASSIFICATION_CODE(String cLASSIFICATION_CODE) {
		CLASSIFICATION_CODE = cLASSIFICATION_CODE;
	}
	public String getCLASSIFICATION_NAME() {
		return CLASSIFICATION_NAME;
	}
	public void setCLASSIFICATION_NAME(String cLASSIFICATION_NAME) {
		CLASSIFICATION_NAME = cLASSIFICATION_NAME;
	}
	public String getCLASSIFICATION_RESERVE() {
		return CLASSIFICATION_RESERVE;
	}
	public void setCLASSIFICATION_RESERVE(String cLASSIFICATION_RESERVE) {
		CLASSIFICATION_RESERVE = cLASSIFICATION_RESERVE;
	}
	public String getCLASSIFICATION_ID() {
		return CLASSIFICATION_ID;
	}
	public void setCLASSIFICATION_ID(String cLASSIFICATION_ID) {
		CLASSIFICATION_ID = cLASSIFICATION_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Classification getClassification() {
		return classification;
	}
	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	public List<Classification> getSubClassification() {
		return subClassification;
	}
	public void setSubClassification(List<Classification> subClassification) {
		this.subClassification = subClassification;
	}
	public boolean isHasClassification() {
		return hasClassification;
	}
	public void setHasClassification(boolean hasClassification) {
		this.hasClassification = hasClassification;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	private Classification classification;
	private List<Classification> subClassification;
	private boolean hasClassification = false;
	private String treeurl;
	private String icon;
	
	
}