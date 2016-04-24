package com.fox.android.foxplay.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Media object represents movie, episode, series, season...
 */
public class MediaEntity {
    public static final int MOVIE_TYPE = 0x1;
    public static final int EPISODE_TYPE = 0x2;
    public static final int SERIES_TYPE = 0x3;
    public static final int SEASON_TYPE = 0x4;
    public static final int LIVE_TYPE = 0x5;
    public static final int OTHER_TYPE = 0x6;

    public String id;
    public LocalizedStringsEntity name;
    public LocalizedStringsEntity description;
    public LocalizedStringsEntity genre;
    public int duration;
    public int year;
    public long publishDate;
    public long availableDate;
    public long expiredDate;
    //Episode number range from 1..n
    public int episodeNumber;
    //Season number range from 1...n
    public int seasonNumber;
    public List<ThumbnailEntity> thumbnails;
    public List<MediaContentEntity> contents;
    public Map<String, String> metadata;
    public FeedEntity<MediaEntity> childFeeds;
    public String parentId;
    public boolean isFreeContent;
    public int mediaType;
    public String mediaFeedId;
    public boolean isRestrictedContent;
    public String trailerId;
    public Map<String, List<String>> tags;
    public List<String> categories;
    public String availableChannel;
    public boolean isSubscribedContent = true;
    public String contentRating;

    public String getMetadataValueWithKey(String key) {
        if (metadata != null) {
            return metadata.get(key);
        }
        return null;
    }

    public void putMetadataValue(String key, String value) {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        metadata.put(key, value);
    }

    @Override
    public String toString() {
        return name.toString();
    }

}
