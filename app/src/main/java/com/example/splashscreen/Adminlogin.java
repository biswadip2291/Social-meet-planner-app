package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Adminlogin extends AppCompatActivity {

    private Button button5;
    EditText Usernameadmin,inputpasswardadmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        getSupportActionBar().hide();

        Usernameadmin = findViewById(R.id.Usernameadmin);
        inputpasswardadmin = findViewById(R.id.inputpasswardadmin);

        //button admin login
        button5=(Button)findViewById(R.id.btnabminlogin);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password,username;

                password = inputpasswardadmin.getText().toString();
                username =  Usernameadmin.getText().toString();


            }
        });

    }

    public void openMain_page1(){
        Intent intent = new Intent(this, MainPage1.class);
        startActivity(intent);
    }


}