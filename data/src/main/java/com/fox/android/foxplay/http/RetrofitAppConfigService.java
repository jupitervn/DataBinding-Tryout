package com.fox.android.foxplay.http;

import com.fox.android.foxplay.model.AppLanguageEntity;
import com.fox.android.foxplay.model.ChannelEntity;
import com.fox.android.foxplay.model.IntroSlideEntity;
import com.fox.android.foxplay.model.PageEntity;
import com.fox.android.foxplay.model.SettingsEntity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 2/19/16.
 */
public interface RetrofitAppConfigService {
    @GET("pages.json?platform=app")
    Call<List<PageEntity>> getPages();

    @GET("intro_slides.json")
    Call<List<IntroSlideEntity>> getIntroSlides();

    @GET("channels.json")
    Call<List<ChannelEntity>> getChannels();

    @GET("pages/{id}.json?platform=app")
    Call<PageEntity> getPageById(@Path("id") int id);

    @GET("settings.json")
    Call<SettingsEntity> getSettings();

    @GET("languages.json")
    Call<List<AppLanguageEntity>> getAppLanguages();

    @GET("languages/{id}.json")
    Call<Map<String, String>> getLanguageInfo(@Path("id") int id);
}
