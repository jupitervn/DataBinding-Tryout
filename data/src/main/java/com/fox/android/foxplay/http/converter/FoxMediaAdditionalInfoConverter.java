package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import com.fox.android.foxplay.model.MediaAdditionalInfo;
import com.fox.android.foxplay.model.ThumbnailEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 3/21/16.
 */
public class FoxMediaAdditionalInfoConverter extends EasyDeserializer<MediaAdditionalInfo> {
    @Override
    public MediaAdditionalInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MediaAdditionalInfo mediaInfo = null;

        if (!json.isJsonNull()) {
            mediaInfo = new MediaAdditionalInfo();
            JsonObject jsonObject = json.getAsJsonObject();

            mediaInfo.id = getStringValue(jsonObject.get("id"), null);
            mediaInfo.isFree = getBooleanValue(jsonObject.get("pl1$isFree"), false);
            mediaInfo.isRestricted = getBooleanValue(jsonObject.get("pl1$isRestricted"), false);
            mediaInfo.trailerId = getStringValue(jsonObject.get("pl1$trailerID"), null);
            mediaInfo.localRating = getStringValue(jsonObject.get("pl1$localRating"), null);
            mediaInfo.productionYear = getStringValue(jsonObject.get("pl1$productionYear"), null);

            JsonElement channelIdEleJson = jsonObject.get("pl3$channel_id");
            if (channelIdEleJson != null && channelIdEleJson.isJsonArray()) {
                mediaInfo.channelId = getStringValue(channelIdEleJson.getAsJsonArray().get(0), null);
            }
            Type typeToken = new TypeToken<List<ThumbnailEntity>>(){}.getType();
            mediaInfo.thumbnails = context.deserialize(jsonObject.get("thumbnails"), typeToken);
        }

        return mediaInfo;
    }
}
