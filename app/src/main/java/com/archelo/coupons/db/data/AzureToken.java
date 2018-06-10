package com.archelo.coupons.db.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "azure_token_table")
public class AzureToken {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    public final static String AUTHORIZATION = "Authorization";
    public final static String ZUMO_APPLICATION_TOKEN = "\"X-ZUMO-APPLICATION\":\"";
    private String authorization;
    private String userID;
    private String zumoApplicationToken;
    private boolean isSignedIn;

    public AzureToken(){}
    private AzureToken(AzureTokenBuilder builder) {
        this.authorization = builder.authorization;
        this.userID = builder.userID;
        this.zumoApplicationToken = builder.zumoApplicationToken;
        this.isSignedIn = builder.isSignedIn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getZumoApplicationToken() {
        return zumoApplicationToken;
    }

    public void setZumoApplicationToken(String zumoApplicationToken) {
        this.zumoApplicationToken = zumoApplicationToken;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public String toString() {
        return "{ \"Authorization\" : \"" + authorization + "\", " +
                "\"userID\" : \"" + userID + "\", " +
                "\"isSignedIn\" : \"" + isSignedIn + "\", " +
                "\"zumoApplicationToken\" : \"" + zumoApplicationToken + "\"" +
                "}";
    }

    public static class AzureTokenBuilder {
        private String authorization;
        private String userID;
        private String zumoApplicationToken;
        private boolean isSignedIn;

        public AzureTokenBuilder() {
        }

        public AzureTokenBuilder authorization(String authorization) {
            this.authorization = authorization;
            return this;
        }

        public AzureTokenBuilder userID(String userID) {
            this.userID = userID;
            return this;
        }

        public AzureTokenBuilder zumoApplicationToken(String zumoApplicationToken) {
            this.zumoApplicationToken = zumoApplicationToken;
            return this;
        }

        public AzureTokenBuilder signInStatus(boolean signedIn) {
            this.isSignedIn = signedIn;
            return this;
        }

        public AzureToken build() {
            return new AzureToken(this);
        }


    }
}
