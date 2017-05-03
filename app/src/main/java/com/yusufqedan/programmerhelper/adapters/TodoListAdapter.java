package com.yusufqedan.programmerhelper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yusufqedan.programmerhelper.R;
import com.yusufqedan.programmerhelper.models.Task;
import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ToDoListViewHolder>{
    private ArrayList<Task> mToDoList = new ArrayList<>();

    public TodoListAdapter(ArrayList<Task> toDoList) {
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
        @Bind(R.id.checkBoxImageView) ImageView mCheckBoxImageView;

        public ToDoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bindTask(Task task){
            mCheckedTextView.setText(task.getTitle());
            mCheckedTextView.setChecked(task.isComplete());
            if (task.isComplete()) {
                mCheckBoxImageView.setImageResource(R.drawable.ic_check_box_black_24dp);
            }else{
                mCheckBoxImageView.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
            }
        }

        @Override
        public void onClick(View v) {
            if (v == itemView){
                int itemPosition = getLayoutPosition();
                mCheckedTextView.toggle();
                if (mCheckedTextView.isChecked()) {
                    mCheckBoxImageView.setImageResource(R.drawable.ic_check_box_black_24dp);
                    mToDoList.get(itemPosition).setComplete();
                }else{
                    mCheckBoxImageView.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    mToDoList.get(itemPosition).setIncomplete();
                }
            }
        }
    }
}
