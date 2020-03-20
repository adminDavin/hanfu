package com.hanfu.order.center.manual.model;

import io.swagger.models.auth.In;

import java.util.List;

public class DiscoverUser {
    private Integer id;
    private Integer discoverId;
    private String nickName;
    private Integer fileId;
    private List<Integer> discoverFiles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(Integer discoverId) {
        this.discoverId = discoverId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public List<Integer> getDiscoverFiles() {
        return discoverFiles;
    }

    public void setDiscoverFiles(List<Integer> discoverFiles) {
        this.discoverFiles = discoverFiles;
    }
}
