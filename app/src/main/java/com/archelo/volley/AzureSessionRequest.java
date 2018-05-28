package com.archelo.volley;

import android.util.Log;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.archelo.shoprite.coupons.json.AzureToken;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AzureSessionRequest extends StringRequest {
    public final String TAG = "AzureSessionRequest";
    private final AzureToken azureToken;

    public AzureSessionRequest(AzureToken azureToken,String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.azureToken = azureToken;
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
        params.put("Authorization", azureToken.getAuthorization());
        params.put("returnUrl", "http://coupons.shoprite.com/");
        return super.getUrl()+azureToken.getUserID() + VolleyUtils.toURLEncodedString(params,false);
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        //headers.put("Accept-Encoding", "gzip, deflate, br"); //death
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");

        headers.put("Host", "wfsso.azurewebsites.net");
        headers.put("Referer", "http://coupons.shoprite.com/");
        headers.put("Origin", "http://coupons.shoprite.com/");
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

