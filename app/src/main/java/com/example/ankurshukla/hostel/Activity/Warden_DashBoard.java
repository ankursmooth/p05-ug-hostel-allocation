package com.example.ankurshukla.hostel.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Submitted_Request;
import com.example.ankurshukla.hostel.Warden_DashboardActivity.Notifications;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Search;
import com.example.ankurshukla.hostel.R;
import com.example.ankurshukla.hostel.Warden_DashboardActivity.Warden_Sr;
import com.example.ankurshukla.hostel.Warden_DashboardActivity.Warden_Wing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Warden_DashBoard extends AppCompatActivity {

    Button wing,notification,search,special_req;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_dash_board);


        wing= (Button) findViewById(R.id.btn_warden_wing);
        notification= (Button) findViewById(R.id.btn_warden_notify);
        name=(TextView)findViewById(R.id.display_name);
        search = (Button)findViewById(R.id.btn_warden_search);
        special_req = (Button)findViewById(R.id.btn_warden_special);

        String display = AppController.getString(Warden_DashBoard.this ,"username");
        name.setText(display);

        wing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Warden_DashBoard.this, Warden_Wing.class);
                startActivity(i);
                finish();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Warden_DashBoard.this, Notifications.class);
                startActivity(i);
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Warden_DashBoard.this, Search.class);
                startActivity(i);
            }
        });

        special_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getspecialrequest();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dash_board, menu);
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

    private void getspecialrequest(){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GETSR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                String [] msg = new String[4];
                String [] rdate = new String[4];
                String [] sid = new String[4];
                String [] rqid = new String[4];
                try {
                    JSONObject jObj = new JSONObject(response);
                    String noofrequests = jObj.getString("noofrequests");
                    int k =Integer.parseInt(noofrequests);
                    if(noofrequests.equals("0")){
                        Toast.makeText(Warden_DashBoard.this,"No Request till Now",Toast.LENGTH_LONG).show();
                    }else{
                        JSONArray reques = jObj.getJSONArray("requez");
                        for(int l=0;l<k;l++){
                            JSONObject jobj1 = reques.getJSONObject(l);
                            msg[l] = jobj1.getString("reqmessage");
                            rqid[l] =  jobj1.getString("rqid");
                            rdate[l] = "Request Made On: "+jobj1.getString("rdate");
                            sid[l] = "Request Made by- "+jobj1.getString("sid");
                        }

                        //for warden i am getting only id of student their msg and request made on which date
                        Intent i = new Intent(Warden_DashBoard.this,Warden_Sr.class);
                        i.putExtra("noofrequests",noofrequests);
                        i.putExtra("msg",msg);
                        i.putExtra("rdate",rdate);
                        i.putExtra("studentid",sid);
                        i.putExtra("rqid",rqid);
                        startActivity(i);
                        finish();
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
                String usertype = "warden";
                String getsr = "numcsa";
                Map<String, String> params = new HashMap<String, String>();
                params.put("getsr", getsr);
                params.put("usertype",usertype);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

}
