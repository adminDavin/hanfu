package com.hanfu.shejiao.center.controller;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.sl.usermodel.ObjectMetaData.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.shejiao.center.config.TenpayConfig;
import com.hanfu.shejiao.center.dao.SjFileDescMapper;
import com.hanfu.shejiao.center.dao.SjUserMapper;
import com.hanfu.shejiao.center.manual.model.ConstantInfo;
import com.hanfu.shejiao.center.manual.model.PayTypeEnum;
import com.hanfu.shejiao.center.manual.model.TenPayVO;
import com.hanfu.shejiao.center.model.SjFileDesc;
import com.hanfu.shejiao.center.model.SjUser;
import com.hanfu.shejiao.center.service.PayService;
import com.hanfu.shejiao.center.utils.Message;
import com.hanfu.shejiao.center.utils.RandomString;
import com.hanfu.shejiao.center.utils.TenPayUtils;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@Api
@RequestMapping("/login")
@CrossOrigin
public class SjLoginController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
    private TenpayConfig tenpayConfig;

	@Autowired
	private SjUserMapper sjUserMapper;
	
	@Autowired
	private SjFileDescMapper sjFileDescMapper;
	
	@Autowired
	private PayService payService;

	@RequestMapping(path = "/loginCode", method = RequestMethod.POST)
	@ApiOperation(value = "手机号验证码登录", notes = "手机号验证码登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, type = "Integer"),
//    })
	public ResponseEntity<JSONObject> loginCode(String phone, Integer code, HttpServletResponse response)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Object result = redisTemplate.opsForValue().get(phone + "shejiao");
		if (code == null) {
			return builder.body(ResponseUtils.getResponseBody("验证码为null"));
		}
		if (result == null || !(Integer.valueOf((String) result) == code)) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
			return builder.body(ResponseUtils.getResponseBody("0"));
		}
		SjUser user = new SjUser();
		user.setPhone(phone);
		user.setUsername("SJ" + RandomString.GetRandomString(8));
		sjUserMapper.insert(user);
		return builder.body(ResponseUtils.getResponseBody(user.getId()));
	}

	@RequestMapping(path = "/getCode", method = RequestMethod.GET)
	@ApiOperation(value = "获取验证码", notes = "获取验证码")
	public ResponseEntity<JSONObject> getCode(String phone) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (!StringUtils.isEmpty(phone)) {
			String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
			Pattern p = Pattern.compile(s2);
			Matcher m = p.matcher(phone);
			boolean b = m.matches();
			if (b) {
				Integer code = Message.sendSms(phone);
				redisTemplate.opsForValue().set(phone + "shejiao", String.valueOf(code));
				return builder.body(ResponseUtils.getResponseBody(code));
			}
			return builder.body(ResponseUtils.getResponseBody("手机号有误"));
		} else {
			return builder.body(ResponseUtils.getResponseBody("请输入手机号"));
		}
	}

	@RequestMapping(path = "/updateUserInfo", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public ResponseEntity<JSONObject> updateUserInfo(SjUser info, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		SjUser user = sjUserMapper.selectByPrimaryKey(info.getId());
		if (file != null) {
			SjFileDesc fileDesc = new SjFileDesc();
			FileMangeService fileMangeService = new FileMangeService();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(os);
			String arr[];
			arr = fileMangeService.uploadFile(os.toByteArray(), String.valueOf(user.getId()));
			fileDesc = new SjFileDesc();
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setFileName(file.getOriginalFilename());
			fileDesc.setIsDeleted((short) 0);
			sjFileDescMapper.insert(fileDesc);
			user.setFileId(fileDesc.getId());
		}
		if (!StringUtils.isEmpty(info.getUsername())) {
			user.setUsername(info.getUsername());
		}
		if (!StringUtils.isEmpty(info.getSex())) {
			user.setSex(info.getSex());
		}
		if (!StringUtils.isEmpty(info.getBirthDay())) {
			user.setBirthDay(info.getBirthDay());
		}
		if (!StringUtils.isEmpty(info.getRegion())) {
			user.setRegion(info.getRegion());
		}
		if (!StringUtils.isEmpty(info.getAddress())) {
			user.setAddress(info.getAddress());
		}
		sjUserMapper.updateByPrimaryKey(user);
		return builder.body(ResponseUtils.getResponseBody(user.getId()));
	}
	
	@RequestMapping(path = "/adminLogin", method = RequestMethod.POST)
	@ApiOperation(value = "cs", notes = "cs")
	public ResponseEntity<JSONObject> adminLogin(String username, String password) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		System.out.println("username" + username);
		if("sunwangda".equals(username) && "123456".equals(password)) {
			return builder.body(ResponseUtils.getResponseBody(1));
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@RequestMapping(path = "/cscs", method = RequestMethod.GET)
	@ApiOperation(value = "cscs", notes = "cscs")
	public ResponseEntity<JSONObject> cscs(String payType, String outTradeNo, BigDecimal decimal) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Map<String, String> map = payRequest(payType,outTradeNo,decimal);
//		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		return builder.body(ResponseUtils.getResponseBody(map));
	}
	
	
	 /** 
     * @Description: 支付请求
     * @Param: [payType, outTradeNo, totalAmount]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: zengXianKang
     * @Date: 2019/7/28 
     */
    public Map<String, String> payRequest(String payType, String outTradeNo, BigDecimal totalAmount) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        
        switch (PayTypeEnum.valueOf(payType)) {
            case TENPAY://财付通
                SortedMap<String, Object> paramsMap = new TreeMap<String, Object>();
                //公众账号ID
                paramsMap.put("appid", tenpayConfig.getAppId());
                //商户号
                paramsMap.put("mch_id", tenpayConfig.getMchId());
                System.out.println("mch_id++++++"+tenpayConfig.getMchId());
                //随机字符串
                paramsMap.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
                //描述
                paramsMap.put("body", "名绘优家订单支付");
                //商户订单号(支付编号)
                paramsMap.put("out_trade_no", outTradeNo);
                //支付金额,金额单位为 分
                double price = totalAmount.doubleValue();
                int totalFee = (int) (price * 100);
                paramsMap.put("total_fee", String.valueOf(totalFee));
                //回调地址
                paramsMap.put("notify_url", ConstantInfo.TENPAY_ORDER_CALLBACK);
                //交易类型
                paramsMap.put("trade_type", "APP");
                //用户端ip
                String spbillCreateIp = "";
                InetAddress inetAddress = InetAddress.getLocalHost();
                if (inetAddress != null) {
                    spbillCreateIp = inetAddress.getHostAddress();
                }
                paramsMap.put("spbill_create_ip", spbillCreateIp);
                TenPayUtils tenPayUtils = new TenPayUtils();
                //sign签名
                String sign = tenPayUtils.createSign(paramsMap, "UTF-8");
                paramsMap.put("sign", sign);
                //请求报文
                String requestXml = TenPayUtils.tenPayXmlInfo(paramsMap);
                //logger.info("微信支付请求报文: " + requestXml);

                //发送微信支付post请求
                String tenPayPost = TenPayUtils.httpsRequest(tenpayConfig.getPayUrl(), "POST", requestXml);
                
                System.out.println("tenPayPost++++"+tenPayPost);
                //获取返回
                Map<String, String> tenPayMap = (Map<String, String>) TenPayUtils.readXml(tenPayPost, "xml", "");
                
                System.out.println("tenPayMap----------"+tenPayMap);
                //微信返回状态码
                if (!tenPayMap.get("return_code").equals("SUCCESS")) {
                    logger.error("微信支付请求连接失败: " + tenPayMap.get("return_msg"));
//                    throw new RRException(AppWSConstant.RS_MSG_TENPAY_FALL);
                }
                //业务结果
                if (!tenPayMap.get("result_code").equals("SUCCESS")) {
                    logger.error("err_code: " + tenPayMap.get("err_code"), "err_code_des: " + tenPayMap.get("err_code_des"));
//                    throw new RRException(AppWSConstant.RS_MSG_TENPAY_FALL);
                }
                //APPID
                map.put("appid", tenPayMap.get("appid"));
                //商户号
                map.put("partnerid", tenPayMap.get("mch_id"));
                //预支付交易会话ID
                map.put("prepayid", tenPayMap.get("prepay_id"));
                //扩展字段
                map.put("package", tenpayConfig.getPackageValue());
                //随机字符串
                map.put("noncestr", tenPayMap.get("nonce_str"));
                //时间戳
                map.put("timestamp", String.valueOf(new Date().getTime()).substring(0, 10));
                SortedMap<String, Object> signMap = new TreeMap<>(map);
                String newSign = tenPayUtils.createSign(signMap, "UTF-8");
                //签名
                map.put("sign", newSign);
                break;
            default:
                break;
        }
        return map;
    }
    
    
    /** 
     * @Description: 订单微信支付回调
     *
     * @Param: [request]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    @RequestMapping(value = "/tenPayOrderCallBack", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> tenPayOrderCallBack(HttpServletRequest request){
    	System.out.println("/tenPayOrderCallBack/tenPayOrderCallBack/tenPayOrderCallBack");
        Map<String, String> map = new HashMap<String, String>();
        try {
            TenPayVO tenPayVO = payService.tenPayCallBack(request);
//            payService.tenPayOrderCallBack(tenPayVO);
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("mapmapmap"+map);
        return map;
    }
    
    /**
     * 定时任务:每十五分钟触发一次主动调用订单支付回调
     */
    @Scheduled(cron = "0 */15 * * * ?")
    public void initiativeOrderPayCallBack(){
        //主动调用订单支付回调
        try {
            payService.initiativeOrderPayCallBack();
        } catch (Exception e) {
            logger.error("timer initiativeOrderPayCallBack Error.", e);
            e.printStackTrace();
        }
    }
    
}
