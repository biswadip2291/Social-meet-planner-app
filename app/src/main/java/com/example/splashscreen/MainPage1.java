package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

public class MainPage1 extends AppCompatActivity {

    //private Button addmeet;
    //private Button logout;
    //private Button history;
    //private Button mailbox;

    TextView txtchat;
    TextView txtlogout;
    TextView txtmailbox;
    TextView txthistory;
    TextView txtplanemeet;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page1);
        getSupportActionBar().hide();

        //addmeet=(Button)findViewById(R.id.btnaddmeet);
        //history = (Button)findViewById(R.id.btnhistory);
        //logout = (Button)findViewById(R.id.btnlogout);
        //mailbox = (Button)findViewById(R.id.btnmailbox);
        txtchat=findViewById(R.id.btnchat);
        txtlogout  =findViewById(R.id.btnlogout);
        txtmailbox  =findViewById(R.id.btnmailbox);
        txthistory =findViewById(R.id.btnhistory);
        txtplanemeet=findViewById(R.id.btnaddmeet);

        txtplanemeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openaddmeeting();
            }
        });

        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openloginpage();
            }
        });

        txthistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmeethistory();
            }
        });

//        txtmailbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openmailbox();
//            }
//        });

        txtchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openchatbox();
            }
        });

    }

    public void openaddmeeting(){
        Intent intent = new Intent(this, addmeeting.class);
        startActivity(intent);
    }
    public void openloginpage(){
        String UUID = OneSignal.getDeviceState().getUserId();
        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/update2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainPage1.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> getparams = new HashMap<String, String>();

                getparams.put("token", UUID);

                return getparams;
            }
        };
        RequestQueue r = Volley.newRequestQueue(MainPage1.this);
        r.add(request);
        Intent intent = new Intent(this, loginpage.class);
        startActivity(intent);
    }

    public void openmeethistory(){
        Intent intent = new Intent(this, meethistory.class);
        startActivity(intent);
    }

//    public void openmailbox(){
//        Intent intent = new Intent(this, mailbox.class);
//        startActivity(intent);
//    }

    public void openchatbox(){
        Intent intent = new Intent(this, chatwithmember.class);
        startActivity(intent);
    }

}