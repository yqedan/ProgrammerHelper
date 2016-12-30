package com.yusuf.programmerhelper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.yusuf.programmerhelper.R;
import com.yusuf.programmerhelper.models.Task;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>{
    private ArrayList<Task> mToDoList = new ArrayList<>();
    private Context mContext;

    public ToDoListAdapter(Context context, ArrayList<Task> toDoList) {
        mContext = context;
        mToDoList = toDoList;
    }

    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        ToDoListViewHolder viewHolder = new ToDoListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoListViewHolder holder, int position) {
        holder.bindTask(mToDoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mToDoList.size();
    }

    public class ToDoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.checkedTextView) CheckedTextView mCheckedTextView;

        private Context mContext;

        public ToDoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            mCheckedTextView.setOnClickListener(this);
        }

        public void bindTask(Task task){
            mCheckedTextView.setText(task.getTitle());
            mCheckedTextView.setChecked(task.isComplete());
        }

        @Override
        public void onClick(View v) {
            if (v == mCheckedTextView){
                int itemPosition = getLayoutPosition();
                mCheckedTextView.toggle();
                if (mCheckedTextView.isChecked()) {
                    mCheckedTextView.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{
                    mCheckedTextView.setCheckMarkDrawable(null);
                }
                //mToDoList.get(itemPosition).setComplete();
            }
        }
    }
}
