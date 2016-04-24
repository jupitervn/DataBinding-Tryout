package com.fox.android.foxplay.di;

import com.fox.android.foxplay.datastore.AppConfigDataStore;
import com.fox.android.foxplay.datastore.ConcurrencyDataStore;
import com.fox.android.foxplay.datastore.LanguageDataStore;
import com.fox.android.foxplay.datastore.MediaDataStore;
import com.fox.android.foxplay.datastore.SubtitleDataStore;
import com.fox.android.foxplay.datastore.ParentalControlDataStore;
import com.fox.android.foxplay.datastore.UserDataStore;
import com.fox.android.foxplay.datastore.impl.CloudLanguageDataStore;
import com.fox.android.foxplay.datastore.impl.CloudSubtitleDataStore;
import com.fox.android.foxplay.datastore.impl.CloudUserDataStore;
import com.fox.android.foxplay.datastore.impl.RetrofitAppConfigDataStoreImpl;
import com.fox.android.foxplay.datastore.impl.RetrofitMediaDataStoreImpl;
import com.fox.android.foxplay.datastore.impl.RetrofitParentalControlDataStoreImpl;
import com.fox.android.foxplay.datastore.impl.TPConcurrencyDataStore;
import com.fox.android.foxplay.http.RetrofitAppConfigService;
import com.fox.android.foxplay.http.RetrofitMediaHttpService;
import com.fox.android.foxplay.http.RetrofitSubtitleHttpService;
import com.fox.android.foxplay.http.RetrofitParentalControlService;
import com.fox.android.foxplay.http.RetrofitTPConcurrencyLockHttpService;
import com.fox.android.foxplay.http.RetrofitUserHttpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
@Module(includes = {NetworkModule.class})
public class DatastoreModule {

    @Provides
    @Singleton
    public AppConfigDataStore providesAppConfigDatastore(RetrofitAppConfigService retrofitAppConfigService) {
        return new RetrofitAppConfigDataStoreImpl(retrofitAppConfigService);
    }
    @Provides
    @Singleton
    public MediaDataStore providesMediaDatastore(RetrofitMediaHttpService retrofitMediaHttpService) {
        return new RetrofitMediaDataStoreImpl(retrofitMediaHttpService);
    }

    @Provides
    @Singleton
    public LanguageDataStore providesCloudLanguageDataStore(RetrofitAppConfigService retrofitAppConfigService) {
        return new CloudLanguageDataStore(retrofitAppConfigService);
    }
    @Provides
    @Singleton
    public SubtitleDataStore providesSubtitleDataStore(RetrofitSubtitleHttpService retrofitSubtitleHttpService) {
        return new CloudSubtitleDataStore(retrofitSubtitleHttpService);
    }

    @Provides
    @Singleton
    public ParentalControlDataStore providesParentalControlDataStore(RetrofitParentalControlService retrofitParentalControlService) {
        return new RetrofitParentalControlDataStoreImpl(retrofitParentalControlService);
    }

    @Provides
    @Singleton
    public UserDataStore providesUserDataStore(RetrofitUserHttpService retrofitUserHttpService) {
        return new CloudUserDataStore(retrofitUserHttpService);
    }

    @Provides
    @Singleton
    public ConcurrencyDataStore providesConcurrencyDataStore(RetrofitTPConcurrencyLockHttpService retrofitTPConcurrencyLockHttpService) {
        return new TPConcurrencyDataStore(retrofitTPConcurrencyLockHttpService);
    }
}
