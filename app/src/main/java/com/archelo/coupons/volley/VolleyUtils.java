package com.archelo.coupons.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.data.LoginStatus;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VolleyUtils {
    public static String getRequestVerificationToken(String string) {
        Document jDoc = Jsoup.parse(string);
        return jDoc.select("input[name=__RequestVerificationToken]").first().val();
    }

    public static String getSamlRequestForm(String string) {
        Document jDoc = Jsoup.parse(string);
        return jDoc.select("input[name=SAMLRequest]").first().val();
    }

    public static String getSamlResponseForm(String string) {
        Document jDoc = Jsoup.parse(string);
        return jDoc.select("input[name=SAMLResponse]").first().val();
    }

    public static String getRelayState(String string) {
        Document jDoc = Jsoup.parse(string);
        return jDoc.select("input[name=RelayState]").first().val();
    }

    public static String getTokenValue(String couponHome) {
        String apiScript = "scripts/web.js?t=";
        int index = couponHome.indexOf(apiScript);
        int finalIndex = couponHome.indexOf("\"", index);
        return couponHome.substring(index + apiScript.length(), finalIndex);
    }

    public static AzureToken buildAzureToken(String webJSFile, String status) {
        LoginStatus loginStatus = new Gson().fromJson(status, LoginStatus.class);
        int index = webJSFile.indexOf(AzureToken.AUTHORIZATION);
        int endIndex = webJSFile.indexOf("&", index);
        String authorization = webJSFile.substring(index + 14, endIndex);

        index = webJSFile.indexOf(AzureToken.ZUMO_APPLICATION_TOKEN);
        endIndex = webJSFile.indexOf("\"", index + AzureToken.ZUMO_APPLICATION_TOKEN.length());
        String zumoApplicationToken = webJSFile.substring(index + AzureToken.ZUMO_APPLICATION_TOKEN.length(), endIndex);

        return new AzureToken.AzureTokenBuilder()
                .authorization(authorization)
                .userID(loginStatus.getUserId())
                .zumoApplicationToken(zumoApplicationToken)
                .signInStatus(loginStatus.isSignedIn())
                .build();
    }

    public static void logLongString(String TAG,String string){
        int maxLogSize = 1000;
        for(int i = 0; i <= string.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > string.length() ? string.length() : end;
            Log.v(TAG, string.substring(start, end));
        }
    }
    protected static String toURLEncodedString(Map<String,String> map, boolean encode) {
        if (map == null || map.isEmpty())
            return "";

        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;

        for (Map.Entry<String,String> item : map.entrySet()) {

            String key = null;
            String value = null;
            try {
                if (encode) {
                    key = URLEncoder.encode(item.getKey(), "UTF-8");
                    value = URLEncoder.encode(item.getValue(), "UTF-8");
                } else {
                    key = item.getKey();
                    value = item.getValue();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            if (isFirst) {
                builder.append(key).append("=").append(value);
                isFirst = false;
            } else {
                builder.append("&").append(key).append("=").append(value);
            }

        }
        return "?" + builder.toString();
    }

    protected static String formatHeaders(Map<String,String> map){
        if(map == null || map.isEmpty()){
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,String> item : map.entrySet()){
            builder.append(item.getKey()).append(": ").append(item.getValue()).append(System.lineSeparator());
        }
        return builder.toString();
    }

    protected static String formatParameters(Map<String,String> map){
        if(map == null || map.isEmpty()){
            return "";
        }

        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();

        for(Map.Entry<String,String> entry = iterator.next(); iterator.hasNext(); entry = iterator.next()){
            builder.append(entry.getKey()).append("=").append(entry.getValue());

            if(iterator.hasNext()){
                builder.append("&");
            }
        }

        return builder.toString();
    }

    public static String logCookies(CookieManager cookieManager){
        StringBuilder builder = new StringBuilder();
        List<HttpCookie> cookies= cookieManager.getCookieStore().getCookies();
        for(HttpCookie cookie : cookies){
            builder.append(cookie).append(System.lineSeparator());
        }
        return builder.toString();
    }

    public static void logToCurlRequest(Request<?> request) {
        StringBuilder builder = new StringBuilder();
        builder.append("curl request: curl ");
        builder.append("-X \"");
        switch (request.getMethod()) {
            case Request.Method.POST:
                builder.append("POST");
                break;
            case Request.Method.GET:
                builder.append("GET");
                break;
            case Request.Method.PUT:
                builder.append("PUT");
                break;
            case Request.Method.DELETE:
                builder.append("DELETE");
                break;
        }
        builder.append("\"");

        try {
            if (request.getBody() != null) {
                builder.append(" -D ");
                String data = new String(request.getBody());
                data = data.replaceAll("\"", "\\\"");
                builder.append("\"");
                builder.append(data);
                builder.append("\"");
            }
            for (String key : request.getHeaders().keySet()) {
                builder.append(" -H '");
                builder.append(key);
                builder.append(": ");
                builder.append(request.getHeaders().get(key));
                builder.append("'");
            }
            builder.append(" \"");
            builder.append(request.getUrl());
            builder.append("\"");

            Log.d("VOLLEY",builder.toString());
        } catch (AuthFailureError e) {
            Log.d("VOLLEY","Unable to get body of response or headers for curl logging");
        }
    }
}
