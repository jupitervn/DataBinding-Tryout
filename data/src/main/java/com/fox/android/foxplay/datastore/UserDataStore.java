package com.fox.android.foxplay.datastore;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public interface UserDataStore {
    String exchangeUserUniqueId(String userId) throws IOException, NoSuchAlgorithmException;
}
