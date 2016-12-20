package com.yusuf.programmerhelper.models;

import java.util.ArrayList;

public class Topic {
    private String topicTitle;
    private ArrayList<Flashcard> flashcards;
    public Topic(){};

    public String getTopicTitle() {
        return topicTitle;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }
}
