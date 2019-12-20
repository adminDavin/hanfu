package com.hanfu.group.center.controller;

import com.hanfu.group.center.manual.model.Group;
import com.hanfu.group.center.manual.model.GroupOpen;
import com.hanfu.group.center.service.GroupOpenConnectService;
import com.hanfu.group.center.service.GroupOpenService;
import com.hanfu.group.center.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.DoubleToIntFunction;

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
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "团购价格", required = false, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "number", value = "成团人数", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "团购开始时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "stopTime", value = "团购结束时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "repertory", value = "库存", required = false, type = "Integer")
    })
    public  boolean  insertGroup( Integer goodsId,Double price,Integer number,String startTime, String stopTime,Integer repertory){
        Integer bossId=1;
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
    @ApiOperation(value = "购买团购商品", notes = "购买团购商品")
    @RequestMapping(value = "/shopping", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "groupId", value = "团购表id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = false, type = "Integer")
    })
    public  String  shoppingGroup(Integer groupId,Integer userId) throws ParseException {
        Group group=groupService.selectByPrimaryKey(groupId);
        if (group==null||group.getNumber()==null){
            return "没有此团购信息";
        }
        GroupOpen groupOpen1 = groupOpenService.selectByGroupOpen(groupId);
        if(group.getRepertory()==null||group.getRepertory()==0){
                    groupService.updateState(groupId);
            return "团购结束";
        }
        int repertory=group.getRepertory();
        GroupOpen groupOpen=groupOpenService.selectByGroupOpen(groupId);
        if(groupOpen ==null||groupOpen.getGroupId()==null){
    //        默认开团12小时没人集齐退钱
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            long time=30*60*1000*24;
            Date date2 = new Date(date.getTime() + time);
            String format1 = formatter.format(date);
            String format = formatter.format(date2);
            Date stopTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format1);
            groupOpenService.insert(groupId,startTime, stopTime);
            GroupOpen groupOpen2 =  groupOpenService.selectByStopTime(groupId, stopTime);
            Integer id=groupOpen2.getId();
            groupOpenConnectService.insert(userId,id);
            int newRrepertory=repertory-1;
            groupService.updateRrepertory(groupId,newRrepertory);
            return "开团";
        }
        int reality=groupOpenService.selectNumber(groupId);
    //    库存没有
        int number=group.getNumber();
        Integer groupOpenId=groupOpen1.getId();
        if(reality+1==number){
    //        成团
            groupOpenConnectService.insert(userId,groupOpenId);
            int newRrepertory=repertory-1;
            groupService.updateRrepertory(groupId,newRrepertory);
            List <Integer>  urId =groupOpenService.selectUserId(groupId);
            for (Integer  id:urId) {
                groupOpenConnectService.updateIsDeleted(id,groupOpenId);
            }
            groupOpenService.updateByIsDeleted(groupOpenId);
            return "转到订单";
        }
        groupOpenConnectService.insert(userId,groupOpenId);
        int newRrepertory=repertory-1;
        groupService.updateRrepertory(groupId,newRrepertory);
        return "等待成团";
    }


    //删除团购商品
    @ApiOperation(value = "批量删除团购商品", notes = "批量删除团购商品")
    @RequestMapping(value = "/deleteMulti", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "groupId", value = "团购表id", required = false, type = "Integer"),
    })
        public  String  deleteMulti(@RequestParam("groupId")List<Integer> groupId){
        for (Integer id:groupId) {
            groupService.deleteByPrimaryKey(id);
            List<Integer> groupOpenId = groupOpenService.selectId(id);
            for (Integer id1 : groupOpenId) {
                groupOpenConnectService.deleteByGroupOpenId(id1);
            }
            groupOpenService.deleteByGroupId(id);
        }
        return "ok";
        }
    @ApiOperation(value = "删除团购商品", notes = "删除团购商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public  String  deleteGroup(Integer groupId){
        groupService.deleteByPrimaryKey(groupId);
        List<Integer>  groupOpenId = groupOpenService.selectId(groupId);
        for (Integer  id:groupOpenId) {
            groupOpenConnectService.deleteByGroupOpenId(id);
        }
        groupOpenService.deleteByGroupId(groupId);
        return "ok";
    }



    //    添加团购商品
    @ApiOperation(value = "修改团购商品", notes = "修改团购商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "groupId", value = "团购表id", required = false, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "团购价格", required = false, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "number", value = "成团人数", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "团购开始时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "stopTime", value = "团购结束时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "repertory", value = "库存", required = false, type = "Integer")
    })
    public  boolean  insertGroup(Integer  groupId, Integer goodsId,Double price,Integer number,String startTime, String stopTime,Integer repertory){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            Date stopTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
            Integer bossId=1;
            groupService.updateByPrimaryKey(groupId, bossId,  goodsId, price, number, startTime1,stopTime1,repertory);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @ApiOperation(value = "查询所有团购商品", notes = "查询所有团购商品")
    @RequestMapping(value = "/selete", method = RequestMethod.GET)
    public @ResponseBody List<Group>  seleteGroup(){
        Integer bossId=1;
        return groupService.seleteAll(bossId);
    }
    @ApiOperation(value = "搜索团购商品", notes = "搜索团购商品")
    @RequestMapping(value = "/seek", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer")
    })

    public  List<Group>  seek( Integer goodsId){

        return groupService.seleteId(goodsId);
    }
    @ApiOperation(value = "根据id查询团购商品", notes = "根据id查询团购商品")
    @RequestMapping(value = "/selectId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "团购表id", required = false, type = "Integer")
    })
    public  Group  seleteId( Integer id){
        return groupService.selectByPrimaryKey(id);
    }
}
