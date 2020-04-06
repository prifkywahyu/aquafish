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

public class Turbidity extends RecyclerView.Adapter<Turbidity.TurbidViewHolder>{

    private ArrayList<SensorModel> turbid;
    private int rowLayout;
    private Context context;

    static class TurbidViewHolder extends RecyclerView.ViewHolder {
        TextView turbidSensorDate;
        TextView turbidValue;
        TextView turbidStatus;

        TurbidViewHolder(View v) {
            super(v);
            turbidSensorDate = v.findViewById(R.id.dateCreatedTurbid);
            turbidValue = v.findViewById(R.id.textValueTurbid);
            turbidStatus = v.findViewById(R.id.textStatusTurbid);
        }
    }

    public Turbidity(ArrayList<SensorModel> turbid, int rowLayout, Context context) {
        this.turbid = turbid;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NotNull
    @Override
    public Turbidity.TurbidViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new Turbidity.TurbidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull Turbidity.TurbidViewHolder holder, final int position) {
        holder.turbidSensorDate.setText(turbid.get(position).getCreatedSensor());
        holder.turbidValue.setText(turbid.get(position).getValueSensor());
        holder.turbidStatus.setText(turbid.get(position).getStatusSensor());
    }

    @Override
    public int getItemCount() {
        return turbid.size();
    }
}