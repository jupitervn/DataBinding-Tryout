package com.fox.android.foxplay.datastore;

import java.io.IOException;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/16/16.
 */
public interface TokenDataStore {
    String exchangeAnonymousToken() throws IOException;
    String exchangeToolboxToken(String toolboxToken) throws IOException;
    String exchangeOauthToken(String oauthToken) throws IOException;

}
