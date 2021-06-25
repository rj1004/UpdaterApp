package com.rahulcompany.updaterlibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rahulcompany.api.ApiClient;
import com.rahulcompany.api.BuildConfig;
import com.rahulcompany.api.UpdaterAPI;
import com.rahulcompany.api.UsersDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Updater {

    public static void init(String appname,Context ctx,Activity activity){
        
        Retrofit retrofit= ApiClient.getClient();
        UpdaterAPI api = retrofit.create(UpdaterAPI.class);
        api.getappdetail(appname).enqueue(new Callback<UsersDTO>() {
            @Override
            public void onResponse(Call<UsersDTO> call, Response<UsersDTO> response) {
                UsersDTO app = response.body();
                if (app.getStatus() == 200) {
                    int latest_version = app.getData().get(0).getVersion();
                    String latest_appurl = app.getData().get(0).getAppurl();

                    int current_version = 0;
                    try {
                        current_version = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES).versionCode;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (current_version != 0 && current_version < latest_version) {
                        show_Update_dialog(ctx,activity,latest_appurl);
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersDTO> call, Throwable t) {

            }
        });
    }

    private static void show_Update_dialog(Context ctx,Activity activity,String url) {

        AlertDialog ad=new AlertDialog.Builder(activity)
                .setMessage("Update Available ...")
                .setPositiveButton("Install", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        install_app(ctx,activity,url);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0); 
                    }
                }).create();

        ad.show();
    }

    public static void install_app(Context context, Activity activity,String url) {
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
        else {
            new downloadtask(url,activity).execute();
        }

    }

    public static void showtoast(){
        Log.d("updaterlibrary", "dsfgsdfdsdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
    }


}
