package com.example.ankurshukla.hostel.Student_Dashboard_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.example.ankurshukla.hostel.Activity.SplashScreen;
import com.example.ankurshukla.hostel.Activity.Student_Dashboard;
import com.example.ankurshukla.hostel.Activity.Warden_DashBoard;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Special_Request extends AppCompatActivity {

    String msg;
    Button send;
    EditText sr_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special__request);

        sr_msg = (EditText)findViewById(R.id.sr_msg);
        send = (Button)findViewById(R.id.sr_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = sr_msg.getText().toString().toLowerCase();

                if(!msg.isEmpty()){
                    createsr(msg);
                }else{
                    Snackbar.make(v,"Please Enter Reason for Room Change",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_special__request, menu);
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

    private void createsr(final String reqmsg){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATESR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                   String rqid = jObj.getString("rqid");
                    String success = jObj.getString("success");
                    String message = jObj.getString("message");

                    //writing the value to sharedpreference in phone database
                    String id = AppController.getString(Special_Request.this,"Student_id");//getting logineed student id so that
                    //we can compare whether it has any existing request id or not
                    rqid = rqid + id;
                    AppController.setString(Special_Request.this,"rqid",rqid);
                    final android.app.AlertDialog.Builder alertdialogbuilder = new android.app.AlertDialog.Builder(Special_Request.this);
                    alertdialogbuilder
                            .setMessage("Your Request is Submitted!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Special_Request.this, Student_Dashboard.class);
                                 //   i.putExtra("rqid",rqid);
                                    startActivity(i);
                                    finish();
                                }
                            });

                    android.app.AlertDialog alertDialog = alertdialogbuilder.create();

                    // show it
                    alertDialog.show();
                    AppController.setString(Special_Request.this,"Request_id",rqid);
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
                // Posting params to saved form url
                java.util.Date date=new java.util.Date();
                SimpleDateFormat sdf;
                sdf=new SimpleDateFormat("yyyy-MM-dd");
                String ndate = (sdf.format(date)).toString();
                String createsr = "jbscjas";//send anything part
                String uid = AppController.getString(Special_Request.this, "Student_id");
                Map<String, String> params = new LinkedHashMap<String, String>();
                //using LinkedHashmap because backend does not check key value and sees order of variables
                params.put("createsr", createsr);
                params.put("sid",uid);
                params.put("rdate",ndate);
                params.put("reqmessage",reqmsg);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }
}
