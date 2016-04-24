package com.fox.android.foxplay.exception;

import java.io.IOException;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/1/16.
 */
public class ConcurrencyLockException extends IOException {

    public ConcurrencyLockException() {
    }

    public ConcurrencyLockException(String message) {
        super(message);
    }

    public ConcurrencyLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrencyLockException(Throwable cause) {
        super(cause);
    }
}
