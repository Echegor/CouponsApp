package com.archelo.coupons.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.archelo.coupons.activities.CouponActivity;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.model.CouponViewModel;
import com.archelo.coupons.recycler.CouponListAdapter;
import com.example.rtl1e.shopritecoupons.R;

import java.util.List;


/**
 * Fragment class for each nav menu item
 */
public class CouponFragment extends Fragment {
    public static final int ACTIVE = 0;
    public static final int UNCLIPPED = 1;
    public static final int INACTIVE = 2;
    private static final String ARG_MODE = "ARG_MODE";
    private CouponListAdapter mAdapter ;


    private String mText;
    private int mMode;

    private View mContent;
    private TextView mTextView;

    public static Fragment newInstance(int mode) {
        Fragment frag = new CouponFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.coupon_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mMode = args.getInt(ARG_MODE);
        } else {
            mMode = savedInstanceState.getInt(ARG_MODE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new CouponListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CouponViewModel couponViewModel = ((CouponActivity)getActivity()).getmCouponViewModel();
        switch (mMode){
            case ACTIVE:
                couponViewModel.getAllClipped().observe(this, new Observer<List<Coupon>>() {
                    @Override
                    public void onChanged(@Nullable final List<Coupon> coupons) {
                        // Update the cached copy of the coupons in the adapter.
                        updateToolbarText("Available: " + (coupons == null ? 0 : coupons.size()));
                        mAdapter.setCoupons(coupons);
                    }
                });
                break;
            case INACTIVE:
                couponViewModel.getAllUnavailable().observe(this, new Observer<List<Coupon>>() {
                    @Override
                    public void onChanged(@Nullable final List<Coupon> coupons) {
                        // Update the cached copy of the coupons in the adapter.
                        updateToolbarText("Unavailable: " + (coupons == null ? 0 : coupons.size()));
                        mAdapter.setCoupons(coupons);
                    }
                });
                break;
            case UNCLIPPED:
                couponViewModel.getAllUnclippedAvailable().observe(this, new Observer<List<Coupon>>() {
                    @Override
                    public void onChanged(@Nullable final List<Coupon> coupons) {
                        // Update the cached copy of the coupons in the adapter.
                        updateToolbarText("Unclipped: " + (coupons == null ? 0 : coupons.size()));
                        mAdapter.setCoupons(coupons);
                    }
                });
                break;
        }
        // initialize views
        //mContent = view.findViewById(R.id.fragment_content);
//        mTextView = (TextView) view.findViewById(R.id.text);
//
//        // set text and background color
//        mTextView.setText(mText);
        //mContent.setBackgroundColor(mColor);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(ARG_MODE, mMode);
        super.onSaveInstanceState(outState);
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = ((CouponActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }
}