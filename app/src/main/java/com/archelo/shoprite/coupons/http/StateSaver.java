package com.archelo.shoprite.coupons.http;

import com.archelo.shoprite.coupons.json.AzureToken;
import com.archelo.shoprite.coupons.json.AzureUserInfo;
import com.archelo.shoprite.coupons.json.Coupon;
import com.archelo.shoprite.coupons.json.UserCoupons;

import org.apache.http.client.protocol.HttpClientContext;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by rtl1e on 5/18/2018.
 */

public class StateSaver implements Serializable{
    private UserCoupons userCoupons;
    private AzureUserInfo azureUserInfo;
    private HttpClientContext httpClientContext;
    private AzureToken azureToken;
    private String errorString;
    private Coupon[] coupons;
    private boolean error;

    private StateSaver(StateSaverBuilder stateSaverBuilder) {
        this.userCoupons = stateSaverBuilder.userCoupons;
        this.azureUserInfo = stateSaverBuilder.azureUserInfo;
        this.httpClientContext = stateSaverBuilder.httpClientContext;
        this.azureToken = stateSaverBuilder.azureToken;
        this.errorString = stateSaverBuilder.errorString;
        this.error = stateSaverBuilder.error;
        this.coupons = stateSaverBuilder.coupons;
    }

    public UserCoupons getUserCoupons() {
        return userCoupons;
    }

    public AzureUserInfo getAzureUserInfo() {
        return azureUserInfo;
    }

    public HttpClientContext getHttpClientContext() {
        return httpClientContext;
    }

    public AzureToken getAzureToken() {
        return azureToken;
    }

    public String getErrorString() {
        return errorString;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "StateSaver{" +
                "userCoupons=" + userCoupons +
                ", azureUserInfo=" + azureUserInfo +
                ", httpClientContext=" + httpClientContext +
                ", azureToken=" + azureToken +
                ", errorString='" + errorString + '\'' +
                ", coupons=" + Arrays.toString(coupons) +
                ", error=" + error +
                '}';
    }

    public static class StateSaverBuilder{
        private UserCoupons userCoupons;
        private AzureUserInfo azureUserInfo;
        private HttpClientContext httpClientContext;
        private AzureToken azureToken;
        private String errorString;
        private boolean error;
        private Coupon[] coupons;

        public StateSaverBuilder(){}

        public StateSaverBuilder couponsArray(UserCoupons userCoupons){
            this.userCoupons = userCoupons;
            return this;
        }

        public StateSaverBuilder azureUserInfo(AzureUserInfo azureUserInfo){
            this.azureUserInfo = azureUserInfo;
            return this;
        }

        public StateSaverBuilder httpClientContext(HttpClientContext httpClientContext){
            this.httpClientContext = httpClientContext;
            return this;
        }

        public StateSaverBuilder azureToken(AzureToken azureToken){
            this.azureToken = azureToken;
            return this;
        }

        public StateSaverBuilder coupons(Coupon[] coupons){
            this.coupons = coupons;
            return this;
        }

        public StateSaverBuilder errorString(String azureToken){
            this.errorString = errorString;
            return this;
        }

        public StateSaverBuilder error(boolean error){
            this.error = error;
            return this;
        }

        public StateSaver build(){
            return new StateSaver(this);
        }
    }
}
