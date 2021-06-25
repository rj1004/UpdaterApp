package com.rahulcompany.updater;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.rahulcompany.api.ApiClient;
import com.rahulcompany.api.AppDTO;
import com.rahulcompany.api.ResultDTO;
import com.rahulcompany.api.UpdaterAPI;
import com.rahulcompany.api.UsersDTO;


import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rv;
    private TextView noapp;

    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        noapp = findViewById(R.id.noapp);

        pd=new ProgressDialog(this);
        pd.setMessage("Fetching Data");
        pd.setCancelable(false);




    }

    private void logout(){
        Toast.makeText(HomeActivity.this,"Logging Out",Toast.LENGTH_SHORT).show();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout :
                logout();
                break;

            case R.id.help:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/rj1004/UpdaterLibrarry"));
                startActivity(Intent.createChooser(i, "Open With"));
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        pd.show();


        Retrofit retrofit=ApiClient.getClient();
        UpdaterAPI api = retrofit.create(UpdaterAPI.class);
        api.getUserapps(FirebaseAuth.getInstance().getCurrentUser().getUid()).enqueue(new Callback<UsersDTO>() {
            @Override
            public void onResponse(Call<UsersDTO> call, Response<UsersDTO> response) {
                UsersDTO userapps=response.body();
                assert userapps!=null;

                if (userapps.getStatus() == 100) {
                    noapp.setVisibility(View.VISIBLE);
                    rv.setAdapter(new AppListAdapter(new ArrayList<>()));
                    pd.dismiss();
                }
                else{
                    noapp.setVisibility(View.GONE);
                    ArrayList<AppDTO> list = userapps.getData();
                    rv.setAdapter(new AppListAdapter(list));
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UsersDTO> call, Throwable t) {
                Toast.makeText(HomeActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                call.cancel();
                pd.dismiss();
            }
        });
    }

    public void addapp(View view) {
        Intent i = new Intent(HomeActivity.this, AddAppActivity.class);
        startActivity(i);
    }
}
