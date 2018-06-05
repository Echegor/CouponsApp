package com.archelo.shoprite.coupons.json;

//import com.archelo.shoprite.coupons.http.StateSaver;

import java.io.Serializable;

public class LoginStatus implements Serializable{
    private String UserId;
    private boolean IsSignedIn;

    public LoginStatus(String userId, boolean isSignedIn) {
        this.UserId = userId;
        this.IsSignedIn = isSignedIn;
    }

    public String getUserId() {
        return UserId;
    }

    public boolean isSignedIn() {
        return IsSignedIn;
    }

    public String toString() {
        return "{ \"UserId\" : \"" + UserId + "\" : \"IsSignedIn\" : \"" + IsSignedIn + "\" }";
    }


}
