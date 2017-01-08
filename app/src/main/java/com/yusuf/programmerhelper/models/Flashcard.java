package com.yusuf.programmerhelper.models;

import org.parceler.Parcel;

@Parcel
public class Flashcard {
    String question;
    String answer;

    public Flashcard(){}

    public Flashcard(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
