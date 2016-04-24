package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.ParentalControlDataStore;
import com.fox.android.foxplay.http.RetrofitParentalControlService;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 3/17/16.
 */
public class RetrofitParentalControlDataStoreImpl implements ParentalControlDataStore {
    private RetrofitParentalControlService retrofitParentalControlService;

    public RetrofitParentalControlDataStoreImpl(RetrofitParentalControlService retrofitParentalControlService) {
        this.retrofitParentalControlService = retrofitParentalControlService;
    }

    @Override
    public boolean resendPasscode(String email, String passcode, int languageId) throws Exception {
        Call<String> call = retrofitParentalControlService.resendPasscode(email, passcode, languageId);
        Response<String> response = call.execute();
        return response.isSuccessful();
    }
}
