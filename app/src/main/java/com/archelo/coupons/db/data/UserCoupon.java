package com.archelo.coupons.db.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_coupons")
public class UserCoupon {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String coupon_id;
    private boolean clipped;

    public UserCoupon(String coupon_id, boolean clipped) {
        this.coupon_id = coupon_id;
        this.clipped = clipped;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public boolean isClipped() {
        return clipped;
    }

    public void setClipped(boolean clipped) {
        this.clipped = clipped;
    }
}
