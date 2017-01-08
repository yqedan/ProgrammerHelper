package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
    @Bind(R.id.back) Button mBack;
    private static final String TAG = FlashcardsActivity.class.getSimpleName();
    private ArrayList<Flashcard> flashcards;
    private int count = 0;
    private boolean mQuestionVisible = true;
    private boolean mRoundOver = false;

    //TODO work on saving state when device is rotated
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
        mBack.setOnClickListener(this);
        mFlashcard.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightRed, null));
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            if (mRoundOver == true) {
                mRoundOver = false;
                setViewsToAnswer();
            }else if(mQuestionVisible == false){
                setViewsToQuestion();
            }else if(count > 0) {
                count--;
                setViewsToAnswer();
            }
        }
        if (v == mFlashcard) {
            if (mRoundOver == true) {
                return;
            }else if (mQuestionVisible == true) {
                setViewsToAnswer();
            }else if(count < (flashcards.size() - 1)){
                count++;
                setViewsToQuestion();
            }else{
                mFlashcard.setGravity(Gravity.CENTER);
                mQAndA.setText(" ");
                mFlashcard.setText("Round Over");
                mFlashcard.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.icons, null));
                mRoundOver = true;
            }
        }
    }

    private void setViewsToAnswer(){
        mQuestionVisible = false;
        mQAndA.setText("Answer");
        String answer = flashcards.get(count).getAnswer();
        mFlashcard.setText(answer);
        setGravityBasedOnCharCount(answer.length());

        mFlashcard.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
    }

    private void setViewsToQuestion(){
        mQuestionVisible = true;
        mQAndA.setText("Question");
        String question = flashcards.get(count).getQuestion();
        mFlashcard.setText(question);
        setGravityBasedOnCharCount(question.length());
        mFlashcard.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightRed, null));
    }

    private void setGravityBasedOnCharCount(int count){
        if (count > 120) {
            mFlashcard.setGravity(Gravity.NO_GRAVITY);
        }else{
            mFlashcard.setGravity(Gravity.CENTER);
        }
    }
}
