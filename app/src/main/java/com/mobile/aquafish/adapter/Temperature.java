package com.mobile.aquafish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.aquafish.R;
import com.mobile.aquafish.model.SensorModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Temperature extends RecyclerView.Adapter<Temperature.TempViewHolder> {

    private ArrayList<SensorModel> temp;
    private int rowLayout;
    private Context context;

    static class TempViewHolder extends RecyclerView.ViewHolder {
        TextView tempSensorDate;
        TextView tempValue;
        TextView tempStatus;

        TempViewHolder(View v) {
            super(v);
            tempSensorDate = v.findViewById(R.id.dateCreatedTemp);
            tempValue = v.findViewById(R.id.textValueTemp);
            tempStatus = v.findViewById(R.id.textStatusTemp);
        }
    }

    public Temperature(ArrayList<SensorModel> temp, int rowLayout, Context context) {
        this.temp = temp;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NotNull
    @Override
    public Temperature.TempViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new Temperature.TempViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull TempViewHolder holder, final int position) {
        holder.tempSensorDate.setText(temp.get(position).getCreatedSensor());
        holder.tempValue.setText(temp.get(position).getValueSensor());
        holder.tempStatus.setText(temp.get(position).getStatusSensor());
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }
}