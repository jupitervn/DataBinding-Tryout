package com.fox.android.foxplay.http.model;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public class MediaTimeRequest {
    public String title;
    public String guid;
    public int length;

    public MediaTimeRequest(String title, String guid, int length) {
        this.title = title;
        this.guid = guid;
        this.length = length;
    }
}
