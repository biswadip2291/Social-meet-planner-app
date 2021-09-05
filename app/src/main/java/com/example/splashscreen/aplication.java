package com.example.splashscreen;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OSMutableNotification;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class aplication extends Application {
    private static final String ONESIGNAL_APP_ID = "0e9c4603-b86e-4e16-bc34-f4110d382527";
    Boolean checking;
    String TOKEN1="";


    @Override
    public void onCreate() {
        super.onCreate();
        HttpsTrustManager.allowAllSSL();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);


        OneSignal.setNotificationOpenedHandler(new ExampleNotificationOpenedHandler(this));





    }

    class ExampleNotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {


        Context context2;

        ExampleNotificationOpenedHandler(Context context) {
            context2 = context;
        }

        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            OSNotificationAction.ActionType actionType = result.getAction().getType();
            JSONObject data = result.getNotification().getAdditionalData();


            if (data != null) {


            } else {
                Log.e("i", "error");
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("OneSignalExample", "Button pressed with id: " + result.getAction().getActionId());
                String string = result.getAction().getActionId();

                if (string != null && string.equals("like-button")) {
                String UUID=OneSignal.getDeviceState().getUserId();
                checking=true;
                retrive2(UUID);




                }
                else if(string!=null && string.equals("like-button-2"))
                {
                    String UUID=OneSignal.getDeviceState().getUserId();
                    checking=false;
                    retrive2(UUID);
                }




                // The following can be used to open an Activity of your choice.
                // Replace - getApplicationContext() - with any Android Context.


                // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
                //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */
            }
        }

        private void retrive2(String TOKEN) {

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




                            if(token.equals(TOKEN))
                            {
                                StringRequest r = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/retrive2.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray array= new JSONArray(response);


                                            for(int i=0;i<array.length();i++)
                                            {
                                                JSONObject object=array.getJSONObject(i);
                                                String member1=object.getString("member1");
                                                String member2=object.getString("member2");
                                                String member3=object.getString("member3");
                                                String author=object.getString("author");


                                             if(member1.equals(username)){

                                                  retrival(author,username);deletemem("member1",username);

                                             }
                                              else if(member2.equals(username)){
                                                 retrival(author,username);
                                                 deletemem("member2",username);

                                                }
                                             else if(member3.equals(username)){
                                                 retrival(author,username);
                                                 deletemem("member3",username);

                                                }



                                            }



                                        }catch (Exception e){

                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(aplication.this, "error 1", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                RequestQueue rq = Volley.newRequestQueue(aplication.this);
                                rq.add(r);



                                Toast.makeText(aplication.this, TOKEN, Toast.LENGTH_SHORT).show();

                            }


                        }



                    }catch (Exception e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(aplication.this, "eoooe", Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue rq = Volley.newRequestQueue(aplication.this);
            rq.add(request);


        }


        private void retrival(String Author,String User) {

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




                            if(username.equals(Author))
                            {

                                TOKEN1=token;
                                send1(TOKEN1,User);



                            }


                        }



                    }catch (Exception e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(aplication.this, "error 3", Toast.LENGTH_SHORT).show();

                }
            });
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
                    Toast.makeText(aplication.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue rq = Volley.newRequestQueue(aplication.this);
            rq.add(request);


        }
        void send1(String token,String Username2){
           if(checking==true) {
               StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/send3.php", new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(aplication.this, "error 5", Toast.LENGTH_SHORT).show();

                   }
               }) {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String, String> getparams = new HashMap<String, String>();
                       getparams.put("tok", token);
                       getparams.put("username", Username2);


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
                       Toast.makeText(aplication.this, "error 4", Toast.LENGTH_SHORT).show();

                   }
               });
               RequestQueue r = Volley.newRequestQueue(aplication.this);
               r.add(request);

           }
           else {
               StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/send4.php", new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(aplication.this, "error 7", Toast.LENGTH_SHORT).show();

                   }
               }) {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String, String> getparams = new HashMap<String, String>();
                       getparams.put("tok", token);
                       getparams.put("username", Username2);


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
                       Toast.makeText(aplication.this, "error 6", Toast.LENGTH_SHORT).show();

                   }
               });
               RequestQueue r = Volley.newRequestQueue(aplication.this);
               r.add(request);

           }
        }

        void deletemem(String memberNum,String Member){
            StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.1.206/fcm/deletemember3.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context2, error.toString(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> getparams = new HashMap<String, String>();
                    getparams.put("member",Member);
                    getparams.put("memberN", memberNum);

                    return getparams;
                }
            };
            RequestQueue r = Volley.newRequestQueue(context2);
            r.add(request);
        }

    }


}
