package com.yusufqedan.programmerhelper.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.models.Topic;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlashcardCategoriesActivity extends BaseActivity implements ListView.OnItemClickListener, View.OnClickListener {
    //private static final String TAG = FlashcardCategoriesActivity.class.getSimpleName();
    @Bind(R.id.flashcardTopicListView)
    ListView mFlashcardTopicListView;
    @Bind(R.id.flashcardAddTopic)
    Button addTopicButton;

    private ArrayList<Topic> mTopics;
    private ArrayList<String> mTopicTitles;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_categories);
        ButterKnife.bind(this);
        addTopicButton.setOnClickListener(this);
        setTitle("Flashcard Topics");
        displayTopicsAndProgress();
    }

    private void displayTopicsAndProgress() {
        //only show progress if nothing loads within 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTopics == null) {
                    mProgressDialog = new ProgressDialog(FlashcardCategoriesActivity.this);
                    mProgressDialog.setTitle("Loading...");
                    mProgressDialog.setMessage("Fetching Data...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                }
            }
        }, 2000);
        getTopics();
    }

    private void getTopics() {
        final ArrayList<Topic> topics = new ArrayList<>();
        final ArrayList<String> topicTitles = new ArrayList<>();
        final DatabaseReference topicReference = FirebaseDatabase.getInstance().getReference("topics");
        // Read the built in topics
        topicReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    topics.add(snapshot.getValue(Topic.class));
                    topicTitles.add(snapshot.getValue(Topic.class).getTopicTitle());
                }
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final DatabaseReference topicReference = FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(uid)
                        .child("topics");
                // Next Read the user created topics
                topicReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            topics.add(snapshot.getValue(Topic.class));
                            topicTitles.add(snapshot.getValue(Topic.class).getTopicTitle());
                        }
                        mTopicTitles = topicTitles;
                        mTopics = topics;
                        ArrayAdapter adapter = new ArrayAdapter(FlashcardCategoriesActivity.this, android.R.layout.simple_list_item_1, topicTitles);
                        mFlashcardTopicListView.setAdapter(adapter);
                        mFlashcardTopicListView.setOnItemClickListener(FlashcardCategoriesActivity.this);
                        if (mProgressDialog != null) mProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void refreshList() {
        ArrayAdapter adapter = new ArrayAdapter(FlashcardCategoriesActivity.this, android.R.layout.simple_list_item_1, mTopicTitles);
        mFlashcardTopicListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FlashcardCategoriesActivity.this, FlashcardsActivity.class);
        intent.putExtra("topic", Parcels.wrap(mTopics.get(position)));
        startActivityForResult(intent, position);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FlashcardCategoriesActivity.this, NewTopicActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //request code is equal to the topic position that was clicked on
        Topic topic = null;
        if (resultCode != 0) { //no result returned, user hit back button on new topic activity, non-user created topic was navigated from with back button
            topic = Parcels.unwrap(data.getParcelableExtra("topic"));
        }
        if (resultCode == 1) { // a topic was deleted from database
            if (topic.equals(mTopics.get(requestCode))) { //check equality to be sure
                mTopics.remove(requestCode); //even though user cant see them we need to delete to ensure data is consistent
                mTopicTitles.remove(requestCode);
            }
        } else if (resultCode == 2) { // a topic was added to database
            mTopics.add(topic);
            mTopicTitles.add(topic.getTopicTitle());
        } else if (resultCode == 3) { // if user presses the back button we refresh the topic in case flashcards were edited
            if (topic.equals(mTopics.get(requestCode))) { //check equality to be sure
                mTopics.set(requestCode, topic);
                mTopicTitles.set(requestCode, topic.getTopicTitle()); //just in case we add a feature to edit topic name
            }
        }
        refreshList();
    }
}
