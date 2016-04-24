package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.fox.android.foxplay.model.FeedEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
public class FoxFeedEntityConverter<T> extends EasyDeserializer<FeedEntity<T>> {
    private Class<T> classOfT;

    public FoxFeedEntityConverter(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    @Override
    public FeedEntity<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        FeedEntity<T> feedEntity = null;
        if (json != null && json.isJsonObject()) {
            feedEntity = new FeedEntity<>();
            feedEntity.entries = Collections.emptyList();
            JsonObject feedJsonObject = json.getAsJsonObject();
            JsonElement entriesElement = feedJsonObject.get("entries");
            if (entriesElement != null && !entriesElement.isJsonNull() && entriesElement.isJsonArray()) {
                JsonArray entriesArray = entriesElement.getAsJsonArray();
                List<T> entries = new ArrayList<>();
                for (int i = 0; i < entriesArray.size(); i++) {
                    entries.add(context.<T>deserialize(entriesArray.get(i), classOfT));
                }
                feedEntity.entries = entries;

            }
            int totalItemCount = getIntValue(feedJsonObject.get("totalResults"), 0);
            int startIndex = getIntValue(feedJsonObject.get("startIndex"), 0);
            int entryCount = getIntValue(feedJsonObject.get("entryCount"), 0);
            int itemPerPage = getIntValue(feedJsonObject.get("itemsPerPage"), 0);
            int newStartIndex = startIndex + entryCount;
            if (newStartIndex <= totalItemCount) {
                feedEntity.nextLink = String.format("%d-%d", newStartIndex, Math.min(newStartIndex + itemPerPage, totalItemCount));
            }
            if (newStartIndex > 1) {
                feedEntity.previousLink = String.format("%d-%d", Math.max(1, startIndex - itemPerPage), startIndex);
            }
        }
        return feedEntity;
    }
}
