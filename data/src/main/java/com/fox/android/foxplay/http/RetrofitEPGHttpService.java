package com.fox.android.foxplay.http;

import com.fox.android.foxplay.http.model.EPGListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/31/16.
 */
public interface RetrofitEPGHttpService {
    @GET
    Call<EPGListResponse> getEPGOfChannel(@Url String epgUrl);
}
