package com.example.scdapp.model;

/**
 * Created by admin on 4/14/2018.
 */

public class User1 {

    public String email;
    public String password;
    public  String city;
    public  String class1;
    public  String gender;
    public  String name;
    public  String phoneno;
    public  String rollno;


    public User1(){

    }

    public User1(String email, String password, String city, String class1, String gender, String name, String phoneno, String rollno) {
        this.email = email;
        this.password = password;
        this.city = city;
        this.class1 = class1;
        this.gender = gender;
        this.name = name;
        this.phoneno = phoneno;
        this.rollno = rollno;
    }
}
