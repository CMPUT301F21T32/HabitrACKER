package com.example.ht;

/**
 * This is a singleton class that is used to keep track of the
 * Profile that the app is currently logged into
 */

public class MainUser {
    private static MainUser instance = null;
    Profile profile;
    protected MainUser(Profile p) {
        // Exists only to defeat instantiation.
        profile = p;
    }


    //sets the logged in profile to p
    public static MainUser setProfile(Profile p) {
        if(instance == null) {
            instance = new MainUser(p);
        }
        return instance;
    }

    //gets the profile of the user currently
    //logged into the app
    public static Profile getProfile() {

        return instance.profile;
    }



}
