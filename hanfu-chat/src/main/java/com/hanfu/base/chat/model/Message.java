package com.hanfu.base.chat.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {
    private HfUser from;
    private String message;
    private HfUser to;
    private String time;

    public HfUser getFrom() {
        return from;
    }

    public void setFrom(HfUser from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HfUser getTo() {
        return to;
    }

    public void setTo(HfUser to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
