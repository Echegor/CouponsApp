package com.archelo.coupons.db.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.archelo.coupons.db.dao.CouponDao;
import com.archelo.coupons.db.dao.UserCouponDao;
import com.archelo.coupons.db.data.UserCoupon;
import com.archelo.coupons.db.database.CouponAppRoomDatabase;

import java.util.List;

public class UserCouponRepository {

    private UserCouponDao mCouponDao;
    private LiveData<List<UserCoupon>> mAllCoupons;

    public UserCouponRepository(Application application) {
        CouponAppRoomDatabase db = CouponAppRoomDatabase.getDatabase(application);
        mCouponDao = db.userCouponDao();
        mAllCoupons = mCouponDao.getAllCoupons();
    }

    public LiveData<List<UserCoupon>> getAllCoupons() {
        return mAllCoupons;
    }


    public void insert (UserCoupon coupon) {
        new insertAsyncTask(mCouponDao).execute(coupon);
    }

    public void insert(UserCoupon[] couponsArray) {
        new insertArrayAsyncTask(mCouponDao).execute(couponsArray);
    }

    private static class insertAsyncTask extends AsyncTask<UserCoupon, Void, Void> {

        private UserCouponDao mAsyncTaskDao;

        insertAsyncTask(UserCouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserCoupon... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertArrayAsyncTask extends AsyncTask<UserCoupon, Void, Void> {

        private UserCouponDao mAsyncTaskDao;

        insertArrayAsyncTask(UserCouponDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserCoupon... params) {
            mAsyncTaskDao.insert(params);
            return null;
        }
    }
}
