package com.fox.android.foxplay.http.model;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public class MediaTimeHistory {
    public String id;
    public int watchedLength;

    public MediaTimeHistory(String historyId, int lastWatchTime) {
        this.id = historyId;
        this.watchedLength = lastWatchTime;
    }
}
