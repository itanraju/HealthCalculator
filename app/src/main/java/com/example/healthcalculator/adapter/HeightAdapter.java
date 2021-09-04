package com.example.healthcalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcalculator.R;
import com.example.healthcalculator.activity.BloodVolumeActivity;
import com.example.healthcalculator.activity.BodyFatActivity;
import com.example.healthcalculator.activity.BodyMassIndexActivity;
import com.example.healthcalculator.activity.IdealWeightActivity;
import com.example.healthcalculator.activity.ProfileActivity;

import java.util.List;

public class HeightAdapter extends RecyclerView.Adapter<HeightAdapter.ViewHolder> {
    List<Integer> heightNumber;
    Context context;
    int selectedHeight;

    public HeightAdapter(List<Integer> heightNumber, Context context, int selectedHeight) {
        this.heightNumber = heightNumber;
        this.context = context;
        this.selectedHeight = selectedHeight;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.height_layout, parent, false);
        return new HeightAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (heightNumber.get(position) % 5 == 0) {
            ViewGroup.LayoutParams params = holder.v1.getLayoutParams();
            params.height = 100;
            holder.v1.setLayoutParams(params);
            holder.meter.setText(String.valueOf(heightNumber.get(position)));
        }

        if(selectedHeight==heightNumber.get(position))
        {
            holder.v1.setBackgroundResource(R.color.toolbar);
        }
        else
        {
            holder.v1.setBackgroundResource(R.color.lowgrey);
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHeight=heightNumber.get(position);
                holder.v1.setBackgroundResource(R.color.toolbar);
                ProfileActivity.selectedHeight=heightNumber.get(position);
                IdealWeightActivity.selectedHeight = heightNumber.get(position);
                BodyMassIndexActivity.selectedHeight = heightNumber.get(position);
                BodyFatActivity.selectedHeight = heightNumber.get(position);
                BloodVolumeActivity.selectedHeight = heightNumber.get(position);
                ((RecyclerView.Adapter) HeightAdapter.this).notifyItemChanged(position);
                ((RecyclerView.Adapter) HeightAdapter.this).notifyItemChanged(selectedHeight);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return heightNumber.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView meter;
        LinearLayout mainLayout;
        RelativeLayout r1;
        View v1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meter = itemView.findViewById(R.id.meter);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            v1 = itemView.findViewById(R.id.v1);
            r1 = itemView.findViewById(R.id.r1);
        }
    }
}
