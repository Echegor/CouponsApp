package com.archelo.coupons.db.data;

import android.arch.persistence.room.Entity;

import java.net.HttpCookie;

@Entity(tableName = "cookie_table")
public class Cookie {
    private HttpCookie httpCookie;

    public Cookie(HttpCookie httpCookie) {
        this.httpCookie = httpCookie;
    }

    public HttpCookie getHttpCookie() {
        return httpCookie;
    }
}
