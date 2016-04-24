package com.fox.android.foxplay.di;

import com.fox.android.foxplay.http.converter.LocalizedStringsEntityConverter;
import com.fox.android.foxplay.model.LocalizedStringsEntity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.fox.android.foxplay.http.RetrofitAppConfigService;
import com.fox.android.foxplay.http.RetrofitEPGHttpService;
import com.fox.android.foxplay.http.RetrofitLocationHttpService;
import com.fox.android.foxplay.http.RetrofitMediaHttpService;
import com.fox.android.foxplay.http.RetrofitMovieTimeService;
import com.fox.android.foxplay.http.RetrofitParentalControlService;
import com.fox.android.foxplay.http.RetrofitSubtitleHttpService;
import com.fox.android.foxplay.http.RetrofitTPConcurrencyLockHttpService;
import com.fox.android.foxplay.http.RetrofitTokenHttpService;
import com.fox.android.foxplay.http.RetrofitUserHttpService;
import com.fox.android.foxplay.http.converter.FoxEPGListConverter;
import com.fox.android.foxplay.http.converter.FoxEPGRecordConverter;
import com.fox.android.foxplay.http.converter.FoxFeedEntityConverter;
import com.fox.android.foxplay.http.converter.FoxMediaAdditionalInfoConverter;
import com.fox.android.foxplay.http.converter.FoxMediaContentEntityConverter;
import com.fox.android.foxplay.http.converter.FoxMediaEntityConverter;
import com.fox.android.foxplay.http.converter.FoxThumbnailEntityConverter;
import com.fox.android.foxplay.http.converter.MovieTimeRequestConverter;
import com.fox.android.foxplay.http.converter.TPUpdateLockResponseConverter;
import com.fox.android.foxplay.http.model.EPGListResponse;
import com.fox.android.foxplay.http.model.MediaTimeRequest;
import com.fox.android.foxplay.http.model.TPUpdateLockResponse;
import com.fox.android.foxplay.model.EPGRecordEntity;
import com.fox.android.foxplay.model.FeedEntity;
import com.fox.android.foxplay.model.MediaAdditionalInfo;
import com.fox.android.foxplay.model.MediaContentEntity;
import com.fox.android.foxplay.model.MediaEntity;
import com.fox.android.foxplay.model.ThumbnailEntity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient providesOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader("Content-Type", "application/json").build());
            }
        });
        builder.addInterceptor(httpLoggingInterceptor);

        return builder.build();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, @Named("cms_endpoint") String endpointUrl,
            Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides
    @Singleton
    public Converter.Factory providesConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type mediaFeedType = new TypeToken<FeedEntity<MediaEntity>>() {
        }.getType();
        Type mediaInfoType = new TypeToken<FeedEntity<MediaAdditionalInfo>>() {
        }.getType();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(MediaEntity.class, new FoxMediaEntityConverter())
                .registerTypeAdapter(MediaContentEntity.class, new FoxMediaContentEntityConverter())
                .registerTypeAdapter(ThumbnailEntity.class, new FoxThumbnailEntityConverter())
                .registerTypeAdapter(mediaFeedType, new FoxFeedEntityConverter<>(MediaEntity.class))
                .registerTypeAdapter(MediaAdditionalInfo.class, new FoxMediaAdditionalInfoConverter())
                .registerTypeAdapter(mediaInfoType, new FoxFeedEntityConverter<>(MediaAdditionalInfo.class))
                .registerTypeAdapter(MediaTimeRequest.class, new MovieTimeRequestConverter())
                .registerTypeAdapter(EPGListResponse.class, new FoxEPGListConverter())
                .registerTypeAdapter(EPGRecordEntity.class, new FoxEPGRecordConverter())
                .registerTypeAdapter(TPUpdateLockResponse.class, new TPUpdateLockResponseConverter())
                .registerTypeAdapter(LocalizedStringsEntity.class, new LocalizedStringsEntityConverter());

        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    @Named("cms_endpoint")
    public String providesConfigEndpoint() {
        return "http://52.77.14.43/api/v1/";
    }

    @Provides
    @Singleton
    public RetrofitAppConfigService providesRetrofitAppConfigService(Retrofit retrofit) {
        return retrofit.create(RetrofitAppConfigService.class);
    }

    @Provides
    @Singleton
    public RetrofitMediaHttpService providesRetrofitMediaHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitMediaHttpService.class);
    }

    @Provides
    @Singleton
    public RetrofitSubtitleHttpService providesRetrofitSubtitleHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitSubtitleHttpService.class);
    }

    @Provides
    @Singleton
    public RetrofitParentalControlService providesRetrofitParentalControlService(Retrofit retrofit) {
        return retrofit.create(RetrofitParentalControlService.class);
    }

    @Provides
    @Singleton
    public RetrofitTokenHttpService providesTokenHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitTokenHttpService.class);
    }

    @Provides
    @Singleton
    public RetrofitUserHttpService providesUserHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitUserHttpService.class);
    }

    @Provides
    @Singleton
    public RetrofitMovieTimeService providesMovieTimeService(Retrofit retrofit) {
        return retrofit.create(RetrofitMovieTimeService.class);
    }

    @Provides
    @Singleton
    public RetrofitLocationHttpService providesLocationHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitLocationHttpService.class);
    }

    @Provides
    @Singleton
    public RetrofitEPGHttpService providesEPGHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitEPGHttpService.class);
    }

    @Provides
    @Singleton
    public RetrofitTPConcurrencyLockHttpService providesRetrofitTPConcurrencyLockHttpService(Retrofit retrofit) {
        return retrofit.create(RetrofitTPConcurrencyLockHttpService.class);
    }
}

