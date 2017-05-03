package com.yusufqedan.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.models.Topic;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewTopicActivity extends AppCompatActivity implements View.OnClickListener {
    //private static final String TAG = NewTopicActivity.class.getSimpleName();
    @Bind(R.id.new_topic_button)
    Button mNewTopicButton;
    @Bind(R.id.topic_name)
    EditText mTopicName;

    private Topic mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);
        ButterKnife.bind(this);
        setTitle("New Topic");
        mNewTopicButton.setOnClickListener(this);
    }

    private void newTopic() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .child("topics")
                .push();
        mTopic = new Topic(mTopicName.getText().toString(), ref.getKey());
        ref.setValue(mTopic);
    }

    @Override
    public void onClick(View v) {
        newTopic();
        Intent intent = new Intent();
        intent.putExtra("topic", Parcels.wrap(mTopic));
        setResult(2, intent);
        finish();
    }
}