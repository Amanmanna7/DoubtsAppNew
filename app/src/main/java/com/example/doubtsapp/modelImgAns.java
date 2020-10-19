package com.example.doubtsapp;

public class modelImgAns {
    String User,description,image;

    modelImgAns(){

    }

    public modelImgAns(String user, String description, String image) {
        User = user;
        this.description = description;
        this.image = image;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
