package com.archelo.coupons.db.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.repo.AzureTokenRepository;

import java.util.List;

public class AzureTokenViewModel extends AndroidViewModel {

    private AzureTokenRepository mRepository;

    private LiveData<List<AzureToken>> mAllAzureTokens;

    public AzureTokenViewModel (Application application) {
        super(application);
        mRepository = new AzureTokenRepository(application);
        mAllAzureTokens = mRepository.getAllAzureTokens();
    }

    public LiveData<List<AzureToken>> getAllAzureTokens() { return mAllAzureTokens; }

    public void insert(AzureToken azureToken) { mRepository.insert(azureToken); }

    public void insert(AzureToken[] AzureTokensArray) {
        mRepository.insert(AzureTokensArray);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
