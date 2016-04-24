package com.fox.android.foxplay.http.converter;

import com.fox.android.foxplay.model.LocalizedStringsEntity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 4/4/16.
 */
public class LocalizedStringsEntityConverter extends EasyDeserializer<LocalizedStringsEntity> {
    @Override
    public LocalizedStringsEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LocalizedStringsEntity localizedStrings = new LocalizedStringsEntity();

        if (!json.isJsonNull()) {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> map = context.deserialize(json.getAsJsonObject(), type);
            if (map != null) {
                localizedStrings.putAll(map);
            }
        }

        return localizedStrings;
    }
}
