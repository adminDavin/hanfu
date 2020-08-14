package com.hanfu.shejiao.center.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanfu.shejiao.center.service.impl.ALiserviceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/alipay")
public class AlipayController {
    @Autowired
    ALiserviceImpl aLiservice;
 
    /**
     * 支付
     * @param total_fee
     * @param oid
     * @return
     * @throws Exception
     */
   @PostMapping("/apppay")
   @ApiOperation(value = "apppay", notes = "apppay")
   public Map pay( @RequestParam(value = "total_fee") String total_fee,@RequestParam(value = "oid") String oid) throws Exception {
        return aLiservice.pay(total_fee,oid);
   }
 
    /**
     * 回调
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/alipayNotify")
    @ApiOperation(value = "alipayNotify", notes = "alipayNotify")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
         aLiservice.paymentCallback(request,response);
    }
 
}