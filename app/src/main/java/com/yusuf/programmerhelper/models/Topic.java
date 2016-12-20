package com.yusuf.programmerhelper.models;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Topic {
    String topicTitle;
    ArrayList<Flashcard> flashcards;
    ArrayList<TriviaQuestion> triviaQuestions;

    public Topic(){}

    public String getTopicTitle() {
        return topicTitle;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public ArrayList<TriviaQuestion> getTriviaQuestions() {
        return triviaQuestions;
    }
}
