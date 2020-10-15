package com.example.doubtsapp;

public class model {

    String QuestionNo,User,description,image;

    model(){

    }

    public model(String questionNo, String user, String description, String image) {
        QuestionNo = questionNo;
        User = user;
        this.description = description;
        this.image = image;
    }

    public String getQuestionNo() {
        return QuestionNo;
    }

    public void setQuestionNo(String questionNo) {
        QuestionNo = questionNo;
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
