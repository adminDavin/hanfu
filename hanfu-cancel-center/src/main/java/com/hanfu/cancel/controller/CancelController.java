package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.cancel.dao.*;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;

import com.hanfu.cancel.model.HfOrders;
import com.hanfu.cancel.model.HfOrdersDetail;
import com.hanfu.cancel.model.HfUser;
import com.hanfu.cancel.model.cancel;
import com.hanfu.cancel.service.CancelService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.util.*;

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
    private HfOrdersMapper hfOrdersMapper;
    @Autowired
    private HfOrdersDetailMapper hfOrdersDetailMapper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/selectCancel", method = RequestMethod.GET)
    @ApiOperation(value = "核销员信息", notes = "核销员信息")
    public ResponseEntity<JSONObject> selectCancel() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(cancelService.select()));
    }

    @RequestMapping(value = "/insertCancel", method = RequestMethod.GET)
    @ApiOperation(value = "增加核销员", notes = "增加核销员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "UserId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "site", value = "核销地点", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "GoodsId", value = "需要核销的商品", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> insertCancel(int UserId,String site,int GoodsId) throws Exception {
        System.out.println(UserId);
        System.out.println(site);
        System.out.println(GoodsId);
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser hfUser = new HfUser();
        hfUser.setId(UserId);

        HfUser hfUser1 = hfUserMapper.selectByPrimaryKey(hfUser);
        if (hfUser1==null){
            return builder.body(ResponseUtils.getResponseBody("用户不存在"));
        }
        Integer cancel = hfUser1.getCancelId();
        System.out.println(hfUser1.getCancelId());
        if (cancel!=0){
            return builder.body(ResponseUtils.getResponseBody("该人员已经是核销员"));
        }
        hfUser.setCancelId(1);
        hfUserMapper.updateByPrimaryKeySelective(hfUser);
        cancel cancel1=new cancel();
        cancel1.setGoodsId(GoodsId);
        cancel1.setCreateDate(LocalDateTime.now());
        cancel1.setModifyDate(LocalDateTime.now());
        cancel1.setUserId(UserId);
        cancel1.setSite(site);
        cancelsMapper.insert(cancel1);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(value = "/updateCancel", method = RequestMethod.GET)
    @ApiOperation(value = "修改核销员信息", notes = "修改核销员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "UserId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "site", value = "核销地点", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "GoodsId", value = "需要核销的商品", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "cancel", value = "是否为核销员0否默认0,1是", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> updateCancel(int UserId,String site,int GoodsId,int cancel2) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser hfUser = new HfUser();
        hfUser.setId(UserId);
        hfUser.setCancelId(cancel2);
        hfUserMapper.updateByPrimaryKeySelective(hfUser);
        cancel cancel1 = new cancel();
        cancel1.setSite(site);
        cancel1.setUserId(UserId);
        cancel1.setCreateDate(LocalDateTime.now());
        cancel1.setModifyDate(LocalDateTime.now());
        cancel1.setGoodsId(GoodsId);
        Example example = new Example(cancel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",UserId);
        cancelsMapper.updateByExampleSelective(cancel1,example);
        return builder.body(ResponseUtils.getResponseBody(cancelService.select()));
    }

//    @RequestMapping(path = "/wxLogin",  method = RequestMethod.GET)
//    @ApiOperation(value = "微信登录", notes = "微信登录")
//    public ResponseEntity<JSONObject> wxLogin(
//                                              @RequestParam(value = "code",required = false) String code,
//                                              @RequestParam(value = "rawData",required = false) String rawData,
//                                              @RequestParam(value = "signature",required = false) String signature,
//                                              @RequestParam(value = "encryptedData",required = false) String encryptedData,
//                                              @RequestParam(value = "iv",required = false) String iv
//    ) throws Exception{
//        logger.info( "Start get SessionKey" );
//        Integer userId = null;
//        Map<String,Object> map = new HashMap<String, Object>();
//        //JSONObject rawDataJson = JSON.parseObject( rawData );
//        JSONObject SessionKeyOpenId = getSessionKeyOrOpenId( code );
//        String openid = (String) SessionKeyOpenId.get("openid");
//        String sessionKey = (String) SessionKeyOpenId.get( "session_key" );
//        //uuid生成唯一key
//        String skey = UUID.randomUUID().toString();
//        //根据openid查询skey是否存在
//        String skey_redis =(String) redisTemplate.opsForValue().get( openid );
//        if(!StringUtils.isEmpty(skey_redis)){
//            //存在 删除 skey 重新生成skey 将skey返回
//            redisTemplate.delete( skey_redis );
//            skey = UUID.randomUUID().toString();
//        }
//        //  缓存一份新的
//        JSONObject sessionObj = new JSONObject();
//        sessionObj.put( "openId",openid );
//        sessionObj.put( "sessionKey",sessionKey );
//        redisTemplate.opsForValue().set( skey,sessionObj.toJSONString() );
//        redisTemplate.opsForValue().set( openid.toString(),skey );
//        //把新的sessionKey和oppenid返回给小程序
//        map.put( "skey",skey );
//        map.put( "result","0" );
//        JSONObject userInfo = getUserInfo( encryptedData, sessionKey, iv );
//        String unionId = "";
//        String nickName = "";
//        String avatarUrl = "";
//        if(userInfo != null) {
//            if(userInfo.get("unionId") != null) {
//                unionId = (String) userInfo.get("unionId");
//            }
//            nickName = 	userInfo.getString("nickName");
//            avatarUrl = userInfo.getString("avatarUrl");
//        }
//
//
//
//
//
//
//        HfUserExample example = new HfUserExample();
//        example.createCriteria().andUsernameEqualTo(unionId);
//        List<HfUser> list = hfUserMapper.selectByExample(example);
//        if(list.isEmpty()) {
//            HfUser hfUser = new HfUser();
//            hfUser.setAddress(avatarUrl);
//            hfUser.setNickName(nickName);
//            hfUser.setUsername(unionId);
//            hfUser.setCreateDate(LocalDateTime.now());
//            hfUser.setModifyDate(LocalDateTime.now());
//            hfUser.setIdDeleted((byte) 0);
//            hfUserMapper.insert(hfUser);
//            userId = hfUser.getId();
//        }else {
//            HfUser hfUser = list.get(0);
//            hfUser.setAddress(avatarUrl);
//            hfUser.setNickName(nickName);
//            hfUserMapper.updateByPrimaryKey(hfUser);
//            userId = hfUser.getId();
//        }
//        map.put("userId", userId);
//        map.put( "userInfo",userInfo );
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
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
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
//            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
//            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
//            parameters.init(new IvParameterSpec(ivByte));
//            cipher.init( Cipher.DECRYPT_MODE, spec, parameters);// 初始化
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
//
//
//    private JSONObject getSessionKeyOrOpenId(String code) {
//        //微信端登录code
//        String wxCode = code;
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=wx16159fcc93b0400c&secret=1403f2e207dfa2f1f348910626f5aa42&js_code="+code+"&grant_type=authorization_code";
//        Map<String,String> requestUrlParam = new HashMap<String, String>(  );
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
//            if(entity != null) {
//                String result = EntityUtils.toString(entity,"UTF-8");
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
