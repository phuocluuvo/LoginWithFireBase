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

    public Account(String username, String password, String email, int neutral, int happy, int sad) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.neutral = neutral;
        this.happy = happy;
        this.sad = sad;
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
