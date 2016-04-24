package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.EPGDataStore;
import com.fox.android.foxplay.http.RetrofitEPGHttpService;
import com.fox.android.foxplay.http.model.EPGListResponse;
import com.fox.android.foxplay.model.EPGRecordEntity;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/31/16.
 */
public class FoxCloudEPGDataStore implements EPGDataStore {
    private String baseUrl;
    private RetrofitEPGHttpService epgHttpService;

    public FoxCloudEPGDataStore(String baseUrl, RetrofitEPGHttpService epgHttpService) {
        this.baseUrl = baseUrl;
        this.epgHttpService = epgHttpService;
    }

    @Override
    public List<EPGRecordEntity> getEPGOfChannel(String channelCode, Date date) throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        baseUrl = baseUrl.replace("[channel_code]", channelCode)
                .replace("[yy][MM][dd]", String.format(Locale.US, "%02d%02d%02d.json", year % 100, month, day));
        Call<EPGListResponse> epgCall = epgHttpService.getEPGOfChannel(baseUrl);
        Response<EPGListResponse> response = epgCall.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Cannot get epg list " + response.errorBody().string());
        }
    }
}
