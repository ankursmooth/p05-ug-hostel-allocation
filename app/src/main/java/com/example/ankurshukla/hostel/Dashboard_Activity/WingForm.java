package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Ankur Shukla on 10/17/2015.
 */
public class WingForm extends AppCompatActivity {

    CardView one,two,three;
    String snumber;//no coming from prefernce
    String number; //number direct accesss by shared preference
    String hostel[] = new String[]{"",""};
    String floor[] = new String[]{"",""};
    Button save,submit;
    EditText[] rname = new EditText[6];//r ==room and n=name
    EditText [] rollno = new EditText[6]; //r=room and id=rool no
    String roomname [] = new String[6];
    String rollId [] = new String[6];
    String sname [] = new String[6];
    String sid [] = new String[6];
    int n;
    String name = "";//for determining which type of button has called the check conflicts fucntion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        snumber=intent.getStringExtra("number");//getting no of rooms in the wing from prference class
        hostel = intent.getStringArrayExtra("Hostel_type");//getting all hostel types for preferneces
        floor  = intent.getStringArrayExtra("Floor_type");//getting all floor types from preferences
        sname = intent.getStringArrayExtra("sname");
        sid = intent.getStringArrayExtra("sid");
        setContentView(R.layout.wing_form);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        one= (CardView) findViewById(R.id.wfl1);
        two= (CardView) findViewById(R.id.wfl2);
        three= (CardView) findViewById(R.id.wfl3);
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

        number = AppController.getString(WingForm.this,"noofrooms");

        if(number.equals("1")){
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
            for(int i=0;i<2*Integer.parseInt(number);i++){//setting the names and roll no from  wingform array to saved from edittext
                if(sname == null){
                    rname[i].setText("");
                    rollno[i].setText("");
                }else {
                    rname[i].setText(sname[i]);
                    rollno[i].setText(sid[i]);
                }
            }
        }
        else if(number.equals("2")){
            three.setVisibility(View.GONE);
            for(int i=0;i<2*Integer.parseInt(number);i++){//setting the names and roll no from  wingform array to saved from edittext
                if(sname == null){
                    rname[i].setText("");
                    rollno[i].setText("");
                }else {
                    rname[i].setText(sname[i]);
                    rollno[i].setText(sid[i]);
                }
            }
        }else {
            for (int i = 0; i < 2 * Integer.parseInt(number); i++) {//setting the names and roll no from  wingform array to saved from edittext
                if(sname == null){
                    rname[i].setText("");
                    rollno[i].setText("");
                }else {
                    rname[i].setText(sname[i]);
                    rollno[i].setText(sid[i]);
                }
            }

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
                }else
                   savedForm(roomname, rollId, number, hostel, floor);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
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
                    final AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(WingForm.this);
                    // alertdialogBuilder.setTitle("");
                    String msg = "You wont be able to make further changes.Your sure want to submit the form";
                    alertdialogBuilder
                            .setMessage(msg)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    checkconflict(roomname, rollId, number, hostel, floor);
                                 //   submitform(roomname,rollId, number, hostel, floor);
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = alertdialogBuilder.create();
                    dialog.show();
                }
            }
        });

    }

    //for saving the form for first time
    private void savedForm(final String sname[],final String sid[],final String noOfStudents,final String hostelid[],final
                           String floorno[]){

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_SAVEDFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String wfid = jObj.getString("wfid");
                    String success = jObj.getString("success");
                    String message = jObj.getString("message");

                    //writing the value to sharedpreference in phone database

                    Toast.makeText(WingForm.this,message ,Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(WingForm.this);
                    // alertdialogBuilder.setTitle("");


                    alertdialogBuilder
                            .setMessage(message)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i =  new Intent(WingForm.this,Saved_Form.class);
                                    i.putExtra("sname",sname);//passing all names
                                    i.putExtra("sid",sid);//passing all id
                                    i.putExtra("noOfStudents",noOfStudents);//passing number of rooms
                                    i.putExtra("Hostel_type",hostel);//sending hostel types to wing form class
                                    i.putExtra("Floor_type",floor);//sending floor types to wing form class
                                    startActivity(i);
                                }
                            });

                    AlertDialog dialog = alertdialogBuilder.create();
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
                // Posting params to saved form url
                int noofstudent = 2*Integer.parseInt(noOfStudents);
                String creatre = "jbscjas";//send anything part
                String uid = AppController.getString(WingForm.this,"Student_id");
                Map<String, String> params = new LinkedHashMap<String, String>();
                //using LinkedHashmap because backend does not check key value and sees order of variables
                params.put("createsavedform", creatre);
                params.put("uid",uid);
                params.put("noofstudent", String.valueOf(noofstudent));
                for(int m=1;m<3;m++){
                    String z= String.valueOf(m);
                    params.put("pfid["+m+"]",z);
                    params.put("hostelid["+m+"]",hostelid[m-1]);
                    params.put("floorno["+m+"]",floorno[m-1]);
                }
                for (int k = 0; k < noofstudent; k++) {
                    int m = k/2 +1;
                    String z= String.valueOf(m);
                    params.put("sid["+k+"]", sid[k]);
                    params.put("sname["+k+"]", sname[k]);
                    params.put("roominwing["+k+"]",z );
                }

                return params;
            }//pori array paas ho rhi hai
            //debug krke dekho smjh aa jaega ek ke sath ek kaise bheju smjh nh aa rha

        };

        AppController.getInstance().addToRequestQueue(strReq);

    }




    //here number is no of rooms only so total student will be 2*no of students
    private void submitform(final String sname[],final String sid[],final String noOfStudents,final String hostelid[],final
    String floorno[]){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_SUBMITFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String wfid = jObj.getString("wfid");
                    String success = jObj.getString("success");
                    String message = jObj.getString("message");

                    //writing the value to sharedpreference in phone database

                    Toast.makeText(WingForm.this,message ,Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(WingForm.this);
                    // alertdialogBuilder.setTitle("");


                    alertdialogBuilder
                            .setMessage(message)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i =  new Intent(WingForm.this,Submitted_Form.class);
                                    i.putExtra("sid",sid);//passing all id
                                    i.putExtra("noOfStudents",noOfStudents);//passing number of rooms
                                    startActivity(i);
                                }
                            });

                    AlertDialog dialog = alertdialogBuilder.create();
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
                // Posting params to saved form url
                int noofstudent = 2*Integer.parseInt(noOfStudents);
                String creatre = "jbscjas";//send anything part
                String uid = AppController.getString(WingForm.this,"Student_id");
                Map<String, String> params = new LinkedHashMap<String, String>();
                //using LinkedHashmap because backend does not check key value and sees order of variables
                params.put("createsubmittedform", creatre);
                params.put("uid",uid);
                params.put("noofstudent", String.valueOf(noofstudent));
                for(int m=1;m<3;m++){
                    String z= String.valueOf(m);
                    params.put("pfid["+m+"]",z);
                    params.put("hostelid["+m+"]",hostelid[m-1]);
                    params.put("floorno["+m+"]",floorno[m-1]);
                }
                for (int k = 0; k < noofstudent; k++) {
                    int m = k/2 +1;
                    String z= String.valueOf(m);
                    params.put("sid["+k+"]", sid[k]);
                    params.put("roominwing["+k+"]",z );
                }
                return params;
            }//pori array paas ho rhi hai
            //debug krke dekho smjh aa jaega ek ke sath ek kaise bheju smjh nh aa rha

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }





    //no of conflicts will be no of students so total conflicts we have to sent is 2*no of conflicts
    private void checkconflict(final String sname[],final String sid[],final String noofconflicts,final String hostelid[],final
    String floorno[]){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_CONFLICTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from serveR
                    String conflicts = jObj.getString("conflicts");//no of conflicts it will show
                    String [] wfid = new String[Integer.parseInt(conflicts)];
                    String [] sid = new String[Integer.parseInt(conflicts)];
                    if(conflicts.equals("0")){
                        submitform(roomname,rollId, number, hostel, floor);
                    }else{
                        JSONArray repeat = jObj.getJSONArray("repeat");
                        for(int i=0;i<Integer.parseInt(conflicts);i++){
                            JSONObject jobji = repeat.getJSONObject(i);
                             wfid[i] = jobji.getString("wfid");
                            sid[i] = jobji.getString("sid");
                        }
                        String m1 = "Following Roll Number Already have been Submitted:-";
                        for(int i=0;i<Integer.parseInt(conflicts);i++){
                           name = name + sid[i].toUpperCase();
                        }
                        String msg = m1 + name;
                        final AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(WingForm.this);
                        alertdialogBuilder.setTitle("Conflicts!");


                        alertdialogBuilder
                                .setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = alertdialogBuilder.create();
                        dialog.show();
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
                // Posting params to saved form url
                int noofstudent = 2*Integer.parseInt(noofconflicts);
                String checkconflicts = "jbscjas";//send anything part
                String uid = AppController.getString(WingForm.this,"Student_id");
                Map<String, String> params = new LinkedHashMap<String, String>();
                //using LinkedHashmap because backend does not check key value and sees order of variables
                params.put("checkconflicts", checkconflicts);
                params.put("noofstudent", String.valueOf(noofstudent));
                for(int m=0;m<noofstudent;m++){
                    params.put("sid["+m+"]",sid[m]);
                }


                return params;
            }//pori array paas ho rhi hai
            //debug krke dekho smjh aa jaega ek ke sath ek kaise bheju smjh nh aa rha

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

}
