package com.example.ankurshukla.hostel.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                   /*     checkconnect();
                    String connect = AppController.getString(SplashScreen.this,"token");
                    if(connect.equals("1")){
                        Intent i =new Intent(SplashScreen.this ,MainActivity.class);
                        startActivity(i);
                    }else {
                        AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(SplashScreen.this);
                        // alertdialogBuilder.setTitle("");

                        alertdialogBuilder
                                .setMessage("Connection to Database not established.Try again!!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertdialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }*/
                    Intent i =new Intent(SplashScreen.this ,MainActivity.class);
                    startActivity(i);
                }
                finish();
            }
        };
        logoTimer.start();
    }

    //function to check whether connect to databse has been made or not
    private void checkconnect(){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_CHECKCONNECT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String success = jObj.getString("success");
                    /*if(success.equals("1")){
                        Intent i =new Intent(SplashScreen.this ,MainActivity.class);
                        startActivity(i);
                    }else{
                        AlertDialog.Builder alertdialogBuilder=new AlertDialog.Builder(SplashScreen.this);
                        // alertdialogBuilder.setTitle("");

                        alertdialogBuilder
                                .setMessage("Connection to Database not established.Try again!!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertdialogBuilder.create();

                        // show it
                        alertDialog.show();*/
                    AppController.setString(SplashScreen.this,"token",success);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                String checkconnect = "numcsa";
                Map<String, String> params = new HashMap<String, String>();
                params.put("checkconnect", checkconnect);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

}
