package com.yusuf.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;

import org.parceler.Parcels;

public class FlashcardsActivity extends AppCompatActivity {
    private static final String TAG = FlashcardsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);
        Intent intent = getIntent();
        Topic topic = Parcels.unwrap(intent.getParcelableExtra("topic"));
        Log.d(TAG, "onCreate: "+ topic.getTopicTitle());
    }
}
