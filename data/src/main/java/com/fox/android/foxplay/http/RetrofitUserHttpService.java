package com.fox.android.foxplay.http;

import com.fox.android.foxplay.http.model.UserUniqueIdResponse;
import com.fox.android.foxplay.http.model.UserUniqueIdRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public interface RetrofitUserHttpService {
    @POST("affiliates")
    Call<UserUniqueIdResponse> exchangeUniqueId(@Body UserUniqueIdRequest body);
}
