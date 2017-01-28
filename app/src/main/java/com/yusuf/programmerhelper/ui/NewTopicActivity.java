package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yusuf.programmerhelper.R;

public class NewTopicActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
    }

    private void newTopic(){
        final DatabaseReference topicReference = FirebaseDatabase.getInstance().getReference("topics");
    }

    @Override
    public void onClick(View v) {

    }
}
