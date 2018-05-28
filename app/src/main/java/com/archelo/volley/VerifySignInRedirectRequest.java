package com.archelo.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.archelo.shoprite.coupons.json.LoginStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifySignInRedirectRequest extends StringRequest {
    public final String TAG = "VerifySignInRequest";

    public VerifySignInRedirectRequest( String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super( url, listener, errorListener);
    }

    /*
     * This method return the entire un-parsed network response. The only purpose of this call is to
     * get cookies assigned as my random session.
     * */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        List<Header> headers =  response.allHeaders;
        Log.d(TAG,"Headers: \n" +headers.toString().replaceAll("],",",\r\n"));
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

        headers.put("Host", "coupons.shoprite.com");
        //headers.put("Cookie",cookieStore.getCookies(getUrl()));
        //Log.d(TAG,"getHeaders " + headers);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String,String> params = new LinkedHashMap<>();
        return params;
    }


    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
