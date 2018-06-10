package com.archelo.coupons.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.archelo.coupons.db.data.UserInfo;

public class UserInfoConverter {
    @TypeConverter
    public UserInfo fromString(String value) {
        return new UserInfo(value);
    }

    @TypeConverter
    public String toString(UserInfo userInfo) {
        return userInfo.toString();
    }

}
