package com.example.ht;

/**
 * This is a singleton class that is used to keep track of the
 * Profile that the app is currently logged into
 */

public class MainUser {
    private static Profile temp = new Profile("test", "test", "test", "[]", "[]");
    private static MainUser instance = new MainUser(temp);
    Profile profile;
    protected MainUser(Profile p) {
        // Exists only to defeat instantiation.
        profile = p;
    }


    /**
     * sets the profile of the main user to the Profile p
     *
     * @param p the profile to me set to be the main user
     * @return the instance of the mainUser class
     */
    public static MainUser setProfile(Profile p) {
        //if(instance == null) {
            instance = new MainUser(p);
        //}
        return instance;
    }

    /**
     * returns the profile of the main user
     *
     * @return the Profile class for the main user
     */
    public static Profile getProfile() {

        return instance.profile;
    }



}
