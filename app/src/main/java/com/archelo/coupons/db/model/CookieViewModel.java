package com.archelo.coupons.db.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.archelo.coupons.db.data.Cookie;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.repo.CookieRepository;
import com.archelo.coupons.db.repo.CouponRepository;

import java.util.List;

public class CookieViewModel extends AndroidViewModel {

    private CookieRepository mRepository;

    private LiveData<List<Cookie>> mAllCookies;

    public CookieViewModel (Application application) {
        super(application);
        mRepository = new CookieRepository(application);
        mAllCookies = mRepository.getAllCookies();
    }

    public LiveData<List<Cookie>> getAllCookies() { return mAllCookies; }

    public void insert(Cookie word) { mRepository.insert(word); }

    public void insert(Cookie[] CookiesArray) {
        mRepository.insert(CookiesArray);
    }
}