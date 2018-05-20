package com.archelo.volley;

import com.archelo.shoprite.coupons.json.AzureToken;
import com.archelo.shoprite.coupons.json.LoginStatus;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
}
