package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.SubtitleDataStore;
import com.fox.android.foxplay.exception.ContentNotFoundException;
import com.fox.android.foxplay.exception.ParseException;
import com.fox.android.foxplay.http.RetrofitSubtitleHttpService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/15/16.
 */
public class CloudSubtitleDataStore implements SubtitleDataStore {
    private RetrofitSubtitleHttpService retrofitSubtitleHttpService;
    private List<Call> historyCall = new ArrayList<>();

    public CloudSubtitleDataStore(RetrofitSubtitleHttpService retrofitSubtitleHttpService) {
        this.retrofitSubtitleHttpService = retrofitSubtitleHttpService;
    }

    @Override
    public InputStream getSubtitleContent(String subtitleUrl)
            throws ContentNotFoundException, IOException, ParseException {
        Call<ResponseBody> call = retrofitSubtitleHttpService.getSubtitleData(subtitleUrl);
        historyCall.add(call);
        Response<ResponseBody> response = call.execute();
        if (response.isSuccessful()) {
            return response.body().byteStream();
        } else {
            String errorBody = response.errorBody().string();
            if (response.code() == 404) {
                throw new ContentNotFoundException(errorBody);
            } else {
                throw new IOException(response.code() + " " + errorBody);
            }
        }
    }

    @Override
    public void cancelAllPreviousRequests() {
        if (!historyCall.isEmpty()) {
            for (Call call : historyCall) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
            }
        }

    }
}
