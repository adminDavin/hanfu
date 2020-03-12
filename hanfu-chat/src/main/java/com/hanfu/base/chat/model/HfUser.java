package com.hanfu.base.chat.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HfUser implements Serializable {
    private Integer id;
    private String name;
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
