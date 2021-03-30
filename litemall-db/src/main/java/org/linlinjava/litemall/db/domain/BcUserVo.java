package org.linlinjava.litemall.db.domain;

public class BcUserVo {
    private int id;//微信用户表id
    private int bcId;//报餐用户表id

    private String avatar; //头像
    private String nickname;  //昵称
    private String name;//注册时填写的真实姓名
    private String mobile;//注册时填写的手机号码
    private Boolean isAdmin; //是否为管理员
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBcId() {
        return bcId;
    }

    public void setBcId(int bcId) {
        this.bcId = bcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
