package com.yusufqedan.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.models.Flashcard;
import com.yusufqedan.programmerhelper.models.Topic;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yusufqedan.programmerhelper.R.id.deleteTopic;
import static com.yusufqedan.programmerhelper.R.id.flashcard;
import static com.yusufqedan.programmerhelper.R.id.flashcard_scroll;

public class FlashcardsActivity extends BaseActivity implements View.OnClickListener {
    //private static final String TAG = FlashcardsActivity.class.getSimpleName();
    @BindView(flashcard)
    TextView mFlashcard;
    @BindView(flashcard_scroll)
    ScrollView mFlashcardScroll;
    @BindView(R.id.qanda)
    TextView mQAndA;
    @BindView(R.id.navigationButtons)
    LinearLayout mNavigationButtons;
    @BindView(R.id.back)
    Button mBack;
    @BindView(R.id.forward)
    Button mForward;
    @BindView(R.id.start)
    Button mStart;
    @BindView(R.id.add_flashcard)
    Button mAddFlashcard;
    @BindView(deleteTopic)
    Button mDeleteTopic;
    @BindView(R.id.no_flashcards_message)
    TextView mNoFlashcards;

    private ArrayList<Flashcard> flashcards;
    private int count = 0;
    private boolean mQuestionVisible = true;
    private boolean mRoundOver = false;
    private Topic mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);
        ButterKnife.bind(this);
        mTopic = Parcels.unwrap(getIntent().getParcelableExtra("topic"));
        setTitle(mTopic.getTopicTitle());
        flashcards = mTopic.getFlashcards();
        mBack.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mAddFlashcard.setOnClickListener(this);
        mDeleteTopic.setOnClickListener(this);
        if (mTopic.getPushId() == null) { //Is it not a user created topic?
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
            } else if (mQuestionVisible == false) {
                setViewsToQuestion();
            } else if (count > 0) {
                count--;
                setViewsToAnswer();
            }
        }
        if (v == mForward) {
            if (mRoundOver == true) {
                return;
            } else if (mQuestionVisible == true) {
                setViewsToAnswer();
            } else if (count < (flashcards.size() - 1)) {
                count++;
                setViewsToQuestion();
            } else {
                mFlashcard.setGravity(Gravity.CENTER);
                mQAndA.setText(" ");
                mFlashcard.setText("Round Over");
                mFlashcardScroll.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.icons, null));
                mRoundOver = true;
            }
        }
        if (v == mStart) {
            start();
        }
        if (v == mAddFlashcard) {
            Intent intent = new Intent(FlashcardsActivity.this, NewFlashcardActivity.class);
            intent.putExtra("topicId", mTopic.getPushId());
            intent.putExtra("flashcards", Parcels.wrap(flashcards));
            startActivityForResult(intent, 1);
        }
        if (v == mDeleteTopic) {
            //todo add confirmation message before deleting topic
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(uid)
                    .child("topics");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ref.child(mTopic.getPushId()).removeValue();
                    Intent intent = new Intent();
                    intent.putExtra("topic", Parcels.wrap(mTopic));
                    setResult(1, intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == -1){ //Flashcard added
            flashcards = Parcels.unwrap(intent.getParcelableExtra("flashcards"));
            Flashcard flashcard = Parcels.unwrap(intent.getParcelableExtra("flashcard"));
            mTopic.addFlashcard(flashcard);
            mStart.setVisibility(View.VISIBLE);
            mNoFlashcards.setVisibility(View.GONE);
        }
        if(resultCode == 0){  //back or up button pressed

        }
    }

    @Override
    public void onBackPressed() {
        saveTopicsParcel();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveTopicsParcel();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTopicsParcel() {
        if (mTopic.getPushId() != null) {//is this a user created topic?
            Intent intent = new Intent();
            intent.putExtra("topic", Parcels.wrap(mTopic));
            setResult(3, intent);
        }
    }

    private void start() {
        Collections.shuffle(flashcards);
        mFlashcard.setText(flashcards.get(count).getQuestion());
        mFlashcardScroll.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightRed, null));
        mStart.setVisibility(View.INVISIBLE);
        mAddFlashcard.setVisibility(View.INVISIBLE);
        mFlashcardScroll.setVisibility(View.VISIBLE);
        mNavigationButtons.setVisibility(View.VISIBLE);
        mQAndA.setVisibility(View.VISIBLE);
        mDeleteTopic.setVisibility(View.GONE);
    }

    private void setViewsToAnswer() {
        mQuestionVisible = false;
        mQAndA.setText("Answer");
        String answer = flashcards.get(count).getAnswer();
        mFlashcard.setText(answer);
        setGravityBasedOnCharCount(answer.length());
        mFlashcardScroll.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightYellow, null));
    }

    private void setViewsToQuestion() {
        mQuestionVisible = true;
        mQAndA.setText("Question");
        String question = flashcards.get(count).getQuestion();
        mFlashcard.setText(question);
        setGravityBasedOnCharCount(question.length());
        mFlashcardScroll.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lightRed, null));
    }

    private void setGravityBasedOnCharCount(int count) {
        if (count > 120) {
            mFlashcard.setGravity(Gravity.NO_GRAVITY);
        } else {
            mFlashcard.setGravity(Gravity.CENTER);
        }
    }
}
