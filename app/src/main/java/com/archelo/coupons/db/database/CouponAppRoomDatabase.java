package com.archelo.coupons.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.archelo.coupons.db.dao.CouponDao;
import com.archelo.coupons.db.dao.CookieDao;
import com.archelo.coupons.db.data.Cookie;
import com.archelo.coupons.db.data.Coupon;

@Database(entities = {Coupon.class, Cookie.class}, version = 1)
public abstract class CouponAppRoomDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "coupon_app_database";
    public abstract CouponDao couponDao();
    public abstract CookieDao cookieDao();

    private static CouponAppRoomDatabase INSTANCE;


    public static CouponAppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CouponAppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CouponAppRoomDatabase.class, DATABASE_NAME)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}


