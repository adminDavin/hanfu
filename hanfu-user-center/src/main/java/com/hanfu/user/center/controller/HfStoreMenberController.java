package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.HfBossMapper;
import com.hanfu.user.center.dao.HfStoneMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.dao.hfStoreMenberMapper;
import com.hanfu.user.center.manual.model.StoreUser;
import com.hanfu.user.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@CrossOrigin
@RestController
@RequestMapping("/HfStoreMenber")
public class HfStoreMenberController {
    @Autowired
    hfStoreMenberMapper hfStoreMenberMappers;
    @Autowired
    HfBossMapper hfBossMapper;
    @Autowired
    HfStoneMapper hfStoneMapper;
    @Autowired
    HfUserMapper hfUserMapper;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "店铺添加成员", notes = "店铺添加成员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> add(hfStoreMenber hfStoreMenbers) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(hfStoreMenberMappers.insert(hfStoreMenbers)));
    }



    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "店铺成员查询", notes = "店铺成员查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "storeId", value = "店铺id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> select(Integer storeId,Integer bossId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<StoreUser> storeUsers = new ArrayList<>();

        hfStoreMenberExample hfStoreMenbersExample = new hfStoreMenberExample();
        hfStoreMenbersExample.createCriteria().andStoreIdEqualTo(storeId).andIsDeletedEqualTo((short) 0);
        List<hfStoreMenber> hfStoreMenber = hfStoreMenberMappers.selectByExample(hfStoreMenbersExample);
//        storeUsers.forEach(storeUser -> {
            hfStoreMenber.forEach(hfStoreMenber1 -> {
                StoreUser storeUser = new StoreUser();
                System.out.println(hfStoreMenber1);
                storeUser.setCreatetime(hfStoreMenber1.getCreateTime());
                storeUser.setIsCancel(hfStoreMenber1.getIsCancel());
                storeUser.setLastModifier(hfStoreMenber1.getLastModifier());
                storeUser.setPhone(hfStoreMenber1.getPhone());
                storeUser.setStoreId(hfStoreMenber1.getStoreId());
                storeUser.setUserId(hfStoreMenber1.getUserId());
                storeUser.setStoreRole(hfStoreMenber1.getStoreRole());
                HfStoneExample hfStoneExample = new HfStoneExample();
                hfStoneExample.createCriteria().andIdEqualTo(hfStoreMenber1.getStoreId()).andIsDeletedEqualTo((short) 0);
                List<HfStone> hfStones= hfStoneMapper.selectByExample(hfStoneExample);
                hfStones.forEach(hfStone -> {
                    storeUser.setStoreDesc(hfStone.getHfDesc());
                    storeUser.setStoreName(hfStone.getHfName());
                    storeUser.setStoreAddress(hfStone.getAddress());
                });
                HfBossExample hfBossExample = new HfBossExample();
                hfBossExample.createCriteria().andIdEqualTo(bossId).andIsDeletedEqualTo((short) 0);
                List<HfBoss> hfBosses= hfBossMapper.selectByExample(hfBossExample);
                hfBosses.forEach(hfBoss -> {
                    storeUser.setBossName(hfBoss.getName());
                    storeUser.setBossId(bossId);
                });

                HfUserExample hfUserExample = new HfUserExample();
                hfUserExample.createCriteria().andIdEqualTo(hfStoreMenber1.getUserId()).andIdDeletedEqualTo((byte) 0);
                List<HfUser> hfUsers= hfUserMapper.selectByExample(hfUserExample);
                storeUser.setUserName(hfUsers.get(0).getRealName());
                storeUsers.add(storeUser);
                System.out.println(storeUser);
            });
//        });
        return builder.body(ResponseUtils.getResponseBody(storeUsers));
    }

    @RequestMapping(value = "/deleted", method = RequestMethod.GET)
    @ApiOperation(value = "店铺成员删除", notes = "店铺成员删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "StoreId", value = "店铺id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> deleted(Integer StoreId,Integer userId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        hfStoreMenber hfStoreMenbers = new hfStoreMenber();
        hfStoreMenbers.setIsDeleted((short) 1);
        hfStoreMenberExample hfStoreMenberExamples = new hfStoreMenberExample();
        hfStoreMenberExamples.createCriteria().andUserIdEqualTo(userId).andStoreIdEqualTo(StoreId);
        hfStoreMenberMappers.updateByExampleSelective(hfStoreMenbers,hfStoreMenberExamples);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ApiOperation(value = "店铺成员修改", notes = "店铺成员修改")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "StoreId", value = "店铺id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> update(hfStoreMenber hfStoreMenber) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        hfStoreMenber hfStoreMenbers = new hfStoreMenber();
        hfStoreMenbers.setIsDeleted((short) 1);
        hfStoreMenberExample hfStoreMenberExamples = new hfStoreMenberExample();
        hfStoreMenberExamples.createCriteria().andUserIdEqualTo(hfStoreMenber.getUserId()).andStoreIdEqualTo(hfStoreMenber.getStoreId());
        hfStoreMenberMappers.updateByExampleSelective(hfStoreMenbers,hfStoreMenberExamples);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

}
