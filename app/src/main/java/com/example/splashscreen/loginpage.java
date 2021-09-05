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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginpage extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    EditText Username,inputpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        HttpsTrustManager.allowAllSSL();
        getSupportActionBar().hide();

        Username = findViewById(R.id.Username);
        inputpassword = findViewById(R.id.inputpassword);


        //button1 login button
        button1=(Button) findViewById(R.id.btnLogin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password,username;

                password = inputpassword.getText().toString();
                username = Username.getText().toString();



                 if( !username.equals("") && !password.equals("") ){

                        login(username,password);
                            //End Write and Read data with URL
                        }
                 else {
                     Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                 }
                    }});








        //button2 ragistar new user  openMain_page1();

        button2 = (Button) findViewById(R.id.btnragister);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openNew_Ragista();

            }
        });

        //button3 admin login button
        button3 = (Button) findViewById(R.id.adminsignbtn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openAdminlogin();

            }
        });
    }
    void login(String Username,String Password){
        String UUID = OneSignal.getDeviceState().getUserId();



        // to retrive data

        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/retrive.php", new Response.Listener<String>() {


            @Override

            public void onResponse(String response) {
                try {
                    JSONArray array= new JSONArray(response);
                    Boolean check= false;

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String username=object.getString("username");
                        String password=object.getString("password");






                        if(username.equals(Username) && password.equals(Password))
                        {

                            check=true;
                            Toast.makeText(loginpage.this, "login sucess", Toast.LENGTH_SHORT).show();
                            Log.e("l",Username);

                            //to update data
                            StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/update.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(loginpage.this, error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> getparams = new HashMap<String, String>();
                                    getparams.put("username",Username);
                                    getparams.put("token", UUID);

                                    return getparams;
                                }
                            };
                            RequestQueue r = Volley.newRequestQueue(loginpage.this);
                            r.add(request);
                            openMain_page1();

                        }


                    }
                    if(check==false)
                    {
                        Toast.makeText(loginpage.this, "Username or Password invalid", Toast.LENGTH_SHORT).show();
                    }



                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginpage.this, "mmmm", Toast.LENGTH_SHORT).show();

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rq = Volley.newRequestQueue(loginpage.this);
        rq.add(request);




    }



    public void openMain_page1(){
        Intent intent = new Intent(this, MainPage1.class);
        startActivity(intent);
    }

    public void openNew_Ragista(){
        Intent intent = new Intent(this, NewRagistar.class);
        startActivity(intent);
    }

    public void openAdminlogin(){
        Intent intent = new Intent(this, Adminlogin.class);
        startActivity(intent);
    }

}