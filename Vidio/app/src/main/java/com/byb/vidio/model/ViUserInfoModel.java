package com.byb.vidio.model;

/**
 * Created by baoyb on 2017/5/31.
 */

public class ViUserInfoModel extends BaseResponseModel {
    private String tokenId;
    private String username;
    private String te;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTe() {
        return te;
    }

    public void setTe(String te) {
        this.te = te;
    }
}
