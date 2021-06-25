package com.rahulcompany.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit client=null;
    public static Retrofit getClient(){
        if (client != null) {
            return client;
        }
        client=new Retrofit.Builder()
                .baseUrl("https://appupdater1234.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return client;
    }
}
