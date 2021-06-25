package com.rahulcompany.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersDTO {
    @SerializedName("status")
    @Expose
    private int status;
    @Expose
    @SerializedName("data")
    private ArrayList<AppDTO> data;

    public int getStatus() {
        return status;
    }

    public ArrayList<AppDTO> getData() {
        return data;
    }
}
