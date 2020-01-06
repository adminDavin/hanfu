package com.hanfu.activity.center.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityUserEvaluateRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "部门id")
    private Integer id;
    @ApiModelProperty(required = false, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "模板id")
    private Integer[] evaluateTemplateId;
    @ApiModelProperty(required = false, value = "评价内容")
    private List<String> evaluateContent;
    @ApiModelProperty(required = false, value = "评价结果")
    private String evaluateResult;
    @ApiModelProperty(required = false, value = "评论")
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer[] getEvaluateTemplateId() {
        return evaluateTemplateId;
    }

    public void setEvaluateTemplateId(Integer[] evaluateTemplateId) {
        this.evaluateTemplateId = evaluateTemplateId;
    }

    public List<String> getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(List<String> evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getEvaluateResult() {
        return evaluateResult;
    }

    public void setEvaluateResult(String evaluateResult) {
        this.evaluateResult = evaluateResult;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
