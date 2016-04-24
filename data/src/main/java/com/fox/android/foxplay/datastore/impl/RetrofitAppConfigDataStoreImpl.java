package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.AppConfigDataStore;
import com.fox.android.foxplay.http.RetrofitAppConfigService;
import com.fox.android.foxplay.model.ChannelEntity;
import com.fox.android.foxplay.model.IntroSlideEntity;
import com.fox.android.foxplay.model.PageEntity;
import com.fox.android.foxplay.model.SettingsEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 2/19/16.
 */
public class RetrofitAppConfigDataStoreImpl implements AppConfigDataStore {

    private RetrofitAppConfigService appConfigService;

    public RetrofitAppConfigDataStoreImpl(RetrofitAppConfigService appConfigService) {
        this.appConfigService = appConfigService;
    }

    @Override
    public List<PageEntity> getPages() throws Exception {
        Call<List<PageEntity>> getPagesCall = appConfigService.getPages();
        Response<List<PageEntity>> response = getPagesCall.execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string());
        }
    }

    @Override
    public List<IntroSlideEntity> getIntroSlides() throws Exception {
        Call<List<IntroSlideEntity>> getSlidesCall = appConfigService.getIntroSlides();
        Response<List<IntroSlideEntity>> response = getSlidesCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string());
        }
    }

    @Override
    public List<ChannelEntity> getChannels() throws Exception {
        Call<List<ChannelEntity>> getChannelsCall = appConfigService.getChannels();
        Response<List<ChannelEntity>> response = getChannelsCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string());
        }
    }

    public PageEntity getPage(int pageId) throws Exception {
        Call<PageEntity> getPagesByIdCall = appConfigService.getPageById(pageId);
        Response<PageEntity> response = getPagesByIdCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string());
        }
    }

    @Override
    public SettingsEntity getSettings() throws Exception {
        Call<SettingsEntity> getPagesByIdCall = appConfigService.getSettings();
        Response<SettingsEntity> response = getPagesByIdCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string());
        }
    }
}
