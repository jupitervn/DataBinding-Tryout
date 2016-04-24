package com.fox.android.foxplay.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/15/16.
 */
public interface RetrofitSubtitleHttpService {
    @GET
    Call<ResponseBody> getSubtitleData(@Url String feedUrl);

}
