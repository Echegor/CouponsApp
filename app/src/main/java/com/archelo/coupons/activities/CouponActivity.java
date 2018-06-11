package com.archelo.coupons.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
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
import com.archelo.coupons.fragments.CouponFragment;
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

public class CouponActivity extends AppCompatActivity {
    private static final String TAG = "CouponActivity";
    private Toast lastToast;
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

    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return selectFragment(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        mTextMessage = (TextView) findViewById(R.id.message);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);

        queue = Volley.newRequestQueue(this);

        mCouponViewModel = ViewModelProviders.of(this).get(CouponViewModel.class);
        mCookieViewModel = ViewModelProviders.of(this).get(CookieViewModel.class);
        mAzureTokenViewModel = ViewModelProviders.of(this).get(AzureTokenViewModel.class);
        mAzureUserInfoModel = ViewModelProviders.of(this).get(AzureUserInfoViewModel.class);

        mAzureTokenViewModel.getAllAzureTokens().observe(this, new Observer<List<AzureToken>>() {
            @Override
            public void onChanged(@Nullable final List<AzureToken> azureTokens) {
                if(azureTokens != null && azureTokens.size() > 0)
                    azureToken = azureTokens.get(0);
                else
                    Log.d(TAG,"Azure tokens is empty");
            }
        });

        mAzureUserInfoModel.getAllAzureUserInfos().observe(this, new Observer<List<AzureUserInfo>>() {
            @Override
            public void onChanged(@Nullable final List<AzureUserInfo> azureUserInfoList) {
                if(azureUserInfoList != null && azureUserInfoList.size() > 0)
                    azureUserInfo = azureUserInfoList.get(0);
                else
                    Log.d(TAG,"azureUserInfo is empty");
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

    public CouponViewModel getmCouponViewModel() {
        return mCouponViewModel;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }

    private boolean selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {

            case R.id.navigation_home:
                frag = CouponFragment.newInstance(CouponFragment.ACTIVE);
                mTextMessage.setText(R.string.title_home);
                break;
            case R.id.navigation_dashboard:
                frag = CouponFragment.newInstance(CouponFragment.UNCLIPPED);
                mTextMessage.setText(R.string.title_dashboard);
                break;
            case R.id.navigation_notifications:
                frag = CouponFragment.newInstance(CouponFragment.INACTIVE);
                mTextMessage.setText(R.string.title_notifications);
                break;
            default:
                return false;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }


        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, frag, frag.getTag());
            ft.commit();
        }

        return true;
    }



    private void showToast(CharSequence text) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        lastToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        lastToast.show();
    }

    private void performAddCouponRequest(List<Coupon> coupons) {
        showToast("Performing add coupon request ");
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
