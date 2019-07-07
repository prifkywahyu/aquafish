package com.mobile.aquafish.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.aquafish.R;
import com.mobile.aquafish.model.SensorModel;

import java.util.ArrayList;

public class AdapterWlc extends RecyclerView.Adapter<AdapterWlc.WlcViewHolder> {

    private ArrayList<SensorModel> wlc;
    private int rowLayout;
    private Context context;

    static class WlcViewHolder extends RecyclerView.ViewHolder {
        TextView wlcSensorDate;
        TextView wlcValue;
        TextView wlcStatus;

        WlcViewHolder(View v) {
            super(v);
            wlcSensorDate = v.findViewById(R.id.dateCreatedWlc);
            wlcValue = v.findViewById(R.id.textValueWlc);
            wlcStatus = v.findViewById(R.id.textStatusWlc);
        }
    }

    public AdapterWlc(ArrayList<SensorModel> wlc, int rowLayout, Context context) {
        this.wlc = wlc;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterWlc.WlcViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new AdapterWlc.WlcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWlc.WlcViewHolder wlcViewHolder, final int ignite) {
        wlcViewHolder.wlcSensorDate.setText(wlc.get(ignite).getCreatedSensor());
        wlcViewHolder.wlcValue.setText(wlc.get(ignite).getValueSensor());
        wlcViewHolder.wlcStatus.setText(wlc.get(ignite).getStatusSensor());
    }

    @Override
    public int getItemCount() {
        return wlc.size();
    }
}
