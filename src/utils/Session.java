/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.User;


public class Session {
    private static Session instance;
    private User user;

    private Session() {
        // Private constructor to prevent instantiation
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void clear() {
        user = null;
    }

    public boolean isValid() {
        // Check if the session is still valid based on some criteria, e.g. session timeout
        return true;
    }
}


