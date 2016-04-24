package com.fox.android.foxplay.exception;

/**
 * This exception is thrown when there's an error when parsing the response.
 * Created by Jupiter (vu.cao.duy@gmail.com) on 7/2/15.
 */
public class ParseException extends Exception {

    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

}
