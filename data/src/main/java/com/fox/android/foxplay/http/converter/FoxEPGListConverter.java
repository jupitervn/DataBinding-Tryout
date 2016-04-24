package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import com.fox.android.foxplay.http.model.EPGListResponse;
import com.fox.android.foxplay.model.EPGRecordEntity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/31/16.
 */
public class FoxEPGListConverter extends EasyDeserializer<EPGListResponse> {

    @Override
    public EPGListResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        EPGListResponse epgListResponse = null;
        if (json != null) {
            epgListResponse = new EPGListResponse();
            JsonArray epgListArray = null;
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> jsonEntries = jsonObject.entrySet();
                if (!jsonEntries.isEmpty()) {
                    Map.Entry<String, JsonElement> firstEntry = jsonEntries.iterator().next();
                    JsonElement firstEntryValue = firstEntry.getValue();
                    if (firstEntryValue != null && firstEntryValue.isJsonArray()) {
                        epgListArray = firstEntryValue.getAsJsonArray();
                    }
                }
            } else if (json.isJsonArray()) {
                epgListArray = json.getAsJsonArray();
            }
            if (epgListArray != null) {
                Type listOfEpgRecordType = new TypeToken<List<EPGRecordEntity>>() {}.getType();
                List<EPGRecordEntity> epgRecordEntityList = context.<List<EPGRecordEntity>>deserialize(epgListArray,
                        listOfEpgRecordType);
                epgListResponse.addAll(epgRecordEntityList);
            }
        }
        return epgListResponse;
    }
}
