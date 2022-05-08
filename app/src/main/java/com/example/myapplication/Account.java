package com.example.myapplication;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Account implements Serializable {


    @Exclude
    private String id;
    private String username;
    private String password;
    private String email;
    private int neutral, happy, sad;
    private String yourfeeling;

    public Account(String username, String password, String email, int neutral, int happy, int sad, String yourfeeling) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.neutral = neutral;
        this.happy = happy;
        this.sad = sad;
        this.yourfeeling = yourfeeling;

    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getSad() {
        return sad;
    }

    public void setSad(int sad) {
        this.sad = sad;
    }

    public String getYourfeeling() {
        return yourfeeling;
    }

    public void setYourfeeling(String yourfeeling) {
        this.yourfeeling = yourfeeling;
    }

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
