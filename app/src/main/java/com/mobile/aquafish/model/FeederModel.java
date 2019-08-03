package com.mobile.aquafish.model;

import com.google.gson.annotations.SerializedName;

public class FeederModel {

    @SerializedName("start_hour")
    private String startHour;
    @SerializedName("start_min")
    private String startMin;
    @SerializedName("end_hour")
    private String endHour;
    @SerializedName("end_min")
    private String endMin;
    @SerializedName("delay")
    private String delay;

    public FeederModel(String startHour, String startMin, String endHour, String endMin, String delay) {
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.delay = delay;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMin() {
        return startMin;
    }

    public void setStartMin(String startMin) {
        this.startMin = startMin;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMin() {
        return endMin;
    }

    public void setEndMin(String endMin) {
        this.endMin = endMin;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
