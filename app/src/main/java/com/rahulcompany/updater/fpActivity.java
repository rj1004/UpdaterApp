package com.rahulcompany.updater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class fpActivity extends AppCompatActivity {


    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp);
        email = findViewById(R.id.fpemail);
    }



    public void resetpassword(View view) {
        Toast.makeText(fpActivity.this, "Resetting...", Toast.LENGTH_SHORT).show();

        String e=email.getText().toString();
        if (!e.equalsIgnoreCase("") && Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(fpActivity.this, "Password Reset Email Sent Successful...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(fpActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else {
            Toast.makeText(fpActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
        }
    }
}