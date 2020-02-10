package com.hanfu.message.center.service.impl;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import com.hanfu.message.center.model.TemplateDataDTO;
import com.hanfu.message.center.model.WxTemplateDTO;
import com.hanfu.message.center.service.WxTemplateService;
import com.hanfu.message.center.utils.WxTemplateUtil;
import com.hanfu.message.center.vo.WxAccessTokenVO;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxTemplateServiceImpl implements WxTemplateService {

    /**
     * 接口描述：向用户发送消息模板
     *
     * @throws JSONException
     */
    @Override
    public void sendTemplateToUsers(String message01, String message02) throws JSONException {
        // 获取accessToken
        WxAccessTokenVO wxAccessTokenVO = WxTemplateUtil.getAccessToken("wxfa188a42d843a0b0", "0433593dd1887ea5381e6d01308f81ba");

        // 消息模板对象
        WxTemplateDTO wxTemplateDTO = new WxTemplateDTO();
        // 时间格式转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 用户openId
        wxTemplateDTO.setTouser("openId");
        // 表单ID （需要小程序前端传递）
        wxTemplateDTO.setForm_id("formId");
        // 模板ID
        wxTemplateDTO.setTemplate_id("NkcLCSJf_wIsp9xON1X2Rb-7o3YsXROz8KklPZ3eEXw");
        // 跳转页面设置（仅限上线后的小程序，测试时可以忽略此属性）
        wxTemplateDTO.setPage("templates/****/****");

        Map<String, TemplateDataDTO> data = new HashMap<>();
        data.put("keyword1", new TemplateDataDTO(message01, "#173177"));
        data.put("keyword2", new TemplateDataDTO(simpleDateFormat.format(new Date()), "#173177"));
        data.put("keyword3", new TemplateDataDTO(message02, "#173177"));
        // 模板内容
        wxTemplateDTO.setData(data);

        // 发送消息模板
        WxTemplateUtil.sendTemplateMsg(wxTemplateDTO, wxAccessTokenVO.getAccessToken());

    }
}
