package com.yusuf.programmerhelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.adapters.TodoListAdapter;
import com.yusuf.programmerhelper.models.Task;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoActivity extends AppCompatActivity {
    public static final String TAG = ToDoActivity.class.getSimpleName();
    private ArrayList<Task> mToDoList;
    private TodoListAdapter mAdapter;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);
        getTasks();
        setTitle("To-do List");
    }

    private void getTasks(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid).child("tasks");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mToDoList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mToDoList.add(snapshot.getValue(Task.class));
                }
                mAdapter = new TodoListAdapter(getApplicationContext(), mToDoList);
                mRecyclerView.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause(){
        if(mToDoList != null){ //ensure we have read database at least once
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid).child("tasks");
            ref.setValue(mToDoList);
        }
        super.onPause();
    }
}
