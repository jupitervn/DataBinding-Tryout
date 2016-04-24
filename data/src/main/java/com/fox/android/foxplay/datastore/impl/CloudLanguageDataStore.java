package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.LanguageDataStore;
import com.fox.android.foxplay.http.RetrofitAppConfigService;
import com.fox.android.foxplay.model.AppLanguageEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/9/16.
 */
public class CloudLanguageDataStore implements LanguageDataStore {

    private RetrofitAppConfigService appConfigService;

    public CloudLanguageDataStore(RetrofitAppConfigService appConfigService) {
        this.appConfigService = appConfigService;
    }

    @Override
    public Map<Integer, AppLanguageEntity> getAppLanguages() throws Exception {
        Call<List<AppLanguageEntity>> getLanguagesCall = appConfigService.getAppLanguages();
        Response<List<AppLanguageEntity>> response = getLanguagesCall.execute();
        if (response.isSuccessful()) {
            List<AppLanguageEntity> body = response.body();
            Map<Integer, AppLanguageEntity> appLanguageEntityMap = new LinkedHashMap<>();
            if (!body.isEmpty()) {
                for (AppLanguageEntity appLanguageEntity : body) {
                    appLanguageEntityMap.put(appLanguageEntity.id, appLanguageEntity);
                }
            }
            return appLanguageEntityMap;
        } else {
            throw new Exception(response.errorBody().string());
        }
    }

    @Override
    public void storeAppLanguages(Map<Integer, AppLanguageEntity> appLanguages) throws Exception {
        throw new RuntimeException("Not supported");
    }

    @Override
    public AppLanguageEntity getCurrentAppLanguage() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public int getCurrentLanguageId() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public AppLanguageEntity getAppLanguageWithId(int languageId) throws Exception {
        Map<Integer, AppLanguageEntity> appLanguages = getAppLanguages();
        if (appLanguages != null && !appLanguages.isEmpty()) {
            return appLanguages.get(languageId);
        }
        return null;
    }

    @Override
    public Map<String, String> getLanguageInfo(int languageId) throws Exception {
        Call<Map<String, String>> languageInfoCall = appConfigService.getLanguageInfo(languageId);
        Response<Map<String, String>> response = languageInfoCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string());
        }
    }

    @Override
    public String getLanguageValueWithKey(int languageId, String key) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void storeLanguageInfo(AppLanguageEntity appLanguageEntity, Map<String, String> languageInfo)
            throws Exception {
        throw new RuntimeException("Not supported");

    }

    @Override
    public void setCurrentLanguage(int languageId) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void removeLanguage(int languageId) throws Exception {
        throw new RuntimeException("Not supported");
    }
}
