package com.fox.android.foxplay.http.model;

import com.fox.android.foxplay.exception.ConcurrencyLockException;

/**
 * The platform update lock response
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/1/16.
 */
public class TPUpdateLockResponse {
    public ConcurrencyLockException exception;
    public String id;
    public String title;
    public String sequenceToken;
    public String encryptedLock;

}
