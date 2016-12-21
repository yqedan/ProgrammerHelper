package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;
import com.yusuf.programmerhelper.models.TriviaQuestion;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TriviaActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.question) TextView mQuestionTextView;
    @Bind(R.id.status) TextView mStatusTextView;
    @Bind(R.id.choice1) Button mChoice1;
    @Bind(R.id.choice2) Button mChoice2;
    @Bind(R.id.choice3) Button mChoice3;
    @Bind(R.id.choice4) Button mChoice4;

    private static final String TAG = TriviaActivity.class.getSimpleName();
    private ArrayList<TriviaQuestion> triviaQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        ButterKnife.bind(this);
        Topic topic = Parcels.unwrap(getIntent().getParcelableExtra("topic"));
        setTitle(topic.getTopicTitle());
        triviaQuestions = topic.getTriviaQuestions();
        Log.d(TAG, "onCreate: " + triviaQuestions.get(0).getQuestion());

    }

    @Override
    public void onClick(View v) {
        if (v == mChoice1) {

        }
        if (v == mChoice2) {

        }
        if (v == mChoice3) {

        }
        if (v == mChoice4) {

        }
    }
}
