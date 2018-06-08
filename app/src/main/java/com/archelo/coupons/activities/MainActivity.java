package com.archelo.coupons.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.data.AzureUserInfo;
import com.archelo.coupons.db.data.Cookie;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.data.UserCoupons;
import com.archelo.coupons.db.model.CookieViewModel;
import com.archelo.coupons.db.model.CouponViewModel;
import com.archelo.coupons.recycler.CouponListAdapter;
import com.archelo.coupons.urls.AzureUrls;
import com.archelo.coupons.volley.AvailableCouponsRequest;
import com.archelo.coupons.volley.VolleyUtils;
import com.example.rtl1e.shopritecoupons.R;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toast lastToast;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CookieManager cookieManager = new CookieManager();
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
    private CouponViewModel mCouponViewModel;
    private CookieViewModel mCookieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CouponListAdapter adapter = new CouponListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCouponViewModel = ViewModelProviders.of(this).get(CouponViewModel.class);
        mCookieViewModel = ViewModelProviders.of(this).get(CookieViewModel.class);
        mCouponViewModel.getAllCoupons().observe(this, new Observer<List<Coupon>>() {
            @Override
            public void onChanged(@Nullable final List<Coupon> coupons) {
                // Update the cached copy of the coupons in the adapter.
                adapter.setCoupons(coupons);
            }
        });

        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        final CookieStore cookieStore = cookieManager.getCookieStore();
        mCookieViewModel.getAllCookies().observe(this, new Observer<List<Cookie>>() {
            @Override
            public void onChanged(@Nullable final List<Cookie> cookies) {
                // Update the cached copy of the coupons in the adapter.
                if(cookies == null){
                    Log.w(TAG,"Cookies were not saved");
                    return;
                }
                for(Cookie cookie :cookies){
                    cookieStore.add(null,cookie.getAsHttpCookie());
                }
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
            }
        });



    }

    private void showToast(CharSequence text) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        lastToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        lastToast.show();
    }

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

}
