package com.hanfu.seckill.center.controller;


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

/**
 * @author:gyj
 * @date: 2019/12/17
 * @time: 9:49
 */
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
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "秒杀开始时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类别id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "团购价格", required = false, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "repertory", value = "库存", required = false, type = "Integer")
    })
//    添加秒杀表
    public Object insertSeckill(Integer bossId, Integer goodsId,String startTime, Integer categoryId, Double   price, Integer repertory) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            seckillService.insertSeckill( bossId, goodsId,  time,  categoryId,  price, repertory);
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
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "类别id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "团购价格", required = false, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "秒杀开始时间", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "repertory", value = "库存", required = false, type = "Integer")
    })
    public  boolean  insertGroup(Integer id,Integer bossId, Integer goodsId, Integer categoryId,Double price,String startTime,Integer repertory){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            seckillService.updateByPrimaryKey(id,  bossId,  goodsId,  time,  categoryId,  price,  repertory);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    @ApiOperation(value = "秒杀业务", notes = "秒杀业务")
    @RequestMapping(value = "/seckill",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer")
    })
    @ResponseBody
    public Object execute( Integer goodsId, Integer bossId){
//        判断是不是有库存
        Integer repertory = seckillService.getRepertory(goodsId, bossId);
        if(repertory==0){
            seckillService.updateIsDeleted(goodsId,bossId);
            return "秒杀已结束";
        }
//        秒杀成功
        synchronized(this){
            Integer repertory1=repertory-1;
            seckillService.updateRepertory(goodsId,bossId,repertory1);
        }
        return "跳转订单页面";
    }

}
