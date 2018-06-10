package com.archelo.coupons.db.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.archelo.coupons.db.data.AzureUserInfo;
import com.archelo.coupons.db.repo.AzureUserInfoRepository;

import java.util.List;

public class AzureUserInfoViewModel extends AndroidViewModel {

    private AzureUserInfoRepository mRepository;

    private LiveData<List<AzureUserInfo>> mAllAzureUserInfos;

    public AzureUserInfoViewModel (Application application) {
        super(application);
        mRepository = new AzureUserInfoRepository(application);
        mAllAzureUserInfos = mRepository.getAllAzureUserInfos();
    }

    public LiveData<List<AzureUserInfo>> getAllAzureUserInfos() { return mAllAzureUserInfos; }

    public void insert(AzureUserInfo azureUserInfo) { mRepository.insert(azureUserInfo); }

    public void insert(AzureUserInfo[] AzureUserInfosArray) {
        mRepository.insert(AzureUserInfosArray);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
