package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.*;
import com.hanfu.user.center.manual.model.StoreUser;
import com.hanfu.user.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @Autowired
    CancelMapper cancelMapper;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "店铺添加成员", notes = "店铺添加成员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> add(hfStoreMenber hfStoreMenbers,@RequestParam(value = "ids")List<Integer> ids) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (hfStoreMenbers.getIsCancel()==null){
            hfStoreMenbers.setIsCancel(0);
        }
        for (Integer id : ids) {
            hfStoreMenbers.setUserId(id);
            hfStoreMenberExample hfStoreMenberExamples = new hfStoreMenberExample();
            hfStoreMenberExamples.createCriteria().andUserIdEqualTo(hfStoreMenbers.getUserId()).andIsDeletedEqualTo((short) 0).andStoreIdEqualTo(hfStoreMenbers.getStoreId());

            if (hfStoreMenberMappers.selectByExample(hfStoreMenberExamples).size() != 0) {
                return builder.body(ResponseUtils.getResponseBody("此人已经是此店铺管理员"));
            }
            hfStoreMenberExample hfStoreMenberExamples1 = new hfStoreMenberExample();
            hfStoreMenberExamples1.createCriteria().andStoreIdEqualTo(hfStoreMenbers.getStoreId()).andUserIdEqualTo(hfStoreMenbers.getUserId()).andIsDeletedEqualTo((short) 1);
            if (hfStoreMenberMappers.selectByExample(hfStoreMenberExamples1).size() != 0) {
                hfStoreMenbers.setIsDeleted((short) 0);
                hfStoreMenbers.setCreateTime(LocalDateTime.now());
                hfStoreMenbers.setModifyTime(LocalDateTime.now());
                hfStoreMenberMappers.updateByExampleSelective(hfStoreMenbers, hfStoreMenberExamples1);

            } else {
                hfStoreMenbers.setIsDeleted((short) 0);
                hfStoreMenbers.setCreateTime(LocalDateTime.now());
                hfStoreMenbers.setModifyTime(LocalDateTime.now());
                if (hfStoreMenbers.getIsCancel() != 0) {
                    hfStoreMenbers.setIsCancel(addCancel(hfStoreMenbers.getUserId()));
                }
                hfStoreMenberMappers.insert(hfStoreMenbers);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(ids.size()));
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
                storeUser.setRealName(hfUsers.get(0).getNickName());
                storeUser.setUserPhone(hfUsers.get(0).getPhone());

                CancelExample cancelExample = new CancelExample();
//                System.out.println(hfStoreMenber1.getUserId()+"qwqwqwq"+hfStoreMenber1.getIsCancel());
                cancelExample.createCriteria().andUserIdEqualTo(hfStoreMenber1.getUserId()).andIsDeletedEqualTo(0).andIdEqualTo(hfStoreMenber1.getIsCancel());
//                System.out.println(cancelMapper.selectByExample(cancelExample).get(0).getId()+"--------------1");
//                System.out.println(cancelMapper.selectByExample(cancelExample).get(0).getUserId()+"--------------2");
                if (cancelMapper.selectByExample(cancelExample).size()!=0){
                    storeUser.setIsCancel(1);
                    storeUser.setCancelId(hfStoreMenber1.getIsCancel());
                }else {
                    storeUser.setIsCancel(0);
                }
                storeUsers.add(storeUser);
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

    @RequestMapping(value = "/isCancel", method = RequestMethod.GET)
    @ApiOperation(value = "是否核销权限", notes = "是否核销权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "isCancel", value = "是否", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> isCancel(Integer userId,Integer isCancel,Integer stoneId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();

        hfStoreMenberExample hfStoreMenbersExample = new hfStoreMenberExample();
        hfStoreMenbersExample.createCriteria().andStoreIdEqualTo(stoneId).andIsDeletedEqualTo((short) 0).andUserIdEqualTo(userId);
        List<hfStoreMenber> hfStoreMenber = hfStoreMenberMappers.selectByExample(hfStoreMenbersExample);

        Cancel cancel = new Cancel();
        if (isCancel==1){
            CancelExample cancelExample = new CancelExample();
            cancelExample.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo(1).andIdEqualTo(hfStoreMenber.get(0).getIsCancel());
            CancelExample cancelExample1 = new CancelExample();
            cancelExample1.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo(0).andIdEqualTo(hfStoreMenber.get(0).getIsCancel());
            if (cancelMapper.selectByExample(cancelExample1).size()!=0){
                return builder.body(ResponseUtils.getResponseBody("已经是核销员"));
            }
            if (cancelMapper.selectByExample(cancelExample).size()!=0){
                cancel.setIsDeleted(0);
                cancelMapper.updateByExampleSelective(cancel,cancelExample);
            } else {
                hfStoreMenber hfStoreMenber1 = new hfStoreMenber();
                hfStoreMenber1.setIsCancel(addCancel(userId));
                hfStoreMenberMappers.updateByExampleSelective(hfStoreMenber1,hfStoreMenbersExample);
            }
        }else {
            cancel.setIsDeleted(1);
            CancelExample cancelExample = new CancelExample();
            cancelExample.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo(0);
            cancelMapper.updateByExampleSelective(cancel,cancelExample);
        }

        return builder.body(ResponseUtils.getResponseBody(0));
    }

    private Integer addCancel(Integer userId){
        Cancel cancel = new Cancel();
        cancel.setUserId(userId);
        cancel.setIsDeleted(0);
        cancel.setCreateDate(LocalDateTime.now());
        cancel.setModifyDate(LocalDateTime.now());
        cancel.setMoney(0);
        cancel.setPresentMoney(0);
        cancelMapper.insert(cancel);
        return cancel.getId();
    }

}
