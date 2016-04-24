package com.fox.android.foxplay.http;

import com.fox.android.foxplay.http.model.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/28/16.
 */
public interface RetrofitLocationHttpService {
    @GET
    Call<CountryResponse> getLocation(@Url String locationUrl);
}
