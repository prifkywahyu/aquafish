package com.mobile.aquafish.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.aquafish.FragmentMain;
import com.mobile.aquafish.R;
import com.mobile.aquafish.ReportTemp;
import com.mobile.aquafish.model.SensorModel;
import com.mobile.aquafish.rest.ApiClient;
import com.mobile.aquafish.rest.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    TextView date, timeText, valueTemp, statusTemp, valueTurbid, statusTurbid, valueWlc, statusWlc;
    Button reportTemp;
    int FOR_REFRESH = 10000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forView = inflater.inflate(R.layout.home_fragment, container, false);

        setHasOptionsMenu(true);
        reportTemp = forView.findViewById(R.id.actionForTemp);
        reportTemp.setOnClickListener(this);
        date = forView.findViewById(R.id.dateText);
        timeText = forView.findViewById(R.id.timeText);
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
                refresh.postDelayed(this, FOR_REFRESH);
            }
        }; refresh.postDelayed(runnable, FOR_REFRESH);

        return forView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_reload, menu);
        for (int in = 0; in < menu.size(); in++) {
            Drawable draw = menu.getItem(in).getIcon();
            if (draw != null) {
                draw.mutate();
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getValueStatus() {
        String ID_TEMP = "101";
        String ID_TURBID = "202";
        String ID_WLC = "303";

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<SensorModel> temp = apiService.getTwoData(ID_TEMP);
        temp.enqueue(new Callback<SensorModel>() {
            @Override
            public void onResponse(@NotNull Call<SensorModel> call, @NotNull Response<SensorModel> response) {
                if (response.body() != null) {
                    String setValueTemp = response.body().getValueSensor();
                    String setStatusTemp = response.body().getStatusSensor();
                    Log.d(TAG, "Temp successfully received data");

                    valueTemp.setText(setValueTemp);
                    statusTemp.setText(setStatusTemp);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

        Call<SensorModel> turbid = apiService.getTwoData(ID_TURBID);
        turbid.enqueue(new Callback<SensorModel>() {
            @Override
            public void onResponse(@NotNull Call<SensorModel> call, @NotNull Response<SensorModel> response) {
                if (response.body() != null) {
                    String setValueTurbid = response.body().getValueSensor();
                    String setStatusTurbid = response.body().getStatusSensor();
                    Log.d(TAG, "Turbid successfully received data");

                    valueTurbid.setText(setValueTurbid);
                    statusTurbid.setText(setStatusTurbid);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

        Call<SensorModel> wlc = apiService.getTwoData(ID_WLC);
        wlc.enqueue(new Callback<SensorModel>() {
            @Override
            public void onResponse(@NotNull Call<SensorModel> call, @NotNull Response<SensorModel> response) {
                if (response.body() != null) {
                    String setValueWlc = response.body().getValueSensor();
                    String setStatusWlc = response.body().getStatusSensor();
                    Log.d(TAG, "WLC successfully received data");

                    valueWlc.setText(setValueWlc);
                    statusWlc.setText(setStatusWlc);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel> call, @NotNull Throwable t) {
                Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionForTemp:
                Intent getTemp = new Intent(getContext(), ReportTemp.class);
                getTemp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(getTemp);
        }
    }
}
