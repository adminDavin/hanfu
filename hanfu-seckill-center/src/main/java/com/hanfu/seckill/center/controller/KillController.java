package com.hanfu.seckill.center.controller;


import com.hanfu.seckill.center.model.Seckill;
import com.hanfu.seckill.center.service.SeckillService;
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

/**
 * @author:gyj
 * @date: 2019/12/17
 * @time: 9:49
 */
@CrossOrigin
@RestController
@RequestMapping("/kill")
@Api
public class KillController {
    @Autowired
    SeckillService seckillService;

    @ApiOperation(value = "添加秒杀业务", notes = "添加秒杀业务")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "秒杀开始时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "stopTime", value = "秒杀结束时间", required = false, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类别id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "团购价格", required = false, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "repertory", value = "库存", required = false, type = "Integer")
    })
//    添加秒杀表
    public Object insertSeckill( Integer goodsId,String startTime,String stopTime,  Double   price, Integer repertory) {
        Integer categoryId=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time;
        try {
            Integer bossId=1;
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
           Date time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
            seckillService.insertSeckill( bossId, goodsId, time,time1,  categoryId,  price, repertory);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "建立秒杀单";
    }
    //删除团购商品
    @ApiOperation(value = "删除秒杀商品", notes = "删除团购商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "seckillId", value = "秒杀表id", required = false, type = "Integer"),
    })
    public  String  deleteSeckill(Integer seckillId){
        seckillService.deleteByPrimaryKey(seckillId);
        return "ok";
    }
    //    添加团购商品
    @ApiOperation(value = "修改秒杀商品", notes = "修改秒杀商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "秒杀表id", required = false, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类别id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "团购价格", required = false, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "秒杀开始时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "stopTime", value = "秒杀结束时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "repertory", value = "库存", required = false, type = "Integer")
    })
    public  boolean  insertGroup(Integer id, Integer goodsId,Double price,String startTime,String stopTime,Integer repertory){
        Integer bossId=1;
        Integer categoryId=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            Date time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
            seckillService.updateByPrimaryKey(id,  bossId,  goodsId,  time,time1,  categoryId,  price,  repertory);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    @ApiOperation(value = "秒杀业务", notes = "秒杀业务")
    @RequestMapping(value = "/seckill",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "Integer")
//            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer")
    })
    @ResponseBody
    public Object execute( Integer goodsId){
//        判断是不是有库存
        Integer bossId=1;
        Integer repertory = seckillService.getRepertory(goodsId, bossId);
        if(repertory==0){
            Short isDeleted=0;
            seckillService.updateIsDeleted(goodsId,isDeleted,bossId);
            return "秒杀已结束";
        }
//        秒杀成功
        synchronized(this){
            Integer repertory1=repertory-1;
            seckillService.updateRepertory(goodsId,bossId,repertory1);
        }
        return "跳转订单页面";
    }

    @ApiOperation(value = "查询所有的秒杀商品", notes = "查询秒杀")
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    @ResponseBody
    public List<Seckill> selectSeckill(){
        Integer bossId=1;
        return seckillService.selectAll(bossId);
    }



    @ApiOperation(value = "搜索秒杀", notes = "搜索秒杀")
    @RequestMapping(value = "/seek",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "Integer"),

    })
    @ResponseBody
    public List<Seckill> seekSeckill(Integer goodsId){
        return seckillService.selectGoodsId(goodsId);
    }


    //删除团购商品
    @ApiOperation(value = "批量删除秒杀商品", notes = "批量删除秒杀商品")
    @RequestMapping(value = "/deleteMulti", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "seckillId", value = "秒杀表id", required = false, type = "Integer"),
//    })
    public  String  deleteMulti(@RequestParam("seckillId")List<Integer> seckillId){
        for (Integer id:seckillId) {
            seckillService.deleteByPrimaryKey(id);
        }
        return "ok";
    }
    @ApiOperation(value = "下架秒杀商品", notes = "下架秒杀商品")
    @RequestMapping(value = "/updateIsDeleted", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "seckillId", value = "秒杀表id", required = false, type = "Integer"),
    })
    public  String  updateIsDeleted(Integer seckillId){
        seckillService.updateState(seckillId);
        return "ok";
    }

    @ApiOperation(value = "批量下架秒杀商品", notes = "批量下架秒杀商品")
    @RequestMapping(value = "/updateMulti", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "seckillId", value = "秒杀表id", required = false, type = "int"),
//    })
    public  String  updateMulti(@RequestParam("seckillId")List<Integer> seckillId){
        for (Integer id:seckillId) {
            seckillService.updateState(id);
        }
        return "ok";
    }
    @ApiOperation(value = "查询根据id秒杀表", notes = "查询根据id秒杀表")
    @RequestMapping(value = "/selectId",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "秒杀表id", required = true, type = "Integer"),

    })
    @ResponseBody
    public Seckill selectId(Integer id){
        return seckillService.selectId(id);
    }

}
