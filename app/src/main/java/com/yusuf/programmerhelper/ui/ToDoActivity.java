package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yusuf.programmerhelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoActivity extends AppCompatActivity {
    String[] mTasks = {"Clean up Github", "Create a LinkedIn Profile", "Work on Resume", "Work on a Cover Letter", "Apply for Jobs", "Study using this amazing app", "Go to the Interview", "Win the Interview!"};
    @Bind(R.id.ToDoList) ListView mToDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);
        ArrayAdapter adapter = new ArrayAdapter(ToDoActivity.this, android.R.layout.simple_list_item_1, mTasks);
        mToDoList.setAdapter(adapter);
        setTitle("To Do List");
    }
}
