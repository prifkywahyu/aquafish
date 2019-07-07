package com.mobile.aquafish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mobile.aquafish.adapter.AdapterWlc;
import com.mobile.aquafish.model.SensorModel;
import com.mobile.aquafish.rest.ApiClient;
import com.mobile.aquafish.rest.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportWlc extends AppCompatActivity {

    private final static String TYPE_WLC = "303";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(bar).setTitle("Water Level Report");

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<SensorModel.Report> listCall = service.getSensorData(TYPE_WLC);
        listCall.enqueue(new Callback<SensorModel.Report>() {
            @Override
            public void onResponse(@NotNull Call<SensorModel.Report> call, @NotNull Response<SensorModel.Report> response) {
                SensorModel.Report report = response.body();

                ArrayList<SensorModel> sensorModels = Objects.requireNonNull(report).records;
                AdapterWlc adapterWlc = new AdapterWlc(sensorModels, R.layout.report_wlc, getApplicationContext());
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                recyclerView.setAdapter(adapterWlc);
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel.Report> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.toString());
            }
        });
    }
}
