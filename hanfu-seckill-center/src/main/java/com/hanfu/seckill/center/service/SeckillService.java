package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.controller.KillController;
import com.hanfu.seckill.center.model.Seckill;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/11
 * @time: 10:41
 */
public interface SeckillService {
    Integer getRepertory(Integer goodsId, Integer bossId);
    void updateRepertory(Integer goodsId, Integer bossId, Integer repertory);
    void insertSeckill(Integer bossId, Integer goodsId, Date startTime,Date stopTime,  Integer categoryId, Double price, Integer repertory);
    void updateIsDeleted(Integer goodsId,Short isDeleted, Integer bossId);
    void deleteByPrimaryKey(Integer id);
    void updateByPrimaryKey( Integer id, Integer bossId, Integer goodsId,  Date startTime,Date stopTime,  Integer categoryId, Double price,
                             Integer repertory);
    List<Seckill>selectSeckill(Integer bossId);
    List<Seckill> selectGoodsId(Integer goodsId);
    void updateState(Integer id);
    Seckill selectId(Integer id);
    List<Seckill> selectAll(Integer bossId);

}