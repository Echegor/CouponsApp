package com.archelo.coupons.db.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.archelo.coupons.db.dao.CouponDao;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.database.CouponAppRoomDatabase;

import java.util.List;

public class CouponRepository {
    private static final String TAG = "CouponRepository";
    private CouponDao mCouponDao;
    private LiveData<List<Coupon>> mAllCoupons;
    private LiveData<List<Coupon>> mAllUnclippedAvailable;
    private LiveData<List<Coupon>> mAllClipped;
    private LiveData<List<Coupon>> mAllUnavailable;
    private int queriesNumber = 0;
    public CouponRepository(Application application) {
        CouponAppRoomDatabase db = CouponAppRoomDatabase.getDatabase(application);
        mCouponDao = db.couponDao();
        mAllCoupons = mCouponDao.getAllCoupons();
        mAllUnclippedAvailable = mCouponDao.getAllUnclippedCoupons();
        mAllClipped = mCouponDao.getAllClippedCoupons();
        mAllUnavailable = mCouponDao.getAllUnavailableCoupons();
    }

    public LiveData<List<Coupon>> getAllCoupons() {
        return mAllCoupons;
    }

    public LiveData<List<Coupon>> getAllUnclippedAvaiable() {
        return mAllUnclippedAvailable;
    }

    public LiveData<List<Coupon>> getAllClipped() {
        return mAllClipped;
    }

    public LiveData<List<Coupon>> getAllUnavailable() {
        return mAllUnavailable;
    }

    public void insert (Coupon coupon) {
        new insertAsyncTask(mCouponDao).execute(coupon);
    }

    public void insert(Coupon[] couponsArray) {
        Log.d(TAG,"Launching async task " + queriesNumber + " with " + couponsArray.length + " coupons") ;
        new insertArrayAsyncTask(mCouponDao,queriesNumber++).execute(couponsArray);
    }


    public void update(Coupon coupon) {
        new updateAsyncTask(mCouponDao).execute(coupon);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mCouponDao).execute();
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

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private CouponDao mAsyncTaskDao;

        deleteAllAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class insertArrayAsyncTask extends AsyncTask<Coupon, Void, Void> {

        private CouponDao mAsyncTaskDao;
        private int queryNumber;

        insertArrayAsyncTask(CouponDao dao, int i) {
            mAsyncTaskDao = dao;
            queryNumber = i;
        }

        @Override
        protected Void doInBackground(final Coupon... params) {
            Log.d(TAG,"Inserting " + params.length + " coupons on task " + queryNumber);
            mAsyncTaskDao.insert(params);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG,"Finished inserting coupons on task " + queryNumber);
        }
    }

    private static class insertListAsyncTask extends AsyncTask<List<Coupon>, Void, Void> {

        private CouponDao mAsyncTaskDao;

        insertListAsyncTask(CouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Coupon>... lists) {
            mAsyncTaskDao.insert(lists[0]);
            return null;
        }
    }
}
