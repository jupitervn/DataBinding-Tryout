package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.fox.android.foxplay.http.model.MediaTimeRequest;

import java.lang.reflect.Type;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public class MovieTimeRequestConverter implements JsonSerializer<MediaTimeRequest> {

    @Override
    public JsonElement serialize(MediaTimeRequest src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = null;
        if (src != null) {
            jsonObject = new JsonObject();
            JsonObject mediaJsonObject = new JsonObject();
            mediaJsonObject.add("title", new JsonPrimitive(src.title));
            mediaJsonObject.add("guid", new JsonPrimitive(src.guid));
            mediaJsonObject.add("length", new JsonPrimitive(src.length));
            jsonObject.add("media", mediaJsonObject);
        }
        return jsonObject;
    }
}
