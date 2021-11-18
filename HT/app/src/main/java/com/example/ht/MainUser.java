package com.example.ht;

public class MainUser {
    private static MainUser instance = null;
    Profile profile;
    protected MainUser(Profile p) {
        // Exists only to defeat instantiation.
        profile = p;
    }


    public static MainUser setProfile(Profile p) {
        if(instance == null) {
            instance = new MainUser(p);
        }
        return instance;
    }

    public static Profile getProfile() {

        return instance.profile;
    }



}
