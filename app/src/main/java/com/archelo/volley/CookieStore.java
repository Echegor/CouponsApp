package com.archelo.volley;

import com.android.volley.Header;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CookieStore {
    public static final String SET_COOKIE = "Set-Cookie";
    Map<String,HttpCookie> cookies;

    public CookieStore() {
        cookies = new LinkedHashMap<>();
    }

    public void parseHeaders(List<Header> headers){
        for(Header header : headers ){
            if (header.getName().equals(SET_COOKIE)){
                List<HttpCookie> parsedCookies = HttpCookie.parse(header.getValue());
                for(HttpCookie cookie: parsedCookies){
                    cookies.put(cookie.getName(),cookie);
                }
            }
        }
    }

    public String getCookies(){
        if(cookies.isEmpty())
            return "";

        StringBuilder builder = new StringBuilder();
        for(HttpCookie cookie : cookies.values()){
            builder.append(cookie).append("; ");
        }

        return builder.toString();
    }

    /*
    * Note: on the to string method, it looks as if the cookie name is being appended twice. That is not the case.
    * */
    @Override
    public String toString() {
        return "CookieStore{" + getCookies() + '}';
    }
}
