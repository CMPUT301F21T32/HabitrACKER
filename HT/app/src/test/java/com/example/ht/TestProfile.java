package com.example.ht;

import org.junit.Before;

public class TestProfile {
    Profile testUser;

    @Before
    public void createUser(){
        testUser = new Profile("username", "password", "name");
    }
}
