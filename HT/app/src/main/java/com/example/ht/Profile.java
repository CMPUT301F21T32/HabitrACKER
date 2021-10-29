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

    protected ArrayList<Profile> followers;
    protected ArrayList<Profile> following;


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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
