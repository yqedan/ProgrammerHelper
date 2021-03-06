package com.yusufqedan.programmerhelper.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yusufqedan.programmerhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements View.OnClickListener {
    private static Context mContext;
    private static String mUserName;
    @BindView(R.id.quiz)
    Button mQuizButton;
    @BindView(R.id.todo)
    Button mTodoButton;
    @BindView(R.id.flashcards)
    Button mFlashcardButton;
    @BindView(R.id.welcome)
    TextView mWelcomeText;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Context context, String userName) {
        mContext = context;
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        mUserName = userName;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mWelcomeText.setText("Welcome " + mUserName + "!");
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
