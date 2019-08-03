package com.mobile.aquafish.rest;

import com.mobile.aquafish.model.FeederModel;
import com.mobile.aquafish.model.SensorModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("sensor_read.php")
    Call<SensorModel> getTwoData(@Query("type") String type);

    @GET("sensor_report.php")
    Call<SensorModel.Report> getSensorData(@Query("type") String type);

    @POST("feeder_add.php")
    Call<FeederModel> postSchedule(@Query("start_hour") String startOne, @Query("start_min") String startTwo,
                                   @Query("end_hour") String endOne, @Query("end_min") String endTwo,
                                   @Query("delay") String postDelay);

    @GET("feeder_read.php")
    Call<FeederModel> getFeederData();
}
