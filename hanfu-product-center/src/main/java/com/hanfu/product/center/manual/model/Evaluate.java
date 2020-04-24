package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.product.center.model.HfOrderDetail;

public class Evaluate implements Serializable{
	
	
public static enum EvaluateTypeEnum {
        
		DISCOVER("discover"),
		EVALUATE("evaluate");
		private String evaluateType;

		private EvaluateTypeEnum(String evaluateType) {
			this.evaluateType = evaluateType;
		}
		

		public String getEvaluateType() {
			return evaluateType;
		}

		public void setEvaluateType(String evaluateType) {
			this.evaluateType = evaluateType;
		}

		public static EvaluateTypeEnum getEvaluateTypeEnum(String evaluateType) {
			for(EvaluateTypeEnum d:EvaluateTypeEnum.values()) {
				if(d.getEvaluateType().equals(evaluateType)) {
					return d;
				}
			}
			return EVALUATE;
		}
    }

public static enum EvaluateContentTypeEnum {
	
	HEART("heart"),
	VIDEO("video"),
	ARTICLE("article");
	private String evaluateContentType;
	
	private EvaluateContentTypeEnum(String evaluateContentType) {
		this.evaluateContentType = evaluateContentType;
	}
	
	public String getEvaluateContentType() {
		return evaluateContentType;
	}

	public void setEvaluateContentType(String evaluateContentType) {
		this.evaluateContentType = evaluateContentType;
	}


	public static EvaluateContentTypeEnum getEvaluateContentTypeEnum(String evaluateContentType) {
		for(EvaluateContentTypeEnum d:EvaluateContentTypeEnum.values()) {
			if(d.getEvaluateContentType().equals(evaluateContentType)) {
				return d;
			}
		}
		return HEART;
	}
}
	
	private Integer id;
	private Integer userId;
	private String comment;
	private String type;
	private String typeContent;
	private Integer star;
	private Integer comment_count;
	private Integer praise;
	private String hfDesc;
	private String username;
	private String levelName;
	private List<Integer> fileId;
	private Integer outId;
	private Integer inId;
	private String outName;
	private String inName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;
	private HfOrderDetail list;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeContent() {
		return typeContent;
	}
	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public Integer getComment_count() {
		return comment_count;
	}
	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}
	public Integer getPraise() {
		return praise;
	}
	public void setPraise(Integer praise) {
		this.praise = praise;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public List<Integer> getFileId() {
		return fileId;
	}
	public void setFileId(List<Integer> fileId) {
		this.fileId = fileId;
	}
	public Integer getOutId() {
		return outId;
	}
	public void setOutId(Integer outId) {
		this.outId = outId;
	}
	public Integer getInId() {
		return inId;
	}
	public void setInId(Integer inId) {
		this.inId = inId;
	}
	public String getOutName() {
		return outName;
	}
	public void setOutName(String outName) {
		this.outName = outName;
	}
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public HfOrderDetail getList() {
		return list;
	}
	public void setList(HfOrderDetail list) {
		this.list = list;
	}
	public String getHfDesc() {
		return hfDesc;
	}
	public void setHfDesc(String hfDesc) {
		this.hfDesc = hfDesc;
	}
}
