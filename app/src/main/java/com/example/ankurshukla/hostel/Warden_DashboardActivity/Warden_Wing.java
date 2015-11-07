package com.example.ankurshukla.hostel.Warden_DashboardActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.Activity.Warden_DashBoard;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Warden_Wing extends AppCompatActivity {

    EditText stdate,edate;
    Button startalloc,doalloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden__wing);

        stdate = (EditText) findViewById(R.id.start_date);
        edate = (EditText) findViewById(R.id.end_date);
        startalloc = (Button)findViewById(R.id.startalloc);
        doalloc = (Button)findViewById(R.id.donealloc);
        doalloc.setEnabled(false);


        startalloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedid = AppController.getString(Warden_Wing.this, "loginId");
                String button_pressed = "On";
                AppController.setString(Warden_Wing.this,"button_pressed",button_pressed);
                 String sdate = stdate.getText().toString().toLowerCase();
                 String sedate = edate.getText().toString().toLowerCase();
                if(!sdate.isEmpty() && !sedate.isEmpty()) {
                    createalloprocess(sdate, sedate, loggedid);
                }else{
                    Snackbar.make(v,"Enter All Credentials",Snackbar.LENGTH_SHORT).show();
                }
                startalloc.setEnabled(false);

            }
        });

        if (AppController.getString(Warden_Wing.this,"button_pressed").equals("On")){
            doalloc.setEnabled(true);
        }

        doalloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startalloc.setEnabled(true);
                String wid = AppController.getString(Warden_Wing.this,"loginId");
                doAllocation(wid);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_warden__wing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //function for create alloc process
    private void createalloprocess(final String Sdate,final  String SEdate,final  String wid){

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URl_CREATEALLOPROCESS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String success = jObj.getString("success");
                    String message = jObj.getString("message");
                    String wid = jObj.getString("wid");

                    //writing the value to sharedpreference in phone database
                    AppController.setString(Warden_Wing.this, "creator_Id", wid);


                    String creatorId  = AppController.getString(Warden_Wing.this, "creator_Id");
                    createNotification(creatorId,Sdate,SEdate);

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
                String screatealloprocess = "Hello";
                Map<String, String> params = new HashMap<String, String>();
                params.put("createalloprocess",screatealloprocess);
                params.put("wid", wid);
                params.put("startdate", Sdate);
                params.put("enddate",SEdate);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

//function for create Notification
    private  void createNotification( final String creator_ID ,final String startdate ,final String enddate){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_NOTIFY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String success = jObj.getString("success");
                    String message = jObj.getString("message");
                    String nfid = jObj.getString("nfid");
                    String susergroup = jObj.getString("usergroup");

                    //writing the value to sharedpreference in phone database

                    AppController.setString(Warden_Wing.this, "Notification_ID", nfid);
                    AppController.setString(Warden_Wing.this, "usergroup" , susergroup);

                    AlertDialog.Builder alertdialogBuilder=new AlertDialog.Builder(Warden_Wing.this);
                    // alertdialogBuilder.setTitle("");

                    alertdialogBuilder
                            .setMessage("Notification Sent to UserGroup")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Warden_Wing.this, Warden_DashBoard.class);
                                    startActivity(i);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertdialogBuilder.create();

                    // show it
                    alertDialog.show();


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
                String screatenotify = "hello";
                String ntype = "allocation";
                String usergroup = "student";
                String message = "Allocation process starts on " + startdate + " fill wing form before " + enddate;
                //for present date
                java.util.Date date=new java.util.Date();
                SimpleDateFormat sdf;
                sdf=new SimpleDateFormat("yyyy-MM-dd");
                String ndate = (sdf.format(date)).toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("createnotification",screatenotify);
                params.put("ntype", ntype);
                params.put("usergroup", usergroup);
                params.put("creatorid",creator_ID);
                params.put("ndate", ndate);
                params.put("message",message);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void doAllocation(final String warden_id){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_DOALLOCATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String success = jObj.getString("success");
                    String message = jObj.getString("message");


                    final android.app.AlertDialog.Builder alertdialog = new android.app.AlertDialog.Builder(Warden_Wing.this);

                    alertdialog
                            .setMessage("Allocation Done!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Warden_Wing.this,Warden_DashBoard.class);
                                    startActivity(i);
                                    finish();
                                }
                            });

                    AlertDialog dialog = alertdialog.create();
                    dialog.show();

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
                String allocate = "Hello";
                Map<String, String> params = new HashMap<String, String>();
                params.put("doallocation",allocate);
                params.put("wid", warden_id);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

}
