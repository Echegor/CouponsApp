package com.archelo.coupons.db.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.archelo.coupons.db.dao.CookieDao;
import com.archelo.coupons.db.data.Cookie;
import com.archelo.coupons.db.database.CouponAppRoomDatabase;

import java.util.List;


public class CookieRepository {

    private CookieDao mCookieDao;
    private LiveData<List<Cookie>> mAllCookies;

    public CookieRepository(Application application) {
        CouponAppRoomDatabase db = CouponAppRoomDatabase.getDatabase(application);
        mCookieDao = db.cookieDao();
        mAllCookies = mCookieDao.getAllCookies();
    }

    public LiveData<List<Cookie>> getAllCookies() {
        return mAllCookies;
    }


    public void insert (Cookie Cookie) {
        new insertAsyncTask(mCookieDao).execute(Cookie);
    }

    public void insert(Cookie[] CookiesArray) {
        new insertArrayAsyncTask(mCookieDao).execute(CookiesArray);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mCookieDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private CookieDao mAsyncTaskDao;

        deleteAllAsyncTask(CookieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<Cookie, Void, Void> {

        private CookieDao mAsyncTaskDao;

        insertAsyncTask(CookieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cookie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertArrayAsyncTask extends AsyncTask<Cookie, Void, Void> {

        private CookieDao mAsyncTaskDao;

        insertArrayAsyncTask(CookieDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Cookie... params) {
            mAsyncTaskDao.insert(params);
            return null;
        }
    }
}
