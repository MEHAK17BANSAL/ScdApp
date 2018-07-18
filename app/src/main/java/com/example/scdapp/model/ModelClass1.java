package com.example.scdapp.model;

/**
 * Created by admin on 4/22/2018.
 */

public class ModelClass1 {

    String title,image;


    public ModelClass1(){

    }
    public ModelClass1(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
