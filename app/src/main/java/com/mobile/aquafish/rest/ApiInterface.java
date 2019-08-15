package com.mobile.aquafish.rest;

import com.mobile.aquafish.model.FeederModel;
import com.mobile.aquafish.model.SensorModel;
import com.mobile.aquafish.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("sensor_read.php")
    Call<SensorModel> getTwoData(@Query("type") String type);

    @GET("sensor_report.php")
    Call<SensorModel.Report> getSensorData(@Query("type") String type);

    @POST("user_setup.php")
    Call<UserModel> postUserData(@Query("name") String name, @Query("email") String email, @Query("aqua_code")
            String aqua_code);

    @POST("feeder_add.php")
    Call<FeederModel> postSchedule(@Query("start_hour") String startHour, @Query("start_min") String startMint,
                                   @Query("end_hour") String endHour, @Query("end_min") String endMint,
                                   @Query("delay") String delay);

    @GET("feeder_read.php")
    Call<FeederModel> getFeederData();
}
