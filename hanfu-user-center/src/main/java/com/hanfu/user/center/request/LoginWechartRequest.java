package com.hanfu.user.center.request;

import io.swagger.annotations.ApiParam;

public class LoginWechartRequest {
    @ApiParam(required = true, value = "id", example = "0")
    private Integer id;
    @ApiParam(required = true, value = "登录的用户id", example = "0")
    private Integer userId;
    @ApiParam(required = true, value = "国家")
    private String hfConty;
    @ApiParam(required = true, value = "省")
    private String hfProvince;
    @ApiParam(required = true, value = "城市")
    private String hfCity;
    @ApiParam(required = true, value = "备注")
    private String hfDesc;
    //  @ApiParam(required = true, value = "创建时间")
//  private LocalDateTime createTime;
    @ApiParam(required = true, value = "详细地址信息")
    private String hfAddressDetail;
    @ApiParam(required = true, value = "是否为默认地址")
    private Integer isFaultAddress;
    @ApiParam(required = true, value = "联系人")
    private String contact;
    @ApiParam(required = true, value = "手机号")
    private String phoneNumber;

}
