package com.example.healthcalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcalculator.R;
import com.example.healthcalculator.activity.BloodPressureActivity;
import com.example.healthcalculator.activity.BodyFatActivity;
import com.example.healthcalculator.activity.BodyMassIndexActivity;
import com.example.healthcalculator.activity.IdealWeightActivity;
import com.example.healthcalculator.activity.ProfileActivity;
import com.example.healthcalculator.activity.TargetHeartRateActivity;
import com.example.healthcalculator.activity.WaterIntakeActivity;

import java.util.List;

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.ViewHolder> {
    List<Integer> ageNumber;
    Context context;
    int selectedItem;

    public AgeAdapter(List<Integer> ageNumber, Context context, int selectedItem) {
        this.ageNumber = ageNumber;
        this.context = context;
        this.selectedItem = selectedItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {   TextView age;
        FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            age=itemView.findViewById(R.id.age);
            frameLayout=itemView.findViewById(R.id.frameLayout);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.age_layout, parent, false);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if(selectedItem==ageNumber.get(position))
        {
            holder.age.setText(String.valueOf(ageNumber.get(position)));
            holder.age.setTextColor(Color.parseColor("#170a59"));
            holder.frameLayout.setBackgroundResource(R.drawable.border_layout);
        }
        else
        {
            holder.age.setText(String.valueOf(ageNumber.get(position)));
            holder.frameLayout.setBackgroundResource(R.drawable.white_border_layout);
        }

        holder.age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem=ageNumber.get(position);
                ProfileActivity.selectedAge=ageNumber.get(position);
                IdealWeightActivity.selectedAge=ageNumber.get(position);
                BodyMassIndexActivity.selectedAge=ageNumber.get(position);
                BodyFatActivity.selectedAge=ageNumber.get(position);
                WaterIntakeActivity.selectedAge=ageNumber.get(position);
                TargetHeartRateActivity.selectedAge=ageNumber.get(position);
                BloodPressureActivity.selectedSystolic=ageNumber.get(position);
                ((RecyclerView.Adapter)AgeAdapter.this).notifyItemChanged(position);
                ((RecyclerView.Adapter)AgeAdapter.this).notifyItemChanged(selectedItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ageNumber.size();
    }
}
