package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.model.EPGRecordEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/31/16.
 */
public interface EPGDataStore {
    List<EPGRecordEntity> getEPGOfChannel(String channelCode, Date date) throws IOException;
}
