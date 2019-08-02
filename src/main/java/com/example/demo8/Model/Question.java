package com.example.demo8.Model;

import java.sql.Time;

public class Question {
    private int id;
    private String question;
    private String lastAccessUser;
    private Time lastUpdateTime;

    public Question() {
    }

    public Question(int id, String question, String lastAccessUser, Time lastUpdateTime) {
        this.id = id;
        this.question = question;
        this.lastAccessUser = lastAccessUser;
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLastAccessUser() {
        return lastAccessUser;
    }

    public void setLastAccessUser(String lastAccessUser) {
        this.lastAccessUser = lastAccessUser;
    }

    public Time getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Time lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
