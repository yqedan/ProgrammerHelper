package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Task;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoActivity extends AppCompatActivity {
    private String[] mTitles = {"Clean up GitHub", "Create a LinkedIn profile", "Work on your resume", "Work on your cover letter", "Apply for Jobs", "Study using this amazing app", "Go to the Interview", "Win the Interview!"};
    private ArrayList<Task> mToDoList = new ArrayList<>();
    @Bind(R.id.ToDoList) ListView mToDoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);
//        for (int i = 0; i < mTitles.length; i++) {
//            mToDoList.add(new Task(mTitles[i]));
//        }
        ArrayAdapter adapter = new ArrayAdapter(ToDoActivity.this, android.R.layout.simple_list_item_1, mTitles);
        mToDoListView.setAdapter(adapter);
        setTitle("To-do List");
    }
}
