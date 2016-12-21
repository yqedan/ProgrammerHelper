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
        Collections.shuffle(triviaQuestions);
        setFields();
        mChoice1.setOnClickListener(this);
        mChoice2.setOnClickListener(this);
        mChoice3.setOnClickListener(this);
        mChoice4.setOnClickListener(this);
        mStatusTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (answered) {
            if (v == mStatusTextView) {
                if (count < (triviaQuestions.size() - 1)) {
                    count++;
                    answered = false;
                    setFields();
                } else {
                    mStatusTextView.setText("Your score is " + Math.round((score.doubleValue() / triviaQuestions.size()) * 100) + "%");
                }
            }
        }
        else {
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
        mChoice1.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
        mChoice2.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
        mChoice3.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
        mChoice4.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));

        mQuestionTextView.setText(triviaQuestions.get(count).getQuestion());
        mStatusTextView.setText("");
        mChoice1.setText("A: " + triviaQuestions.get(count).getChoices().get(0));
        mChoice2.setText("B: " + triviaQuestions.get(count).getChoices().get(1));
        if (triviaQuestions.get(count).getChoices().size() > 2) {
            mChoice3.setText("C: " + triviaQuestions.get(count).getChoices().get(2));
            mChoice4.setText("D: " + triviaQuestions.get(count).getChoices().get(3));
        }else{
            mChoice3.setText("");
            mChoice4.setText("");
        }
    }

    private void setFieldsAfterAnswer(View v, int option){
        if(triviaQuestions.get(count).getAnswer() == option){
            mStatusTextView.setText("Correct: " + triviaQuestions.get(count).getExplanation());
            score++;
            v.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentLight, null));
        }else{
            mStatusTextView.setText("Incorrect: " + triviaQuestions.get(count).getExplanation());
            int answer = triviaQuestions.get(count).getAnswer();
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
    }
}
