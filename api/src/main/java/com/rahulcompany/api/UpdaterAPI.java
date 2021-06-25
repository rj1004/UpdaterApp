package com.rahulcompany.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UpdaterAPI {
    @GET("/{user}")
    Call<UsersDTO> getUserapps(@Path("user") String uid);
    @FormUrlEncoded
    @POST("/{user}")
    Call<ResultDTO> postuserapp(@Path("user") String uid, @Field("appname") String appname,@Field("appurl") String appurl,@Field("version") int version,@Field("iconurl") String iconurl);
    @DELETE("/{user}/{appname}")
    Call<ResultDTO> deleteapp(@Path("user") String uid,@Path("appname")String appname);
    @GET("/app/{appname}")
    Call<UsersDTO> getappdetail(@Path("appname")String appname);
}
