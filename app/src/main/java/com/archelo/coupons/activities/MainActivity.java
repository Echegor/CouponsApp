package com.archelo.coupons.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.data.AzureUserInfo;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.data.UserCoupons;
import com.archelo.coupons.db.model.CouponViewModel;
import com.archelo.coupons.recycler.CouponListAdapter;
import com.archelo.coupons.states.CookieManagerState;
import com.archelo.coupons.urls.AzureUrls;
import com.archelo.coupons.volley.AvailableCouponsRequest;
import com.archelo.coupons.volley.ProxiedHurlStack;
import com.archelo.coupons.volley.VolleyUtils;
import com.example.rtl1e.shopritecoupons.R;

import java.net.CookieHandler;
import java.util.Arrays;
import java.util.List;


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
    private CouponViewModel mCouponViewModel;

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
        mCouponViewModel.getAllCoupons().observe(this, new Observer<List<Coupon>>() {
            @Override
            public void onChanged(@Nullable final List<Coupon> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setCoupons(words);
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
