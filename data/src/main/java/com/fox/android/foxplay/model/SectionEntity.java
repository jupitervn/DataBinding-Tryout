package com.fox.android.foxplay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
public class SectionEntity {
    public int id;
    public String name;
    public String feedUrl;
    public String type;
    @SerializedName("names")
    public LocalizedStringsEntity localizedNames;
}
