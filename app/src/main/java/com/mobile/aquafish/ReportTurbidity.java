package com.mobile.aquafish;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.aquafish.adapter.Turbidity;
import com.mobile.aquafish.model.SensorModel;
import com.mobile.aquafish.rest.Client;
import com.mobile.aquafish.rest.Interface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportTurbidity extends AppCompatActivity {

    private final static String typeTurbidity = "202";
    SharedPreferences prefMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(bar).setTitle("Turbidity Report");
        prefMain = new SharedPreferences(this);
        String getCode = Objects.requireNonNull(prefMain.getAquaCode());

        final TextView textView = findViewById(R.id.sorryFound);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Interface service = Client.getClient().create(Interface.class);
        Call<SensorModel.Report> listCall = service.getSensorData(getCode, typeTurbidity);
        listCall.enqueue(new Callback<SensorModel.Report>() {
            @Override
            public void onResponse(@NotNull Call<SensorModel.Report> call, @NotNull Response<SensorModel.Report> response) {
                if(response.body() != null) {
                    SensorModel.Report report = response.body();

                    ArrayList<SensorModel> sensorModels = Objects.requireNonNull(report).records;
                    Turbidity turbidity = new Turbidity(sensorModels, R.layout.report_turbidity, getApplicationContext());
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerView.setAdapter(turbidity);
                    textView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textView.setText(R.string.notFound);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SensorModel.Report> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.toString());
            }
        });
    }
}