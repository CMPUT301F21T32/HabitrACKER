package com.example.ht;




import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProfileTest{



    public Profile createUser(){
                return new Profile("username", "password", "name");
    }

    @Test
    public void testGetUsername() {
        assertEquals(createUser().getUsername(), "username");
    }

    @Test
    public void testGetName() {
        assertEquals(createUser().getName(), "name");
    }

    @Test
    public void testCheckPassword() {
        assertTrue(createUser().checkPassword("password"));
    }

    @Test
    public void testSetUsername() {
        String temp = "test";
        Profile testUser = createUser();
        testUser.setUsername(temp);
        assertEquals(testUser.getUsername(), temp);

    }

    @Test
    public void testSetPassword() {
        String temp = "test";
        Profile testUser = createUser();
        testUser.setPassword(temp);
        assertTrue(testUser.checkPassword(temp));

    }

    @Test
    public void testSetName() {
        String temp = "test";
        Profile testUser = createUser();
        testUser.setName(temp);
        assertEquals(testUser.getName(), temp);
        testUser.setName("name");
    }
}