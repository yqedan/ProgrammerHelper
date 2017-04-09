package com.yusuf.programmerhelper.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;
import java.util.ArrayList;
import org.parceler.Parcels;

public class FlashcardCategoriesActivity extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener{
    private static final String TAG = FlashcardCategoriesActivity.class.getSimpleName();
    @Bind(R.id.flashcardTopicListView) ListView mFlashcardTopicListView;
    @Bind(R.id.flashcardAddTopic) Button addTopicButton;
    private ArrayList<Topic> mTopics;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_categories);
        ButterKnife.bind(this);
        addTopicButton.setOnClickListener(this);
        setTitle("Flashcards");
    }

    @Override
    public void onResume(){
        super.onResume();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Fetching Data...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        getTopics();
    }

    private void getTopics(){
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
                        mTopics = topics;
                        ArrayAdapter adapter = new ArrayAdapter(FlashcardCategoriesActivity.this, android.R.layout.simple_list_item_1, topicTitles);
                        mFlashcardTopicListView.setAdapter(adapter);
                        mFlashcardTopicListView.setOnItemClickListener(FlashcardCategoriesActivity.this);
                        mProgressDialog.dismiss();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FlashcardCategoriesActivity.this,FlashcardsActivity.class);
        intent.putExtra("topic",Parcels.wrap(mTopics.get(position)));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FlashcardCategoriesActivity.this, NewTopicActivity.class);
        startActivity(intent);
    }
}
