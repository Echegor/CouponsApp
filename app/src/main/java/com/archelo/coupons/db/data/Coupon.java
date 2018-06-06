package com.archelo.coupons.db.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by rtl1e on 5/18/2018.
 */


@Entity(tableName = "coupon_table")
public class Coupon {

    @PrimaryKey
    @NonNull
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getRequirement_description() {
        return requirement_description;
    }

    public void setRequirement_description(String requirement_description) {
        this.requirement_description = requirement_description;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getReward_upcs() {
        return reward_upcs;
    }

    public void setReward_upcs(String reward_upcs) {
        this.reward_upcs = reward_upcs;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTotal_downloads() {
        return total_downloads;
    }

    public void setTotal_downloads(String total_downloads) {
        this.total_downloads = total_downloads;
    }

    public String getTargeting_buckets() {
        return targeting_buckets;
    }

    public void setTargeting_buckets(String targeting_buckets) {
        this.targeting_buckets = targeting_buckets;
    }

    public String getTargeted_offer() {
        return targeted_offer;
    }

    public void setTargeted_offer(String targeted_offer) {
        this.targeted_offer = targeted_offer;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getPos_live_date() {
        return pos_live_date;
    }

    public void setPos_live_date(String pos_live_date) {
        this.pos_live_date = pos_live_date;
    }

    public String getDisplay_start_date() {
        return display_start_date;
    }

    public void setDisplay_start_date(String display_start_date) {
        this.display_start_date = display_start_date;
    }

    public String getDisplay_end_date() {
        return display_end_date;
    }

    public void setDisplay_end_date(String display_end_date) {
        this.display_end_date = display_end_date;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(String publication_id) {
        this.publication_id = publication_id;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getRequirement_upcs() {
        return requirement_upcs;
    }

    public void setRequirement_upcs(String requirement_upcs) {
        this.requirement_upcs = requirement_upcs;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getOffer_priority() {
        return offer_priority;
    }

    public void setOffer_priority(String offer_priority) {
        this.offer_priority = offer_priority;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getLong_description_header() {
        return long_description_header;
    }

    public void setLong_description_header(String long_description_header) {
        this.long_description_header = long_description_header;
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
