package com.archelo.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusRequest extends StringRequest {
    public final String TAG = "StatusRequest";
    private CookieStore cookieStore;
    public StatusRequest(CookieStore cookieStore,int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.cookieStore = cookieStore;
    }

    /*
    * This method return the entire un-parsed network response. The only purpose of this call is to
    * get cookies assigned as my random session.
    * */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        List<Header> headers =  response.allHeaders;
        Log.d(TAG,"Headers: \n" +headers.toString());
        cookieStore.parseHeaders(headers);
        return super.parseNetworkResponse(response);
    }


    @Override
    public Map<String, String> getHeaders() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,application/json, text/javascript,*/*;q=0.8");
        //headers.put("Accept-Encoding", "gzip, deflate, br"); //death
        headers.put("Accept-Language", "en-US,en;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Host", "shop.shoprite.com");
        headers.put("Origin", "http://coupons.shoprite.com");
        headers.put("Referer", "http://coupons.shoprite.com/");
        //Log.d(TAG,"getHeaders " + headers);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    public String getBodyContentType() {
        return super.getBodyContentType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return super.getBody();
    }

    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
