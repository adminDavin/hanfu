
package com.hanfu.activity.center.manual.model;

import java.io.Serializable;

public class Evaluate implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8591891341984149827L;

    private String evaluateContent;
    private String evaluateType;
    private Integer evaluateTemplateId;
    private String score;
    private String templateContent;
    private String evaluateWeight;
    private String remarks;
    private char zimu;
    private boolean flag;

    //	private Boolean isRecord;
    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public Integer getEvaluateTemplateId() {
        return evaluateTemplateId;
    }

    public void setEvaluateTemplateId(Integer evaluateTemplateId) {
        this.evaluateTemplateId = evaluateTemplateId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public char getZimu() {
        return zimu;
    }

    public void setZimu(char zimu) {
        this.zimu = zimu;
    }

    public String getEvaluateWeight() {
        return evaluateWeight;
    }

    public void setEvaluateWeight(String evaluateWeight) {
        this.evaluateWeight = evaluateWeight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
//	public Boolean getIsRecord() {
//		return isRecord;
//	}
//	public void setIsRecord(Boolean isRecord) {
//		this.isRecord = isRecord;
//	}
}