package com.fox.android.foxplay.http;

import com.fox.android.foxplay.http.model.MediaTimeHistory;
import com.fox.android.foxplay.http.model.MediaTimeRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public interface RetrofitMovieTimeService {
    @POST("affiliates/{id}/histories.json")
    Call<MediaTimeHistory> createHistoryTime(@Path("id") String uniqueId, @Body MediaTimeRequest request);
    @DELETE("affiliates/{id}/histories.json")
    Call<ResponseBody> clearHistory(@Path("id") String uniqueId);
    @PUT("histories/{id}")
    Call<ResponseBody> updateHistory(@Path("id") String historyId, @Query("watched_length") int watchedLength);

}
