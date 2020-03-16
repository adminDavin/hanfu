package com.hanfu.order.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hanfu.order.center.cancel.dao.*;
import com.hanfu.order.center.cancel.model.*;
import com.hanfu.order.center.service.CancelService;
import com.hanfu.order.center.tool.PageTool;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api
@RequestMapping("/cancel")
@CrossOrigin
public class CancelController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CancelService cancelService;
    @Autowired
    private HfUserMapper hfUserMapper;
    @Autowired
    private CancelsMapper cancelsMapper;
    @Autowired
    private HfOrdersCancelDetailMapper hfOrdersCancelDetailMapper;
    @Autowired
    private HfLogMapper hfLogMapper;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Autowired
    private CancelRecordMapper cancelRecordMapper;
    @Autowired
    private HfPriceMapper hfPriceMapper;
    @Autowired
    private CancelProductMapper cancelProductMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CancelMapper cancelMapper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/selectCancel", method = RequestMethod.GET)
    @ApiOperation(value = "核销员信息", notes = "核销员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> selectCancel(Paging paging) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        PageTool.num(paging);
        PageInfo<record> pageInfo = new PageInfo<>(cancelService.select());
        return builder.body(ResponseUtils.getResponseBody(pageInfo));
    }

    @RequestMapping(value = "/selectCancelRecord", method = RequestMethod.GET)
    @ApiOperation(value = "核销记录", notes = "核销记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "Id", value = "核销Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> selectCancelRecord(Integer Id, Paging paging) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(CancelRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cancelId", Id);
        PageTool.num(paging);
        PageInfo<CancelRecord> pageInfo = new PageInfo<>(cancelRecordMapper.selectByExample(example));
        return builder.body(ResponseUtils.getResponseBody(pageInfo));
    }

    @RequestMapping(value = "/deleteEmpty", method = RequestMethod.GET)
    @ApiOperation(value = "清空", notes = "清空")
    @ApiImplicitParam(paramType = "query", name = "id", value = "核销id", required = true, type = "Integer")
    public ResponseEntity<JSONObject> deleteEmpty(int id) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        cancel cancel = new cancel();
        cancel.setId(id);
        cancel.setPresentMoney(0);
        cancel.setModifyDate(LocalDateTime.now());
        cancel.setEmptyDate(LocalDateTime.now());
        cancelsMapper.updateByPrimaryKeySelective(cancel);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

    @RequestMapping(value = "/selectCancelId", method = RequestMethod.GET)
    @ApiOperation(value = "核销员Id查询", notes = "核销员Id查询")
    public ResponseEntity<JSONObject> selectCancelId(HttpServletResponse response, int cancelId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(cancelService.selectCancelId(cancelId)));
    }

    @RequestMapping(value = "/selectDate", method = RequestMethod.GET)
    @ApiOperation(value = "核销时间筛选", notes = "核销时间筛选")
    public ResponseEntity<JSONObject> selectDate(Date createData, Date createDate1) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(cancelService.selectDate(createData, createDate1)));
    }

    @RequestMapping(value = "/selectRegion", method = RequestMethod.GET)
    @ApiOperation(value = "地区筛选", notes = "地区筛选")
    public ResponseEntity<JSONObject> selectRegion(String site) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(cancelService.selectRegion(site)));
    }

    //转换时间格式
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    @RequestMapping(value = "/deleterecord", method = RequestMethod.GET)
    @ApiOperation(value = "删除记录", notes = "删除记录")
    public ResponseEntity<JSONObject> deleterecord(int id) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(hfLogMapper.deleteByPrimaryKey(id)));
    }

    @RequestMapping(value = "/deselectCancel", method = RequestMethod.GET)
    @ApiOperation(value = "取消核销员对此商品的核销权限", notes = "取消核销员对此商品的核销权限")
    public ResponseEntity<JSONObject> deselectCancel(Integer productId,Integer cancelId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(CancelProduct.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId).andEqualTo("cancelId",cancelId);
        return builder.body(ResponseUtils.getResponseBody(cancelProductMapper.deleteByExample(example)));
    }

    @RequestMapping(value = "/addCancel", method = RequestMethod.POST)
    @ApiOperation(value = "商品添加核销员", notes = "商品添加核销员")
    public ResponseEntity<JSONObject> addCancel(Integer productId,Integer[] cancelId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();

        if (productMapper.selectByPrimaryKey(productId).getClaim()!=1) {
            return builder.body(ResponseUtils.getResponseBody("非自体商品"));
        }
        for (int i=0;i<cancelId.length;i++){
            CancelProduct cancelProduct = new CancelProduct();
            cancelProduct.setCancelId(cancelId[i]);
            cancelProduct.setClaim((short) 0);
            cancelProduct.setCreateTime(LocalDateTime.now());
            cancelProduct.setModifyTime(LocalDateTime.now());
            cancelProduct.setProductId(productId);
            cancelProduct.setIsDeleted((short) 0);
            cancelProductMapper.insert(cancelProduct);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @RequestMapping(value = "/selectProductCancel", method = RequestMethod.GET)
    @ApiOperation(value = "商品核销员查询", notes = "商品核销员查询")
    public ResponseEntity<JSONObject> selectProductCancel(Integer productId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (productMapper.selectByPrimaryKey(productId).getClaim()!=1) {
            return builder.body(ResponseUtils.getResponseBody("非自体商品"));
        }
        List<SelectCancelProduct> cancelProductList= cancelMapper.selectProductCancel(productId);
        cancelProductList.forEach(cancelProduct -> {
            cancel cancel= cancelsMapper.selectByPrimaryKey(cancelProduct.getCancelId());
            HfUser hfUser= hfUserMapper.selectByPrimaryKey(cancel.getUserId());
            Product product= productMapper.selectByPrimaryKey(cancelProduct.getProductId());
            cancelProduct.setProductName(product.getHfName());
            cancelProduct.setProductDesc(product.getProductDesc());
            cancelProduct.setUserId(hfUser.getId());
            cancelProduct.setUsername(hfUser.getRealName());
        });
        return builder.body(ResponseUtils.getResponseBody(cancelProductList));
    }
//    @RequestMapping(value = "/deleteJudge", method = RequestMethod.GET)
//    @ApiOperation(value = "判断是否删除", notes = "判断是否删除")
//    public ResponseEntity<JSONObject> deleteJudge(int id) throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        Example example = new Example(HfGoods.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("cancelId", id);
//        List<HfGoods> hfGoodsList = hfGoodsMapper.selectByExample(example);
//        List<HfGoods> list = new ArrayList<>();
//        if (hfGoodsList.size() != 0) {
//            for (int i = 0; i < hfGoodsList.size(); i++) {
//                if (hfGoodsList.get(i).getClaim() != 0) {
//                    if (cancelsMapper.selectByPrimaryKey(hfGoodsList.get(i).getCancelId()) != null) {
//                        list.add(hfGoodsList.get(i));
//                    }
//                }
//            }
//            return builder.body(ResponseUtils.getResponseBody(list));
//        }
//        deleteCancel(id);
//        return builder.body(ResponseUtils.getResponseBody("该核销员没有对应的核销商品,已删除"));
//    }

    @RequestMapping(value = "/deleteCancel", method = RequestMethod.GET)
    @ApiOperation(value = "删除核销员", notes = "删除核销员")
    @ApiImplicitParam(paramType = "query", name = "id", value = "核销id", required = true, type = "Integer")
    public ResponseEntity<JSONObject> deleteCancel(int id) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        cancel cancel = cancelsMapper.selectByPrimaryKey(id);
        System.out.println(id);
        if (cancel == null) {
            return builder.body(ResponseUtils.getResponseBody("核销员不存在"));
        }
        HfUser hfUser = new HfUser();
        hfUser.setModifyDate(LocalDateTime.now());
        hfUser.setId(cancel.getUserId());
        hfUser.setCancelId(0);
        hfUserMapper.updateByPrimaryKeySelective(hfUser);
        cancelsMapper.deleteByPrimaryKey(id);
        //删除核销和商品中间表
        Example example = new Example(CancelProduct.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cancelId",id);
        cancelProductMapper.deleteByExample(example);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

    @RequestMapping(value = "/deleteBatchCancel", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除核销员", notes = "批量删除核销员")
    public ResponseEntity<JSONObject> deleteBatchCancel(@RequestParam("id") List id) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        System.out.println(id);
        if (id == null) {
            builder.body(ResponseUtils.getResponseBody("请选择核销员"));
        }
        for (int i = 0; i < id.size(); i++) {
            int cancelID = Integer.parseInt(id.get(i).toString());
            System.out.println(cancelID);
            cancel cancel = cancelsMapper.selectByPrimaryKey(cancelID);
            System.out.println(cancel);
            HfUser hfUser = new HfUser();
            hfUser.setId(cancel.getUserId());
            hfUser.setCancelId(0);
            hfUserMapper.updateByPrimaryKeySelective(hfUser);
            cancelsMapper.deleteByPrimaryKey(cancelID);
            //删除核销和商品中间表
        Example example = new Example(CancelProduct.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cancelId",cancelID);
        cancelProductMapper.deleteByExample(example);
        }
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }


    @RequestMapping(value = "/insertCancel", method = RequestMethod.GET)
    @ApiOperation(value = "增加核销员", notes = "增加核销员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "UserId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "site", value = "核销地点", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "RealName", value = "核销员姓名", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> insertCancel(Integer UserId, String site, String RealName) throws Exception {
        System.out.println(UserId);
        System.out.println(site);
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser hfUser = new HfUser();
        hfUser.setId(UserId);

        HfUser hfUser1 = hfUserMapper.selectByPrimaryKey(hfUser);
        if (hfUser1 == null) {
            return builder.body(ResponseUtils.getResponseBody("用户不存在"));
        }
        Integer cancel = hfUser1.getCancelId();
        System.out.println(hfUser1.getCancelId());
        if (cancel == 1) {
            return builder.body(ResponseUtils.getResponseBody("该人员已经是核销员"));
        }
        hfUser.setCancelId(1);
        hfUser.setRealName(RealName);
        hfUser.setModifyDate(LocalDateTime.now());
        hfUserMapper.updateByPrimaryKeySelective(hfUser);
        cancel cancel1 = new cancel();
        cancel1.setCreateDate(LocalDateTime.now());
        cancel1.setModifyDate(LocalDateTime.now());
        cancel1.setUserId(UserId);
        cancel1.setSite(site);
        int a = cancelsMapper.insertSelective(cancel1);
        System.out.println(a);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

    @RequestMapping(value = "/updateCancelUser", method = RequestMethod.GET)
    @ApiOperation(value = "根据核销id修改核销员信息", notes = "根据核销id修改核销员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "Id", value = "核销id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "site", value = "核销地点", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "RealName", value = "核销员姓名", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "cancel2", value = "是否为核销员0否,1是,不填为1", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> updateCancelUser(int Id, String site, Integer cancel2, String RealName) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (cancel2 == null) {
            cancel2 = 1;
        }
        cancel cancel = cancelsMapper.selectByPrimaryKey(Id);
        cancel cancel1 = new cancel();
        cancel1.setSite(site);
        cancel1.setModifyDate(LocalDateTime.now());
        cancel1.setId(Id);
        cancelsMapper.updateByPrimaryKeySelective(cancel1);
        HfUser hfUser = new HfUser();
        hfUser.setModifyDate(LocalDateTime.now());
        hfUser.setCancelId(cancel2);
        hfUser.setRealName(RealName);
        hfUser.setId(cancel.getUserId());
        hfUserMapper.updateByPrimaryKeySelective(hfUser);
        if (cancel2 == 0) {
            cancelsMapper.deleteByPrimaryKey(Id);
            return builder.body(ResponseUtils.getResponseBody("已经取消此人的核销资格"));
        }
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

    @RequestMapping(value = "/updateCancel", method = RequestMethod.POST)
    @ApiOperation(value = "修改核销员信息", notes = "修改核销员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "UserId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "site", value = "核销地点", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "RealName", value = "核销员姓名", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "cancel2", value = "是否为核销员0否,1是，不填为1", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> updateCancel(Integer UserId, String site, Integer cancel2, String RealName) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (cancel2 == null) {
            cancel2 = 1;
        }
        cancel cancel1 = new cancel();
        HfUser hfUser = new HfUser();
        hfUser.setId(UserId);
        hfUser.setCancelId(cancel2);
        hfUser.setRealName(RealName);
        hfUserMapper.updateByPrimaryKeySelective(hfUser);
        System.out.println(hfUserMapper.selectByPrimaryKey(UserId).getCancelId()+"取消QIAN的CANCELid");
        Example example1 = new Example(cancel.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId",UserId);
        if (cancel2==1 &cancelsMapper.selectByExample(example1).size()==0){
            cancel1.setSite(site);
            cancel1.setUserId(UserId);
            cancel1.setMoney(0);
            cancel1.setPresentMoney(0);
            cancel1.setCreateDate(LocalDateTime.now());
            cancel1.setModifyDate(LocalDateTime.now());
            cancelsMapper.insert(cancel1);
            return builder.body(ResponseUtils.getResponseBody("成功"));
        }

        cancel1.setSite(site);
        cancel1.setUserId(UserId);
        cancel1.setCreateDate(LocalDateTime.now());
        cancel1.setModifyDate(LocalDateTime.now());
        Example example = new Example(cancel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", UserId);
        cancelsMapper.updateByExampleSelective(cancel1, example);
        if (cancel2 == 0) {
            //删除核销和商品中间表
            if (cancelsMapper.selectByExample(example).get(0).getId()!=null){
                Example example7 = new Example(CancelProduct.class);
                Example.Criteria criteria7 = example7.createCriteria();
                criteria7.andEqualTo("cancelId",cancelsMapper.selectByExample(example).get(0).getId());
                cancelProductMapper.deleteByExample(example7);
            }

            cancelsMapper.deleteByExample(example);
            System.out.println(hfUserMapper.selectByPrimaryKey(UserId).getCancelId()+"取消后的CANCELid");
            return builder.body(ResponseUtils.getResponseBody("已取消此人的核销资格"));
        }
        System.out.println("1222255555");
        return builder.body(ResponseUtils.getResponseBody("成功1"));
    }

//    @RequestMapping(path = "/wxLogin", method = RequestMethod.GET)
//    @ApiOperation(value = "授权核销", notes = "授权核销")
//    public ResponseEntity<JSONObject> wxLogin(
//            @RequestParam(value = "code", required = false) String code,
//            @RequestParam(value = "rawData", required = false) String rawData,
//            @RequestParam(value = "signature", required = false) String signature,
//            @RequestParam(value = "goodsId商品Id", required = false) Integer goodsId,
//            @RequestParam(value = "orderId订单Id", required = false) Integer orderId,
//            @RequestParam(value = "encryptedData", required = false) String encryptedData,
//            @RequestParam(value = "iv", required = false) String iv
//    ) throws Exception {
//        System.out.println(code);
//        if (code.equals("")) {
//            ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//            System.out.println(code + "为空");//判断传值code
//            return builder.body(ResponseUtils.getResponseBody("扫描获取失败"));
//        }
//        logger.info("Start get SessionKey");
//        Integer userId = null;
//        Map<String, Object> map = new HashMap<String, Object>();
//        //JSONObject rawDataJson = JSON.parseObject( rawData );
//        JSONObject SessionKeyOpenId = getSessionKeyOrOpenId(code);
//        String openid = (String) SessionKeyOpenId.get("openid");
//        String sessionKey = (String) SessionKeyOpenId.get("session_key");
//        System.out.println(openid + "这是获取的oppid");//oppid判断
//        System.out.println(sessionKey + "这是获取的sessionKey");
//        //uuid生成唯一key
//        String skey = UUID.randomUUID().toString();
//        //根据openid查询skey是否存在
//        String skey_redis = (String) redisTemplate.opsForValue().get(openid);
//        if (!StringUtils.isEmpty(skey_redis)) {
//            //存在 删除 skey 重新生成skey 将skey返回
//            redisTemplate.delete(skey_redis);
//            skey = UUID.randomUUID().toString();
//        }
//        //  缓存一份新的
//        JSONObject sessionObj = new JSONObject();
//        sessionObj.put("openId", openid);
//        sessionObj.put("sessionKey", sessionKey);
//        redisTemplate.opsForValue().set(skey, sessionObj.toJSONString());
//        redisTemplate.opsForValue().set(openid.toString(), skey);
//        //把新的sessionKey和oppenid返回给小程序
//        map.put("skey", skey);
//        map.put("result", "0");
//        JSONObject userInfo = getUserInfo(encryptedData, sessionKey, iv);
//        String unionId = "";
//        String nickName = "";
//        String avatarUrl = "";
//        if (userInfo != null) {
//            if (userInfo.get("unionId") != null) {
//                unionId = (String) userInfo.get("unionId");
//            }
//            nickName = userInfo.getString("nickName");
//            avatarUrl = userInfo.getString("avatarUrl");
//        }
//        logger.info("unionId是:" + unionId);
//        logger.info("goodsId是:" + goodsId);
//        logger.info("orderId是:" + orderId);
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        if (goodsId == null) {
//            logger.error("goodsId为空");
//            return builder.body(ResponseUtils.getResponseBody("goodsId为空"));
//        }
//        if (orderId == null) {
//            logger.error("orderId是空");
//            return builder.body(ResponseUtils.getResponseBody("orderId为空"));
//        }
//        Example example = new Example(HfUser.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("username", unionId);
//        List<HfUser> hfUserList = hfUserMapper.selectByExample(example);
//        if (hfUserList.size() == 0) {
//            logger.info("List" + hfUserList.size());
//            return builder.body(ResponseUtils.getResponseBody("请登录后操作"));
//        }
//        HfUser hfUser1 = hfUserMapper.selectByPrimaryKey(hfUserList.get(0));
//        logger.info("核销员对应的用户id" + hfUser1.getCancelId());
//        System.out.println(hfUser1);
//        if (hfUser1.getCancelId() == 0) {
//            return builder.body(ResponseUtils.getResponseBody("对不起你不是核销员无法核销商品"));
//        }
//        System.out.println(hfUser1.getCancelId() + "hfUser1.getCancelId()");
//        //判断核销的商品是否为自提商品
//        logger.info("是否自提对应goodsId的claim" + hfGoodsMapper.selectByPrimaryKey(goodsId).getClaim());
//        if (hfGoodsMapper.selectByPrimaryKey(goodsId).getClaim().equals(0)) {
//            return builder.body(ResponseUtils.getResponseBody("该商品不是自提商品"));
//        }
//        //判断核销员是否为该商品的核销员
//        logger.info("User的Id" + hfUserList.get(0).getId());
//        Example example1 = new Example(cancel.class);
//        Example.Criteria criteria1 = example1.createCriteria();
//        criteria1.andEqualTo("userId", hfUserList.get(0).getId());
//        List<cancel> cancelList = cancelsMapper.selectByExample(example1);
//        logger.info("cancel对应的User查询" + cancelList);
//
//        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
//        cancel cancel1 = cancelsMapper.selectByPrimaryKey(cancelList.get(0).getId());
//        logger.info("cancel的Id" + cancelList.get(0).getId());
//        logger.info("goods对应的cancelID" + hfGoods.getCancelId());
//        if (!hfGoods.getCancelId().equals(cancel1.getId())) {
//            return builder.body(ResponseUtils.getResponseBody("你不是该商品的核销员"));
//        }
//        //判断订单的商品与核销商品是否一致
//        //价格，根据订单id，设置订单状态
//        Example example2 = new Example(HfOrdersDetail.class);
//        Example.Criteria criteria2 = example2.createCriteria();
//        criteria2.andEqualTo("ordersId", orderId);
//        List<HfOrdersDetail> hfPriceList = hfOrdersCancelDetailMapper.selectByExample(example2);
//        HfOrdersDetail hfPrice = hfOrdersCancelDetailMapper.selectByPrimaryKey(hfPriceList.get(0));
//        System.out.println(hfPrice.getOrderDetailStatus());
//        if (hfPrice.getOrderDetailStatus().equals("已完成")) {
//            return builder.body(ResponseUtils.getResponseBody("该订单已被核销"));
//        }
//        if (!hfPrice.getGoogsId().equals(goodsId)) {
//            return builder.body(ResponseUtils.getResponseBody("订单核销的商品与实际不符合"));
//        }
//        HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
//        hfOrdersDetail.setModifyTime(LocalDateTime.now());
//        hfOrdersDetail.setId(hfPrice.getId());
//        hfOrdersDetail.setOrderDetailStatus("已完成");
//        hfOrdersCancelDetailMapper.updateByPrimaryKeySelective(hfOrdersDetail);
//        //添加核销记录
//        CancelRecord cancelRecord = new CancelRecord();
//        cancelRecord.setCreateDate(LocalDateTime.now());
//        cancelRecord.setModifyDate(LocalDateTime.now());
//        cancelRecord.setGoodsId(goodsId);
//        cancelRecord.setCancelId(cancel1.getId());
//        System.out.println(cancel1.getId() + "--cancel1.getId()--");//123456789
//        Example example3 = new Example(HfPrice.class);
//        Example.Criteria criteria3 = example3.createCriteria();
//        criteria3.andEqualTo("googsId", goodsId);
//        List<HfPrice> hfPriceList1 = hfPriceMapper.selectByExample(example3);
//        System.out.println("hfPriceList1:" + hfPriceList1);
//        System.out.println(hfPriceList1.get(0).getSellPrice());//1234564865
//        cancelRecord.setAmount(hfPriceList1.get(0).getSellPrice() * hfPrice.getPurchaseQuantity());
//        hfLogMapper.insert(cancelRecord);
//        //添加核销员核销额记录
//        cancel cancel = new cancel();
//        cancel.setId(cancel1.getId());
//
//        cancel.setModifyDate(LocalDateTime.now());
//        cancel.setMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getPurchaseQuantity() + cancel1.getMoney());
//        cancel.setPresentMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getPurchaseQuantity() + cancel1.getPresentMoney());
//        cancelsMapper.updateByPrimaryKeySelective(cancel);
////        HfUserExample example = new HfUserExample();
////        example.createCriteria().andUsernameEqualTo(unionId);
////        List<HfUser> list = hfUserMapper.selectByExample(example);
////        if(list.isEmpty()) {
////            HfUser hfUser = new HfUser();
////            hfUser.setAddress(avatarUrl);
////            hfUser.setNickName(nickName);
////            hfUser.setUsername(unionId);
////            hfUser.setCreateDate(LocalDateTime.now());
////            hfUser.setModifyDate(LocalDateTime.now());
////            hfUser.setIdDeleted((byte) 0);
////            hfUserMapper.insert(hfUser);
////            userId = hfUser.getId();
////        }else {
////            HfUser hfUser = list.get(0);
////            hfUser.setAddress(avatarUrl);
////            hfUser.setNickName(nickName);
////            hfUserMapper.updateByPrimaryKey(hfUser);
////            userId = hfUser.getId();
////        }
//        map.put("userId", userId);
//        map.put("userInfo", userInfo);
//
//        return builder.body(ResponseUtils.getResponseBody(map));
//    }
//
//    private JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
//        // 被加密的数据
//        byte[] dataByte = Base64.getDecoder().decode(encryptedData);
//        // 加密秘钥
//        byte[] keyByte = Base64.getDecoder().decode(sessionKey);
//        // 偏移量
//        byte[] ivByte = Base64.getDecoder().decode(iv);
//        try {
//            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
//            int base = 16;
//            if (keyByte.length % base != 0) {
//                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
//                byte[] temp = new byte[groups * base];
//                Arrays.fill(temp, (byte) 0);
//                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
//                keyByte = temp;
//            }
//            // 初始化
//            Security.addProvider(new BouncyCastleProvider());
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
//            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
//            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
//            parameters.init(new IvParameterSpec(ivByte));
//            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
//            byte[] resultByte = cipher.doFinal(dataByte);
//            if (null != resultByte && resultByte.length > 0) {
//                String result = new String(resultByte, "UTF-8");
//                return JSON.parseObject(result);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            logger.error(e.getMessage(), e);
//        } catch (NoSuchPaddingException e) {
//            logger.error(e.getMessage(), e);
//        } catch (InvalidParameterSpecException e) {
//            logger.error(e.getMessage(), e);
//        } catch (IllegalBlockSizeException e) {
//            logger.error(e.getMessage(), e);
//        } catch (BadPaddingException e) {
//            logger.error(e.getMessage(), e);
//        } catch (UnsupportedEncodingException e) {
//            logger.error(e.getMessage(), e);
//        } catch (InvalidKeyException e) {
//            logger.error(e.getMessage(), e);
//        } catch (InvalidAlgorithmParameterException e) {
//            logger.error(e.getMessage(), e);
//        } catch (NoSuchProviderException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return null;
//
//    }
//
//
//    private JSONObject getSessionKeyOrOpenId(String code) {
//        //微信端登录code
//        String wxCode = code;
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=wxfa188a42d843a0b0&secret=0433593dd1887ea5381e6d01308f81ba&js_code=" + code + "&grant_type=authorization_code";
//        Map<String, String> requestUrlParam = new HashMap<String, String>();
////		requestUrlParam.put( "appid","wx16159fcc93b0400c" );//小程序appId
////		requestUrlParam.put( "secret","1403f2e207dfa2f1f348910626f5aa42" );
////		requestUrlParam.put( "js_code",wxCode );//小程序端返回的code
////		requestUrlParam.put( "grant_type","authorization_code" );//默认参数
////		//发送post请求读取调用微信接口获取openid用户唯一标识
////		String str = UrlUtil.sendPost( requestUrl,requestUrlParam );
////		JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost( requestUrl,requestUrlParam ));
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(requestUrl);
//        JSONObject jsonObject = null;
//
//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                String result = EntityUtils.toString(entity, "UTF-8");
//                jsonObject = JSONObject.parseObject(result);
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return jsonObject;
//    }
}