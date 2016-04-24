package com.fox.android.foxplay.http;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 3/17/16.
 */
public interface RetrofitParentalControlService {
    @FormUrlEncoded
    @POST("mails/parental_passcode")
    Call<String> resendPasscode(@Field("email") String email, @Field("passcode") String passcode, @Field("language_id") int languageId);
}
