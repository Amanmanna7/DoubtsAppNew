package com.example.doubtsapp;

public class modelAns {

    String Answer,User;

    modelAns(){

    }

    public modelAns(String answer, String user) {
        Answer = answer;
        User = user;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
