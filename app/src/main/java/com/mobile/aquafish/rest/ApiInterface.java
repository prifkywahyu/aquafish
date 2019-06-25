package com.mobile.aquafish.rest;

import com.mobile.aquafish.model.SensorModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("gets_sensor.php")
    Call<List<SensorModel>> getSensorData(@Query("type") String type);

    @GET("read_sensor.php")
    Call<SensorModel> getTwoData(@Query("type") String type);
}
