package com.archelo.shoprite.coupons.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.archelo.shoprite.coupons.json.AzureToken;
import com.archelo.shoprite.coupons.json.AzureUserInfo;
import com.archelo.shoprite.coupons.json.Coupon;
import com.archelo.shoprite.coupons.json.UserCoupons;
import com.archelo.shoprite.coupons.states.CookieManagerState;
import com.archelo.shoprite.coupons.urls.AzureUrls;
import com.archelo.volley.AvailableCouponsRequest;
import com.archelo.volley.ProxiedHurlStack;
import com.archelo.volley.VolleyUtils;
import com.example.rtl1e.shopritecoupons.R;

import java.net.CookieHandler;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toast lastToast;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CookieManagerState cookieManager;
    private AzureToken azureToken;
    private AzureUserInfo azureUserInfo;
    private UserCoupons userCoupons;
    private Coupon[] couponsArray;

    private RequestQueue queue;
    private Response.ErrorListener volleyErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "Error occured " + error);
//            showProgress(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        cookieManager = (CookieManagerState) intent.getSerializableExtra(CookieManagerState.COOKIE_STATE);
        CookieHandler.setDefault(cookieManager);

        Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding all coupons", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


//        queue = Volley.newRequestQueue(this);
        queue = Volley.newRequestQueue(this, new ProxiedHurlStack());

        mRecyclerView = findViewById(R.id.coupon_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new CouponAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void showToast(CharSequence text) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        lastToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        lastToast.show();
    }

//            List<Header> couponHeader = HeaderUtils.getAvailableCoupons(azureTokens);
//        for (String coupon : couponsArray.getAvailable_ids_array()) {
//            String response = new Post.PostBuilder()
//                    .url(AzureUrls.COUPONS_ADD)
//                    .headers(couponHeader)
//                    .jsonObject(ParamUtils.buildCouponJSON(coupon, userInfo))
//                    .contentType(ContentType.APPLICATION_JSON)
//                    .httpClientContext(context)
//                    .build().doPost();
//            System.out.println("Response: " + response);
//        }

    private void performAddAllCouponsRequest(final AzureToken azureToken, final AzureUserInfo azureUserInfo, final UserCoupons userCoupons) {
        showToast("Performing add coupon request ");
        AvailableCouponsRequest request = new AvailableCouponsRequest(azureToken, azureUserInfo, Request.Method.POST, AzureUrls.COUPONS_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "request response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                Log.d(TAG, Arrays.toString(couponsArray));
            }
        }, volleyErrorListener);
        queue.add(request);
    }
//    public List<TimeEntry> getTimeEntries(){
//        Log.v(TAG,"Fetching items from db");
//        DbHelper database = new DbHelper(this);
//        SQLiteDatabase db = database.getReadableDatabase();
//
//        ArrayList<TimeEntry> list = new ArrayList<>(ARRAY_LIST_SIZE);
//// Filter results WHERE "title" = 'My Title'
////        String selection = COLUMN_NAME_WEEK_DATE_START + " = ?";
////        String[] selectionArgs = { getDateAsString(currentDate) };
//
//        String sortOrder =
//                DbHelperContract.DbEntry.COLUMN_NAME_SAVED_DATE + " DESC";
//
//        try (Cursor cursor = db.query(
//                DbHelperContract.DbEntry.TABLE_NAME,                     // The table to query
//                DbHelperContract.DbEntry.DEFAULT_PROJECTION,             // The columns to return
//                null,                                // The columns for the WHERE clause (selection)
//                null,                            // The values for the WHERE clause (selectionArgs)
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                sortOrder                                 // The sort order
//        )) {
//            if (cursor.moveToFirst()) {
//                int idIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry._ID);
//                int startTimeIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_START_TIME);
//                int endTimeIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_END_TIME);
//                int breakDurationIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_BREAK_DURATION);
//                int breakTickedIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_BREAK_TICKED);
//                int notesIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_NOTES);
//                int savedDateIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_SAVED_DATE);
//                int hoursWorkedIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_HOURS_WORKED);
//                int moneyEarnedIndex = cursor.getColumnIndexOrThrow(DbHelperContract.DbEntry.COLUMN_NAME_MONEY_EARNED);
//
//                do {
//                    TimeEntry entry = new TimeEntry(
//                            cursor.getLong(idIndex),
//                            cursor.getLong(startTimeIndex),
//                            cursor.getLong(endTimeIndex),
//                            cursor.getInt(breakDurationIndex),
//                            cursor.getInt(breakTickedIndex),
//                            cursor.getString(notesIndex),
//                            cursor.getLong(savedDateIndex),
//                            new BigDecimal(cursor.getInt(moneyEarnedIndex)).scaleByPowerOfTen(-2),
//                            new BigDecimal(cursor.getInt(hoursWorkedIndex)).scaleByPowerOfTen(-2)
//                    );
//                    list.add(entry);
//
//                } while (cursor.moveToNext());
//            }
//
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG,"Retrieved " + list.size() + " from DB");
//        return list;
//    }


}
