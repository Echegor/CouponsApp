package com.archelo.coupons.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.archelo.coupons.db.data.AzureUserInfo;

import java.util.List;

@Dao
public interface AzureUserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AzureUserInfo AzureUserInfo);

    @Query("DELETE FROM azure_user_table")
    void deleteAll();

    @Query("SELECT * from azure_user_table ORDER BY id ASC")
    LiveData<List<AzureUserInfo>> getAllAzureUserInfos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AzureUserInfo[] params);
}
