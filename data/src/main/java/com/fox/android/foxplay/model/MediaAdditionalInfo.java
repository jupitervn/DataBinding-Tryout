package com.fox.android.foxplay.model;

import java.util.List;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 3/21/16.
 */
public class MediaAdditionalInfo {
    public String id;
    public boolean isFree;
    public boolean isRestricted;
    public String trailerId;
    public String channelId;
    public String localRating;
    public String productionYear;
    public List<ThumbnailEntity> thumbnails;
}
