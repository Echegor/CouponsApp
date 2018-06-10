package com.archelo.coupons.db.data;

public class UserInfo {
    private String Email;
    private String FSN;
    private String FirstName;
    private String LastName;
    private String AccountId;
    private String StoreId;
    private String ReturnUrl;
    private String sessId;

    public UserInfo(String email, String fsn, String firstName, String lastName, String accountId, String storeId, String returnUrl, String sessId) {
        Email = email;
        FSN = fsn;
        FirstName = firstName;
        LastName = lastName;
        AccountId = accountId;
        StoreId = storeId;
        ReturnUrl = returnUrl;
        this.sessId = sessId;
    }

    public UserInfo(String userInfo){
        String [] info = userInfo.split("%delim%");
        Email = info[0].split("=")[1];
        FSN = info[1].split("=")[1];
        FirstName = info[2].split("=")[1];
        LastName = info[3].split("=")[1];
        AccountId = info[4].split("=")[1];
        StoreId = info[5].split("=")[1];
        ReturnUrl = info[6].split("=")[1];
        this.sessId = info[7].split("=")[1];
    }

    public String getEmail() {
        return Email;
    }

    public String getFSN() {
        return FSN;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getAccountId() {
        return AccountId;
    }

    public String getStoreId() {
        return StoreId;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public String getSessId() {
        return sessId;
    }

    @Override
    public String toString() {
        return "Email=" + Email  +
                "%delim%FSN=" + FSN  +
                "%delim%FirstName=" + FirstName  +
                "%delim%LastName=" + LastName  +
                "%delim%AccountId=" + AccountId  +
                "%delim%StoreId=" + StoreId  +
                "%delim%ReturnUrl=" + ReturnUrl  +
                "%delim%sessId=" + sessId ;
    }
}
