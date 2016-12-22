package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;
import com.yusuf.programmerhelper.models.TriviaQuestion;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TriviaActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = TriviaActivity.class.getSimpleName();
    @Bind(R.id.question) TextView mQuestionTextView;
    @Bind(R.id.status) TextView mStatusTextView;
    @Bind(R.id.choice1) Button mChoice1;
    @Bind(R.id.choice2) Button mChoice2;
    @Bind(R.id.choice3) Button mChoice3;
    @Bind(R.id.choice4) Button mChoice4;
    @Bind(R.id.next) Button mNext;

    private ArrayList<TriviaQuestion> triviaQuestions;
    private int count = 0;
    private Integer score = 0;
    private boolean answered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        ButterKnife.bind(this);
        Topic topic = Parcels.unwrap(getIntent().getParcelableExtra("topic"));
        setTitle(topic.getTopicTitle());
        triviaQuestions = topic.getTriviaQuestions();
        //Reorganize data for randomization of options
        for(TriviaQuestion triviaQuestion : triviaQuestions){
            ArrayList<String> choices = triviaQuestion.getChoices();
            ArrayList<TriviaQuestion.ChoiceWithAnswer> choiceWithAnswers = triviaQuestion.getChoiceWithAnswers();
            int answer = triviaQuestion.getAnswer();
            for(int i = 0;i < choices.size();i++){
                TriviaQuestion.ChoiceWithAnswer choiceWithAnswer;
                if(answer == i){
                    choiceWithAnswer = new TriviaQuestion.ChoiceWithAnswer(true,choices.get(i));
                }else{
                    choiceWithAnswer = new TriviaQuestion.ChoiceWithAnswer(false,choices.get(i));
                }
                choiceWithAnswers.add(choiceWithAnswer);
            }
        }
        Collections.shuffle(triviaQuestions);
        for(TriviaQuestion triviaQuestion : triviaQuestions){
            Collections.shuffle(triviaQuestion.getChoiceWithAnswers());
        }
        setFields();
        mChoice1.setOnClickListener(this);
        mChoice2.setOnClickListener(this);
        mChoice3.setOnClickListener(this);
        mChoice4.setOnClickListener(this);
        mStatusTextView.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (answered) {
            if (v == mStatusTextView || v == mNext) {
                if (count < (triviaQuestions.size() - 1)) {
                    count++;
                    answered = false;
                    setFields();
                } else {
                    mStatusTextView.setText("You answered " + score + " out of " + triviaQuestions.size() +" questions correct. Your scored a(n) " + Math.round((score.doubleValue() / triviaQuestions.size()) * 100) + "%");
                    mNext.setVisibility(View.INVISIBLE);
                    mChoice1.setVisibility(View.INVISIBLE);
                    mChoice2.setVisibility(View.INVISIBLE);
                    mChoice3.setVisibility(View.INVISIBLE);
                    mChoice4.setVisibility(View.INVISIBLE);
                    mQuestionTextView.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            if (v == mChoice1) {
                setFieldsAfterAnswer(v, 0);
            }
            if (v == mChoice2) {
                setFieldsAfterAnswer(v, 1);
            }
            if (v == mChoice3) {
                setFieldsAfterAnswer(v, 2);
            }
            if (v == mChoice4) {
                setFieldsAfterAnswer(v, 3);
            }
        }
    }

    private void setFields(){
        mNext.setVisibility(View.INVISIBLE);
        mChoice1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
        mChoice2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
        mChoice3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
        mChoice4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));

        mQuestionTextView.setText(triviaQuestions.get(count).getQuestion());
        mStatusTextView.setText("");
        mChoice1.setText("A: " + triviaQuestions.get(count).getChoiceWithAnswers().get(0).getAnswer());
        mChoice2.setText("B: " + triviaQuestions.get(count).getChoiceWithAnswers().get(1).getAnswer());
        if (triviaQuestions.get(count).getChoices().size() > 2) {
            mChoice3.setText("C: " + triviaQuestions.get(count).getChoiceWithAnswers().get(2).getAnswer());
            mChoice4.setText("D: " + triviaQuestions.get(count).getChoiceWithAnswers().get(3).getAnswer());
        } else{
            mChoice3.setText("");
            mChoice4.setText("");
        }
    }

    private void setFieldsAfterAnswer(View v, int option){
        if(triviaQuestions.get(count).getChoiceWithAnswers().get(option).isCorrectAnswer()){
            mStatusTextView.setText("Correct: " + triviaQuestions.get(count).getExplanation());
            score++;
            v.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentLight, null));
        } else{
            mStatusTextView.setText("Incorrect: " + triviaQuestions.get(count).getExplanation());
            ArrayList<TriviaQuestion.ChoiceWithAnswer> choicesWithAnswers = triviaQuestions.get(count).getChoiceWithAnswers();
            int answer = -1;
            for(int i = 0; i < choicesWithAnswers.size();i++){
                if(choicesWithAnswers.get(i).isCorrectAnswer()){
                    answer = i;
                    break;
                }
            }
            v.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightRed, null));

            if (answer == 0){
                mChoice1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentLight, null));
            }
            if (answer == 1){
                mChoice2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentLight, null));
            }
            if( answer == 2){
                mChoice3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentLight, null));
            }
            if (answer == 3){
                mChoice4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentLight, null));
            }
        }
        answered = true;
        mNext.setVisibility(View.VISIBLE);
    }
}
