package com.fox.android.foxplay.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 2/18/16.
 */
public class PageEntity {
    public int id;
    public String name;
    public String color;
    public List<SectionEntity> sections;
    public String category;
    @SerializedName("names")
    public LocalizedStringsEntity localizedNames;
}
