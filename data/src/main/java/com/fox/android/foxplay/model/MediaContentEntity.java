package com.fox.android.foxplay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 4/1/15.
 */
public class MediaContentEntity {
    public String id;
    public String url;
    public String format;
    public int duration;
    public Map<String, String> additionalParams;

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public void putAdditionalParam(String key, String value) {
        if (additionalParams == null) {
            additionalParams = new HashMap<>();
        }
        additionalParams.put(key, value);
    }
}
