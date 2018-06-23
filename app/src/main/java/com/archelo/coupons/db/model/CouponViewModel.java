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
    private LiveData<List<Coupon>> mAllUnclippedAvailable;
    private LiveData<List<Coupon>> mAllClipped;
    private LiveData<List<Coupon>> mAllUnavailable;

    public CouponViewModel (Application application) {
        super(application);
        mRepository = new CouponRepository(application);
        mAllCoupons = mRepository.getAllCoupons();
        mAllClipped = mRepository.getAllClipped();
        mAllUnclippedAvailable = mRepository.getAllUnclippedAvaiable();
        mAllUnavailable = mRepository.getAllUnavailable();
    }

    public LiveData<List<Coupon>> getAllCoupons() { return mAllCoupons; }

    public LiveData<List<Coupon>> getAllUnclippedAvailable() {
        return mAllUnclippedAvailable;
    }

    public LiveData<List<Coupon>> getAllClipped() {
        return mAllClipped;
    }

    public LiveData<List<Coupon>> getAllUnavailable() {
        return mAllUnavailable;
    }

    public void insert(Coupon coupon) { mRepository.insert(coupon); }

    public void insert(Coupon[] couponsArray) {
        mRepository.insert(couponsArray);
    }

    public void update(Coupon coupon) {
        mRepository.update(coupon);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}