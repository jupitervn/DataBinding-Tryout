package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.MediaDataStore;
import com.fox.android.foxplay.http.RetrofitMediaHttpService;
import com.fox.android.foxplay.model.FeedEntity;
import com.fox.android.foxplay.model.MediaAdditionalInfo;
import com.fox.android.foxplay.model.MediaEntity;

import java.io.IOException;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
public class RetrofitMediaDataStoreImpl implements MediaDataStore {
    private RetrofitMediaHttpService retrofitMediaHttpService;

    public RetrofitMediaDataStoreImpl(RetrofitMediaHttpService retrofitMediaHttpService) {
        this.retrofitMediaHttpService = retrofitMediaHttpService;
    }

    @Override
    public FeedEntity<MediaEntity> getMediasFromFeed(String feedUrl) throws IOException {
        HttpUrl httpUrl = HttpUrl.parse(feedUrl);
        String countParam = httpUrl.queryParameter("count");
        if (countParam == null) {
            httpUrl = httpUrl.newBuilder().addQueryParameter("count", "true").build();
        }
        Call<FeedEntity<MediaEntity>> feedEntityCall = retrofitMediaHttpService.getMediasFromFeed(httpUrl.toString());
        Response<FeedEntity<MediaEntity>> feedEntityResponse = feedEntityCall.execute();
        if (feedEntityResponse.isSuccessful()) {
            FeedEntity<MediaEntity> feedEntity = feedEntityResponse.body();
            HttpUrl.Builder httpBuilder = httpUrl.newBuilder().removeAllQueryParameters("range");
            if (feedEntity.nextLink != null) {
                feedEntity.nextLink = httpBuilder.addQueryParameter("range", feedEntity.nextLink).build().toString();
            }
            if (feedEntity.previousLink != null) {
                feedEntity.previousLink = httpBuilder.addQueryParameter("range", feedEntity.previousLink).build()
                        .toString();
            }
            return feedEntity;
        } else {
            throw new IOException("Network exception " + feedEntityResponse.errorBody().string());
        }
    }

    @Override
    public FeedEntity<MediaAdditionalInfo> getAdditionalInfo(String feedUrl, String mediaIds) throws IOException {
        HttpUrl httpUrl = HttpUrl.parse(feedUrl);
        httpUrl = httpUrl.newBuilder()
                .addQueryParameter("byid", mediaIds)
                .addQueryParameter("fields", "id,:,thumbnails")
                .build();
        Call<FeedEntity<MediaAdditionalInfo>> feedEntityCall = retrofitMediaHttpService.getAdditionalInfo(httpUrl.toString());
        Response<FeedEntity<MediaAdditionalInfo>> response = feedEntityCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Network exception " + response.errorBody().string());
        }
    }
}
