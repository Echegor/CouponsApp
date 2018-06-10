package com.archelo.coupons.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.archelo.coupons.db.data.AzureToken;

import java.util.List;

@Dao
public interface AzureTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AzureToken AzureToken);

    @Query("DELETE FROM azure_token_table")
    void deleteAll();

    @Query("SELECT * from azure_token_table ORDER BY id ASC")
    LiveData<List<AzureToken>> getAllAzureTokens();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AzureToken[] params);
}
