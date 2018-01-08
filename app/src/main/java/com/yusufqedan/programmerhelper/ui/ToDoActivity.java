package com.yusufqedan.programmerhelper.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.adapters.TodoListAdapter;
import com.yusufqedan.programmerhelper.models.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoActivity extends BaseActivity {
    //private static final String TAG = ToDoActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ArrayList<Task> mToDoList;
    private TodoListAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        ButterKnife.bind(this);
        //only show progress if nothing loads within 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mToDoList == null) {
                    mProgressDialog = new ProgressDialog(ToDoActivity.this);
                    mProgressDialog.setTitle("Loading...");
                    mProgressDialog.setMessage("Fetching Data...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                }
            }
        }, 2000);

        getTasks();
    }

    private void getTasks() {
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
                mAdapter = new TodoListAdapter(mToDoList);
                mRecyclerView.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
                if (mProgressDialog != null) mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        if (mToDoList != null) { //ensure we have read database at least once
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid).child("tasks");
            ref.setValue(mToDoList);
        }
        super.onPause();
    }
}
