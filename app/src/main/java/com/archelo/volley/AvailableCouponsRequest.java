package com.archelo.volley;

import android.util.Log;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.archelo.shoprite.coupons.json.AzureToken;
import com.archelo.shoprite.coupons.json.AzureUserInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AvailableCouponsRequest extends StringRequest {
    public final String TAG = "AvailableCouponsRequest";
    private AzureToken azureToken;
    private AzureUserInfo azureUserInfo;
    public AvailableCouponsRequest(AzureToken azureToken, AzureUserInfo azureUserInfo, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.azureToken = azureToken;
        this.azureUserInfo = azureUserInfo;
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
        headers.put("Accept", "*/*");
        //headers.put("Accept-Encoding", "gzip, deflate, br"); //death
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");

        headers.put("Host", "stagingcouponswest.azure-mobile.net");
        headers.put("Referer", "http://coupons.shoprite.com/");
        headers.put("Origin", "http://coupons.shoprite.com");
        headers.put("X-ZUMO-APPLICATION", azureToken.getZumoApplicationToken());
        headers.put("X-ZUMO-VERSION", "ZUMO/1.0 (lang=Web; os=--; os_version=--; arch=--; version=1.0.20702.0)");
        //headers.put("Cookie",cookieStore.getCookies(getUrl()));
        //Log.d(TAG,"getHeaders " + headers);
        return headers;
    }

    //TODO make sure this is valid json
    @Override
    protected Map<String, String> getParams() {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("ppc_number",azureUserInfo.getUserInfo().getFSN());
        return params;
    }


    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
