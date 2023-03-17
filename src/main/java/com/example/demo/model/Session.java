package com.example.demo.model;

public class Session {
    private String email;

    private String password;

    private  boolean isAdmin;


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn(){
        if(getEmail() != null
                && getPassword() != null) return true;

        return false;
    }
}
