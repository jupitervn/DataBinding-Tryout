package com.fox.android.foxplay.http;

import com.fox.android.foxplay.http.model.TPUpdateLockResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/1/16.
 */
public interface RetrofitTPConcurrencyLockHttpService {
    @GET
    Call<TPUpdateLockResponse> updateLock(@Url String concurrencyUrl, @QueryMap Map<String, String> params);
}
