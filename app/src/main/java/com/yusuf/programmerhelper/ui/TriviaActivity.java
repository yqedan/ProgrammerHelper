package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;
import com.yusuf.programmerhelper.models.TriviaQuestion;

import org.parceler.Parcels;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {
    private static final String TAG = TriviaActivity.class.getSimpleName();
    private ArrayList<TriviaQuestion> triviaQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        Topic topic = Parcels.unwrap(getIntent().getParcelableExtra("topic"));
        setTitle(topic.getTopicTitle());
        triviaQuestions = topic.getTriviaQuestions();
        Log.d(TAG, "onCreate: " + triviaQuestions.get(0).getQuestion());
    }
}
