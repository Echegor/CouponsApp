package com.archelo.coupons.db.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.net.HttpCookie;

@Entity(tableName = "cookie_table")
public class Cookie {
    /*
    * Not really easy to find, but id must be wrapped in an object or the id will not autogenerate
    * */
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String comment;
    private String commentURL;
    private boolean discard;
    private String portlist;
    private String domain;
    private long maxAge;
    private String path;
    private boolean secure;
    private String name;
    private String value;
    private int version;

    public Cookie(String comment, String commentURL, boolean discard, String portlist, String domain, long maxAge, String path, boolean secure, String name, String value, int version) {
        this.comment = comment;
        this.commentURL = commentURL;
        this.discard = discard;
        this.portlist = portlist;
        this.domain = domain;
        this.maxAge = maxAge;
        this.path = path;
        this.secure = secure;
        this.name = name;
        this.value = value;
        this.version = version;
    }

    public Cookie(HttpCookie httpCookie) {
        this.comment = httpCookie.getComment();
        this.commentURL = httpCookie.getCommentURL();
        this.discard = httpCookie.getDiscard();
        this.portlist = httpCookie.getPortlist();
        this.domain = httpCookie.getDomain();
        this.maxAge = httpCookie.getMaxAge();
        this.path = httpCookie.getPath();
        this.secure = httpCookie.getSecure();
        this.name = httpCookie.getName();
        this.value = httpCookie.getValue();
        this.version = httpCookie.getVersion();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentURL() {
        return commentURL;
    }

    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public String getPortlist() {
        return portlist;
    }

    public void setPortlist(String portlist) {
        this.portlist = portlist;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
