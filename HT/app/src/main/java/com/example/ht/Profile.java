package com.example.ht;

import java.util.ArrayList;

/**
 * @author cole
 *This is a class which keeps track of user profiles
 *
 */
public class Profile {
    protected String username;
    protected String password;
    protected String name;

    //to be added when habit class is implemented
    //protected ArrayList<Habit> habits;

    protected ArrayList<String> followers;
    protected ArrayList<String> following;


    public Profile(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }


    /**
     * Returns that profiles username
     *
     * @return The username of this profile
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the name of the user of this profile
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }


    /**
     * Checks a string, p, to see if it equals the password stored in this profile
     *
     * @param p a string that will be checked against the profiles password
     * @return true if the profiles password equals p, otherwise it returns false
     */
    public boolean checkPassword(String p){
        if(password.equals(p)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * sets the user name of the profile to be equal to the
     * string stored in username.
     *
     * @param username the string which the the profiles username will be set to
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * sets the password of the profile to be equal to the
     * string stored in password
     *
     * @param password the string which the the profiles password will be set to
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * sets the name of the profile to be equal to the
     * string stored in name.
     *
     * @param name the string which the the profiles name will be set to
     */
    public void setName(String name) {
        this.name = name;
    }


    //functions to deal with following and followers to be add when related features are added


}
