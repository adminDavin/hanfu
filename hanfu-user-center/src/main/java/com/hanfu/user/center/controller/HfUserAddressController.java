package com.hanfu.user.center.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.HfUserAddresseMapper;
import com.hanfu.user.center.manual.dao.AddressDao;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserAddresse;
import com.hanfu.user.center.model.HfUserAddresseExample;
import com.hanfu.user.center.request.UserAddressRequest;
import com.hanfu.user.center.response.handler.UserAddressNotException;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.sun.mail.imap.protocol.Item;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@RequestMapping("/hf-user-address")
@CrossOrigin
public class HfUserAddressController {

    @Autowired
    private HfUserAddresseMapper hfUserAddresseMapper;
    
    @Autowired
    private AddressDao addressDao;
    
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ApiOperation(value = "获取用戶地址列表", notes = "获取用戶地址列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", value = "用戶id", required = true, type = "Integer"),
        @ApiImplicitParam(paramType = "query", value = "用戶id", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> query(@RequestParam Integer userId, @RequestParam Integer isDefault) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUserAddresseExample example = new HfUserAddresseExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<HfUserAddresse> addresses = hfUserAddresseMapper.selectByExample(example);

        List<HfUserAddresse> result = isDefault == 1 ? addresses.stream().filter(item -> item.getIsFaultAddress() != 0).collect(Collectors.toList()) : addresses;
        return builder.body(ResponseUtils.getResponseBody(result));
    }
    
    @CrossOrigin
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    @ApiOperation(value = "添加用戶地址", notes = "添加用戶地址")
    public ResponseEntity<JSONObject> add(UserAddressRequest request) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser hfUser = new HfUser();
        HfUserAddresse hfUserAddress = new HfUserAddresse();
        hfUserAddress.setUserId(request.getUserId());
        hfUserAddress.setContact(request.getContact());
        hfUserAddress.setHfConty(request.getHfConty());
        hfUserAddress.setCreateTime(LocalDateTime.now());
        hfUserAddress.setHfAddressDetail(request.getHfAddressDetail());
        hfUserAddress.setHfCity(request.getHfCity());
        hfUserAddress.setHfDesc(request.getHfDesc());
        hfUserAddress.setHfProvince(request.getHfProvince());
        hfUserAddress.setIsFaultAddress(request.getIsFaultAddress());
        hfUserAddress.setModifyTime(LocalDateTime.now());
        hfUserAddress.setIsDeleted((short) 0);
        hfUserAddress.setLastModifier("1");
        hfUserAddress.setPhoneNumber(request.getPhoneNumber());
        if (StringUtils.isEmpty(hfUser.getAddress())) {
            hfUserAddress.setIsFaultAddress(1);
        }
        @SuppressWarnings("unused")
		int i = addressDao.updateAddress(request.getId());
        return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.insert(hfUserAddress)));
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.GET)
    @ApiOperation(value = "删除用戶地址", notes = "删除用戶地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> delete(@RequestParam(name = "id") Integer id) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUserAddresseExample example = new HfUserAddresseExample();
        example.createCriteria().andIdEqualTo(id);
        return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.deleteByPrimaryKey(id)));
    }

    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    @ApiOperation(value = "更新用戶地址", notes = "更改用戶地址")
    public ResponseEntity<JSONObject> update(UserAddressRequest request) throws Exception {
        HfUserAddresse hfUserAddresse = hfUserAddresseMapper.selectByPrimaryKey(request.getId());
        if (hfUserAddresse == null) {
            throw new UserAddressNotException(String.valueOf(request.getUserId()));
        }
        if (!StringUtils.isEmpty(request.getContact())) {
            hfUserAddresse.setContact(request.getContact());
        }
        if (!StringUtils.isEmpty(request.getHfAddressDetail())) {
            hfUserAddresse.setHfAddressDetail(request.getHfAddressDetail());
        }
        if (!StringUtils.isEmpty(request.getHfCity())) {
            hfUserAddresse.setHfCity(request.getHfCity());
        }
        if (!StringUtils.isEmpty(request.getHfConty())) {
            hfUserAddresse.setHfConty(request.getHfConty());
        }
        if (!StringUtils.isEmpty(request.getHfDesc())) {
            hfUserAddresse.setHfDesc(request.getHfDesc());
        }
        if (!StringUtils.isEmpty(request.getHfProvince())) {
            hfUserAddresse.setHfProvince(request.getHfProvince());
        }
        if (StringUtils.isEmpty(request.getIsFaultAddress())) {
            hfUserAddresse.setIsFaultAddress(request.getIsFaultAddress());
        }
        if (!StringUtils.isEmpty(request.getUserId())) {
            hfUserAddresse.setUserId(request.getUserId());
        }
        if (!StringUtils.isEmpty(request.getPhoneNumber())) {
            hfUserAddresse.setPhoneNumber(request.getPhoneNumber());
        }
        hfUserAddresse.setModifyTime(LocalDateTime.now());
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.updateByPrimaryKeySelective(hfUserAddresse)));
    }

    @RequestMapping(value = "/addressDetail", method = RequestMethod.GET)
    @ApiOperation(value = "查询地址详情", notes = "查询地址详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> addressDetail(@RequestParam(name = "id") Integer id) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUserAddresseExample example = new HfUserAddresseExample();
        example.createCriteria().andIdEqualTo(id);
        return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.selectByPrimaryKey(id)));
    }
    
    @RequestMapping(value = "/getIngAndLat", method = RequestMethod.GET)
    @ApiOperation(value = "腾讯地图", notes = "腾讯地图")
    public ResponseEntity<JSONObject> getIngAndLat(@RequestParam(required = false, defaultValue = "") String address) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        System.out.println("----------经纬度查询-----------");
        String key = "4G5BZ-7ROKG-KBKQZ-IVBX5-SBG7K-FCBRX";//申请的key

        StringBuffer sb = new StringBuffer("https://apis.map.qq.com/ws/geocoder/v1/?");
        sb.append("address" + address);
        sb.append("&key=" + key);
        System.out.println("===>>>查询链接: " + sb.toString());

        return builder.body(ResponseUtils.getResponseBody(getURLContent(sb.toString())));
    }


    public static String getURLContent(String urlStr) {
        //请求的url
        URL url = null;
        //请求的输入流
        BufferedReader in = null;
        //输入流的缓冲
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            //一行一行进行读入
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (in != null) {
                    in.close(); //关闭流
                }
            } catch (IOException ex) {

            }
        }
        String result = sb.toString();
        return result;
    }
}