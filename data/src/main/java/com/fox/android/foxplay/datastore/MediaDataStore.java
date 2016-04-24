package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.model.FeedEntity;
import com.fox.android.foxplay.model.MediaAdditionalInfo;
import com.fox.android.foxplay.model.MediaEntity;

import java.io.IOException;

/**
 * Media-related data store.
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
public interface MediaDataStore {
    //Get all medias from feed url.
    FeedEntity<MediaEntity> getMediasFromFeed(String feedUrl) throws IOException;

    FeedEntity<MediaAdditionalInfo> getAdditionalInfo(String feedUrl, String mediaIds) throws IOException;
}
