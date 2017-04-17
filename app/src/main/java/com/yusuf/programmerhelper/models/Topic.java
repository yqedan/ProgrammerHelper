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

    public void addFlashcard(Flashcard flashcard){
        if(flashcards == null){
            flashcards = new ArrayList<>();
        }
        this.flashcards.add(flashcard);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Topic){
            return ((Topic) obj).getPushId().equals(this.getPushId());
        }
        return false;
    }
}
