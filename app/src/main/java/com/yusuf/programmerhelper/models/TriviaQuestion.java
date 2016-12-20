package com.yusuf.programmerhelper.models;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class TriviaQuestion {
    ArrayList<String> choices;
    String question;
    String explanation;
    int answer;

    public  TriviaQuestion(){}

    public ArrayList<String> getChoices() {
        return choices;
    }

    public String getQuestion() {
        return question;
    }

    public String getExplanation() {
        return explanation;
    }

    public int getAnswer() {
        return answer;
    }
}
