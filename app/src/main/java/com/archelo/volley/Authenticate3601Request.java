package com.archelo.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.archelo.shoprite.coupons.json.LoginStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Authenticate3601Request extends StringRequest {
    public final String TAG = "Authenticate3601Request";
    private CookieStore cookieStore;
    private LoginStatus loginStatus;
    private String request;
    public Authenticate3601Request(CookieStore cookieStore, LoginStatus loginStatus, String request, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.cookieStore = cookieStore;
        this.loginStatus = loginStatus;
        this.request = request;
    }

    /*
     * This method return the entire un-parsed network response. The only purpose of this call is to
     * get cookies assigned as my random session.
     * */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        List<Header> headers =  response.allHeaders;
        Log.d(TAG,"Response Headers: \n" +headers.toString().replaceAll("],",",\r\n"));
        cookieStore.parseHeaders(headers,getUrl());
        return super.parseNetworkResponse(response);
    }


    @Override
    public String getUrl() {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("binding", "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        params.put("cancelUri", "https://wfsso.azurewebsites.net/SRSSO/CancelSignIn/store/027F776?success=False");
        String url = super.getUrl() + VolleyUtils.toURLEncodedString(params,true);
        Log.d(TAG,"GETURL: " + url);
        return url;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String,String> headers = new LinkedHashMap<>();
        headers.put("Host", "secure.shoprite.com");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,application/json, text/javascript,*/*;q=0.8");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        //headers.put("Accept-Encoding", "gzip, deflate, br"); //death
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        //headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Referer", "https://wfsso.azurewebsites.net/SRSSO/SignIn?sessId=" + loginStatus.getUserId()+ "&returnUrl=http://coupons.shoprite.com/");
        headers.put("Cookie"," " + cookieStore.getCookies(getUrl()));
        Log.d(TAG,"Request Headers: \n" +headers.toString().replaceAll("],",",\r\n"));
        return headers;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String,String> params = new LinkedHashMap<>();
        String samlForm = VolleyUtils.getSamlRequestForm(request);
        String relayState = VolleyUtils.getRelayState(request);
//        try {
//            samlForm = URLEncoder.encode(samlForm, "UTF-8");
//            relayState = URLEncoder.encode(relayState, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        params.put("SAMLRequest", samlForm);
        params.put("RelayState", relayState);

        //Log.d(TAG,"Params: \n" +params.toString().replaceAll("],",",\r\n"));
        return params;
    }

//    @Override
//    public String getBodyContentType() {
//        return super.getBodyContentType();
//    }
    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
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
