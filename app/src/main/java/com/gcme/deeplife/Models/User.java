package com.gcme.deeplife.Models;

/**
 * Created by BENGEOS on 3/27/16.
 */
public class User {
    private String id,User_Name,User_Pass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Pass() {
        return User_Pass;
    }

    public void setUser_Pass(String user_Pass) {
        User_Pass = user_Pass;
    }
}
