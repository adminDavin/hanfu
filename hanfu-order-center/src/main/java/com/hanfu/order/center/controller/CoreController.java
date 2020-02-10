package com.hanfu.order.center.controller;

import com.hanfu.order.center.tool.CreateQrcore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/test2")
public class CoreController {


    /**
     * 接收二维码
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/twoCode",produces="text/html;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public void twoCode(HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception {


        String accessToken = CreateQrcore.getToken();
        String twoCodeUrl = CreateQrcore.getminiqrQr(accessToken,request,response,id);
//        response.setContentType("image/png");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        OutputStream stream = response.getOutputStream();
//        byte[] arr = new byte[1024];
//            stream.write(arr);
//            stream.flush();


    }
}