package com.hanfu.group.center.controller;

import com.hanfu.group.center.manual.model.*;
import com.hanfu.group.center.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author:gyj
 * @date: 2019/12/16
 * @time: 10:35
 */

@RestController
@RequestMapping("/group")
@Api
@CrossOrigin
public class GroupController {
    @Autowired
    GroupService groupService;
    @Autowired
    GroupOpenService groupOpenService;
    @Autowired
    GroupOpenConnectService groupOpenConnectService;
    @Autowired
    HfGoodsService hfGoodsService;
    @Autowired
    HfCategoryService hfCategoryService;
    @Autowired
    HfUserService hfUserService;
    @Autowired
    HfGoodsSpecService hfGoodsSpec;
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
    public  boolean  insertGroup( @RequestParam("goodsId") Integer goodsId,@RequestParam("price")Double price,@RequestParam("number")Integer number,
                                  @RequestParam("startTime")String startTime, @RequestParam("stopTime") String stopTime, @RequestParam("repertory")Integer repertory){
        Integer bossId=1;
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
    public  Object  shoppingGroup(Integer groupId,Integer userId) throws ParseException {
        Integer orderId=0;
        Group group=groupService.selectByPrimaryKey(groupId);
        if (group==null||group.getNumber()==null){
//            没有此团购信息
            return "err";
        }
        List<GroupOpen> groupOpen1 = groupOpenService.selectByGroupOpen(groupId);
        if(group.getRepertory()==null||group.getRepertory()==0){
                    groupService.updateState(groupId);
//            团购结束
            return "err";
        }
        int repertory=group.getRepertory();
        List<GroupOpen> groupOpen=groupOpenService.selectByGroupOpen(groupId);
        if(groupOpen ==null||groupOpen.get(0).getGroupId()==null){
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
            groupOpenConnectService.insert(userId,id,orderId);
            int newRrepertory=repertory-1;
            groupService.updateRrepertory(groupId,newRrepertory);

            Return aReturn = new Return();
            aReturn.setId(id);
            Group group1 = groupService.selectDate(groupId);
            Integer number = group1.getNumber();
            aReturn.setNumber(number);
            aReturn.setGoodsName(group1.getHfGoods().getHfName());
            aReturn.setStartTime(group1.getStartTime());
            aReturn.setStopTime(group1.getStopTime());
            aReturn.setPrice(group1.getPrice());
            int i = groupOpenService.selectNumber(id);
            int a=number-i;
            aReturn.setNumberFew(a);
            HfUser hfUser = hfUserService.selectByPrimaryKey(userId);
            aReturn.setName(hfUser.getNickName());




            return aReturn;
        }
        int reality=groupOpenService.selectNumber(groupId);
    //    库存没有
        int number=group.getNumber();
        Integer groupOpenId=groupOpen1.get(0).getId();
        if(reality+1==number){
    //        成团
            groupOpenConnectService.insert(userId,groupOpenId,orderId);
            int newRrepertory=repertory-1;
            groupService.updateRrepertory(groupId,newRrepertory);
            List <Integer>  urId =groupOpenService.selectUserId(groupId);
            for (Integer  id:urId) {
                groupOpenConnectService.updateIsDeleted(id,groupOpenId);
            }
            groupOpenService.updateByIsDeleted(groupOpenId);

            Return aReturn = new Return();
            aReturn.setId(groupOpenId);
            Group group1 = groupService.selectDate(groupId);
            Integer number1 = group1.getNumber();
            aReturn.setNumber(number1);
            aReturn.setGoodsName(group1.getHfGoods().getHfName());
            aReturn.setStartTime(group1.getStartTime());
            aReturn.setStopTime(group1.getStopTime());
            aReturn.setPrice(group1.getPrice());
            aReturn.setNumberFew(0);
            HfUser hfUser = hfUserService.selectByPrimaryKey(userId);
            aReturn.setName(hfUser.getNickName());



            return aReturn;
        }
        groupOpenConnectService.insert(userId,groupOpenId,orderId);
        int newRrepertory=repertory-1;
        groupService.updateRrepertory(groupId,newRrepertory);
//        等待成团

        Return aReturn = new Return();
        aReturn.setId(groupOpenId);
        Group group1 = groupService.selectDate(groupId);
        Integer number1 = group1.getNumber();
        aReturn.setNumber(number1);
        aReturn.setGoodsName(group1.getHfGoods().getHfName());
        aReturn.setStartTime(group1.getStartTime());
        aReturn.setStopTime(group1.getStopTime());
        aReturn.setPrice(group1.getPrice());
        aReturn.setNumberFew(0);
        HfUser hfUser = hfUserService.selectByPrimaryKey(userId);
        aReturn.setName(hfUser.getNickName());


        return aReturn;
    }


    //删除团购商品
    @ApiOperation(value = "批量删除团购商品", notes = "批量删除团购商品")
    @RequestMapping(value = "/deleteMulti", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "团购表id", required = false, type = "Integer"),
    })
        public  String  deleteMulti(@RequestParam("id")List<Integer> id){
        for (Integer id1:id) {
            groupService.deleteByPrimaryKey(id1);
            List<Integer> groupOpenId = groupOpenService.selectId(id1);
            for (Integer id2 : groupOpenId) {
                groupOpenConnectService.deleteByGroupOpenId(id2);
            }
            groupOpenService.deleteByGroupId(id1);
        }
        return "ok";
        }
    @ApiOperation(value = "删除团购商品", notes = "删除团购商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public  String  deleteGroup(Integer id){
        groupService.deleteByPrimaryKey(id);
        List<Integer>  groupOpenId = groupOpenService.selectId(id);
        for (Integer  id1:groupOpenId) {
            groupOpenConnectService.deleteByGroupOpenId(id1);
        }
        groupOpenService.deleteByGroupId(id);
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

    @ApiOperation(value = "查询根据id或者名字查商品", notes = "查询根据id查商品")
    @RequestMapping(value = "/selectGoodsId",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "商品名字", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "Integer"),
    })
    @ResponseBody
    public List<HfGoods> selectGoodsId(String name , Integer goodsId){
        if(name !=null ){
            return hfGoodsService.selectByPrimaryKey(goodsId);
        }
        return hfGoodsService.selectByPrimaryKey(goodsId);
    }



    @ApiOperation(value = "下架上架团购商品", notes = "下架上架团购商品")
    @RequestMapping(value = "/updateIsDeleted", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "团购表id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "isDeleted", value = "团购表状态", required = false, type = "Integer"),
    })
    public  String  updateIsDeleted(Integer id,Integer isDeleted){
        groupService.updateIsDeleted(isDeleted,id);
        return "ok";
    }



    @ApiOperation(value = "批量下架团购商品", notes = "批量下架团购商品")
    @RequestMapping(value = "/updateMulti", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "seckillId", value = "秒杀表id", required = false, type = "int"),
//    })
    public  String  updateMulti(@RequestParam("id")List<Integer> id){
        for (Integer id1:id) {
            groupService.updateState(id1);
        }
        return "ok";
    }

    @ApiOperation(value = "根据团购表id查团购商品详情", notes = "根据团购表id查团购商品详情")
    @RequestMapping(value = "/seleteDate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "团购表id", required = false, type = "Integer"),
    })
    public  Group seletDate(Integer id){
        Group group = groupService.selectDate(id);
        List<HfGoodsSpec> hfGoodsSpecs = hfGoodsSpec.selectByPrimaryKey(group.getGoodsId());
        if(hfGoodsSpecs!=null ||hfGoodsSpecs.get(0)!=null){
            group.setHfGoodsSpec(hfGoodsSpecs);
        }
        return group;
    }

// 自己开团
    @ApiOperation(value = "开团 团购商品", notes = "开团 团购商品")
    @RequestMapping(value = "/openGroup", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "groupId", value = "团购表id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = false, type = "Integer")
    })
    public  Object  openGroup(Integer groupId,Integer userId) throws ParseException {
        Integer orderId=0;
        Group group = groupService.selectByPrimaryKey(groupId);
        if (group == null || group.getNumber() == null) {
            return "err";
        }
        if (group.getRepertory() == null || group.getRepertory() == 0) {
            groupService.updateState(groupId);
            return "err";
        }
        int repertory = group.getRepertory();
            //        默认开团12小时没人集齐退钱
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

        long time = 30 * 60 * 1000 * 24;
            Date date2 = new Date(date.getTime() + time);
            String format1 = formatter.format(date);
            String format = formatter.format(date2);
            Date stopTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format1);
            groupOpenService.insert(groupId, startTime, stopTime);
            GroupOpen groupOpen2 = groupOpenService.selectByStopTime(groupId, stopTime);
            Integer id = groupOpen2.getId();

            Return aReturn = new Return();
            aReturn.setId(id);
            Group group1 = groupService.selectDate(groupId);
             Integer number = group1.getNumber();
             aReturn.setNumber(number);
            aReturn.setGoodsName(group1.getHfGoods().getHfName());
            aReturn.setStartTime(group1.getStartTime());
            aReturn.setStopTime(group1.getStopTime());
            aReturn.setPrice(group1.getPrice());
            int i = groupOpenService.selectNumber(id);
            int a=number-i;
            aReturn.setNumberFew(a);
            HfUser hfUser = hfUserService.selectByPrimaryKey(userId);
            aReturn.setName(hfUser.getNickName());

            groupOpenConnectService.insert(userId, id, orderId);
            int newRrepertory = repertory - 1;
            groupService.updateRrepertory(groupId, newRrepertory);
            return aReturn ;
     }

//参团
    @ApiOperation(value = "参加团 团购商品", notes = "参加团 团购商品")
    @RequestMapping(value = "/joinGroup", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "开团购表id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = false, type = "Integer"),

    })
    public  Object  joinGroup(Integer id,Integer userId)  {
        Integer orderId=0;
        GroupOpen groupOpen = groupOpenService.selectByPrimaryKey(id);
        Integer groupId = groupOpen.getGroupId();
        Group group=groupService.selectByPrimaryKey(groupId);
        int reality=groupOpenService.selectNumber(id);
        GroupOpen groupOpen1 = groupOpenService.selectById(id);
        //    库存没有
        int number=group.getNumber();
        Integer groupOpenId=groupOpen1.getId();
        int repertory=group.getRepertory();
        if(reality+1==number){
            //        成团
            groupOpenConnectService.insert(userId,groupOpenId,orderId);
            int newRrepertory=repertory-1;
            groupService.updateRrepertory(groupId,newRrepertory);
            List <Integer>  urId =groupOpenService.selectUserId(id);
            for (Integer  id1:urId) {
                groupOpenConnectService.updateIsDeleted(id1,groupOpenId);
            }
            groupOpenService.updateByIsDeleted(groupOpenId);

            Return aReturn = new Return();
            aReturn.setId(id);
            Group group1 = groupService.selectDate(groupId);
            Integer number1 = group1.getNumber();
            aReturn.setNumber(number1);
            aReturn.setGoodsName(group1.getHfGoods().getHfName());
            aReturn.setStartTime(group1.getStartTime());
            aReturn.setStopTime(group1.getStopTime());
            aReturn.setPrice(group1.getPrice());
            aReturn.setNumberFew(0);
            HfUser hfUser = hfUserService.selectByPrimaryKey(userId);
            aReturn.setName(hfUser.getNickName());

            return aReturn ;
        }
        groupOpenConnectService.insert(userId,groupOpenId,orderId);
        int newRrepertory=repertory-1;
        groupService.updateRrepertory(groupId,newRrepertory);

        Return aReturn = new Return();
        aReturn.setId(id);
        Group group1 = groupService.selectDate(groupId);
        Integer number1 = group1.getNumber();
        aReturn.setNumber(number1);
        aReturn.setGoodsName(group1.getHfGoods().getHfName());
        aReturn.setStartTime(group1.getStartTime());
        aReturn.setStopTime(group1.getStopTime());
        aReturn.setPrice(group1.getPrice());
        int i = groupOpenService.selectNumber(id);
        int a=number1-i;
        aReturn.setNumberFew(a);
        HfUser hfUser = hfUserService.selectByPrimaryKey(userId);
        aReturn.setName(hfUser.getNickName());
        aReturn.setHfUser(hfUser);
//        拼团中
        return aReturn;
    }

    @ApiOperation(value = "根据类目查询商品", notes = "根据类目查询商品")
    @RequestMapping(value = "/selectCategory", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "类目名字", required = false, type = "Sting"),
    })
    public  List<Group>  selectCategory(String name)  {
        Integer categoryId = hfCategoryService.selectByName(name);
        return  groupService. selectCategory(categoryId);
    }

    @ApiOperation(value = "所有类目", notes = "所有类目")
    @RequestMapping(value = "/selectCategoryName", method = RequestMethod.GET)
    public  List<String>  selectCategoryName()  {
        return groupService.selectCategoryName();
    }

    @ApiOperation(value = "查询正在开团", notes = "查询正在开团")
    @RequestMapping(value = "/selectGroup", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "团购表id", required = false, type = "Integer"),
    })
    public  List<Open>  selectGroup(Integer id)  {
        Group group = groupService.selectByPrimaryKey(id);
        int number = group.getNumber();
//        获取开团表id
        List<Integer>  id1 = groupOpenService.selectByGroupOpenId(id);
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        List<Open> open=new ArrayList<>();
       for (int i=0;i<id1.size();i++){
           Open open1 = new Open();
//           获取开团对应的用户id
           open1.setId(id1.get(i));
           List<Integer> integers = groupOpenService.selectUserId(id1.get(i));
           HfUser hfUser = hfUserService.selectByPrimaryKey(integers.get(i));
           open1.setName(hfUser.getNickName());
           int a=groupOpenService.selectNumber(id1.get(i));
            open1.setNumber(number-a);
           open.add(open1);
       }
        return open ;
    }
    @ApiOperation(value = "查询开团 详情", notes = "查询开团 详情")
    @RequestMapping(value = "/selectOpenGroup", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "开团购表id", required = false, type = "Integer"),

    })
    public  Object  selectOpenGroup(Integer id)  {
        GroupOpen groupOpen = groupOpenService.selectByPrimaryKey(id);
        Return aReturn = new Return();
        aReturn.setId(id);
        Group group1 = groupService.selectDate(groupOpen.getGroupId());
        Integer number1 = group1.getNumber();
        aReturn.setNumber(number1);
        aReturn.setGoodsName(group1.getHfGoods().getHfName());
        aReturn.setStartTime(group1.getStartTime());
        aReturn.setStopTime(group1.getStopTime());
        aReturn.setPrice(group1.getPrice());

        int i = groupOpenService.selectNumber(id);
        int a1=number1-i;
        aReturn.setNumberFew(a1);
        List <Integer>  urId =groupOpenService.selectUserId(id);
        ArrayList<HfUser> hfUsers = new ArrayList<>();
        for (Integer a:urId) {
            hfUsers.add(hfUserService.selectByPrimaryKey(a));
        }
        aReturn.setUser(hfUsers);
        return aReturn;
    }
    @ApiOperation(value = "查询参加详情", notes = "查询参加详情")
    @RequestMapping(value = "/selectByGroup", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "groupId", value = "团购表id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = false, type = "Integer"),

    })
    public  Object  selectByGroup(Integer groupId,Integer userId)  {
        GroupOpen groupOpen1 = groupOpenService.selectByGroup(groupId, userId);
        Return aReturn = new Return();
        Integer id = groupOpen1.getId();
        aReturn.setId(id);
        Group group1 = groupService.selectDate(groupOpen1.getGroupId());
        Integer number1 = group1.getNumber();
        aReturn.setNumber(number1);
        aReturn.setGoodsName(group1.getHfGoods().getHfName());
        aReturn.setStartTime(group1.getStartTime());
        aReturn.setStopTime(group1.getStopTime());
        aReturn.setPrice(group1.getPrice());
        int i = groupOpenService.selectNumber(id);
        int a1=number1-i;
        aReturn.setNumberFew(a1);
        List <Integer>  urId =groupOpenService.selectByUserId(id);
        ArrayList<HfUser> hfUsers = new ArrayList<>();
        for (Integer a:urId) {
            hfUsers.add(hfUserService.selectByPrimaryKey(a));
        }
        aReturn.setUser(hfUsers);
        return aReturn;
    }
}
