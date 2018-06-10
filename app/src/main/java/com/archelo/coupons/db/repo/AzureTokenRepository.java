package com.archelo.coupons.db.repo;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.archelo.coupons.db.dao.AzureTokenDao;
import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.database.CouponAppRoomDatabase;

import java.util.List;


public class AzureTokenRepository {

    private AzureTokenDao mAzureTokenDao;
    private LiveData<List<AzureToken>> mAllAzureTokens;

    public AzureTokenRepository(Application application) {
        CouponAppRoomDatabase db = CouponAppRoomDatabase.getDatabase(application);
        mAzureTokenDao = db.azureTokenDao();
        mAllAzureTokens = mAzureTokenDao.getAllAzureTokens();
    }

    public LiveData<List<AzureToken>> getAllAzureTokens() {
        return mAllAzureTokens;
    }


    public void insert (AzureToken AzureToken) {
        new insertAsyncTask(mAzureTokenDao).execute(AzureToken);
    }

    public void insert(AzureToken[] AzureTokensArray) {
        new insertArrayAsyncTask(mAzureTokenDao).execute(AzureTokensArray);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mAzureTokenDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private AzureTokenDao mAsyncTaskDao;

        deleteAllAsyncTask(AzureTokenDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<AzureToken, Void, Void> {

        private AzureTokenDao mAsyncTaskDao;

        insertAsyncTask(AzureTokenDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AzureToken... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertArrayAsyncTask extends AsyncTask<AzureToken, Void, Void> {

        private AzureTokenDao mAsyncTaskDao;

        insertArrayAsyncTask(AzureTokenDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final AzureToken... params) {
            mAsyncTaskDao.insert(params);
            return null;
        }
    }
}
