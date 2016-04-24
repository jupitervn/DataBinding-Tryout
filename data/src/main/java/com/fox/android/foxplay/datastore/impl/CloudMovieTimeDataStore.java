package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.MovieTimeDataStore;
import com.fox.android.foxplay.http.RetrofitMovieTimeService;
import com.fox.android.foxplay.http.model.MediaTimeHistory;
import com.fox.android.foxplay.http.model.MediaTimeRequest;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public class CloudMovieTimeDataStore implements MovieTimeDataStore {
    private RetrofitMovieTimeService retrofitMovieTimeService;
    private String uniqueId;

    public CloudMovieTimeDataStore(RetrofitMovieTimeService retrofitMovieTimeService, String userUniqueId) {
        this.retrofitMovieTimeService = retrofitMovieTimeService;
        this.uniqueId = userUniqueId;
    }

    @Override
    public MediaTimeHistory getTimeForMovie(MediaTimeRequest request) throws IOException {
        Call<MediaTimeHistory> timeHistoryCall = retrofitMovieTimeService.createHistoryTime(uniqueId, request);
        Response<MediaTimeHistory> timeHistoryResponse = timeHistoryCall.execute();
        if (timeHistoryResponse.isSuccessful()) {
            return timeHistoryResponse.body();
        } else {
            throw new IOException("Cannot get time for movie " + timeHistoryResponse.errorBody().string());
        }
    }

    @Override
    public void updateTimeForMovie(String historyId, int lastWatchTimeInSec) throws IOException {
        Call<ResponseBody> updateHistoryCall = retrofitMovieTimeService.updateHistory(historyId, lastWatchTimeInSec);
        Response<ResponseBody> updateHistoryResponse = updateHistoryCall.execute();
        if (!updateHistoryResponse.isSuccessful()) {
            throw new IOException("Cannot update time for " + historyId + " " + lastWatchTimeInSec + " " + updateHistoryResponse.errorBody().string());
        }
    }

    @Override
    public void clearHistory() throws IOException {
        Call<ResponseBody> clearHistoryCall = retrofitMovieTimeService.clearHistory(uniqueId);
        clearHistoryCall.execute();
    }
}
