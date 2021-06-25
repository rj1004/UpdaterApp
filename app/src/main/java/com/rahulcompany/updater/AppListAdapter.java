package com.rahulcompany.updater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.rahulcompany.api.ApiClient;
import com.rahulcompany.api.AppDTO;
import com.rahulcompany.api.ResultDTO;
import com.rahulcompany.api.UpdaterAPI;
import com.rahulcompany.api.UsersDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.viewmodel> {


    private ArrayList<AppDTO> list;
    private Context ctx;
    private ProgressDialog pd;
    public AppListAdapter(ArrayList<AppDTO> list){
        this.list=list;

    }



    @NonNull
    @Override
    public viewmodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new viewmodel(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewmodel holder, int position) {
        AppDTO app = list.get(position);
        holder.appname.setText(app.getAppname());
        holder.version.setText(app.getVersion()+"");
        holder.appurl.setText(app.getAppurl());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, AddAppActivity.class);
                i.putExtra("app",app);
                ctx.startActivity(i);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(ctx);
                pd.setMessage("Deleteing App...");
                pd.setCancelable(false);
                pd.show();
                Retrofit retrofit= ApiClient.getClient();
                UpdaterAPI api = retrofit.create(UpdaterAPI.class);
                api.deleteapp(FirebaseAuth.getInstance().getCurrentUser().getUid(), app.getAppname()).enqueue(new Callback<ResultDTO>() {
                    @Override
                    public void onResponse(Call<ResultDTO> call, Response<ResultDTO> response) {
                        pd.dismiss();
                        ResultDTO result = response.body();
                        if (result.getStatus() == 500) {
                            Toast.makeText(ctx, "App Deleted", Toast.LENGTH_SHORT).show();
                            ctx.startActivity(new Intent(ctx, HomeActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultDTO> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        Glide.with(ctx).load(app.getIconurl()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewmodel extends RecyclerView.ViewHolder {

        TextView appname,version,appurl;
        ImageView logo;
        Button edit,delete;

        public viewmodel(@NonNull View itemView) {
            super(itemView);
            appname = itemView.findViewById(R.id.appname);
            version = itemView.findViewById(R.id.appversion);
            appurl=itemView.findViewById(R.id.applink);
            logo = itemView.findViewById(R.id.applogo);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
