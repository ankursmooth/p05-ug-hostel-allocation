package com.example.ankurshukla.hostel.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.Dashboard_Activity.Preference;
import com.example.ankurshukla.hostel.Dashboard_Activity.Saved_Form;
import com.example.ankurshukla.hostel.Dashboard_Activity.Student_Notify;
import com.example.ankurshukla.hostel.Dashboard_Activity.Submitted_Form;
import com.example.ankurshukla.hostel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student_Dashboard extends AppCompatActivity {

    Button wing,notification,search,special_req;
    TextView name,student_notify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        wing= (Button) findViewById(R.id.btn_student_wing);
        notification= (Button) findViewById(R.id.btn_student_notify);
        name=(TextView)findViewById(R.id.display_sname);
        student_notify = (TextView)findViewById(R.id.student_notify);

        String display = AppController.getString(Student_Dashboard.this, "username");
        name.setText(display);

        //number takees the number of notification in database
        final String number = AppController.getString(Student_Dashboard.this, "noOfNotify");
        if(number.equals("0")){
            student_notify.setText("");
        }else{
            String notifym_msg = "You Have total " +number+ " number of Notifications!";
            student_notify.setText(notifym_msg);
        }

        //checkform is called first to check whether is there any saved or submiited form present or not
        wing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = AppController.getString(Student_Dashboard.this,"Student_id");
                CheckForm(uid);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.equals("0")){
                    AlertDialog.Builder alertdialogBuilder=new AlertDialog.Builder(Student_Dashboard.this);
                    // alertdialogBuilder.setTitle("");

                    alertdialogBuilder
                            .setMessage("No Notifications")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Student_Dashboard.this, Student_Dashboard.class);
                                    startActivity(i);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertdialogBuilder.create();

                    // show it
                    alertDialog.show();
                }else {
                    String uid = AppController.getString(Student_Dashboard.this, "Student_id");
                    get_notification(uid, number);
                }
                }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student__dashboard, menu);
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

    private void get_notification(final String uid,final String number){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_NOTIFY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ArrayList<String> msg_list = new ArrayList<String>();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    JSONArray notify = jObj.getJSONArray("notiz");
                    for(int i=0;i<Integer.parseInt(number);i++){
                        JSONObject jobj1=  notify.getJSONObject(i);
                        /* msg = msg + jobj1.getString("nmessage");*/
                        msg_list.add(jobj1.getString("nmessage")+" by "+jobj1.getString("creatorid")+ " on "+ jobj1.getString("ndate") + " ("+ jobj1.getString("ntype")+ ").");
                    }
                   /* Toast.makeText(Student_Dashboard.this,msg,Toast.LENGTH_SHORT).show();*/

                    //writing the value to sharedpreference in phone database


                    //    Toast.makeText(MainActivity.this, message ,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Student_Dashboard.this, Student_Notify.class);
                    i.putStringArrayListExtra("msg_list", msg_list);
                    startActivity(i);

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
                String getnotification = "numcsa";
                String usergroup = "student";
                Map<String, String> params = new HashMap<String, String>();
                params.put("usergroup", usergroup);
                params.put("uid", uid);
                params.put("getnotification",getnotification);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void CheckForm(final String uid){

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URl_CHECKFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ArrayList<String> msg_list = new ArrayList<String>();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String success = jObj.getString("success");
                    String msg = jObj.getString("message");

                    //redirecting to the class according to which class is present
                    if(msg.equals("noFormPresent")){
                        Intent i = new Intent(Student_Dashboard.this,Preference.class);
                        startActivity(i);
                    }else if(msg.equals("savedFormPresent")){
                        Intent i = new Intent(Student_Dashboard.this,Saved_Form.class);
                        startActivity(i);
                    }else if(msg.equals("submittedFormPresent")){
                        Intent i = new Intent(Student_Dashboard.this,Submitted_Form.class);
                        startActivity(i);
                    }

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
                String checkform = "numcsa";
                Map<String, String> params = new HashMap<String, String>();
                params.put("checkform", checkform);
                params.put("uid", uid);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);

    }
}
