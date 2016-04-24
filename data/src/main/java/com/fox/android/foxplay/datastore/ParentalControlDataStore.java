package com.fox.android.foxplay.datastore;

/**
 * Created by Dong (nguyen.dong@2359media.com) on 3/17/16.
 */
public interface ParentalControlDataStore {
    boolean resendPasscode(String email, String passcode, int languageId) throws Exception;
}
