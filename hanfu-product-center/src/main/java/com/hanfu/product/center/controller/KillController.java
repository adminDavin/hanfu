package com.hanfu.product.center.controller;


import com.hanfu.product.center.model.*;
import com.hanfu.product.center.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/kill")
@Api
public class KillController {
    @Autowired
    SeckillService seckillService;
    @Autowired
    HfGoodsService hfGoodsService;
    @Autowired
    HfGoodsSpecService hfGoodsSpecService;
    @Autowired
    HfOrdersService hfOrdersSerice;
    @Autowired
    SeckillConnectService seckillConnectService;
    @Autowired
    ProductGroupService productService;
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
    public Object insertSeckill( Integer goodsId,Date startTime,Date stopTime,  Double   price, Integer repertory) {
        Integer categoryId=0;
            Integer bossId=1;
//           Date time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
            seckillService.insertSeckill( bossId, goodsId, startTime,stopTime,  categoryId,  price, repertory);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return "ok";
    }
    //删除团购商品
    @ApiOperation(value = "删除秒杀商品", notes = "删除团购商品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "秒杀表id", required = false, type = "Integer"),
    })
    public  String  deleteSeckill(Integer id){
        seckillService.deleteByPrimaryKey(id);
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
    public  boolean  insertGroup(Integer id, Integer goodsId,Double price,Date startTime,Date stopTime,Integer repertory){
        Integer bossId=1;
        Integer categoryId=0;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date time;
//        try {
//            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
//            Date time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
            seckillService.updateByPrimaryKey(id,  bossId,  goodsId,  startTime,stopTime,  categoryId,  price,  repertory);
            return true;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return false;
    }
    @ApiOperation(value = "秒杀业务", notes = "秒杀业务")
    @RequestMapping(value = "/seckill",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "秒杀表id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "desc", value = "所选商品规格", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "addressId", value = "用户地址id", required = true, type = "Integer")
    })
    @ResponseBody
    public synchronized   List<Object> execute( Integer id,Integer userId,String desc,Integer addressId) throws ParseException {
        Seckill seckills = seckillService.selectId(id);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String format1 = formatter.format(date);
        Long in2 = Long.valueOf(format1);
        Date startTime = seckills.getStartTime();
        long time = startTime.getTime();
        List<Object> objects = new ArrayList<>();
        if (in2<time){
            objects.add("未开始");
            return objects;
        }
        if (seckillConnectService.selectBySeckillId(userId,id)!=null){
            objects.add("已结束");
            return objects;
        }
//        判断是不是有库存
        Integer bossId=1;
        Integer repertory = seckillService.getRepertory(id);
        if(repertory==0){
            Short isDeleted=1;
            seckillService.updateIsDeleted(id,isDeleted,bossId);
            objects.add("已结束");
            return objects;
        }
//        秒杀成功
        int repertory1=repertory-1;
        seckillService.updateRepertory(id,bossId,repertory1);
        seckillConnectService.insert(userId,seckills.getId());
        List<Object> insert = hfOrdersSerice.insertSeckill(seckills, userId, desc, addressId);
        objects.add(insert);
        return objects;
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
    public  String  deleteMulti(@RequestParam("id")List<Integer> id){
        for (Integer id1:id) {
            seckillService.deleteByPrimaryKey(id1);
        }
        return "ok";
    }
    @ApiOperation(value = "下架上架秒杀商品", notes = "下架上架秒杀商品")
    @RequestMapping(value = "/updateIsDeleted", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "seckillId", value = "秒杀表id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "isDeleted", value = "秒杀表状态", required = false, type = "Integer"),
    })
    public  String  updateIsDeleted(Integer seckillId,short isDeleted){
        Integer boosId=1;
        seckillService.updateIsDeleted(seckillId,isDeleted ,boosId);
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
    public Seckill selectByGoodsId(Integer id){
        return seckillService.selectId(id);
    }


    @ApiOperation(value = "查询根据id或者名字查商品", notes = "查询根据id查商品")
    @RequestMapping(value = "/selectGoodsId",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "商品名字", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品id", required = true, type = "Integer"),
    })
    @ResponseBody
    public List<HfGoods> selectId(String name , Integer goodsId){
        if(name !=null){
            return hfGoodsService.selectByName(name);
        }
        return hfGoodsService.selectByPrimaryKey(goodsId);
    }



    @ApiOperation(value = "查询查所有商品", notes = "查询查所有商品")
    @RequestMapping(value = "/selectAll",method = RequestMethod.GET)
    @ResponseBody
    public List<HfGoods> selectAll(){
        return hfGoodsService.selectAll();
    }



    @ApiOperation(value = "查询查所有商品详情", notes = "查询查所有商品")
    @RequestMapping(value = "/selectAllGoods",method = RequestMethod.GET)
    @ResponseBody
    public List<HfGoods> selectAllGoods(){
        return hfGoodsService.selectAll();
    }



    @ApiOperation(value = "根据时间查秒杀商品", notes = "根据时间查秒杀商品")
    @RequestMapping(value = "/seleteDate", method = RequestMethod.GET)
    public  List<Seckill> seletDate(String startTime) throws ParseException {
        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        return seckillService.selectDate(time);
    }

    @ApiOperation(value = "根据id 查秒杀商品详情", notes = "查秒杀商品详情")
    @RequestMapping(value = "/seletById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "秒杀表id", required = true, type = "Integer"),
    })
    public  Seckill seletById(Integer id)  {
        Seckill seckill = seckillService.seletById(id);
        List<HfGoodsSpec> hfGoodsSpecs = hfGoodsSpecService.selectByPrimaryKey(seckill.getGoodsId());
        if(hfGoodsSpecs!=null ){
            seckill.setHfGoodsSpec(hfGoodsSpecs);
        }
        List<Product> products = productService.selectByPrimaryKey(seckill.getGoodsId());
        seckill.setProduct(products);
        return seckill;
    }



    @ApiOperation(value = "秒杀业务判断支付成功没有", notes = "秒杀业务判断支付成功没有")
    @RequestMapping(value = "/seckillByPay",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, type = "Integer")
    })
    @ResponseBody
    public boolean seckillByPay(Integer id){
        boolean b = hfOrdersSerice.seckillByPay(id);
        if (!b){
            SeckillConnect seckillConnect = seckillConnectService.selectByUserId(id);
            Seckill seckill = seckillService.selectId(seckillConnect.getSeckillId());
            Integer repertory = seckill.getRepertory();
            int a=repertory+1;
            seckillService.updateRepertory(seckill.getId(),1,a);
            seckillConnectService.updateIsDeleted(id);
            return false;
        }
        seckillConnectService.updateIsDeleted(id);
        return true;
    }

    @ApiOperation(value = "查询秒杀今天的秒杀时间点", notes = "查询秒杀今天的秒杀时间点")
    @RequestMapping(value = "/selectByDate",method = RequestMethod.GET)
    @ResponseBody
    public List<TodayDate> selectByDate() {
        Date date = new Date();
        List<TodayDate> todayDates = new ArrayList<>(20);
        SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  format1 = formatter1.format(date);
        String substring = format1.substring(0, 10);
        HashMap<Object,Date > object = new HashMap<>();
        List<Date> dates = seckillService.selectByDate(substring);

        for(int i = 0;i<dates.size();i++){
            TodayDate todayDate1 = new TodayDate();
            todayDate1.setTime(dates.get(i).getTime());
            todayDates.add(todayDate1);
        }
        return todayDates;
    }
}
