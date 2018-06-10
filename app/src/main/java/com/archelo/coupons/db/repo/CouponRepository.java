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
        mAllCoupons = mCouponDao.getAllCoupons();
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

    public void update(Coupon coupon) {
        new updateAsyncTask(mCouponDao).execute(coupon);
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

    private static class updateAsyncTask extends AsyncTask<Coupon, Void, Void> {

        private CouponDao mAsyncTaskDao;

        updateAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Coupon... params) {
            mAsyncTaskDao.update(params[0]);
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
