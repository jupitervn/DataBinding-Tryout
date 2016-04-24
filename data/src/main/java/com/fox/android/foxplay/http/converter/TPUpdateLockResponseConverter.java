package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.fox.android.foxplay.exception.ConcurrencyLockException;
import com.fox.android.foxplay.http.model.TPUpdateLockResponse;

import java.lang.reflect.Type;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/1/16.
 */
public class TPUpdateLockResponseConverter extends EasyDeserializer<TPUpdateLockResponse> {

    @Override
    public TPUpdateLockResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        TPUpdateLockResponse updateLockResponse = null;
        if (json != null && json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            boolean isException = getBooleanValue(jsonObject.get("isException"), false);
            updateLockResponse = new TPUpdateLockResponse();
            if (!isException) {
                JsonElement updateResponseJsonEle = jsonObject.get("updateResponse");
                if (updateResponseJsonEle != null && updateResponseJsonEle.isJsonObject()) {
                    JsonObject updateLockJsonObject = updateResponseJsonEle.getAsJsonObject();
                    updateLockResponse.id = getStringValue(updateLockJsonObject.get("id"), null);
                    updateLockResponse.sequenceToken = getStringValue(updateLockJsonObject.get("sequenceToken"), null);
                    updateLockResponse.encryptedLock = getStringValue(updateLockJsonObject.get("encryptedLock"), null);
                }
            } else {
                updateLockResponse.exception = new ConcurrencyLockException(getStringValue(jsonObject.get("description"), null));
            }

        }
        return updateLockResponse;
    }
}
