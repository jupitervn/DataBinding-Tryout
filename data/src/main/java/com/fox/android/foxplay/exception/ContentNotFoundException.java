package com.fox.android.foxplay.exception;

/**
 * This exception is thrown if the content cannot be retrieved (due to HTTP 404) or has not been stored.
 * Created by Jupiter (vu.cao.duy@gmail.com) on 6/30/15.
 */
public class ContentNotFoundException extends Exception {
    public ContentNotFoundException() {
    }

    public ContentNotFoundException(String message) {
        super(message);
    }

    public ContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentNotFoundException(Throwable cause) {
        super(cause);
    }
}
