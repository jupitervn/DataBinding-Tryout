package com.fox.android.foxplay.datastore;

import com.fox.android.foxplay.model.ConcurrencyLockEntity;

import java.io.IOException;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/1/16.
 */
public interface ConcurrencyDataStore {
    ConcurrencyLockEntity updateConcurrencyLock(ConcurrencyLockEntity previousLockInfo) throws IOException;

    ConcurrencyLockEntity releaseConcurrencyLock(ConcurrencyLockEntity previousLockInfo) throws IOException;
}
