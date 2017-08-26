package com.byb.vidio.model;

/**
 * Created by baoyb on 2017/8/9.
 * 好友列表item
 */

public class ViFriendItemResponseModel extends BaseResponseModel {
    private String id;
    private String url; //头像
    private String name;
    private String description; //描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
