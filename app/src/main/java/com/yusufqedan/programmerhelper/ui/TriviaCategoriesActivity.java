package com.yusufqedan.programmerhelper.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.models.Topic;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TriviaCategoriesActivity extends BaseActivity implements ListView.OnItemClickListener {
    //private static final String TAG = TriviaCategoriesActivity.class.getSimpleName();
    @BindView(R.id.triviaTopicListView)
    ListView mTriviaTopicListView;

    private ArrayList<String> mTopicTitles = new ArrayList<>();
    private ArrayList<Topic> mTopics = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_categories);
        ButterKnife.bind(this);
        setTitle("Multiple Choice Topics");
        displayTopicsAndProgress();
    }

    private void displayTopicsAndProgress() {
        //only show progress if nothing loads within 2 seconds
        new Handler().postDelayed(() -> {
            if (mTopics == null) {
                mProgressDialog = new ProgressDialog(TriviaCategoriesActivity.this);
                mProgressDialog.setTitle("Loading...");
                mProgressDialog.setMessage("Fetching Data...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        }, 2000);
        getTopics();
    }

    private void getTopics() {
        DatabaseReference topicReference = FirebaseDatabase.getInstance().getReference("topics");
        topicReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Topic topic = snapshot.getValue(Topic.class);
                    if (topic.getTriviaQuestions() != null) {
                        mTopics.add(topic);
                        mTopicTitles.add(topic.getTopicTitle());
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(TriviaCategoriesActivity.this, android.R.layout.simple_list_item_1, mTopicTitles);
                mTriviaTopicListView.setAdapter(adapter);
                mTriviaTopicListView.setOnItemClickListener(TriviaCategoriesActivity.this);
                if (mProgressDialog != null) mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TriviaCategoriesActivity.this, TriviaActivity.class);
        intent.putExtra("mTopic", Parcels.wrap(mTopics.get(position)));
        startActivity(intent);
    }
}
