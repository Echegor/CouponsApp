package com.archelo.coupons.db.data;

import java.util.List;

public class UserCoupons {
    private List<String> available_ids_array;
    private List<String> clipped_active_ids_array;

    public List<String> getAvailable_ids_array() {
        return available_ids_array;
    }

    public List<String> getClipped_active_ids_array() {
        return clipped_active_ids_array;
    }

    public int getCombinedSize(){
        return available_ids_array.size() + clipped_active_ids_array.size();
    }


    @Override
    public String toString() {
        return "UserCoupons{" +
                "available_ids_array=" + available_ids_array +
                ", clipped_active_ids_array=" + clipped_active_ids_array +
                '}';
    }
}
