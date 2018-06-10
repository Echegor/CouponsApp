package com.archelo.coupons.db.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.archelo.coupons.db.dao.AzureUserInfoDao;
import com.archelo.coupons.db.data.AzureUserInfo;
import com.archelo.coupons.db.database.CouponAppRoomDatabase;

import java.util.List;


public class AzureUserInfoRepository {

    private AzureUserInfoDao mAzureUserInfoDao;
    private LiveData<List<AzureUserInfo>> mAllAzureUserInfos;

    public AzureUserInfoRepository(Application application) {
        CouponAppRoomDatabase db = CouponAppRoomDatabase.getDatabase(application);
        mAzureUserInfoDao = db.azureUserInfo();
        mAllAzureUserInfos = mAzureUserInfoDao.getAllAzureUserInfos();
    }

    public LiveData<List<AzureUserInfo>> getAllAzureUserInfos() {
        return mAllAzureUserInfos;
    }


    public void insert (AzureUserInfo AzureUserInfo) {
        new insertAsyncTask(mAzureUserInfoDao).execute(AzureUserInfo);
    }

    public void insert(AzureUserInfo[] AzureUserInfosArray) {
        new insertArrayAsyncTask(mAzureUserInfoDao).execute(AzureUserInfosArray);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mAzureUserInfoDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private AzureUserInfoDao mAsyncTaskDao;

        deleteAllAsyncTask(AzureUserInfoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<AzureUserInfo, Void, Void> {

        private AzureUserInfoDao mAsyncTaskDao;

        insertAsyncTask(AzureUserInfoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AzureUserInfo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertArrayAsyncTask extends AsyncTask<AzureUserInfo, Void, Void> {

        private AzureUserInfoDao mAsyncTaskDao;

        insertArrayAsyncTask(AzureUserInfoDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final AzureUserInfo... params) {
            mAsyncTaskDao.insert(params);
            return null;
        }
    }
}

