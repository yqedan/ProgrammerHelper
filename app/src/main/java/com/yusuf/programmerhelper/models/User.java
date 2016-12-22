package com.yusuf.programmerhelper.models;

public class User {
    private String name;
    private long highScore;

    public User(){};

    public User(String name, long highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public long getHighScore() {
        return highScore;
    }

    public void setHighScore(long highScore) {
        this.highScore = highScore;
    }
}
