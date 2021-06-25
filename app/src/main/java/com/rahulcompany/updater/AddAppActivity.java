package com.rahulcompany.updater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rahulcompany.api.ApiClient;
import com.rahulcompany.api.AppDTO;
import com.rahulcompany.api.ResultDTO;
import com.rahulcompany.api.UpdaterAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddAppActivity extends AppCompatActivity {

    private EditText name,version,url,icon;

    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app);
        name = findViewById(R.id.newappname);
        version = findViewById(R.id.newappversion);
        url = findViewById(R.id.newappurl);
        icon = findViewById(R.id.newappicon);


        pd=new ProgressDialog(this);
        pd.setMessage("Adding Data ...");
        pd.setCancelable(false);



        Intent i = getIntent();
        AppDTO app = (AppDTO) i.getSerializableExtra("app");
        if (app != null) {

            name.setText(app.getAppname());
            version.setText(app.getVersion() + "");
            url.setText(app.getAppurl());
            icon.setText(app.getIconurl());

        }

    }

    public void addnewapp(View view) {
        pd.show();
        Retrofit retrofit= ApiClient.getClient();
        UpdaterAPI api = retrofit.create(UpdaterAPI.class);
        api.postuserapp(FirebaseAuth.getInstance().getCurrentUser().getUid(), name.getText().toString(), url.getText().toString(), Integer.parseInt(version.getText().toString()), icon.getText().toString()).enqueue(new Callback<ResultDTO>() {
            @Override
            public void onResponse(Call<ResultDTO> call, Response<ResultDTO> response) {
                pd.cancel();
                ResultDTO result = response.body();
                if (result.getStatus() == 300) {
                    Toast.makeText(AddAppActivity.this,"Success...",Toast.LENGTH_SHORT).show();
                    AddAppActivity.super.onBackPressed();
                }
                else {
                    Toast.makeText(AddAppActivity.this,"Some Error Occured...",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResultDTO> call, Throwable t) {
                pd.cancel();
                Toast.makeText(AddAppActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}