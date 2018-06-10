package com.archelo.coupons.db.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "azure_user_table")
public class AzureUserInfo {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String SSOUrl;
    private String Active;
    private String Message;
    private UserInfo UserInfo;

    public AzureUserInfo(){}

    public AzureUserInfo(String SSOUrl, String active, String message, UserInfo userInfo) {
        this.SSOUrl = SSOUrl;
        Active = active;
        Message = message;
        UserInfo = userInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSSOUrl() {
        return SSOUrl;
    }

    public void setSSOUrl(String SSOUrl) {
        this.SSOUrl = SSOUrl;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        UserInfo = userInfo;
    }

    @Override
    public String toString() {
        return "AzureUserInfo{" +
                "SSOUrl='" + SSOUrl + '\'' +
                ", Active='" + Active + '\'' +
                ", Message='" + Message + '\'' +
                ", UserInfo=" + UserInfo +
                '}';
    }
}
