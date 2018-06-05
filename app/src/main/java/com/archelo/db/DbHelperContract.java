package com.archelo.db;

import android.provider.BaseColumns;

/**
 * Created by Archelo on 9/2/2017.
 */

public class DbHelperContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbHelperContract() {
    }

    /* Inner class that defines the table contents */
    public static class DbEntry implements BaseColumns {
        public static final String COUPONS_DATA_TABLE = "COUPONS_DATA_TABLE";
        public static final String COLUMN_NAME_START_TIME = "COLUMN_NAME_START_TIME";
        public static final String COLUMN_NAME_AZURE_TOKEN = "COLUMN_NAME_AZURE_TOKEN";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS "
                        + COUPONS_DATA_TABLE + " ("
                        + _ID + " INTEGER PRIMARY KEY, "
//                        + COLUMN_NAME_START_TIME + " INTEGER NOT NULL, "
                        + COLUMN_NAME_AZURE_TOKEN + " TEXT NOT NULL); ";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + COUPONS_DATA_TABLE;

        public static final String[] DEFAULT_PROJECTION = new String[]{
                _ID,
                COLUMN_NAME_AZURE_TOKEN,
        };
    }
}