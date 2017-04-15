package com.yusuf.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Flashcard;
import com.yusuf.programmerhelper.models.Topic;
import java.util.ArrayList;
import java.util.Collections;
import org.parceler.Parcels;

import static com.yusuf.programmerhelper.R.id.deleteTopic;
import static com.yusuf.programmerhelper.R.id.flashcard;

public class FlashcardsActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(flashcard) TextView mFlashcard;
    @Bind(R.id.qanda) TextView mQAndA;
    @Bind(R.id.back) Button mBack;
    @Bind(R.id.start) Button mStart;
    @Bind(R.id.add_flashcard) Button mAddFlashcard;
    @Bind(deleteTopic) Button mDeleteTopic;
    @Bind(R.id.no_flashcards_message) TextView mNoFlashcards;
    private static final String TAG = FlashcardsActivity.class.getSimpleName();
    private ArrayList<Flashcard> flashcards;
    private int count = 0;
    private boolean mQuestionVisible = true;
    private boolean mRoundOver = false;
    public Topic mTopic;

    //TODO work on saving state when device is rotated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);
        ButterKnife.bind(this);
        mTopic = Parcels.unwrap(getIntent().getParcelableExtra("topic"));
        setTitle(mTopic.getTopicTitle());
        flashcards = mTopic.getFlashcards();
        mFlashcard.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mAddFlashcard.setOnClickListener(this);
        mDeleteTopic.setOnClickListener(this);
        if(mTopic.getPushId() == null){ //Is it not a user created mTopic?
            mNoFlashcards.setVisibility(View.GONE);
            start();
        } else if (flashcards != null) { //Are there any flashcards?
            mStart.setVisibility(View.VISIBLE);
            mNoFlashcards.setVisibility(View.GONE);
        }
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
        if (v == mStart) {
            start();
        }
        if (v == mAddFlashcard) {
            Intent intent = new Intent(FlashcardsActivity.this, NewFlashcardActivity.class);
            intent.putExtra("topicId",mTopic.getPushId());
            intent.putExtra("flashcards",Parcels.wrap(flashcards));
            startActivityForResult(intent, 1);
        }
        if(v == mDeleteTopic){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("topics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override public void onDataChange(DataSnapshot dataSnapshot) {
                    ref.child(mTopic.getPushId()).removeValue();
                    finish();
                }

                @Override public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        flashcards = Parcels.unwrap(intent.getParcelableExtra("flashcards"));
        mStart.setVisibility(View.VISIBLE);
        mNoFlashcards.setVisibility(View.GONE);
    }

    private void start(){
        Collections.shuffle(flashcards);
        mFlashcard.setText(flashcards.get(count).getQuestion());
        mFlashcard.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightRed, null));
        mStart.setVisibility(View.INVISIBLE);
        mAddFlashcard.setVisibility(View.INVISIBLE);
        mFlashcard.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);
        mQAndA.setVisibility(View.VISIBLE);
        mDeleteTopic.setVisibility(View.GONE);
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
