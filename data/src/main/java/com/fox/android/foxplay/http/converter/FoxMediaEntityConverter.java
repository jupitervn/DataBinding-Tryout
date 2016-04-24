package com.fox.android.foxplay.http.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import com.fox.android.foxplay.model.LocalizedStringsEntity;
import com.fox.android.foxplay.model.MediaContentEntity;
import com.fox.android.foxplay.model.MediaEntity;
import com.fox.android.foxplay.model.ThumbnailEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Gson custom type adapter for parsing media object from ThePlatform feed.
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/18/15.
 */
public class FoxMediaEntityConverter extends EasyDeserializer<MediaEntity> {

    public static final String WIDEVINE_FORMAT = "Widevine";
    public static final String MPEG4_FORMAT = "mpeg4";

    @Override
    public MediaEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        MediaEntity mediaEntity = null;
        if (!json.isJsonNull()) {
            mediaEntity = new MediaEntity();
            JsonObject jsonObject = json.getAsJsonObject();
            mediaEntity.id = getStringValue(jsonObject.get("id"), null);
            if (mediaEntity.id == null) {
                mediaEntity.id = getStringValue(jsonObject.get("guid"), null);
            }
            mediaEntity.putMetadataValue("guid", getStringValue(jsonObject.get("guid"), null));
            mediaEntity.name = getLocalizedStrings("title", "titleLocalized", jsonObject, context);
            mediaEntity.description = getLocalizedStrings("longDescription", "longDescriptionLocalized", jsonObject, context);
            if (mediaEntity.description.isEmpty()) {
                mediaEntity.description = getLocalizedStrings("description", "descriptionLocalized", jsonObject, context);
            }
            mediaEntity.seasonNumber = getIntValue(jsonObject.get("tvSeasonNumber"), -1);
            mediaEntity.episodeNumber = getIntValue(jsonObject.get("tvSeasonEpisodeNumber"), -1);
            mediaEntity.year = getIntValue(jsonObject.get("year"), 0);
            mediaEntity.publishDate = getLongValue(jsonObject.get("pubDate"), 0);
            mediaEntity.availableDate = getLongValue(jsonObject.get("availableDate"), 0);
            mediaEntity.expiredDate = getLongValue(jsonObject.get("expirationDate"), 0);
            mediaEntity.parentId = getStringValue(jsonObject.get("seriesId"), null);
            mediaEntity.genre = getLocalizedStrings("pl1$genre", null, jsonObject, context);
            String programType = getStringValue(jsonObject.get("programType"), null);
            if (programType != null) {
                switch (programType.toLowerCase().trim()) {
                    case "episode":
                        mediaEntity.mediaType = MediaEntity.EPISODE_TYPE;
                        break;
                    case "series":
                        mediaEntity.mediaType = MediaEntity.SERIES_TYPE;
                        break;
                    case "movie":
                        mediaEntity.mediaType = MediaEntity.MOVIE_TYPE;
                        break;
                    case "sportingevent":
                        mediaEntity.mediaType = MediaEntity.LIVE_TYPE;
                        mediaEntity.putMetadataValue("channel-code", getStringValue(jsonObject.get("pl1$channelCodeEPG"), null));
                        break;
                    default:
                        mediaEntity.mediaType = MediaEntity.OTHER_TYPE;
                        break;
                }
            } else {
                mediaEntity.mediaType = MediaEntity.OTHER_TYPE;
            }
            if (jsonObject.has("categories")) {
                JsonArray jsonArray = jsonObject.getAsJsonArray("categories");
                if (jsonArray.size() > 0) {
                    JsonObject categoryJsonObject = jsonArray.get(0).getAsJsonObject();
                    if (categoryJsonObject != null && !categoryJsonObject.isJsonNull()) {
                        mediaEntity.putMetadataValue("category", getStringValue(categoryJsonObject.get("name"), null));
                    }
                }

            }
            List<ThumbnailEntity> thumbnailEntityList = new ArrayList<>();
            JsonElement thumbnailJsonEle = jsonObject.get("thumbnails");
            if (thumbnailJsonEle.isJsonObject()) {
                List<ThumbnailEntity> thumbnailEntities = parseThumbnailFromObject(thumbnailJsonEle.getAsJsonObject(), context);
                thumbnailEntityList.addAll(thumbnailEntities);
            } else if (thumbnailJsonEle.isJsonArray()) {
                JsonArray thumbnailsArray = thumbnailJsonEle.getAsJsonArray();
                thumbnailEntityList.addAll(parseThumbnailFromArray(thumbnailsArray, context));
            }
            String defaultThumbnailString = getStringValue(jsonObject.get("defaultThumbnailUrl"), null);
            if (defaultThumbnailString != null && !defaultThumbnailString.isEmpty()) {
                ThumbnailEntity defaultThumbnailEntity = new ThumbnailEntity(defaultThumbnailString);
                defaultThumbnailEntity.isDefault = true;
                thumbnailEntityList.add(0, defaultThumbnailEntity);
            }

            mediaEntity.thumbnails = thumbnailEntityList;
            if (jsonObject.has("content")) {
                mediaEntity.contents = parseMediaContents(jsonObject.get("content"), context);
            } else {
                JsonElement mediaJsonEle = jsonObject.get("media");
                if (mediaJsonEle != null && mediaJsonEle.isJsonArray()) {
                    JsonArray mediaJsonArray = mediaJsonEle.getAsJsonArray();
                    if (mediaJsonArray != null && !mediaJsonArray.isJsonNull() && mediaJsonArray.size() > 0) {
                        JsonObject mediaContentJsonObject = mediaJsonArray.get(0).getAsJsonObject();
                        mediaEntity.mediaFeedId = getStringValue(mediaContentJsonObject.get("id"), null);

                        if (mediaEntity.mediaType != MediaEntity.SERIES_TYPE) {
                            mediaEntity.contents = parseMediaContents(mediaContentJsonObject.get("content"), context);
                        }
                    }
                }
            }
            if (mediaEntity.contents != null && !mediaEntity.contents.isEmpty()) {
                for (MediaContentEntity mediaContentEntity : mediaEntity.contents) {
                    if (WIDEVINE_FORMAT.equalsIgnoreCase(mediaContentEntity.format) || MPEG4_FORMAT.equalsIgnoreCase(mediaContentEntity.format)) {
                        mediaEntity.duration = mediaContentEntity.duration;
                        break;
                    }
                }
            }

            if (jsonObject.has("tags")) {
                mediaEntity.tags = parseMediaTags(jsonObject.get("tags"), context);
            }
            LocalizedStringsEntity seriesTitleLocalized = getLocalizedStrings("pl1$series-Title", "pl1$series-TitleLocalized", jsonObject, context);
            if (seriesTitleLocalized != null && !seriesTitleLocalized.isEmpty()) {
                for (String key : seriesTitleLocalized.keySet()) {
                    mediaEntity.putMetadataValue("seriesTitle-" + key, seriesTitleLocalized.get(key));
                }
            }
        }

        return mediaEntity;
    }

    private List<MediaContentEntity> parseMediaContents(JsonElement jsonElement, JsonDeserializationContext context) {
        if (jsonElement != null && !jsonElement.isJsonNull() && jsonElement.isJsonArray()) {
            JsonArray contentsArray = jsonElement.getAsJsonArray();
            Type contentsArrayType = new TypeToken<List<MediaContentEntity>>(){}.getType();
            return context.deserialize(contentsArray, contentsArrayType);
        }
        return null;
    }

    /**
     * Parse thumbnail from jsonArray.
     * @param thumbnailsArray
     * @param context
     * @return empty list if there are no thumbnails
     */
    private List<ThumbnailEntity> parseThumbnailFromArray(JsonArray thumbnailsArray, JsonDeserializationContext context) {
        if (!thumbnailsArray.isJsonNull() && thumbnailsArray.size() > 0) {
            Type typeToken = new TypeToken<List<ThumbnailEntity>>(){}.getType();
            return context.deserialize(thumbnailsArray, typeToken);
        }
        return Collections.emptyList();
    }

    /**
     * Parse thumbnail from jsonObject.
     * @param thumbnailJsonObject
     * @param context
     * @return empty list if there are no thumbnails
     */
    private List<ThumbnailEntity> parseThumbnailFromObject(JsonObject thumbnailJsonObject, JsonDeserializationContext context) {
        Set<Map.Entry<String, JsonElement>> entries = thumbnailJsonObject.entrySet();
        if (!entries.isEmpty()) {
            List<ThumbnailEntity> thumbnailEntities = new ArrayList<>();
            for (Map.Entry<String, JsonElement> entry : entries) {
                thumbnailEntities.add(context.<ThumbnailEntity>deserialize(entry.getValue(), ThumbnailEntity.class));
            }
            return thumbnailEntities;
        }

        return Collections.emptyList();
    }

    /**
     * Parse localized strings from media json object.<br>
     * The default language is English(en).
     * @param mainKey
     * @param localizeKey
     * @param mediaObject
     * @param context
     * @return
     */
    private LocalizedStringsEntity getLocalizedStrings(String mainKey, String localizeKey, JsonObject mediaObject, JsonDeserializationContext context) {
        String title = getStringValue(mediaObject.get(mainKey), null);
        LocalizedStringsEntity titleLocalizedStrings = new LocalizedStringsEntity();
        if (title != null && !title.trim().isEmpty()) {
            titleLocalizedStrings.put("en", title);
        }
        if (localizeKey != null) {
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> titleLocalized = context.deserialize(mediaObject.get(localizeKey), type);
            if (titleLocalized != null) {
                titleLocalizedStrings.putAll(titleLocalized);
            }
        }
        return titleLocalizedStrings;
    }

    private Map<String, List<String>> parseMediaTags(JsonElement jsonElement, JsonDeserializationContext context) {
        Map<String, List<String>> map = null;
        if (jsonElement != null && jsonElement.isJsonArray()) {
            JsonArray tagsArray = jsonElement.getAsJsonArray();
            map = new LinkedHashMap<>();
            for (int i = 0, size = tagsArray.size(); i < size; i++) {
                JsonObject tagJsonEle = tagsArray.get(i).getAsJsonObject();
                String scheme = getStringValue(tagJsonEle.get("scheme"), null);
                String categoryName = getStringValue(tagJsonEle.get("title"), null);
                if (scheme != null && scheme.length() > 0) {
                    List<String> categories = map.get(scheme);
                    if (categories == null) {
                        categories = new ArrayList<>(1);
                    }
                    categories.add(categoryName);
                    map.put(scheme.toLowerCase(), categories);
                }
            }
        }

        return map;
    }
}
