package com.yusuf.programmerhelper.models;

public class Task {
    private String title;
    private boolean complete;

    public Task(String title) {
        this.title = title;
        this.complete = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete() {
        this.complete = true;
    }
}
