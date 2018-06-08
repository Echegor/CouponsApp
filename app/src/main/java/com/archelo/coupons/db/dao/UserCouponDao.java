package com.archelo.coupons.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.archelo.coupons.db.data.UserCoupon;

import java.util.List;

@Dao
public interface UserCouponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserCoupon coupon);

    @Query("DELETE FROM user_coupons")
    void deleteAll();

    @Query("SELECT * from user_coupons ORDER BY coupon_id ASC")
    LiveData<List<UserCoupon>> getAllCoupons();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserCoupon[] params);
}
