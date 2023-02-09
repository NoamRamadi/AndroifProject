package com.example.gamemaster;

public class Person {
    public String name;
    public String ID;
    public String password;
    public String email;

    public Person(String email , String password, String name, String ID) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }



    public String getEmail() {
        return email;
    }

    public Person(){}
}