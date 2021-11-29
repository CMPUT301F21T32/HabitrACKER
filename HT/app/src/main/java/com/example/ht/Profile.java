package com.example.ht;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cole
 *This is a class which keeps track of user profiles
 *
 */
public class Profile implements Serializable {
    private String username;
    private String password;
    private String name;

    private ArrayList<String> followers;
    private ArrayList<String> following;


    public Profile(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public Profile(String username, String password, String name, String following, String followers) {
        this.username = username;
        this.password = password;
        this.name = name;


        // Convert the following string into a list
        following = following.substring(1, following.length()-1);

        if(following.length() > 0){
            this.following = new ArrayList<String>(Arrays.asList(following.split(",")));
        }else{
            this.following = new ArrayList<>();
        }


        // Convert the following string into a list
        followers = followers.substring(1, followers.length()-1);
        if(followers.length() > 0) {
            this.followers = new ArrayList<String>(Arrays.asList(followers.split(",")));
        }else{
            this.followers = new ArrayList<>();
        }

        Log.d("FOLLOWING: ", this.following.toString());

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


    /**
     * checks is this profile is following a profile with the username
     * stored in check
     *
     * @param check username to check
     * @return true is profile is following, false otherwise
     */
    public Boolean isFollowing(String check){
        if(following.contains(check)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * checks is this profile is followed by a profile with the username
     * stored in check
     *
     * @param check username to check
     * @return true is profile is followed, false otherwise
     */
    public Boolean isFollower(String check){
        if(followers.contains(check)){
            return true;
        }else{
            return false;
        }
    }
    

    /**
     * adds user to the list of usernames that is
     * following this profile and then updates the database
     *
     * @param user username to add to list of followers
     */
    public void addFollower(String user){
        followers.add(user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(username);


        ref.update("followers", followers.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD FOLLOWER", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD FOLLOWER", "Error updating document", e);
                    }
                });
    }


    /**
     * adds user to the list of usernames that is this
     *  profile is following and then updates the database
     *
     * @param user username to add to list of following
     */
    public void addFollowing(String user){
        following.add(user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(username);


        ref.update("following", following.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD FOLLOWER", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD FOLLOWER", "Error updating document", e);
                    }
                });
    }



    /**
     * removes user from the list of usernames that is
     * following this profile and then updates the database
     *
     * @param user username to remove from list of followers
     */
    public void removeFollower(String user){
        followers.remove(user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(username);


        ref.update("followers", followers.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD FOLLOWER", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD FOLLOWER", "Error updating document", e);
                    }
                });
    }



    /**
     * removes user from the list of usernames that is this
     *  profile is following and then updates the database
     *
     * @param user username to remove from list of following
     */
    public void removerFollowing(String user){
        following.remove(user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(username);


        ref.update("following", following.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD FOLLOWER", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD FOLLOWER", "Error updating document", e);
                    }
                });
    }


}
