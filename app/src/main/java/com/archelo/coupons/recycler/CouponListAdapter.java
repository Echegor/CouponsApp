package com.archelo.coupons.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
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

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CouponViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        if (mCoupons != null) {
            Coupon current = mCoupons.get(position);
            if(current.isClipped()){
                holder.wordItemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_green_light));
            }
            else{
                holder.wordItemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_red_light));
            }

            holder.wordItemView.setText( current.getShort_description());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Coupon");
        }
    }

    public void setCoupons(List<Coupon> words){
        mCoupons = words;
        notifyDataSetChanged();
    }

    public List<Coupon> getCoupons(){
        return mCoupons;
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