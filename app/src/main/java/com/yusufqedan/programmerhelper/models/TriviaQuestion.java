package com.yusufqedan.programmerhelper.models;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class TriviaQuestion {
    ArrayList<String> choices;
    ArrayList<ChoiceWithAnswer> choiceWithAnswers = new ArrayList<>();
    String question;
    String explanation;
    int answer;

    public TriviaQuestion() {
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public ArrayList<ChoiceWithAnswer> getChoiceWithAnswers() {
        return choiceWithAnswers;
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

    @Parcel
    public static class ChoiceWithAnswer {
        String answer;
        boolean isCorrectAnswer;

        public ChoiceWithAnswer() {
        }

        public ChoiceWithAnswer(boolean isCorrectAnswer, String answer) {
            this.isCorrectAnswer = isCorrectAnswer;
            this.answer = answer;
        }

        public String getAnswer() {
            return answer;
        }

        public Boolean isCorrectAnswer() {
            return isCorrectAnswer;
        }
    }
}
