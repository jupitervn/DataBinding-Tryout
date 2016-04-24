package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.model.AppLanguageEntity;

import java.util.Map;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/8/16.
 */
public interface LanguageDataStore {

    /**
     *
     * @return a language map in order of insertion.
     * @throws Exception
     */
    Map<Integer, AppLanguageEntity> getAppLanguages() throws Exception;
    void storeAppLanguages(Map<Integer, AppLanguageEntity> appLanguages) throws Exception;

    /**
     * Return the current app language. Return the first one inserted if no default app language has been defined.
     * @return the current app language
     * @throws Exception
     */
    AppLanguageEntity getCurrentAppLanguage();
    int getCurrentLanguageId();
    AppLanguageEntity getAppLanguageWithId(int languageid) throws Exception;
    Map<String, String> getLanguageInfo(int languageId) throws Exception;
    String getLanguageValueWithKey(int languageId, String key);
    void storeLanguageInfo(AppLanguageEntity appLanguageEntity, Map<String, String> languageInfo) throws Exception;
    void setCurrentLanguage(int languageId);
    void removeLanguage(int languageId) throws Exception;


}
