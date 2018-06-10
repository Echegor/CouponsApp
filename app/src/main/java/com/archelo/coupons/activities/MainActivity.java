package com.archelo.coupons.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.data.AzureUserInfo;
import com.archelo.coupons.db.data.Cookie;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.model.AzureTokenViewModel;
import com.archelo.coupons.db.model.AzureUserInfoViewModel;
import com.archelo.coupons.db.model.CookieViewModel;
import com.archelo.coupons.db.model.CouponViewModel;
import com.archelo.coupons.recycler.CouponListAdapter;
import com.archelo.coupons.urls.AzureUrls;
import com.archelo.coupons.volley.AddCouponRequest;
import com.archelo.coupons.volley.VolleyUtils;
import com.example.rtl1e.shopritecoupons.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toast lastToast;
    private CouponListAdapter mAdapter ;
    private CookieManager cookieManager = new CookieManager();
    private AzureToken azureToken;
    private AzureUserInfo azureUserInfo;

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
    private AzureTokenViewModel mAzureTokenViewModel;
    private AzureUserInfoViewModel mAzureUserInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new CouponListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);

        mCouponViewModel = ViewModelProviders.of(this).get(CouponViewModel.class);
        mCookieViewModel = ViewModelProviders.of(this).get(CookieViewModel.class);
        mAzureTokenViewModel = ViewModelProviders.of(this).get(AzureTokenViewModel.class);
        mAzureUserInfoModel = ViewModelProviders.of(this).get(AzureUserInfoViewModel.class);

        mAzureTokenViewModel.getAllAzureTokens().observe(this, new Observer<List<AzureToken>>() {
            @Override
            public void onChanged(@Nullable final List<AzureToken> azureTokens) {
                if(azureTokens != null)
                    azureToken = azureTokens.get(0);
                else
                    Log.d(TAG,"Azure tokens is empty");
            }
        });

        mAzureUserInfoModel.getAllAzureUserInfos().observe(this, new Observer<List<AzureUserInfo>>() {
            @Override
            public void onChanged(@Nullable final List<AzureUserInfo> azureUserInfoList) {
                if(azureUserInfoList != null)
                    azureUserInfo = azureUserInfoList.get(0);
                else
                    Log.d(TAG,"azureUserInfo is empty");
            }
        });

        mCouponViewModel.getAllCoupons().observe(this, new Observer<List<Coupon>>() {
            @Override
            public void onChanged(@Nullable final List<Coupon> coupons) {
                // Update the cached copy of the coupons in the adapter.
                mAdapter.setCoupons(coupons);
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

        Button button = findViewById(R.id.clip_all_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAddCouponRequest();
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

    private void performAddCouponRequest() {
        showToast("Performing add coupon request ");
        List<Coupon> coupons = mAdapter.getCoupons();
        for(final Coupon coupon : coupons){
            if(coupon.isClipped()){
                continue;
            }
            AddCouponRequest request = new AddCouponRequest(azureUserInfo, azureToken,coupon.getCoupon_id(), Request.Method.POST, AzureUrls.COUPONS_ADD, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "request response " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean clipped = (Boolean) jsonObject.get("result");
                        if(clipped){
                            showToast("Clipped " + coupon.getShort_description());
                            coupon.setClipped(true);
                            mCouponViewModel.update(coupon);
                        }
                        else{
                            showToast("Failed to clip " + coupon.getShort_description());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, volleyErrorListener);
            queue.add(request);
        }
    }

}
