package com.rahulcompany.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppDTO implements Serializable {
    @SerializedName("appname")
    private String appname;
    @SerializedName("appurl")
    private String appurl;
    @SerializedName("version")
    private int version;
    @SerializedName("iconurl")
    private String iconurl;
    @SerializedName("uid")
    private String uid;


    public String getAppname() {
        return appname;
    }

    public String getAppurl() {
        return appurl;
    }

    public int getVersion() {
        return version;
    }

    public String getIconurl() {
        return iconurl;
    }

    public String getUid() {
        return uid;
    }
}
