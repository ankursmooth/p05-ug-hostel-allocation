package com.example.ankurshukla.hostel.Warden_DashboardActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.example.ankurshukla.hostel.Activity.Warden_DashBoard;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Warden_Sr extends AppCompatActivity {

    TextView[] student_id = new TextView[4];
    TextView[] tmsg = new TextView[4];
    TextView[] tdate = new TextView[4];
    TextView[] trqid = new TextView[4];
    String[] gmsg,gdate,gsid,grqid = new String[4];
    String noofrequests;
    CardView c1,c2,c3,c4;
    String wid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        noofrequests = intent.getStringExtra("noofrequests");
        gmsg = intent.getStringArrayExtra("msg");
        gdate = intent.getStringArrayExtra("rdate");
        gsid = intent.getStringArrayExtra("studentid");
        grqid = intent.getStringArrayExtra("rqid");
        setContentView(R.layout.activity_warden__sr);

        int n = Integer.parseInt(noofrequests);
        c1 = (CardView)findViewById(R.id.wsr_c1);
        c2 = (CardView)findViewById(R.id.wsr_c2);
        c3 = (CardView)findViewById(R.id.wsr_c3);
        c4 = (CardView)findViewById(R.id.wsr_c4);
        student_id[0] = (TextView)findViewById(R.id.wsr_id1);
        student_id[1] = (TextView)findViewById(R.id.wsr_id2);
        student_id[2] = (TextView)findViewById(R.id.wsr_id3);
        student_id[3] = (TextView)findViewById(R.id.wsr_id4);
        tmsg[0] = (TextView)findViewById(R.id.wsr_msg1);
        tmsg[1] = (TextView)findViewById(R.id.wsr_msg2);
        tmsg[2] = (TextView)findViewById(R.id.wsr_msg3);
        tmsg[3] = (TextView)findViewById(R.id.wsr_msg4);
        tdate[0] = (TextView)findViewById(R.id.wsr_date1);
        tdate[1] = (TextView)findViewById(R.id.wsr_date2);
        tdate[2] = (TextView)findViewById(R.id.wsr_date3);
        tdate[3] = (TextView)findViewById(R.id.wsr_date4);
        trqid[0] = (TextView)findViewById(R.id.wsr_rno1);
        trqid[1] = (TextView)findViewById(R.id.wsr_rno2);
        trqid[2] = (TextView)findViewById(R.id.wsr_rno3);
        trqid[3] = (TextView)findViewById(R.id.wsr_rno4);
        c1.setVisibility(View.GONE);
        c2.setVisibility(View.GONE);
        c3.setVisibility(View.GONE);
        c4.setVisibility(View.GONE);


        if(noofrequests.equals("1")){
            c1.setVisibility(View.VISIBLE);
            for(int i=0;i<n;i++){
                student_id[i].setText(gsid[i]);
                tmsg[i].setText(gmsg[i]);
                tdate[i].setText(gdate[i]);
                trqid[i].setText("Request No - " + grqid[i]);
            }
        }else if(noofrequests.equals("2")){
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            for(int i=0;i<n;i++){
                student_id[i].setText(gsid[i]);
                tmsg[i].setText(gmsg[i]);
                trqid[i].setText("Request No - " + grqid[i]);
                tdate[i].setText(gdate[i]);
            }
        }else if(noofrequests.equals("3")){
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            for(int i=0;i<n;i++){
                student_id[i].setText(gsid[i]);
                tmsg[i].setText(gmsg[i]);
                trqid[i].setText("Request No - " + grqid[i]);
                tdate[i].setText(gdate[i]);
            }
        }else if(noofrequests.equals("4")){
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c4.setVisibility(View.VISIBLE);
            for(int i=0;i<n;i++){
                student_id[i].setText(gsid[i]);
                tmsg[i].setText(gmsg[i]);
                trqid[i].setText("Request No - " + grqid[i]);
                tdate[i].setText(gdate[i]);
            }
        }

         wid = AppController.getString(Warden_Sr.this,"loginId");

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(Warden_Sr.this);

                adb
                        .setMessage("Respond To Request As follows:-")
                        .setCancelable(false)
                        .setPositiveButton("Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listofemptyrooms(grqid[0]);
                            }
                        })
                        .setNegativeButton("Don't Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               String response = "Room is Not Changed";
                                respondtosr(wid,grqid[0],response);
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = adb.create();
                dialog.show();
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(Warden_Sr.this);

                adb
                        .setMessage("Respond To Request As follows:-")
                        .setCancelable(false)
                        .setPositiveButton("Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listofemptyrooms(grqid[1]);
                            }
                        })
                        .setNegativeButton("Don't Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String response = "Room is Not Changed";
                                respondtosr(wid,grqid[1],response);
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = adb.create();
                dialog.show();
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(Warden_Sr.this);

                adb
                        .setMessage("Respond To Request As follows:-")
                        .setCancelable(false)
                        .setPositiveButton("Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listofemptyrooms(grqid[2]);
                            }
                        })
                        .setNegativeButton("Don't Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String response = "Room is Not Changed";
                                respondtosr(wid,grqid[2],response);
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = adb.create();
                dialog.show();
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(Warden_Sr.this);

                adb
                        .setMessage("Respond To Request As follows:-")
                        .setCancelable(false)
                        .setPositiveButton("Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listofemptyrooms(grqid[3]);
                            }
                        })
                        .setNegativeButton("Don't Change the Room", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String response = "Room is Not Changed";
                                respondtosr(wid,grqid[3],response);
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = adb.create();
                dialog.show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_warden__sr, menu);
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

    private void listofemptyrooms(final String rqid){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_EMPTYROOMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jObj = new JSONObject(response);
                    String noofemptyrooms = jObj.getString("noofemptyrooms");
                    if(noofemptyrooms.equals("0")){//if empty room then dialog box appear
                        final android.app.AlertDialog.Builder alertdialog =
                                new android.app.AlertDialog.Builder(Warden_Sr.this);

                        alertdialog
                                .setMessage("No Empty Rooms!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       dialog.dismiss();
                                    }
                                });

                        AlertDialog dialog = alertdialog.create();
                        dialog.show();;
                    }else{
                        JSONArray room = jObj.getJSONArray("room");
                        int n = Integer.parseInt(noofemptyrooms);
                        if(n<=14) {
                            String[] roomid = new String[n];
                            String[] hostelid = new String[n];
                            String[] floor = new String[n];
                            for (int i = 0; i < n; i++) {
                                JSONObject jobj1 = room.getJSONObject(i);
                                roomid[i] = jobj1.getString("roomid");
                                hostelid[i] = jobj1.getString("hostelid");
                                floor[i] = jobj1.getString("floorno");
                            }

                            for (int k = 0; k < n; k++) {
                                roomid[k] = roomid[k].toUpperCase();
                                hostelid[k] = hostelid[k].toUpperCase();
                                floor[k] = floor[k].toUpperCase();
                            }

                            Intent i = new Intent(Warden_Sr.this,Empty_Rooms.class);
                            i.putExtra("room",roomid);
                            i.putExtra("hostel",hostelid);
                            i.putExtra("floor",floor);
                            i.putExtra("noofemptyrooms",noofemptyrooms);
                            startActivity(i);
                            finish();
                        }else{
                            String[] roomid = new String[14];
                            String[] hostelid = new String[14];
                            String[] floor = new String[14];
                            for (int i = 0; i < 14; i++) {
                                JSONObject jobj1 = room.getJSONObject(i);
                                roomid[i] = jobj1.getString("roomid");
                                hostelid[i] = jobj1.getString("hostelid");
                                floor[i] = jobj1.getString("floorno");
                            }

                            for (int k = 0; k < 14; k++) {
                                roomid[k] = roomid[k].toUpperCase();
                                hostelid[k] = hostelid[k].toUpperCase();
                                floor[k] = floor[k].toUpperCase();
                            }
                            String empty = "14";
                            Intent i = new Intent(Warden_Sr.this,Empty_Rooms.class);
                            i.putExtra("room",roomid);
                            i.putExtra("hostel",hostelid);
                            i.putExtra("floor",floor);
                            i.putExtra("noofemptyrooms",empty);
                            i.putExtra("rqid",rqid);//rqid of that particular request
                            startActivity(i);
                            finish();
                        }



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
                String getemptyrooms = "numcsa";
                Map<String, String> params = new HashMap<String, String>();
                params.put("getemptyrooms", getemptyrooms);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);


    }

    private void respondtosr(final String wid,final String rqid,final String reqresponse){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_RESPONDSR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jObj = new JSONObject(response);
                    String msg = jObj.getString("message");
                    final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(Warden_Sr.this);
                    adb
                            .setMessage("Request responded")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Warden_Sr.this, Warden_DashBoard.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                    AlertDialog dialog = adb.create();
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
