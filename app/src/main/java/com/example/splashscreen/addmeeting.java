package com.example.splashscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class addmeeting extends AppCompatActivity {

    private Button meetingsave;
    private Button btnpicker;

    int PLACE_PICKER_REQUEST = 1;
    Boolean check=false;
    String TOKEN="";



    EditText inputtopic,inputdatetime,inputplace,inputauthor,inputmember1,inputmember2,inputmember3;
    TextView textchat;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeeting);
        getSupportActionBar().hide();

        inputtopic = findViewById(R.id.inputtopic);
        inputdatetime = findViewById(R.id.inputdatetime);
        inputplace = findViewById(R.id.inputplace);
        inputauthor = findViewById(R.id.inputauthor);
        inputmember1 = findViewById(R.id.inputmember1);
        inputmember2 = findViewById(R.id.inputmember2);
        inputmember3 = findViewById(R.id.inputmember3);
        meetingsave=(Button) findViewById(R.id.meetsave);
        btnpicker=(Button) findViewById(R.id.btnpicker);
        textchat=findViewById(R.id.textchat);


       inputdatetime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showDateTimeDialog(inputdatetime);
           }
       });

       //////////////////

        btnpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(addmeeting.this),101);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        textchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openchat();
            }
        });



      //////
        meetingsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String topic,datetime,place,author,member1,member2,member3;
                topic = inputtopic.getText().toString();
                datetime = inputdatetime.getText().toString();
                place = inputplace.getText().toString();
                author = inputauthor.getText().toString();
                member1 = inputmember1.getText().toString();
                member3 = inputmember2.getText().toString();
                member2= inputmember3.getText().toString();

                if(!topic.equals("") &&  !datetime.equals("") && !place.equals("") && !author.equals("")) {
                   // deleteusername(author);
                 //    if(memcheck(author) && memcheck(member1) && memcheck(member2) && memcheck(member3))
                    ArrayList<String> a=new ArrayList<>();
               

                    String title=author+" wants to set a metting.";
                    String message=topic+" "+" "+datetime+" "+place+" "+"Do you want to attend the meeting?";
                        //
                         register2(topic, datetime, place, author, member1, member2, member3);
                         a.add(member1);
                         a.add(member2);
                         a.add(member3);

                         for(int i=0;i<3;i++)
                         {
                             retrive(a.get(i),title,message);




                         }
//




                        }
                else {
                    Toast.makeText(getApplicationContext(), "All fiels required", Toast.LENGTH_SHORT).show();
                }

            }});



        ////////////////////

    }
    void send1(String token,String title,String message){
        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/send2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(addmeeting.this, "send", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addmeeting.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> getparams = new HashMap<String, String>();
                getparams.put("tok", token);
                getparams.put("title", title);
                getparams.put("message", message);

                return getparams;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(addmeeting.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue r = Volley.newRequestQueue(addmeeting.this);
        r.add(request);
    }

    private void deleteusername(String Author){
        StringRequest stringrequest = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/deletemeet.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(addmeeting.this, "deleted", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addmeeting.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> getparams = new HashMap<String, String>();
                getparams.put("author", Author);


                return getparams;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(addmeeting.this);
        rq.add(stringrequest);


    }


    private  void register2(String Topic,String Datetime,String Place,String Author,String Member1,String Member2,String Member3){



        StringRequest stringrequest = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/register2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(addmeeting.this, "Saved", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addmeeting.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> getparams = new HashMap<String, String>();
                getparams.put("topic", Topic);
                getparams.put("daytime", Datetime);
                getparams.put("place", Place);
                getparams.put("author", Author);
                getparams.put("member1", Member1);
                getparams.put("member2", Member2);
                getparams.put("member3", Member3);

                return getparams;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(addmeeting.this);
        rq.add(stringrequest);


    }
    private void showDateTimeDialog(EditText inputdatetime) {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        inputdatetime.setText(simpleDateFormat.format(calendar.getTime()));


                    }
                };

                new TimePickerDialog(addmeeting.this,timeSetListener,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false).show();



            }
        };
        new DatePickerDialog(addmeeting.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //code of place picker to get the place with google api






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                StringBuilder stringBuilder = new StringBuilder();
                double lat = Double.valueOf(place.getLatLng().latitude);
                double longi = Double.valueOf(place.getLatLng().longitude);

                stringBuilder.append("LATITUDE :");
                stringBuilder.append(lat);
                stringBuilder.append("LOGI :");
                stringBuilder.append(longi);

                inputplace.setText(stringBuilder.toString());

                getCompleteAddressString(lat,longi);
            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        String fullA = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                StringBuilder fullAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getSubAdminArea()).append("\n");
                    fullAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                fullA = fullAddress.toString();
                fullAddress.append("LOGI :");
                fullAddress.append(fullA);

                inputplace.setText(fullAddress.toString());





                Toast.makeText(this, strReturnedAddress.toString() ,Toast.LENGTH_SHORT).show();
                Toast.makeText(this, fullAddress.toString() ,Toast.LENGTH_SHORT).show();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "not get address", Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }



    public void openmainpage1(){
        Intent intent = new Intent(this, MainPage1.class);
        startActivity(intent);
    }

    public void openchat(){
        Intent intent = new Intent(this, chatwithmember.class);
        startActivity(intent);
    }

    private boolean memcheck(String member){

        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/retrive.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array= new JSONArray(response);

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String username=object.getString("username");





                        if(username.equals(member))
                        {
                           check=true;


                        }


                    }



                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addmeeting.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue rq = Volley.newRequestQueue(addmeeting.this);
        rq.add(request);
        if(check){
            check=false;
            Toast.makeText(this, "okkkk", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(this, member+"not registered", Toast.LENGTH_SHORT).show();
        return false;

    }

    private void retrive(String Username,String Title,String Message) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/retrive.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array= new JSONArray(response);

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        String username=object.getString("username");
                        String token=object.getString("token");




                        if(username.equals(Username))
                        {

                            TOKEN=token;
                            send1(TOKEN,Title,Message);

                            Toast.makeText(addmeeting.this, TOKEN, Toast.LENGTH_SHORT).show();

                        }


                    }



                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addmeeting.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue rq = Volley.newRequestQueue(addmeeting.this);
        rq.add(request);


    }



}