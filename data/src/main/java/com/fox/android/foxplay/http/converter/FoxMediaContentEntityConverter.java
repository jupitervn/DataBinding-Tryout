package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.fox.android.foxplay.model.MediaContentEntity;

import java.lang.reflect.Type;

/**
 * Gson custom type adapter for parsing media content object from ThePlatform feed.
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/21/15.
 */
public class FoxMediaContentEntityConverter extends EasyDeserializer<MediaContentEntity> {

    @Override
    public MediaContentEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        MediaContentEntity mediaContentEntity = null;
        if (!json.isJsonNull()) {
            mediaContentEntity = new MediaContentEntity();
            JsonObject jsonObject = json.getAsJsonObject();
            mediaContentEntity.id = (getStringValue(jsonObject.get("id"), null));
            mediaContentEntity.duration = getIntValue(jsonObject.get("duration"), 0);
            mediaContentEntity.format = getStringValue(jsonObject.get("format"), null);
            if (jsonObject.has("url")) {
                mediaContentEntity.url = getStringValue(jsonObject.get("url"), null);
            } else if (jsonObject.has("releases")) {
                JsonArray releasesArray = jsonObject.getAsJsonArray("releases");
                if (releasesArray != null && releasesArray.size() > 0) {
                    JsonObject urlJsonObject = releasesArray.get(0).getAsJsonObject();
                    if (urlJsonObject != null) {
                        mediaContentEntity.url = getStringValue(urlJsonObject.get("url"), null);
                    }
                }
            }
            mediaContentEntity.putAdditionalParam("protection_key", getStringValue(jsonObject.get("protection_key"), null));
        }
        return mediaContentEntity;
    }
}
