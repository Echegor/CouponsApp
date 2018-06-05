package com.archelo.volley;

import android.util.Log;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * This class fetches mobile token
 * */
public class AzureServicesJSRequest extends StringRequest {
    public final String TAG = "AzureServicesJSRequest";

    public AzureServicesJSRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

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

        headers.put("Referer", "http://coupons.shoprite.com/");
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
