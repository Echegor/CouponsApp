package com.archelo.coupons.db.data;

import java.util.HashMap;
import java.util.List;

public class UserCoupons {
    private HashMap<String,Boolean> cache;
    private List<String> available_ids_array;
    private List<String> clipped_active_ids_array;

    public List<String> getAvailable_ids_array() {
        return available_ids_array;
    }

    public List<String> getClipped_active_ids_array() {
        return clipped_active_ids_array;
    }

    public void indexCoupons(){
        if(cache == null)
            cache = new HashMap<>();

        if(available_ids_array != null){
            for(String string : available_ids_array){
                cache.put(string,false);
            }
        }

        if(clipped_active_ids_array != null){
            for(String string : clipped_active_ids_array){
                cache.put(string,true);
            }
        }

    }

    public int getSize(){
        int sum = 0;
        if(available_ids_array != null){
            sum += available_ids_array.size();
        }

        if(clipped_active_ids_array!=null){
            sum += clipped_active_ids_array.size();
        }

        return sum;
    }

    public boolean isClipped(String coupon_id){
        Boolean bool = cache.get(coupon_id);
        if(bool == null ){
            return false;
        }
        return bool;
    }

    @Override
    public String toString() {
        return "UserCoupons{" +
                "available_ids_array=" + available_ids_array +
                ", clipped_active_ids_array=" + clipped_active_ids_array +
                '}';
    }
}
