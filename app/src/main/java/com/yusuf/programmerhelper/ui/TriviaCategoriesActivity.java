package com.yusuf.programmerhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TriviaCategoriesActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = TriviaCategoriesActivity.class.getSimpleName();
    @Bind(R.id.triviaTopicListView) ListView mTriviaTopicListView;

    private ArrayList<String> mTopicTitles = new ArrayList<>();
    private ArrayList<Topic> mTopics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_categories);
        ButterKnife.bind(this);
        setTitle("Trivia Game");
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
                ArrayAdapter adapter = new ArrayAdapter(TriviaCategoriesActivity.this, android.R.layout.simple_list_item_1, mTopicTitles);
                mTriviaTopicListView.setAdapter(adapter);
                mTriviaTopicListView.setOnItemClickListener(TriviaCategoriesActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TriviaCategoriesActivity.this,TriviaActivity.class);
        intent.putExtra("topic", Parcels.wrap(mTopics.get(position)));
        startActivity(intent);
    }
}
