package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.model.HfSetBuyMember;
import com.hanfu.user.center.model.HfUserMember;
import com.hanfu.user.center.service.HfUserMemberService;
import com.hanfu.user.center.service.HfsetMemeberService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @ClassName HfUserMemberController
 * @Date 2019/12/19 10:01
 * @Author CONSAK
 **/
@Api
@CrossOrigin
@RestController
@RequestMapping("/member")
public class HfUserMemberController {

	@Autowired
	private HfUserMemberService hfUserMemberService;

	@Autowired
	private HfsetMemeberService hfsetMemeberService;

	@RequestMapping(value = "/rechargeMember",method = RequestMethod.GET)
	@ApiOperation(value = "充值会员",notes = "充值会员")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "money", value = "会员月数对应的金额", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "number", value = "会员月数", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "total", value = "充值的金额", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> rechargeMember(@RequestParam(required = true, defaultValue = "") Integer userId,
			@RequestParam(required = true, defaultValue = "") Integer money,
			@RequestParam(required = true, defaultValue = "") Integer number,
			@RequestParam(required = true, defaultValue = "") Integer total) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		LocalDateTime time = LocalDateTime.now();
		LocalDateTime thirtyTime = LocalDateTime.now();
		LocalDateTime seasonTime = LocalDateTime.now();
		LocalDateTime yearTime = LocalDateTime.now();
		thirtyTime = time.minus(-1, ChronoUnit.MONTHS);
		seasonTime = time.minus(-3, ChronoUnit.MONTHS);
		yearTime = time.minus(-12, ChronoUnit.MONTHS);

		//先判断用户是不是第一次充值或者购买会员
		HfUserMember hfUserMember = hfUserMemberService.itExistUserById(userId);

		//如果用户不是第一次充值会员  直接修改会员表和余额表就可以了
		if (hfUserMember != null) {

			System.out.println("不是第一次开会员");
			//不是第一次开会员得先看他会员过期没有
			String str = hfUserMemberService.getModifyTime(userId);
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime parse = LocalDateTime.parse(str, df);//这个是用户的过期时间
			System.out.println("这是用户的过期日期：" + parse);
			System.out.println("这是当前时间:" + time);
			boolean parseBefore = parse.isBefore(time);

			if (parseBefore) {//如果过期时间在当前时间前面  则已经过期
				System.out.println("已经过期");
				//已经过期重新设置会员开通、过期时间，但余额表的余额是在原来的基础上相加
				hfUserMemberService.updateModify(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
				hfUserMemberService.updateBalance(userId, total);
				return builder.body(ResponseUtils.getResponseBody("会员开通成功"));
			} else {
				System.out.println("没有过期");
				//没有过期的话 过期日期在加上对应的时间  余额表加上即可
				hfUserMemberService.updateModifyTime(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
				hfUserMemberService.updateBalance(userId, total);
				return builder.body(ResponseUtils.getResponseBody("会员开通成功"));
			}

		} else {
			System.out.println("第一次开会员");
			//第一次开会员  根据充值的金额分别开通不同的时间
			if (total == money && number == 1) {
				hfUserMemberService.insertthirtyTime(userId, time, thirtyTime);
			}
			if (total == money && number == 3) {
				hfUserMemberService.insertseasonTime(userId, time, seasonTime);
			}
			if (total == money && number == 12) {
				hfUserMemberService.insertyearTime(userId, time, yearTime);
			}
			hfUserMemberService.insertBalance(userId, total);
			return builder.body(ResponseUtils.getResponseBody("会员开通成功"));
		}
	}


	@ApiOperation(value = "购买会员",notes = "购买会员")
	@RequestMapping(value = "buyMember",method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "money", value = "会员月数对应的金额", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "number", value = "会员月数", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "total", value = "充值的金额", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> buyMember(@RequestParam(required = true, defaultValue = "") Integer userId,
			@RequestParam(required = true, defaultValue = "") Integer money,
			@RequestParam(required = true, defaultValue = "") Integer number,
			@RequestParam(required = true, defaultValue = "") Integer total) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		LocalDateTime time = LocalDateTime.now();
		LocalDateTime thirtyTime = LocalDateTime.now();
		LocalDateTime seasonTime = LocalDateTime.now();
		LocalDateTime yearTime = LocalDateTime.now();
		thirtyTime = time.minus(-1, ChronoUnit.MONTHS);
		seasonTime = time.minus(-3, ChronoUnit.MONTHS);
		yearTime = time.minus(-12, ChronoUnit.MONTHS);

		//先判断用户是不是第一次充值会员
		HfUserMember hfUserMember = hfUserMemberService.itExistUserById(userId);

		if (null != hfUserMember) {
			System.out.println("不是第一次购买会员");
			//不是第一次购买会员  判断他会员过期没有
			String str = hfUserMemberService.getModifyTime(userId);
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime parse = LocalDateTime.parse(str, df);//这个是用户的过期时间
			System.out.println("这是用户的过期日期：" + parse);
			System.out.println("这是当前时间:" + time);
			boolean parseBefore = parse.isBefore(time);

			if (parseBefore) {//如果过期时间在当前时间前面  则已经过期
				System.out.println("已经过期");
				//已经过期重新设置会员开通、过期时间，但余额表的余额是在原来的基础上相加
				hfUserMemberService.buyupdateModify(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
				return builder.body(ResponseUtils.getResponseBody("购买会员成功"));
			} else {
				System.out.println("没有过期");
				hfUserMemberService.buyupdateModifyTime(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
				return builder.body(ResponseUtils.getResponseBody("购买会员成功"));
			}

		} else {
			System.out.println("是第一次购买会员");
			//第一次开会员  根据充值的金额分别开通不同的时间
			if (total == money && number == 1) {
				hfUserMemberService.insertthirtyTime(userId, time, thirtyTime);
			}
			if (total == money && number == 3) {
				hfUserMemberService.insertseasonTime(userId, time, seasonTime);
			}
			if (total == money && number == 12) {
				hfUserMemberService.insertyearTime(userId, time, yearTime);
			}
			return builder.body(ResponseUtils.getResponseBody("购买会员成功"));
		}
	}


	@ApiOperation(value = "设置购买会员钱数月份",notes = "设置会员钱数月份")
	@RequestMapping(value = "/setbuyMember",method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "total", value = "金额", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "number", value = "金额对应的月份", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> setbuyMember(@RequestParam(required = true,defaultValue = "") Integer total,
			@RequestParam(required = true,defaultValue = "") Integer number) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfSetBuyMember hfSetBuyMember = hfsetMemeberService.select(number);
		if (null==hfSetBuyMember){
			System.out.println("没有");
			hfsetMemeberService.insert(number,total);
			return builder.body(ResponseUtils.getResponseBody("设置成功"));
		}else{
			System.out.println("有");
			hfsetMemeberService.update(number,total);
			return builder.body(ResponseUtils.getResponseBody("设置成功"));
		}

	}

	@ApiOperation(value = "设置充值会员钱数月份",notes = "设置会员钱数月份")
	@RequestMapping(value = "/setrechargeMember",method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "total", value = "金额", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "number", value = "金额对应的月份", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> setrechargeMember(@RequestParam(required = true,defaultValue = "") Integer total,
			@RequestParam(required = true,defaultValue = "") Integer number) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfSetBuyMember hfSetBuyMember = hfsetMemeberService.newselect(number);
		if (null==hfSetBuyMember){
			System.out.println("没有");
			hfsetMemeberService.newinsert(number,total);
			return builder.body(ResponseUtils.getResponseBody("设置成功"));
		}else{
			System.out.println("有");
			hfsetMemeberService.newupdate(number,total);
			return builder.body(ResponseUtils.getResponseBody("设置成功"));
		}

	}

	@ApiOperation(value = "查询购买会员",notes = "查询购买会员")
	@RequestMapping(value = "/selectBuy",method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectBuy() throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		List<HfSetBuyMember> list = hfsetMemeberService.selectBuy();

		Object[] objects = list.toArray();
		for(Object a:objects){
			System.out.println(a);
		}

		return builder.body(ResponseUtils.getResponseBody(objects));
	}

	@ApiOperation(value = "查询充值会员",notes = "查询充值会员")
	@RequestMapping(value = "/selectRe",method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectRe() throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		List<HfSetBuyMember> list = hfsetMemeberService.selectRe();

		Object[] objects = list.toArray();
		for(Object a:objects){
			System.out.println(a);
		}

		return builder.body(ResponseUtils.getResponseBody(objects));
	}
}