package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.fox.android.foxplay.model.ThumbnailEntity;

import java.lang.reflect.Type;

/**
 * Gson custom type adapter for parsing thumbnail from ThePlatform feed.
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/18/15.
 */
public class FoxThumbnailEntityConverter extends EasyDeserializer<ThumbnailEntity> {

    @Override
    public ThumbnailEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        ThumbnailEntity entity = null;
        if (!json.isJsonNull()) {
            entity = new ThumbnailEntity();
            JsonObject jsonObject = json.getAsJsonObject();
            entity.url = (getStringValue(jsonObject.get("url"), null));
            entity.width = (getIntValue(jsonObject.get("width"), 0));
            entity.height = (getIntValue(jsonObject.get("height"), 0));
            entity.isDefault = getBooleanValue(jsonObject.get("isDefault"), false);
        }
        return entity;
    }
}
