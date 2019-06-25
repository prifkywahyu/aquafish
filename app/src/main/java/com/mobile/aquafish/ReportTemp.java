package com.mobile.aquafish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mobile.aquafish.model.SensorModel;
import com.mobile.aquafish.rest.ApiClient;
import com.mobile.aquafish.rest.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportTemp extends AppCompatActivity {

    private final static String TYPE_TEMP = "101";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_temp);

        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(bar).setTitle("Temperature Report");

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SensorModel>> listCall = service.getSensorData(TYPE_TEMP);
        listCall.enqueue(new Callback<List<SensorModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<SensorModel>> call, @NotNull Response<List<SensorModel>> response) {
                List<SensorModel> models = response.body();

                for (SensorModel sensorModel: Objects.requireNonNull(models)) {
                    Log.d("type", sensorModel.getTypeSensor());
                    Log.d("value", sensorModel.getValueSensor());
                    Log.d("status", sensorModel.getStatusSensor());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<SensorModel>> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
