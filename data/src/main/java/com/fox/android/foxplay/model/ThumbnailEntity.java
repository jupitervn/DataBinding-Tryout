package com.fox.android.foxplay.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/18/15.
 */
public class ThumbnailEntity {

    public String id;
    public String url;
    public int width;
    public int height;
    public boolean isDefault;
    public HashMap<String, String> additionalParams;

    public ThumbnailEntity() {
    }

    public ThumbnailEntity(String url) {
        this.url = url;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(HashMap<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public String getParamWithKey(String key) {
        return additionalParams == null ? null : additionalParams.get(key);
    }

    public void putParamWithKey(String key, String value) {
        if (additionalParams == null) {
            additionalParams = new HashMap<>();
        }
        additionalParams.put(key, value);
    }



}
