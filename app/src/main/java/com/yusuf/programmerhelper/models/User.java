package com.yusuf.programmerhelper.models;

import java.util.HashMap;

public class User {
    private String name;
    private HashMap<String,Long> highScores;

    public User(){};

    public User(String name) {
        this.name = name;
        this.highScores = new HashMap<>();
        //TODO this works for now but will break as we add more subjects
        this.highScores.put("Java",0L);
        this.highScores.put("JavaScript",0L);
    }

    public String getName() {
        return name;
    }

    public void setHighScores(String key, Long value) {
        this.highScores.put(key, value);
    }

    public HashMap<String, Long> getHighScores() {
        return highScores;
    }
}
