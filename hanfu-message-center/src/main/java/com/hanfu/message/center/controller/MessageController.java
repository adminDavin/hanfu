package com.hanfu.message.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.message.center.model.Template;
import com.hanfu.message.center.model.TemplateParam;
import com.hanfu.message.center.utils.HttpRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/message")
@Api
public class MessageController {

    @RequestMapping(path = "/sendMessage",  method = RequestMethod.GET)
    @ApiOperation(value = "发送消息", notes = "发送消息")
    public ResponseEntity<JSONObject> sendMessage(String openid,String title, String defeated, String instructions) throws Exception {
    	BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Template tem=new Template();
        tem.setTemplateId("模板消息id");
        tem.setTopColor("#00DD00");//颜色
        tem.setToUser(openid);//接收方ID
        System.out.println(openid+"=====================");
        //设置超链接（点击模板可跳转相应链接中）
        tem.setUrl("你要跳转的链接");
        List<TemplateParam> paras=new ArrayList<TemplateParam>();//消息主体
        paras.add(new TemplateParam("first",title,"#333")); //标题
        paras.add(new TemplateParam("keyword1",defeated,"#333"));//审核类型
        paras.add(new TemplateParam("keyword2",instructions,"#333"));//时间
//        paras.add(new TemplateParam("keyword3",createds,"#333"));
        paras.add(new TemplateParam("remark","点击此消息查看详情","#333"));
        tem.setTemplateParamList(paras);
        boolean result=sendTemplateMsg(tem);
        System.out.println(result);
        return builder.body(ResponseUtils.getResponseBody(result));
    }
    /**
     * 发送模板消息
     * @param template
     * @return
     */
    public static boolean sendTemplateMsg(Template template){
        String token = getToken(template);
//           String token = "";
        boolean flag=false;
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

        requestUrl=requestUrl.replace("ACCESS_TOKEN", token);
        String jsonString = template.toJSON();//把String拼接转为json类型
        JSONObject jsonResult=HttpRequest.httpsRequest(requestUrl, "POST", jsonString);
        if(jsonResult!=null){
            int errorCode=jsonResult.getIntValue("errcode");
            String errorMessage=jsonResult.getString("errmsg");
            if(errorCode==0){
                flag=true;
            }else{
                System.out.println("模板消息发送失败:"+errorCode+","+errorMessage);
                System.out.println(token+"================");
                flag=false;
            }
        }
        return flag;
    }
    public static String getToken(Template template){
        String requestUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=你的appid&secret=你的appid对应的秘钥";
        JSONObject jsonResult= HttpRequest.httpsRequest(requestUrl, "POST", template.toJSON());
        if(jsonResult!=null){
            String access_token=jsonResult.getString("access_token");
            return access_token;
        }else{
            return "";
        }
    }
}
