package com.archelo.volley;

import com.android.volley.Header;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
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

    public void parseHeaders(List<Header> headers, String url){

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        for(Header header : headers ){
            if (header.getName().equals(SET_COOKIE)){
                List<HttpCookie> parsedCookies = HttpCookie.parse(header.getValue());
                for(HttpCookie cookie: parsedCookies){
                    String domain = cookie.getDomain();
                    String path = cookie.getPath();

                    if(domain == null || domain.isEmpty()){
                        cookie.setDomain(uri.getHost());
                    }

                    if(path == null || path.isEmpty()){
                        cookie.setPath(uri.getPath());
                    }

                    cookies.put(cookie.getName(),cookie);
                }
            }
        }
    }

    public String getCookies(String url){
        if(cookies.isEmpty())
            return "";

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for(HttpCookie cookie : cookies.values()){

            /*
            * Attempts to follow http spec
            * */
            if(uri.getHost().contains(cookie.getDomain()) && uri.getPath().contains(cookie.getPath())){
                builder.append(cookie).append("; ");
            }

        }

        return builder.toString();
    }

    public String getFormattedCookies(){
        if(cookies.isEmpty())
            return "";

        StringBuilder builder = new StringBuilder();
        for(HttpCookie cookie : cookies.values()){
            builder.append(cookie).append("\r\n");
        }

        return builder.toString();
    }

    /*
    * Note: on the to string method, it looks as if the cookie name is being appended twice. That is not the case.
    * */
    @Override
    public String toString() {
        return "CookieStore{\n\n" + getFormattedCookies() + "\r\n}";
    }
}
