package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.UserDataStore;
import com.fox.android.foxplay.http.RetrofitUserHttpService;
import com.fox.android.foxplay.http.model.UserUniqueIdResponse;
import com.fox.android.foxplay.http.model.UserUniqueIdRequest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public class CloudUserDataStore implements UserDataStore {

    private RetrofitUserHttpService httpService;

    public CloudUserDataStore(RetrofitUserHttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public String exchangeUserUniqueId(String userId) throws IOException {
        byte[] bytesOfMessage = userId.getBytes("UTF-8");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] md5Digest = md.digest(bytesOfMessage);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md5Digest.length; ++i) {
                sb.append(Integer.toHexString((md5Digest[i] & 0xFF) | 0x100).substring(1,3));
            }
            System.out.println("HASH " + sb.toString() + " " + userId);
            Call<UserUniqueIdResponse> userUniqueIdResponseCall = httpService
                    .exchangeUniqueId(new UserUniqueIdRequest(sb.toString()));
            Response<UserUniqueIdResponse> response = userUniqueIdResponseCall.execute();
            if (response.isSuccessful()) {
                return response.body().id;
            } else {
                throw new IOException("Cannot exchange unique id: " + response.errorBody().string());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }

    }
}
