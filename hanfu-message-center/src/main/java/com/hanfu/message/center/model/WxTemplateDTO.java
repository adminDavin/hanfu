package com.hanfu.message.center.model;

import java.util.Map;

/**
 * 消息模板基本属性封装，参数命名风格需参照微信参数定义
 */
public class WxTemplateDTO {

    /**
     * 模板消息ID（必填）.
     */
    private String template_id;

    /**
     * 接收者（用户）的 openid(必填).
     */
    private String touser;

    /**
     * 支付场景下，为本次支付的 prepay_id（必填）.
     */
    private String form_id;

    /**
     * 点击模板卡片后的跳转页面.
     */
    private String page;

    /**
     * 模板内容，不填则下发空模板（必填）.
     */
    private Map<String, TemplateDataDTO> data;

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, TemplateDataDTO> getData() {
        return data;
    }

    public void setData(Map<String, TemplateDataDTO> data) {
        this.data = data;
    }
}
