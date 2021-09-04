package com.example.healthcalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcalculator.R;
import com.example.healthcalculator.activity.BloodPressureActivity;

import java.util.List;

public class SystolicAdapter extends RecyclerView.Adapter<SystolicAdapter.ViewHolder> {
    List<Integer> systolicNumber;
    Context context;
    int selectedItem=121;
    int id;

    public SystolicAdapter(List<Integer> systolicNumber, Context context, int id) {
        this.systolicNumber = systolicNumber;
        this.context = context;
        this.id = id;
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
        if(selectedItem==systolicNumber.get(position))
        {
            holder.age.setText(String.valueOf(systolicNumber.get(position)));
            holder.age.setTextColor(Color.parseColor("#170a59"));
            holder.frameLayout.setBackgroundResource(R.drawable.border_layout);
        }
        else
        {
            holder.age.setText(String.valueOf(systolicNumber.get(position)));
            holder.frameLayout.setBackgroundResource(R.drawable.white_border_layout);
        }

        holder.age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem=systolicNumber.get(position);
                if(id==1)
                {
                    BloodPressureActivity.selectedSystolic=systolicNumber.get(position);
                }
                if(id==2)
                {
                    BloodPressureActivity.selectedDiastolic=systolicNumber.get(position);
                }
                ((RecyclerView.Adapter)SystolicAdapter.this).notifyItemChanged(position);
                ((RecyclerView.Adapter)SystolicAdapter.this).notifyItemChanged(selectedItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return systolicNumber.size();
    }
}
