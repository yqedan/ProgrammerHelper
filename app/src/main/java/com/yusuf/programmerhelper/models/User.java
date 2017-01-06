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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setHighScores(String key, Long value) {
        if (this.highScores == null) {
            this.highScores = new HashMap<>();
        }
        this.highScores.put(key, value);
    }

    public HashMap<String, Long> getHighScores() {
        return highScores;
    }
}
