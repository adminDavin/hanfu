package com.hanfu.group.center.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.group.center.manual.dao.GroupOpenMapper;
import com.hanfu.group.center.manual.model.Group;
import com.hanfu.group.center.service.GroupOpenConnectService;
import com.hanfu.group.center.service.GroupOpenService;
import com.hanfu.group.center.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/16
 * @time: 10:35
 */
@RestController
@RequestMapping("/group")
@Api
public class GroupController {
    @Autowired
    GroupService groupService;
    @Autowired
    GroupOpenService groupOpenService;
    @Autowired
    GroupOpenConnectService groupOpenConnectService;

//    添加团购商品
@ApiOperation(value = "添加团购商品", notes = "添加团购商品")
@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public  boolean  insertGroup(Integer bossId, Integer goodsId,Double price,Integer number,String startTime, String stopTime,Integer repertory){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
        Date startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        Date stopTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
        groupService.insert( bossId,  goodsId, price, number, startTime1,stopTime1,repertory);
        return true;
    } catch (ParseException e) {
        e.printStackTrace();
    }
        return false;
    }
//团购商品
@ApiOperation(value = "团购商品", notes = "团购商品")
@RequestMapping(value = "/shopping", method = RequestMethod.POST)
public  String  shoppingGroup(Integer groupId,Integer userId, Integer groupOpenId){
//    判断是不是人数到
    Group group=groupService.selectByPrimaryKey(groupId);
    int reality=groupOpenService.selectNumber(groupId);
    int number=group.getNumber();
    int repertory=group.getRepertory();
//    库存没有
    if(repertory==0){
       return "团购结束";
    }
    else if(reality+1==number){
//        成团
        groupOpenConnectService.insert(userId,groupOpenId);
        int newRrepertory=repertory-1;
        groupService.updateRrepertory(groupId,newRrepertory);
        List <Integer>  urId =groupOpenService.selectUserId(groupId);
        for (Integer  id:urId) {
            groupOpenConnectService.updateIsDeleted(id,groupOpenId);
        }
        return "转到订单";
    }
    groupOpenConnectService.insert(userId,groupOpenId);
    int newRrepertory=repertory-1;
    groupService.updateRrepertory(groupId,newRrepertory);
    return "等待成团";
}
//删除团购商品
//@ApiOperation(value = "删除团购商品", notes = "删除团购商品")
//@ApiImplicitParams({
//        @ApiImplicitParam(paramType = "query", name = "parentCategoryId", value = "上级的类目id", required = false, type = "Integer"),
//        @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类目id", required = false, type = "Integer"),
//        @ApiImplicitParam(paramType = "query", name = "levelId", value = "类目级别", required = false, type = "Integer") })
//@RequestMapping(value = "/insert", method = RequestMethod.GET)
//public  String  deleteGroup(Integer  groupId,Integer userId, Integer groupOpenId){
////    判断是不是人数到
//
//}

}
