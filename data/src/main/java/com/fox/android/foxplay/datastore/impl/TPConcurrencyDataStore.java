package com.fox.android.foxplay.datastore.impl;

import com.fox.android.foxplay.datastore.ConcurrencyDataStore;
import com.fox.android.foxplay.exception.ConcurrencyLockException;
import com.fox.android.foxplay.http.RetrofitTPConcurrencyLockHttpService;
import com.fox.android.foxplay.http.model.TPUpdateLockResponse;
import com.fox.android.foxplay.model.ConcurrencyLockEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/1/16.
 */
public class TPConcurrencyDataStore implements ConcurrencyDataStore {

    private RetrofitTPConcurrencyLockHttpService httpService;

    public TPConcurrencyDataStore(RetrofitTPConcurrencyLockHttpService httpService) {
        this.httpService = httpService;
    }

    @Override
    public ConcurrencyLockEntity updateConcurrencyLock(ConcurrencyLockEntity previousLockInfo) throws IOException {
        Call<TPUpdateLockResponse> updateLockCall = httpService
                .updateLock(previousLockInfo.lockServiceEndpoint + "/web/Concurrency/update",
                        buildLockParams(previousLockInfo));
        Response<TPUpdateLockResponse> response = updateLockCall.execute();
        if (response.isSuccessful()) {
            TPUpdateLockResponse updateLockResponse = response.body();
            if (updateLockResponse.exception == null) {
                ConcurrencyLockEntity concurrencyLockEntity = new ConcurrencyLockEntity();
                concurrencyLockEntity.nextSequenceToken = updateLockResponse.sequenceToken;
                concurrencyLockEntity.encryptedLock = updateLockResponse.encryptedLock;
                concurrencyLockEntity.lockId = updateLockResponse.id;
                concurrencyLockEntity.clientId = previousLockInfo.clientId;
                concurrencyLockEntity.lockServiceEndpoint = previousLockInfo.lockServiceEndpoint;
                return concurrencyLockEntity;
            } else {
                throw updateLockResponse.exception;
            }
        } else {
            throw new ConcurrencyLockException("Cannot update lock " + response.errorBody().string());
        }
    }

    @Override
    public ConcurrencyLockEntity releaseConcurrencyLock(ConcurrencyLockEntity previousLockInfo) throws IOException {
        Call<TPUpdateLockResponse> releaseLockCall = httpService
                .updateLock(previousLockInfo.lockServiceEndpoint + "/web/Concurrency/unlock",
                        buildLockParams(previousLockInfo));
        Response<TPUpdateLockResponse> response = releaseLockCall.execute();
        if (response.isSuccessful()) {
            TPUpdateLockResponse updateLockResponse = response.body();
            if (updateLockResponse.exception == null) {
                ConcurrencyLockEntity concurrencyLockEntity = new ConcurrencyLockEntity();
                concurrencyLockEntity.nextSequenceToken = updateLockResponse.sequenceToken;
                concurrencyLockEntity.encryptedLock = updateLockResponse.encryptedLock;
                concurrencyLockEntity.lockId = previousLockInfo.lockId;
                concurrencyLockEntity.clientId = previousLockInfo.clientId;
                concurrencyLockEntity.lockServiceEndpoint = previousLockInfo.lockServiceEndpoint;
                return concurrencyLockEntity;
            } else {
                throw updateLockResponse.exception;
            }
        } else {
            throw new ConcurrencyLockException("Cannot update lock " + response.errorBody().string());
        }
    }


    private Map<String, String> buildLockParams(ConcurrencyLockEntity previousLockInfo) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("schema", "1.0");
        params.put("_id", previousLockInfo.lockId);
        params.put("_clientId", previousLockInfo.clientId);
        params.put("_sequenceToken", previousLockInfo.nextSequenceToken);
        params.put("_encryptedLock", previousLockInfo.encryptedLock);
        params.put("form", "json");
        return params;
    }
}
