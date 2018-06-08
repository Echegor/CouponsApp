package com.archelo.coupons.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.archelo.coupons.db.data.Cookie;

import java.util.List;

@Dao
public interface CookieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cookie Cookie);

    @Query("DELETE FROM Cookie_table")
    void deleteAll();

    @Query("SELECT * from Cookie_table ORDER BY id ASC")
    LiveData<List<Cookie>> getAllCookies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cookie[] params);
}