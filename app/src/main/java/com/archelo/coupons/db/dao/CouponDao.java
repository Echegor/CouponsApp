package com.archelo.coupons.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.archelo.coupons.db.data.Coupon;

import java.util.List;

@Dao
public interface CouponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Coupon coupon);

    @Query("DELETE FROM coupon_table")
    void deleteAll();

    @Query("SELECT * from coupon_table ORDER BY clipped DESC")
    LiveData<List<Coupon>> getAllCoupons();

    @Query("SELECT * from coupon_table WHERE clipped = 0 AND available = 1 ORDER BY clipped DESC")
    LiveData<List<Coupon>> getAllUnclippedCoupons();

    @Query("SELECT * from coupon_table WHERE clipped = 1 ORDER BY clipped DESC")
    LiveData<List<Coupon>> getAllClippedCoupons();

    @Query("SELECT * from coupon_table WHERE available = 0 ORDER BY clipped DESC")
    LiveData<List<Coupon>> getAllUnavailableCoupons();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Coupon[] params);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Coupon> params);

    @Update()
    void update(Coupon param);
}
