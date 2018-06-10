package com.archelo.coupons.db.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.repo.CouponRepository;

import java.util.List;

public class CouponViewModel extends AndroidViewModel {

    private CouponRepository mRepository;

    private LiveData<List<Coupon>> mAllCoupons;

    public CouponViewModel (Application application) {
        super(application);
        mRepository = new CouponRepository(application);
        mAllCoupons = mRepository.getAllCoupons();
    }

    public LiveData<List<Coupon>> getAllCoupons() { return mAllCoupons; }

    public void insert(Coupon coupon) { mRepository.insert(coupon); }

    public void insert(Coupon[] couponsArray) {
        mRepository.insert(couponsArray);
    }

    public void update(Coupon coupon) {
        mRepository.update(coupon);
    }
}