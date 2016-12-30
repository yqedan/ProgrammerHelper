package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.adapters.ToDoListAdapter;
import com.yusuf.programmerhelper.models.Task;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoActivity extends AppCompatActivity {
    private String[] mTitles = {"Clean up GitHub", "Create a LinkedIn profile", "Work on your resume", "Work on your cover letter", "Apply for Jobs", "Study using this amazing app", "Go to the Interview", "Win the Interview!"};
    private ArrayList<Task> mToDoList = new ArrayList<>();
    private ToDoListAdapter mAdapter;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);
        for (int i = 0; i < mTitles.length; i++) {
            mToDoList.add(new Task(mTitles[i]));
        }
        mAdapter = new ToDoListAdapter(getApplicationContext(), mToDoList);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        setTitle("To-do List");
    }
}
