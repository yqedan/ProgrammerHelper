package com.yusufqedan.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.models.Flashcard;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewFlashcardActivity extends BaseActivity implements View.OnClickListener {
    //private static final String TAG = NewFlashcardActivity.class.getSimpleName();
    @Bind(R.id.new_flashcard_button)
    Button mNewFlashcardButton;
    @Bind(R.id.flashcard_question)
    EditText mFlashcardQuestion;
    @Bind(R.id.flashcard_answer)
    EditText mFlashcardAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flashcard);
        ButterKnife.bind(this);
        mNewFlashcardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        String pushId = intent.getStringExtra("topicId");
        String question = mFlashcardQuestion.getText().toString();
        String answer = mFlashcardAnswer.getText().toString();
        List<Flashcard> flashcards = Parcels.unwrap(intent.getParcelableExtra("flashcards"));
        Flashcard flashcard = new Flashcard(question, answer);
        if (flashcards == null) {
            flashcards = new ArrayList<>();
        }
        flashcards.add(flashcard);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .child("topics")
                .child(pushId)
                .child("flashcards")
                .setValue(flashcards);

        intent = new Intent();
        intent.putExtra("flashcards", Parcels.wrap(flashcards));
        intent.putExtra("flashcard", Parcels.wrap(flashcard));
        setResult(RESULT_OK, intent);
        finish();
    }
}
