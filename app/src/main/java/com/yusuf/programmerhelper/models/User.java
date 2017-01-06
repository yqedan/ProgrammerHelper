package com.yusuf.programmerhelper.models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String name;
    private HashMap<String,Long> highScores;
    private ArrayList<Task> tasks;

    public User(){}

    public User(String name) {
        this.name = name;
        this.highScores = new HashMap<>();
        //TODO this works for now but will break as we add more subjects
        this.highScores.put("Java",0L);
        this.highScores.put("JavaScript",0L);
        // hard-coded starter to-do list
        this.tasks = new ArrayList<>();
        this.tasks.add(new Task("Clean up GitHub"));
        this.tasks.add(new Task("Create a LinkedIn profile"));
        this.tasks.add(new Task("Work on your resume"));
        this.tasks.add(new Task("Work on your cover letter"));
        this.tasks.add(new Task("Study using this app"));
        this.tasks.add(new Task("Complete the Interview"));
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
