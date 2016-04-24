package com.fox.android.foxplay.http;

import com.fox.android.foxplay.model.TokenResponse;
import com.fox.android.foxplay.model.TokenZResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/16/16.
 */
public interface RetrofitTokenHttpService {
    @GET
    Call<TokenResponse> getToken(@Url String urlToken, @Query("access_token") String accessToken);
    @GET
    Call<TokenResponse> exchangeToolboxToken(@Url String urlToken, @Query("token") String accessToken, @Query("channel") String channel);
    @GET
    Call<TokenZResponse> exchangeToolboxTokenZ(@Url String toolboxUrl, @Query("authn_token") String authNToken);


}
