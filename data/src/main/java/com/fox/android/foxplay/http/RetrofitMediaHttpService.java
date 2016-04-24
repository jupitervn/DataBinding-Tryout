package com.fox.android.foxplay.http;

import com.fox.android.foxplay.model.FeedEntity;
import com.fox.android.foxplay.model.MediaAdditionalInfo;
import com.fox.android.foxplay.model.MediaEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
public interface RetrofitMediaHttpService {
    @GET
    Call<FeedEntity<MediaEntity>> getMediasFromFeed(@Url String feedUrl);

    @GET
    Call<FeedEntity<MediaAdditionalInfo>> getAdditionalInfo(@Url String url);
}
