package com.archelo.shoprite.coupons.http;

import org.apache.http.protocol.HttpContext;

import java.io.Serializable;

/**
 * Created by rtl1e on 5/18/2018.
 * Extended super class to implement Seriallizable
 */

public class HttpClientContext extends org.apache.http.client.protocol.HttpClientContext implements Serializable{

    public HttpClientContext(HttpContext context){
        super(context);
    }

}
