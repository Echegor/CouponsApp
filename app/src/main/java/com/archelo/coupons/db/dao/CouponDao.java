package com.archelo.coupons.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.archelo.coupons.db.data.Coupon;

import java.util.List;

@Dao
public interface CouponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Coupon coupon);

    @Query("DELETE FROM coupon_table")
    void deleteAll();

    @Query("SELECT * from coupon_table ORDER BY coupon_id ASC")
    LiveData<List<Coupon>> getAllWords();
}
