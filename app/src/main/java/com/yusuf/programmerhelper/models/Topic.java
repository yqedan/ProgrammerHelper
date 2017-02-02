package com.yusuf.programmerhelper.models;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Topic {
    String topicTitle;
    ArrayList<Flashcard> flashcards;
    ArrayList<TriviaQuestion> triviaQuestions;
    String pushId;

    public Topic(){}

    public Topic(String topicTitle, String pushId) {
        this.topicTitle = topicTitle;
        this.pushId = pushId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public ArrayList<TriviaQuestion> getTriviaQuestions() {
        return triviaQuestions;
    }

    public String getPushId() {
        return pushId;
    }

    public void addFlashcard(String question, String answer){
        this.flashcards.add(new Flashcard(question,answer));
    }
}
