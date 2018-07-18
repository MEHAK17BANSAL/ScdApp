package com.example.scdapp.model;

import java.io.Serializable;

/**
 * Created by admin on 4/22/2018.
 */

public class PrincipalNotice1 implements Serializable {

    public String title;
    public String body;
    public String date;


    public PrincipalNotice1(){

    }


    public PrincipalNotice1(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
