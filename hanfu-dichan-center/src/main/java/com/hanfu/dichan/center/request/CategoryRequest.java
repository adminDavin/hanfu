package com.hanfu.dichan.center.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@SuppressWarnings("serial")
@ApiModel
public class CategoryRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "类目名称")
    private String category;
    @ApiModelProperty(required = false, value = "上级类目id, 如果顶级类目, 上级类目ID, 设置为-1", example = "-1")
    private Integer parentCategoryId;
    @ApiModelProperty(required = false, value = "类目级别, 是指当前类目至顶层类目的间隔,  顶层类目的类目级别是0, 紧跟顶层类目的类目级别是1, 以此类推", example = "0")
    private Integer levelId;
    @ApiModelProperty(required = false, value = "项目id")
    private Integer projectId;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

}
