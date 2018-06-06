package com.archelo.coupons.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.archelo.coupons.db.data.Coupon;
import com.example.rtl1e.shopritecoupons.R;

import java.util.List;

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.CouponViewHolder> {

    class CouponViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private CouponViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Coupon> mCoupons; // Cached copy of words

    public CouponListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CouponViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        if (mCoupons != null) {
            Coupon current = mCoupons.get(position);
            holder.wordItemView.setText(current.getBrand_name());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Coupon");
        }
    }

    void setCoupons(List<Coupon> words){
        mCoupons = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCoupons has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCoupons != null)
            return mCoupons.size();
        else return 0;
    }
}