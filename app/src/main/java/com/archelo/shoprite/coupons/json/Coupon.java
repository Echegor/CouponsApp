package com.archelo.shoprite.coupons.json;

import java.io.Serializable;

/**
 * Created by rtl1e on 5/18/2018.
 */

public class Coupon implements Serializable {
    public static final String COUPON = "COUPON";
    private String id;
    private String coupon_id;
    private String featured;
    private String requirement_description;
    private String brand_name;
    private String reward_upcs;
    private String short_description;
    private String image_url;
    private String value;
    private String total_downloads;
    private String targeting_buckets;
    private String targeted_offer;
    private String tags;
    private String enabled;
    private String external_id;
    private String pos_live_date;
    private String display_start_date;
    private String display_end_date;
    private String offer_type;
    private String Category;
    private String publication_id;
    private String long_description;
    private String requirement_upcs;
    private String subcategory;
    private String offer_priority;
    private String expiration_date;
    private String long_description_header;

    public String getId() {
        return id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public String getFeatured() {
        return featured;
    }

    public String getRequirement_description() {
        return requirement_description;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getReward_upcs() {
        return reward_upcs;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getValue() {
        return value;
    }

    public String getTotal_downloads() {
        return total_downloads;
    }

    public String getTargeting_buckets() {
        return targeting_buckets;
    }

    public String getTargeted_offer() {
        return targeted_offer;
    }

    public String getTags() {
        return tags;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getExternal_id() {
        return external_id;
    }

    public String getPos_live_date() {
        return pos_live_date;
    }

    public String getDisplay_start_date() {
        return display_start_date;
    }

    public String getDisplay_end_date() {
        return display_end_date;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public String getCategory() {
        return Category;
    }

    public String getPublication_id() {
        return publication_id;
    }

    public String getLong_description() {
        return long_description;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id='" + id + '\'' +
                ", coupon_id='" + coupon_id + '\'' +
                ", featured='" + featured + '\'' +
                ", requirement_description='" + requirement_description + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", reward_upcs='" + reward_upcs + '\'' +
                ", short_description='" + short_description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", value='" + value + '\'' +
                ", total_downloads='" + total_downloads + '\'' +
                ", targeting_buckets='" + targeting_buckets + '\'' +
                ", targeted_offer='" + targeted_offer + '\'' +
                ", tags='" + tags + '\'' +
                ", enabled='" + enabled + '\'' +
                ", external_id='" + external_id + '\'' +
                ", pos_live_date='" + pos_live_date + '\'' +
                ", display_start_date='" + display_start_date + '\'' +
                ", display_end_date='" + display_end_date + '\'' +
                ", offer_type='" + offer_type + '\'' +
                ", Category='" + Category + '\'' +
                ", publication_id='" + publication_id + '\'' +
                ", long_description='" + long_description + '\'' +
                ", requirement_upcs='" + requirement_upcs + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", offer_priority='" + offer_priority + '\'' +
                ", expiration_date='" + expiration_date + '\'' +
                ", long_description_header='" + long_description_header + '\'' +
                '}';
    }
}
