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
import java.util.ArrayList;


class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList medicationID, medicationName, medicationDosage, medicationTime, medicationDate;
    String currentDate;
    private Activity activity;

    public CustomAdapter(Activity activity, Context context, ArrayList medicationID, ArrayList medName, ArrayList medDosage, ArrayList medTime, ArrayList medDate) {
        this.activity = activity;
        this.context = context;
        this.medicationID = medicationID;
        this.medicationName = medName;
        this.medicationDosage = medDosage;
        this.medicationTime = medTime;
        this.medicationDate = medDate;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.medName.setText(String.valueOf(medicationName.get(position)));
        holder.medDosage.setText("Dosage: " +String.valueOf(medicationDosage.get(position)));
        holder.medTime.setText(String.valueOf(medicationTime.get(position)));
        holder.medicationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNewMedicaiton(position);
            }
        });

    }

    public void updateNewMedicaiton(int position){
        Intent updateMedication = new Intent(context, UpdateMedication.class);
        updateMedication.putExtra("medicationID", String.valueOf(medicationID.get(position)));
        updateMedication.putExtra("medicationName",String.valueOf(medicationName.get(position)));
        updateMedication.putExtra("medicationDosage",String.valueOf(medicationDosage.get(position)));
        updateMedication.putExtra("medicationDate",String.valueOf(medicationDate.get(position)));
        updateMedication.putExtra("medicationTime",String.valueOf(medicationTime.get(position)));
        activity.startActivityForResult(updateMedication, 1);
    }
    @Override
    public int getItemCount() {
        return medicationName.size();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medicationlayout, parent, false);
        return new MyViewHolder(view);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView medName, medDosage, medTime;
        CardView card;
        LinearLayout medicationLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView);
            medName = itemView.findViewById(R.id.medName);
            medDosage = itemView.findViewById(R.id.medDosage);
            medTime = itemView.findViewById(R.id.medTime);
            medicationLayout = itemView.findViewById(R.id.medicationLayout);
        }
    }
}