package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.exception.ContentNotFoundException;
import com.fox.android.foxplay.exception.ParseException;

import java.io.IOException;
import java.io.InputStream;

public interface SubtitleDataStore {
    InputStream getSubtitleContent(String subtitleUrl) throws ContentNotFoundException, IOException, ParseException;

    void cancelAllPreviousRequests();
}