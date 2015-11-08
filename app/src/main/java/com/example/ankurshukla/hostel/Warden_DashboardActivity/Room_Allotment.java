package com.example.ankurshukla.hostel.Warden_DashboardActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.Activity.Warden_DashBoard;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Saved_Form;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class Room_Allotment extends AppCompatActivity {

    EditText id1,id2,hostel,room;
    String shostel,sroom,sid1,sid2,rqid;
    Button allot;
    TextView trqid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        shostel = intent.getStringExtra("hostel");
        sroom = intent.getStringExtra("room");
        rqid = intent.getStringExtra("rqid");
        setContentView(R.layout.activity_room__allotment);


        trqid = (TextView)findViewById(R.id.ra_rqid);
        id1 = (EditText)findViewById(R.id.ra_id1);
        id2 = (EditText)findViewById(R.id.ra_id2);
        hostel = (EditText)findViewById(R.id.ra_hostel);
        room = (EditText)findViewById(R.id.ra_room);
        allot = (Button)findViewById(R.id.btn_ra);

        hostel.setText(shostel);
        room.setText(sroom);
        trqid.setText(rqid);

        allot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sid1 = id1.getText().toString().toLowerCase();
                sid2 = id2.getText().toString().toLowerCase();
                shostel = hostel.getText().toString().toLowerCase();
                sroom = room.getText().toString().toLowerCase();

                if(!sid1.isEmpty() && !sid2.isEmpty() && !shostel.isEmpty() && !sroom.isEmpty()){
                    changeroom(sid1,sid2,shostel,sroom,rqid);
                }else{
                    Snackbar.make(v,"Enter All Credentials",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room__allotment, menu);
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

    private void changeroom(final String sid1,final String sid2,
                                    final String hostel, final String room,final String rqid ){

            StringRequest strReq =new StringRequest(Request.Method.POST,
                    AppConfig.URL_CHANGEROOM, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jObj = new JSONObject(response);
                        //Response from server
                        String success = jObj.getString("success");
                        String message = jObj.getString("message");


                        respondtosr(rqid);

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
                    String changeroom ="avabcj";
                    Map<String, String> params = new LinkedHashMap<String, String>();
                    //using LinkedHashmap because backend does not check key value and sees order of variables
                    params.put("changeroom",changeroom);
                    params.put("sid1",sid1);
                    params.put("sid2",sid2);
                    params.put("hostelid",hostel);
                    params.put("roomid",room);

                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(strReq);


    }

    private void respondtosr(final String rqid){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_RESPONDSR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jObj = new JSONObject(response);
                    String msg = jObj.getString("message");
                    final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(Room_Allotment.this);
                    adb
                            .setMessage("Request Responeded!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   Intent i = new Intent(Room_Allotment.this,Warden_DashBoard.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                    android.app.AlertDialog dialog = adb.create();
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
                String wid = AppController.getString(Room_Allotment.this,"loginId");
                String reqresponse = "Ok ,Your room is changed";
                String respondtosr = "numcsa";
                Map<String, String> params = new LinkedHashMap<String, String>();
                params.put("respondtosr", respondtosr);
                params.put("rqid",rqid);
                params.put("wid",wid);
                params.put("reqresponse",reqresponse);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

}
