package com.hcl.userlist_jsonexample;

public class User {


    private String name;
    private String username;
    private String email;


    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;

    }


}
