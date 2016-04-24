package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.http.model.MediaTimeHistory;
import com.fox.android.foxplay.http.model.MediaTimeRequest;

import java.io.IOException;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public interface MovieTimeDataStore {
    MediaTimeHistory getTimeForMovie(MediaTimeRequest request) throws IOException;
    void updateTimeForMovie(String historyId, int lastWatchTimeInSec) throws IOException;
    void clearHistory() throws IOException;
}
