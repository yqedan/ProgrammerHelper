package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewTopicActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.new_topic_button) Button newTopicButton;
    @Bind(R.id.topic_name) EditText mTopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
        ButterKnife.bind(this);
        newTopicButton.setOnClickListener(this);
    }

    private void newTopic(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .child("topics")
                .push()
                .setValue(new Topic(mTopicName.getText().toString()));
    }

    @Override
    public void onClick(View v) {
        newTopic();
        NavUtils.navigateUpFromSameTask(this);
    }
}
