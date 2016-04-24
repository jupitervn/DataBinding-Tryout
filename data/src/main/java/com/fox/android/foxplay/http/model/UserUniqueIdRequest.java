package com.fox.android.foxplay.http.model;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 3/22/16.
 */
public class UserUniqueIdRequest {
    public String userId;
    public String name = "foxplay";

    public UserUniqueIdRequest(String userId) {
        this.userId = userId;
    }
}
