package com.example.shinebookstore;

public class User {

    private  int id;
    private  String username;
    private  String password;
    private  String email;
    private  String address;
    private  String mobile;
    private  String secret;
    private  String last_logged;



    public User() {
    }

    public User(int id, String username, String password, String email, String address, String mobile, String secret, String last_logged) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.secret = secret;
        this.last_logged=last_logged;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLast_logged() {
        return last_logged;
    }

    public void setLast_logged(String last_logged) {
        this.last_logged = last_logged;
    }

}
