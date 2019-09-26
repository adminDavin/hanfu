package com.hanfu.user.center.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class UserInfoRequest extends CommonRequest {
	@ApiParam(required = false, value="登录的用户名")
	private String username;
	@ApiParam(required = false, value = "用户邮箱")
	private String email;
	@ApiParam(required = false, value = "用户昵称")
	private String nickName;
	@ApiParam(required = false, value = "用户真实姓名")
	private String realName;
	@ApiParam(required = false, value = "用户性别")
	private String sex;
	@ApiParam(required = false, value = "出生时间, 时间格式:20181023T081324Z")
	private String birthDay;
	@ApiModelProperty(required = false, value = "图片文件")
	private MultipartFile fileInfo;
	@ApiParam(required = false, value = "详细地址")
	private String address;
	@ApiParam(required = false, value = "所在地区")
	private String region;
}
