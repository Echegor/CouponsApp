package com.archelo.coupons.db.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.data.UserCoupon;
import com.archelo.coupons.db.repo.CouponRepository;
import com.archelo.coupons.db.repo.UserCouponRepository;

import java.util.List;

public class UserCouponViewModel extends AndroidViewModel {

    private UserCouponRepository mRepository;

    private LiveData<List<UserCoupon>> mAllCoupons;

    public UserCouponViewModel (Application application) {
        super(application);
        mRepository = new UserCouponRepository(application);
        mAllCoupons = mRepository.getAllCoupons();
    }

    public LiveData<List<UserCoupon>> getAllCoupons() { return mAllCoupons; }

    public void insert(UserCoupon coupon) { mRepository.insert(coupon); }

    public void insert(UserCoupon[] couponsArray) {
        mRepository.insert(couponsArray);
    }
}