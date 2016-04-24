package com.fox.android.foxplay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 3/4/16.
 */
public class AppLanguageEntity {
    public int id;
    public String name;
    @SerializedName("kvps_updated_at")
    public long kvpsUpdated;
    public String code;
}
