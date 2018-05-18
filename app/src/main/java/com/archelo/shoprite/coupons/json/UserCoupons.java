package com.archelo.shoprite.coupons.json;

import java.io.Serializable;
import java.util.List;

public class UserCoupons implements Serializable{
    List<String> available_ids_array;
    List<String> clipped_active_ids_array;

    public List<String> getAvailable_ids_array() {
        return available_ids_array;
    }

    public List<String> getClipped_active_ids_array() {
        return clipped_active_ids_array;
    }


    @Override
    public String toString() {
        return "UserCoupons{" +
                "available_ids_array=" + available_ids_array +
                ", clipped_active_ids_array=" + clipped_active_ids_array +
                '}';
    }
}
