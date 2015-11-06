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
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Preference;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Saved_Form;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Search;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Special_Request;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Student_Notify;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Submitted_Form;
import com.example.ankurshukla.hostel.R;
import com.example.ankurshukla.hostel.Student_Dashboard_Activity.Submitted_Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student_Dashboard extends AppCompatActivity {

    Button wing,notification,search,special_req;//buttons by which student will access the different functionality
    //wing button direct it to the wing form if allocation has started and redirect to the wing form or saved form as present
    //notification show the no of notifications if present
    //search allows them to search for students room no hostel after allocation
    //special req allows them to end request for room change after allocation
    TextView name,student_notify;
    // name testview showing the name of login user after taking from server
    //notify tells the no of notification if present
    String rqid;
    //rqid is used to redirect the submitted request if present else redirect them to fill one request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        wing= (Button) findViewById(R.id.btn_student_wing);//link of the wing form button in xml to use in java
        notification= (Button) findViewById(R.id.btn_student_notify);//link of the notification  button in xml to us in java
        search = (Button)findViewById(R.id.btn_student_search);//link of the w search button in xml to us in java
        name=(TextView)findViewById(R.id.display_sname);//link of textview to show name of the login user
        student_notify = (TextView)findViewById(R.id.student_notify);//no of notifications is hshown by this
        special_req = (Button)findViewById(R.id.btn_student_special);//link of the special request form button in xml to us in java

        String display = AppController.getString(Student_Dashboard.this, "username");//taking the name of user stored in the phone database
        name.setText(display);//displaying the name of user in dashboard

        //number takees the number of notification in database and according to it is showing the no of notification if present
        final String number = AppController.getString(Student_Dashboard.this, "noOfNotify");
        if(number.equals("0")){
            student_notify.setText("");
        }else{
            String notifym_msg = "You Have total " +number+ " number of Notifications!";
            student_notify.setText(notifym_msg);
        }

        //checkform is called first to check whether is there any saved or submiited form present or not
        //when clicked on wing from button and checkform receives the response from server according to which it is redirected
        wing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = AppController.getString(Student_Dashboard.this,"Student_id");
                CheckForm(uid);
            }
        });

        //it shows the notification if present by calling get notification method when clicked else show msg that no notification is present
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
                    get_notification(uid, number);//student is id of student by which he has login and number is the no of notification
                    // which is taken  by in response from server
                }
                }


        });

        //search button redirected to the search screen from where if student can search for details about student room and hostel after allocation been done
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student_Dashboard.this, Search.class);
                startActivity(i);
            }
        });

        //when clicked on special request button it is redirected to screen which is present which is being decided by the rqid if rqid is null then
        //special request screen else then request sent by student is shown
        rqid = AppController.getString(Student_Dashboard.this,"rqid");//comparing
        String id = AppController.getString(Student_Dashboard.this,"Student_id");//comparing whether it is equal to login student rqid or not
        final String compare = "1" + id;//storing the same value as 1y13uc007 login student id
        special_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rqid.equals(compare)){
                    String uid = AppController.getString(Student_Dashboard.this, "Student_id");
                    getspecialrequest(uid);
                }else{
                    Intent i = new Intent(Student_Dashboard.this, Special_Request.class);
                    startActivity(i);
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

    /*  get notification is called when student click on notification button
    * it gives the response by sending student id and usergroup as student and in response it shows all the notification given by warden*/
    private void get_notification(final String uid,final String number){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_NOTIFY, new Response.Listener<String>() {
                //url is called which is currently stored in appconfig class

            @Override
            public void onResponse(String response) {

                ArrayList<String> data = new ArrayList<>();//arraylist to store all msg we get in response from server

                try {
                    JSONObject jObj = new JSONObject(response);//make json object jobj to take data from getnotification.php file
                    //Response from server
                    JSONArray notify = jObj.getJSONArray("notiz");//make json array notify of jobj as data is received in array form because there can be many noifications
                    for(int i=0;i<Integer.parseInt(number);i++){
                        JSONObject jobj1=  notify.getJSONObject(i);//make json object of that array and take all the msg present in each notification and who created the notification and notification type
                        //which gets stored in arraylist
                        data.add(jobj1.getString("nmessage")+" by "+jobj1.getString("creatorid")+ " on "+ jobj1.getString("ndate") + " ("+ jobj1.getString("ntype")+ ").");
                    }


                    Intent i = new Intent(Student_Dashboard.this, Student_Notify.class);//notification class is called where the student will see the notification present and their creator id
                    // and when notification is created adn type of notification
                    i.putStringArrayListExtra("msg_list", data);//array list is passed to the notification class
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


    //checkforn is called when student clicks on wing form button
    private void CheckForm(final String uid){

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URl_CHECKFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);//for storing their response when called checkform api
                    //Response from server
                    String success = jObj.getString("success");
                    String msg = jObj.getString("message");//we take response msg and according to redirect the student to three different class
                    //if no form present then procedure of wing form details submission
                    //if saved form present then getsaved form function is called else getsubmitted form function is called

                    //redirecting to the class according to which class is present
                    if(msg.equals("noFormPresent")){
                        Intent i = new Intent(Student_Dashboard.this,Preference.class);
                        startActivity(i);
                        finish();
                    }else if(msg.equals("savedFormPresent")){
                        getsavedform();
                    }else if(msg.equals("submittedFormPresent")){
                        getsubmittedform();
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

            @Override//response getting after calling checkform.php link and sending student id and checkform string to the server
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

//based in the response of checkform this function is called if saved form is present of that student in database
    private  void  getsavedform(){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GETSAVEDFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                String savesid[] = new String[6];
                String savename[] = new String[6];
                String hostelid[] = new String[2];
                String floor[] = new String[2];

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String number = jObj.getString("noofstudent");//no of student in the wing from which he filled
                    int n = Integer.parseInt(number);
                    JSONArray entry = jObj.getJSONArray("entry");//json array of above json object to store name and student id
                    //in string array and then passing to saved form for diplay
                    for(int i=0;i<n;i++){
                        JSONObject jobj1 = entry.getJSONObject(i);
                         savesid[i] = jobj1.getString("sid");
                        savename[i] = jobj1.getString("sname");
                    }
                    JSONArray pref = jObj.getJSONArray("pref");//no of preferences filled in the form
                    for(int j=0;j<2;j++){
                        JSONObject jobj2 = pref.getJSONObject(j);
                        hostelid[j] = jobj2.getString("hostelid");//storing the preferrred hostel from getsavedform.php file response
                        floor[j] = jobj2.getString("floorno");//storing the preferrred floor from getsavedform.php file response
                    }

                    Intent i = new Intent(Student_Dashboard.this,Saved_Form.class);
                    i.putExtra("sname",savename);//passing all names
                    i.putExtra("sid",savesid);//passing all id
                    i.putExtra("no_Of_Students",number);//passing number of rooms
                    i.putExtra("Hostel_type",hostelid);
                    i.putExtra("Floor_type",floor);
                    startActivity(i);
                    //pfid hostel id and floor id is also passing from this activity to saved form activity


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

            @Override//getting response after sending string getsavedform and uid of that student
            //it gives saved form if present in database
            protected Map<String, String> getParams() {
                // Posting params to register url
                String form = "numcsa";
                String uid = AppController.getString(Student_Dashboard.this,"Student_id");
                Map<String, String> params = new HashMap<String, String>();
                params.put("getsavedform", form);
                params.put("uid", uid);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);

    }


    //based in the response of checkform this function is called if submit  form is present of that student in database
    private  void  getsubmittedform(){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GETSUBMITTEDFORM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                String savesid[] = new String[6];
                String hostelid[] = new String[2];
                String floor[] = new String[2];

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server
                    String number = jObj.getString("noofstudent");
                    int n = Integer.parseInt(number);//no of student in the wing from which he filled
                    int m = n/2;//m will show the no of rooms required for displaying in submitted form
                    String pref_noofrooms = String.valueOf(m);//convert in string so that we can pass via intent to submitted form class
                    JSONArray entry = jObj.getJSONArray("entry");//json array of above json object to store student id
                    //in string array and then passing to submitted form for diplay
                    for(int i=0;i<n;i++){
                        JSONObject jobj1 = entry.getJSONObject(i);
                        savesid[i] = jobj1.getString("sid");
                    }
                    JSONArray pref = jObj.getJSONArray("pref");
                    for(int j=0;j<2;j++){
                        JSONObject jobj2 = pref.getJSONObject(j);
                        hostelid[j] = jobj2.getString("hostelid");//storing the preferrred hostel from getsubmittedform.php file response
                        floor[j] = jobj2.getString("floorno");//storing the preferrred hostel from getsubmittedform.php file response
                    }

                    Intent i = new Intent(Student_Dashboard.this,Submitted_Form.class);
                    i.putExtra("sid",savesid);//passing all id
                    i.putExtra("noOfStudents",pref_noofrooms);//passing number of rooms
                    i.putExtra("Hostel_type",hostelid);
                    i.putExtra("Floor_type",floor);
                    startActivity(i);
                    //pfid hostel id and floor id is also passing from this activity to saved form activity



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

            @Override//getting response after sending string getsubmittedform and uid of that student
            //it gives submitted form if present in database
            protected Map<String, String> getParams() {
                // Posting params to register url
                String form = "numcsa";
                String uid = AppController.getString(Student_Dashboard.this,"Student_id");
                Map<String, String> params = new HashMap<String, String>();
                params.put("getsubmittedform", form);
                params.put("uid", uid);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);

    }

    //special request is called when student clicks on the special request button
    // and based on rqid this function is called
    private void getspecialrequest(final String uid){

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GETSR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    //response from sever
                    JSONArray reques = jObj.getJSONArray("requez");
                    JSONObject jobj1 = reques.getJSONObject(0);
                    String msg = jobj1.getString("reqmessage");//getting the stored msg present which he sent while sending special request
                    //rdate is the date on which he sent the request
                    String rdate = jobj1.getString("rdate");
                    //it shows the response of that request whether yet responded or not and what action taken if responeded
                    String reqresponse = jobj1.getString("reqresponse");

                    //passing all above data to submitted request class and showing the request if present else
                    //redirected to the screen from where he will send the request
                    Intent i = new Intent(Student_Dashboard.this,Submitted_Request.class);
                    i.putExtra("msg",msg);
                    i.putExtra("rdate",rdate);
                    i.putExtra("response",reqresponse);
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

            @Override//sending getsr and usergroup and student type to see whether
            //any special request is present in databse or not for that student
            protected Map<String, String> getParams() {
                // Posting params to register url
                String usertype = "student";
                String getsr = "numcsa";
                Map<String, String> params = new HashMap<String, String>();
                params.put("getsr", getsr);
                params.put("uid", uid);
                params.put("usertype",usertype);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }
}
