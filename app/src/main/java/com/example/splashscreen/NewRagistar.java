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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewRagistar extends AppCompatActivity {


    private Button button4;

    EditText inputemail,inputpassword,Username,fullnameuser;

    String emailpatern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ragistar);
        getSupportActionBar().hide();

        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);
        Username = findViewById(R.id.Username);
        fullnameuser = findViewById(R.id.fullnameuser);

        button4=(Button) findViewById(R.id.btnsignup);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,username,fullname;
                email = inputemail.getText().toString();
                password = inputpassword.getText().toString();
                username = Username.getText().toString();
                fullname = fullnameuser.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(NewRagistar.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }else if(!email.matches(emailpatern)){
                    Toast.makeText(NewRagistar.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toast.makeText(NewRagistar.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }else if (password.length()<8){
                    Toast.makeText(NewRagistar.this, "Please Enter 8 Digit Password", Toast.LENGTH_SHORT).show();
                }else if (username.isEmpty()){
                    Toast.makeText(NewRagistar.this, "Please Enter UserName ", Toast.LENGTH_SHORT).show();
                }else if (fullname.isEmpty()){
                    Toast.makeText(NewRagistar.this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
                }else if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")){
                    registercheck(email,username,password,fullname);





                }



            }
        });}



    void registercheck(String Email,String Username,String Password,String Fullname) {



        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/retrive.php", new Response.Listener<String>() {




            @Override

            public void onResponse(String response) {

                try {
                    JSONArray array= new JSONArray(response);

                    boolean check=false;
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String username=object.getString("username");
                        String email=object.getString("email");



                        if(username.equals(Username) )
                        {

                               check=true;

                            Toast.makeText(NewRagistar.this, "Username already exist", Toast.LENGTH_SHORT).show();
                            return;



                        }
                        else if(email.equals(Email)){
                           check=true;


                            Toast.makeText(NewRagistar.this, "Email already existooo", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }
                    if(check==false){
                        register(Email, Username, Password, Fullname);


                    }



                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewRagistar.this, "mmmm", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(NewRagistar.this);
        rq.add(request);


    }

    private void register(String Email,String Username,String Password,String Fullname) {
        String UUID = OneSignal.getDeviceState().getUserId();
        Log.e("m",Username);
//

//
        StringRequest stringrequest = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewRagistar.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> getparams = new HashMap<String, String>();
                getparams.put("token", UUID);
                getparams.put("email", Email);
                getparams.put("password", Password);
                getparams.put("username", Username);
                getparams.put("fullname", Fullname);

                return getparams;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(NewRagistar.this);
        rq.add(stringrequest);
        Toast.makeText(this, "register sucess", Toast.LENGTH_SHORT).show();
        openloginpage();
    }


    public void openloginpage(){
        Intent intent = new Intent(this, loginpage.class);
        startActivity(intent);
    }
}