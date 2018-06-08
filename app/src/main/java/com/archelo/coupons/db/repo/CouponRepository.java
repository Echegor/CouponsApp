package com.archelo.coupons.db.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.archelo.coupons.db.dao.CouponDao;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.database.CouponAppRoomDatabase;

import java.util.List;

public class CouponRepository {

    private CouponDao mCouponDao;
    private LiveData<List<Coupon>> mAllCoupons;

    public CouponRepository(Application application) {
        CouponAppRoomDatabase db = CouponAppRoomDatabase.getDatabase(application);
        mCouponDao = db.couponDao();
        mAllCoupons = mCouponDao.getAllCookies();
    }

    public LiveData<List<Coupon>> getAllCoupons() {
        return mAllCoupons;
    }


    public void insert (Coupon coupon) {
        new insertAsyncTask(mCouponDao).execute(coupon);
    }

    public void insert(Coupon[] couponsArray) {
        new insertArrayAsyncTask(mCouponDao).execute(couponsArray);
    }

    private static class insertAsyncTask extends AsyncTask<Coupon, Void, Void> {

        private CouponDao mAsyncTaskDao;

        insertAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Coupon... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertArrayAsyncTask extends AsyncTask<Coupon, Void, Void> {

        private CouponDao mAsyncTaskDao;

        insertArrayAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Coupon... params) {
            mAsyncTaskDao.insert(params);
            return null;
        }
    }
}
