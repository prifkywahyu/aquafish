package com.mobile.aquafish.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.R;
import com.mobile.aquafish.ReportTemperature;
import com.mobile.aquafish.ReportTurbidity;
import com.mobile.aquafish.ReportWaterLevel;
import com.mobile.aquafish.SharedPreferences;
import com.mobile.aquafish.model.SensorModel;
import com.mobile.aquafish.rest.Client;
import com.mobile.aquafish.rest.Interface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String tagged = HomeFragment.class.getSimpleName();
    private TextView valueTemp;
    private TextView statusTemp;
    private TextView valueTurbid;
    private TextView statusTurbid;
    private TextView valueWlc;
    private TextView statusWlc;
    private SharedPreferences main;
    private int timeRefresh = 12000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.fragment_home, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        main = new SharedPreferences(Objects.requireNonNull(activity));
        Button reportWlc = forView.findViewById(R.id.actionForWlc);
        reportWlc.setOnClickListener(this);
        Button reportTemp = forView.findViewById(R.id.actionForTemp);
        reportTemp.setOnClickListener(this);
        Button reportTurbid = forView.findViewById(R.id.actionForTurbid);
        reportTurbid.setOnClickListener(this);
        TextView date = forView.findViewById(R.id.dateText);
        TextView times = forView.findViewById(R.id.timeText);
        valueTemp = forView.findViewById(R.id.valueForTemp);
        statusTemp = forView.findViewById(R.id.statusForTemp);
        valueTurbid = forView.findViewById(R.id.valueForTurbid);
        statusTurbid = forView.findViewById(R.id.statusForTurbid);
        valueWlc = forView.findViewById(R.id.valueForWlc);
        statusWlc = forView.findViewById(R.id.statusForWlc);
        ((FragmentMain) Objects.requireNonNull(getActivity())).setTitleActionBar("Aqua Home");
        getValueStatus();

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);

        final Handler refresh = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getValueStatus();
                refresh.postDelayed(this, timeRefresh);
            }
        }; refresh.postDelayed(runnable, timeRefresh);

        return forView;
    }

    private void getValueStatus() {
        String resultCode = Objects.requireNonNull(main.getAquaCode());
        String idTemperature = "101";
        String idTurbidity = "202";
        String idWaterLevel = "303";

        Interface apiService = Client.getClient().create(Interface.class);

        Call<SensorModel> temp = apiService.getTwoData(resultCode, idTemperature);
        temp.enqueue(new Callback<SensorModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<SensorModel> call, @NotNull Response<SensorModel> response) {
                if(response.body() != null) {
                    String setValueTemp = response.body().getValueSensor();
                    String setStatusTemp = response.body().getStatusSensor();
                    Log.d(tagged, "Temp successfully received data");

                    valueTemp.setText(setValueTemp);
                    statusTemp.setText(setStatusTemp);
                } else {
                    valueTemp.setText("Null");
                    statusTemp.setText("Not Found");
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(tagged, t.toString());
            }
        });

        Call<SensorModel> turbid = apiService.getTwoData(resultCode, idTurbidity);
        turbid.enqueue(new Callback<SensorModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<SensorModel> call, @NotNull Response<SensorModel> response) {
                if(response.body() != null) {
                    String setValueTurbid = response.body().getValueSensor();
                    String setStatusTurbid = response.body().getStatusSensor();
                    Log.d(tagged, "Turbid successfully received data");

                    valueTurbid.setText(setValueTurbid);
                    statusTurbid.setText(setStatusTurbid);
                } else {
                    valueTurbid.setText("Null");
                    statusTurbid.setText("Not Found");
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(tagged, t.toString());
            }
        });

        Call<SensorModel> wlc = apiService.getTwoData(resultCode, idWaterLevel);
        wlc.enqueue(new Callback<SensorModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<SensorModel> call, @NotNull Response<SensorModel> response) {
                if(response.body() != null) {
                    String setValueWlc = response.body().getValueSensor();
                    String setStatusWlc = response.body().getStatusSensor();
                    Log.d(tagged, "WLC successfully received data");

                    valueWlc.setText(setValueWlc);
                    statusWlc.setText(setStatusWlc);
                } else {
                    valueWlc.setText("Null");
                    statusWlc.setText("Not Found");
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(tagged, t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.actionForTemp:
                Intent getTemp = new Intent(getContext(), ReportTemperature.class);
                getTemp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(getTemp);
                break;
            case R.id.actionForTurbid:
                Intent getTurbid = new Intent(getContext(), ReportTurbidity.class);
                getTurbid.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(getTurbid);
                break;
            case R.id.actionForWlc:
                Intent getWlc = new Intent(getContext(), ReportWaterLevel.class);
                getWlc.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(getWlc);
                break;
        }
    }
}