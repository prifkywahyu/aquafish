package com.mobile.aquafish.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SensorModel {

    @SerializedName("aqua_code")
    private String aquaCode;
    @SerializedName("type")
    private String typeSensor;
    @SerializedName("value")
    private String valueSensor;
    @SerializedName("status")
    private String statusSensor;
    @SerializedName("created")
    private String createdSensor;

    public SensorModel(String aquaCode, String typeSensor, String valueSensor, String statusSensor, String createdSensor) {
        this.aquaCode = aquaCode;
        this.typeSensor = typeSensor;
        this.valueSensor = valueSensor;
        this.statusSensor = statusSensor;
        this.createdSensor = createdSensor;
    }

    public String getTypeSensor() {
        return typeSensor;
    }

    public void setTypeSensor(String typeSensor) {
        this.typeSensor = typeSensor;
    }

    public String getValueSensor() {
        return valueSensor;
    }

    public void setValueSensor(String valueSensor) {
        this.valueSensor = valueSensor;
    }

    public String getStatusSensor() {
        return statusSensor;
    }

    public void setStatusSensor(String statusSensor) {
        this.statusSensor = statusSensor;
    }

    public String getCreatedSensor() {
        return createdSensor;
    }

    public void setCreatedSensor(String createdSensor) {
        this.createdSensor = createdSensor;
    }

    public String getAquaCode() {
        return aquaCode;
    }

    public void setAquaCode(String aquaCode) {
        this.aquaCode = aquaCode;
    }

    public static class Report {

        @SerializedName("records")
        public ArrayList<SensorModel> records;

        public Report() {
            records = new ArrayList<>();
        }
    }
}
