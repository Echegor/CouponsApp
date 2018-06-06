package com.archelo.coupons.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.archelo.coupons.db.dao.CouponDao;
import com.archelo.coupons.db.data.Coupon;

@Database(entities = {Coupon.class}, version = 1)
public abstract class CouponRoomDatabase extends RoomDatabase {

    public abstract CouponDao couponDao();

    private static CouponRoomDatabase INSTANCE;


    public static CouponRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CouponRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CouponRoomDatabase.class, "coupon_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
