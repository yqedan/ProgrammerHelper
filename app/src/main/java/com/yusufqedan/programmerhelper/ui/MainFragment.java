package com.yusufqedan.programmerhelper.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yusufqedan.programmerhelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.quiz)
    Button mQuizButton;
    @Bind(R.id.todo)
    Button mTodoButton;
    @Bind(R.id.flashcards)
    Button mFlashcardButton;
    private static Context mContext;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Context context) {
        mContext = context;
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mQuizButton.setOnClickListener(this);
        mTodoButton.setOnClickListener(this);
        mFlashcardButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mQuizButton) {
            Intent intent = new Intent(mContext, TriviaCategoriesActivity.class);
            startActivity(intent);
        } else if (v == mTodoButton) {
            Intent intent = new Intent(mContext, ToDoActivity.class);
            startActivity(intent);
        } else if (v == mFlashcardButton) {
            Intent intent = new Intent(mContext, FlashcardCategoriesActivity.class);
            startActivity(intent);
        }
    }
}
