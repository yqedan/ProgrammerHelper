package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlashcardCategoriesActivity extends AppCompatActivity {
    private static final String TAG = FlashcardCategoriesActivity.class.getSimpleName();
    private ArrayList<String> mTopicTitles = new ArrayList<>();
    private ArrayList<Topic> mTopics = new ArrayList<>();
    @Bind(R.id.flashcardTopicListView) ListView mFlashcardTopicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_categories);
        ButterKnife.bind(this);
        getTopics();
    }

    private void getTopics(){
        final DatabaseReference topicReference = FirebaseDatabase.getInstance().getReference("topics");
        topicReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mTopics.add(snapshot.getValue(Topic.class));
                    mTopicTitles.add(snapshot.getValue(Topic.class).getTopicTitle());
                }
                ArrayAdapter adapter = new ArrayAdapter(FlashcardCategoriesActivity.this, android.R.layout.simple_list_item_1, mTopicTitles);
                mFlashcardTopicListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
