package com.rahulcompany.api;

import com.google.gson.annotations.SerializedName;

public class ResultDTO {
    @SerializedName("status")
    private int status;

    public int getStatus() {
        return status;
    }
}
