package com.archelo.coupons.volley;

import android.util.Log;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.archelo.coupons.db.data.LoginStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SamlResponse extends StringRequest {
    public final String TAG = "SamlResponse";
    private final String email;
    private final String password;
    private LoginStatus loginStatus;
    private String request;
    public SamlResponse( LoginStatus loginStatus, String request,String email,String password, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.loginStatus = loginStatus;
        this.request = request;
        this.email = email;
        this.password = password;
    }

    /*
     * This method return the entire un-parsed network response. The only purpose of this call is to
     * get cookies assigned as my random session.
     * */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        List<Header> headers =  response.allHeaders;
        Log.d(TAG, "Response code " + response.statusCode +" Headers: \n" + headers.toString().replaceAll("],", ",\r\n"));
        return super.parseNetworkResponse(response);
    }


    @Override
    public String getUrl() {
        Map<String,String> params = new LinkedHashMap<>();
        return super.getUrl() + VolleyUtils.toURLEncodedString(params,true);
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,application/json, text/javascript,*/*;q=0.8");
        //headers.put("Accept-Encoding", "gzip, deflate, br"); //death
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Host", "secure.shoprite.com");
        headers.put("Origin", "https://secure.shoprite.com");
        headers.put("Referer", "https://secure.shoprite.com/User/SignIn/3601");
        //Log.d(TAG,"getHeaders " + headers);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() {
        String token = VolleyUtils.getRequestVerificationToken(request);
        Map<String,String> params = new LinkedHashMap<>();
        params.put("__RequestVerificationToken", token);
        params.put("Email", email);
        params.put("Password", password);
        params.put("StoreGroupId", "3601");
        params.put("ReturnUrl", "");

        Log.d(TAG,"Params " + params);
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded";
    }


    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}