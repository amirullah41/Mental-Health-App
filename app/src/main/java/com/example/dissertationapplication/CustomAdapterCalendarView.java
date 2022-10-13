package com.example.dissertationapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CustomAdapterCalendarView extends RecyclerView.Adapter<CustomAdapterCalendarView.MyViewHolder>{

    private Context context;
    private ArrayList taskID, taskTitle, taskDesc, taskDate, taskTime;
    String currentDate;
    private Activity activity;

    public CustomAdapterCalendarView(Activity activity, Context context, ArrayList taskID, ArrayList taskTitle, ArrayList taskDesc, ArrayList taskDate, ArrayList taskTime) {
        this.activity = activity;
        this.context = context;
        this.taskID = taskID;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapterCalendarView.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.taskTitleText.setText(String.valueOf(taskTitle.get(position)));
        holder.taskDescText.setText(String.valueOf(taskDesc.get(position)));
        holder.taskTimeText.setText(String.valueOf(taskTime.get(position)));
        holder.taskDateText.setText(String.valueOf(taskDate.get(position)));
        holder.calendarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentTask(position);
            }
        });
    }

    public void updateCurrentTask(int position){
        Intent updateTask = new Intent(context, UpdateCalendarTask.class);
        updateTask.putExtra("taskID", String.valueOf(taskID.get(position)));
        updateTask.putExtra("taskName",String.valueOf(taskTitle.get(position)));
        updateTask.putExtra("taskDesc",String.valueOf(taskDesc.get(position)));
        updateTask.putExtra("taskDate",String.valueOf(taskDate.get(position)));
        updateTask.putExtra("taskTime",String.valueOf(taskTime.get(position)));
        activity.startActivityForResult(updateTask, 1);
    }
    @Override
    public int getItemCount() {
        return taskTitle.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendarlayout, parent, false);
        return new CustomAdapterCalendarView.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitleText, taskDescText, taskTimeText, taskDateText;
        CardView card;
        LinearLayout calendarLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView1);
            taskTitleText = itemView.findViewById(R.id.taskName);
            taskDescText = itemView.findViewById(R.id.taskDesc);
            taskTimeText = itemView.findViewById(R.id.taskTime);
            taskDateText = itemView.findViewById(R.id.taskDate);
            calendarLayout = itemView.findViewById(R.id.calendarLayout);
        }
    }
}
