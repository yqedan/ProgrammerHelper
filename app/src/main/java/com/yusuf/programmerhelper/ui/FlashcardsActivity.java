package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Flashcard;
import com.yusuf.programmerhelper.models.Topic;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlashcardsActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.flashcard) TextView mFlashcard;
    @Bind(R.id.qanda) TextView mQAndA;
    private static final String TAG = FlashcardsActivity.class.getSimpleName();
    private ArrayList<Flashcard> flashcards;
    private int count = 0;
    private boolean mQuestionVisible = true;
    private boolean mRoundOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);
        ButterKnife.bind(this);
        Topic topic = Parcels.unwrap(getIntent().getParcelableExtra("topic"));
        setTitle(topic.getTopicTitle());
        flashcards = topic.getFlashcards();
        Collections.shuffle(flashcards);
        mFlashcard.setText(flashcards.get(count).getQuestion());
        mFlashcard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mRoundOver == true) {
            return;
        }
        if (mQuestionVisible == true) {
            String answer = flashcards.get(count).getAnswer();
            mFlashcard.setText(answer);
            if (answer.length() > 120) {
                mFlashcard.setGravity(Gravity.NO_GRAVITY);
            }else{
                mFlashcard.setGravity(Gravity.CENTER);
            }
            mQAndA.setText("Answer");
        }else if(count < (flashcards.size() - 1)){
            count++;
            String question = flashcards.get(count).getQuestion();
            mFlashcard.setText(question);
            mQAndA.setText("Question");
            if (question.length() > 120) {
                mFlashcard.setGravity(Gravity.NO_GRAVITY);
            }else{
                mFlashcard.setGravity(Gravity.CENTER);
            }
        }else{
            mFlashcard.setGravity(Gravity.CENTER);
            mFlashcard.setText("Round Over");
            mRoundOver = true;
        }
        mQuestionVisible = !mQuestionVisible;
    }
}
