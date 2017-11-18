package com.baitap.quan.eatitorderfood.Model;

/**
 * Created by User on 11/12/2017.
 */

public class User {

    private String Name;
    private String Password;
    private String PhoneNumber;

    public User(String name, String password) {
        Name = name;
        Password = password;
//        PhoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
