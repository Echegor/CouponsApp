package com.archelo.shoprite.coupons;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.archelo.shoprite.coupons.http.Post;
import com.archelo.shoprite.coupons.http.StateSaver;
import com.archelo.shoprite.coupons.urls.AzureUrls;
import com.archelo.shoprite.coupons.utils.HeaderUtils;
import com.archelo.shoprite.coupons.utils.ParamUtils;
import com.example.rtl1e.shopritecoupons.R;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private StateSaver stateSaver;
    private CouponAddTask couponAddTask = null;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stateSaver = (StateSaver) getIntent().getSerializableExtra(StateSaver.class.getName());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding all coupons", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                couponAddTask = new CouponAddTask(stateSaver, view);
                couponAddTask.execute((Void) null);


            }
        });


        if (stateSaver == null) {
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.coupon_recycler_view);

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

    public class CouponAddTask extends AsyncTask<Void, Void, Void> {

        private final StateSaver stateSaver;
        private final View view;

        CouponAddTask(StateSaver stateSaver, View view) {
            this.stateSaver = stateSaver;
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<Header> couponHeader = HeaderUtils.getAvailableCoupons(stateSaver.getAzureToken());

            try {
                for (final String coupon : stateSaver.getUserCoupons().getAvailable_ids_array()) {
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(), "Adding coupon " + coupon, Toast.LENGTH_SHORT).show();
                        }
                    });

                    String response = response = new Post.PostBuilder()
                            .url(AzureUrls.COUPONS_ADD)
                            .headers(couponHeader)
                            .jsonObject(ParamUtils.buildCouponJSON(coupon, stateSaver.getAzureUserInfo()))
                            .contentType(ContentType.APPLICATION_JSON)
                            .httpClientContext(stateSaver.getHttpClientContext())
                            .build().doPost();
                    Log.d(TAG, "Response " + response);
                }
            } catch (final IOException ioe) {
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(), "Error occured " + ioe.toString(), Toast.LENGTH_LONG).show();
                    }
                });

                ioe.printStackTrace();
            }
            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            Snackbar.make(view, "Added all coupons", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        @Override
        protected void onCancelled() {
            couponAddTask = null;
        }
    }

}
