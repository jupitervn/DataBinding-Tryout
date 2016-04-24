package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.TokenDataStore;
import com.fox.android.foxplay.http.RetrofitTokenHttpService;
import com.fox.android.foxplay.model.TokenResponse;
import com.fox.android.foxplay.model.TokenZResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/16/16.
 */
public class CloudTokenDataStore implements TokenDataStore {
    private RetrofitTokenHttpService tokenHttpService;
    private String apiUrl;
    private String tokenZApiUrl;
    private String channelCode;

    public CloudTokenDataStore(String apiUrl, String tokenZApiUrl, String channelCode,
            RetrofitTokenHttpService tokenHttpService) {
        this.apiUrl = apiUrl;
        this.tokenZApiUrl = tokenZApiUrl;
        this.channelCode = channelCode;
        this.tokenHttpService = tokenHttpService;
    }

    @Override
    public String exchangeAnonymousToken() throws IOException {
        Call<TokenResponse> call = tokenHttpService.getToken(apiUrl + "/mpx_token/anonymous", null);
        return getTokenFromCall(call);
    }

    @Override
    public String exchangeToolboxToken(String toolboxToken) throws IOException {
        Call<TokenZResponse> tokenZResponseCall = tokenHttpService
                .exchangeToolboxTokenZ(tokenZApiUrl, toolboxToken);
        Response<TokenZResponse> tokenZResponse = tokenZResponseCall.execute();
        if (tokenZResponse.isSuccessful()) {
            String authZToken = tokenZResponse.body().authZToken;
            Call<TokenResponse> call = tokenHttpService
                    .exchangeToolboxToken(apiUrl + "/mpx_token/toolbox.json", authZToken, "isPremium");
            return getTokenFromCall(call);
        } else {
            throw new IOException("Cannot get tokenz for playing " + tokenZResponse.errorBody().string());
        }
    }

    @Override
    public String exchangeOauthToken(String oauthToken) throws IOException {
        Call<TokenResponse> call = tokenHttpService.getToken(apiUrl + "/mpx_token", oauthToken);
        return getTokenFromCall(call);
    }

    private String getTokenFromCall(Call<TokenResponse> call) throws IOException {
        Response<TokenResponse> tokenResponse = call.execute();
        if (tokenResponse.isSuccessful()) {
            return tokenResponse.body().token;
        } else {
            throw new IOException("Cannot get token " + tokenResponse.errorBody().string());
        }
    }
}
