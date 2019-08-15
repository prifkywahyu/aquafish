package com.mobile.aquafish.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("aqua_code")
    private String code;

    public UserModel(String name, String email, String code) {
        this.name = name;
        this.email = email;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
