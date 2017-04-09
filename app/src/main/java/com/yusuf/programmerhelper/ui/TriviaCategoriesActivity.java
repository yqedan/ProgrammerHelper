package com.yusuf.programmerhelper.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Topic;
import java.util.ArrayList;
import org.parceler.Parcels;

public class TriviaCategoriesActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = TriviaCategoriesActivity.class.getSimpleName();
    @Bind(R.id.triviaTopicListView) ListView mTriviaTopicListView;

    private ArrayList<String> mTopicTitles = new ArrayList<>();
    private ArrayList<Topic> mTopics = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_categories);
        ButterKnife.bind(this);
        setTitle("Trivia Game");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Fetching Data...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
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
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TriviaCategoriesActivity.this,TriviaActivity.class);
        intent.putExtra("mTopic", Parcels.wrap(mTopics.get(position)));
        startActivity(intent);
    }
}
