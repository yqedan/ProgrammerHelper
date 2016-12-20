package com.yusuf.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yusuf.programmerhelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.quiz) Button mQuizButton;
    @Bind(R.id.todo) Button mTodoButton;
    @Bind(R.id.flashcards) Button mFlashcardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mQuizButton.setOnClickListener(this);
        mTodoButton.setOnClickListener(this);
        mFlashcardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mQuizButton) {
            Log.d(TAG, "onClick: QuizButton");
            Intent intent = new Intent(MainActivity.this,TriviaCategoriesActivity.class);
            startActivity(intent);
        }
        else if (v == mTodoButton) {
            Log.d(TAG, "onClick: TodoButton");
        }
        else if (v == mFlashcardButton) {
            Log.d(TAG, "onClick: FlashcardButton");
            Intent intent = new Intent(MainActivity.this,FlashcardCategoriesActivity.class);
            startActivity(intent);
        }
    }
}
