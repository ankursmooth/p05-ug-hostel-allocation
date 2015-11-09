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

public class Notifications extends AppCompatActivity {

    EditText msg;//getting from edittext
    Button send;
    String smsg;//storin in string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        msg = (EditText)findViewById(R.id.wardnotify_msg);
        send =(Button)findViewById(R.id.wardnotify_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsg = msg.getText().toString().toLowerCase();

                if(!smsg.isEmpty()){
                    createnotification(smsg);
                }else{
                    Snackbar.make(v,"Enter Message to Send",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
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

    private void createnotification(final String notifymsg){
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

                    AppController.setString(Notifications.this, "Notification_ID", nfid);
                    AppController.setString(Notifications.this, "usergroup" , susergroup);

                    AlertDialog.Builder alertdialogBuilder=new AlertDialog.Builder(Notifications.this);
                    // alertdialogBuilder.setTitle("");

                    alertdialogBuilder
                            .setMessage("Notification Sent to UserGroup")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Notifications.this, Warden_DashBoard.class);
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
                String ntype = "notice";
                String usergroup = "student";
                String creator_ID = AppController.getString(Notifications.this,"loginId");
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
                params.put("message",notifymsg);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }
}
