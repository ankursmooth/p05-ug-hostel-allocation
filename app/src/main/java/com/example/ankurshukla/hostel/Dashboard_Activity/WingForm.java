package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


/**
 * Created by Ankur Shukla on 10/17/2015.
 */
public class WingForm extends AppCompatActivity {

    LinearLayout one,two,three,four,five,six,seven,eight;
    String number;
    String hostel[] = new String[]{"",""};
    String floor[] = new String[]{"",""};
    Button save,submit;
    EditText[] rname = new EditText[6];//r ==room and n=name
    EditText [] rollno = new EditText[6]; //r=room and id=rool no
    String roomname [] = new String[]{"","","","","",""};
    String rollId [] = new String[]{"","","","","",""};
    String sname[] = new String[]{"","","","","",""};
    String sid[] = new String[]{"","","","","",""};
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        number=intent.getStringExtra("number");//getting no of rooms in the wing from prference class
        hostel = intent.getStringArrayExtra("Hostel_type");//getting all hostel types for preferneces
        floor  = intent.getStringArrayExtra("Floor_type");//getting all floor types from preferences
        setContentView(R.layout.wing_form);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        one= (LinearLayout) findViewById(R.id.wfl1);
        two= (LinearLayout) findViewById(R.id.wfl2);
        three= (LinearLayout) findViewById(R.id.wfl3);
        rname[0] = (EditText) findViewById(R.id.fullname1rm1);
        rname[1] = (EditText) findViewById(R.id.fullname2rm1);
        rname[2] = (EditText) findViewById(R.id.fullname1rm2);
        rname[3] = (EditText) findViewById(R.id.fullname2rm2);
        rname[4] = (EditText) findViewById(R.id.fullname1rm3);
        rname[5] = (EditText) findViewById(R.id.fullname2rm3);
        rollno[0] = (EditText) findViewById(R.id.id1rm1);
        rollno[1] = (EditText) findViewById(R.id.id2rm1);
        rollno[2] = (EditText) findViewById(R.id.id1rm2);
        rollno[3] = (EditText) findViewById(R.id.id2rm2);
        rollno[4] = (EditText) findViewById(R.id.id1rm3);
        rollno[5] = (EditText) findViewById(R.id.id2rm3);
        save = (Button)findViewById(R.id.save_but);
        submit = (Button)findViewById(R.id.submit_but);


        if(number.equals("1")){
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
        }
        else if(number.equals("2")){
            three.setVisibility(View.GONE);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<2*Integer.parseInt(number);i++){
                    roomname [i] = rname[i].getText().toString().toLowerCase();
                    rollId [i] = rollno[i].getText().toString().toLowerCase();
                }
                boolean b = false;
                for(int j=0;j<2*Integer.parseInt(number);j++){
                    if(roomname[j].equals("") || rollId[j].equals("")){
                        b = true;
                        break;
                    }
                }
                if(b == true){
                    final AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(WingForm.this);
                    // alertdialogBuilder.setTitle("");


                    alertdialogBuilder
                            .setMessage("Fill all the names")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = alertdialogBuilder.create();
                    dialog.show();
                }else{
                    savedForm(roomname, rollId, number, hostel, floor);
                }
            }
        });
    }

    private void savedForm(final String sname[],final String sid[],final String noOfStudents,final String hostelid[],final
                           String floorno[]){

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_SAVEDFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String wid = jObj.getString("wid");
                    String success = jObj.getString("success");
                    String message = jObj.getString("message");

                    //writing the value to sharedpreference in phone database

                    Toast.makeText(WingForm.this,message ,Toast.LENGTH_SHORT).show();

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
                String creatre = "jbscjas";//send anything part
                String [] roomwing = new String[]{"","",""};
                String [] pfid  = new String[]{"",""};
                String uid = AppController.getString(WingForm.this,"Student_id");
                Map<String, String> params = new HashMap<String, String>();
                params.put("createsavedform", creatre);
                params.put("uid",uid);
                for (int k = 0; k < 2*Integer.parseInt(noOfStudents); k++) {
                    int m = k/2;
                    params.put("sid["+k+"]", sid[k]);
                    params.put("sname["+k+"]", sname[k]);
                    params.put("roomwing["+k+"]", String.valueOf(m));
                    params.put("pfid["+k+"]",String.valueOf(k));
                    params.put("hostelid["+k+"]",hostelid[k]);
                    params.put("floorno["+k+"]",floorno[k]);
                    params.put("noofstudent",String .valueOf(k*2));
                }
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);

    }
   
}
