package com.hanfu.user.center.manual.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.user.center.model.HfMessage;

import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: MiniMessage
 * @ProjectName Second-order-center
 * @Description: 小程序消息
 * @date Created in
 * @Version: $
 */
public class MiniMessage {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private HfMessage hfMessage;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public HfMessage getHfMessage() {
        return hfMessage;
    }

    public void setHfMessage(HfMessage hfMessage) {
        this.hfMessage = hfMessage;
    }
}
