package com.fox.android.foxplay.model;

import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 2/19/16.
 */
public class FeedEntity<T> {
    public String nextLink;
    public String previousLink;
    public List<T> entries;
}
