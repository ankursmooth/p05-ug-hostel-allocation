package com.example.ankurshukla.hostel.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.ConnectionDetector;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toollogin;
    Button login;
    EditText uid,password;
    Spinner logingroup;
    final Context context=this;
    String usergroup;
    private ProgressDialog pDialog;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button) findViewById(R.id.login);
        uid = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.psswrd);
        logingroup = (Spinner) findViewById(R.id.spinnermain);
        cd = new ConnectionDetector(getApplicationContext());
        toollogin = (Toolbar)findViewById(R.id.toolbar_login);
        getSupportActionBar();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        List<String> types = Arrays.asList(getResources().getStringArray(R.array.usergroup));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        logingroup.setAdapter(dataAdapter);

        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
        // check for Internet status
        if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(MainActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);

        }



        logingroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usergroup = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usergroup.equals("warden")) {
                    String suid = uid.getText().toString().toLowerCase();
                    String spaasword = password.getText().toString().toLowerCase();

                    if (!suid.isEmpty() && !spaasword.isEmpty()) {
                        Warden_Details(suid, spaasword, usergroup);

                    } else {
                        Toast.makeText(MainActivity.this, "Enter Both Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else if (usergroup.equals("student")) {
                    String suid = uid.getText().toString().toLowerCase();
                    String spaasword = password.getText().toString().toLowerCase();

                    if (!suid.isEmpty() && !spaasword.isEmpty()) {
                        Student_Details(suid, spaasword, usergroup);

                    } else {
                        Toast.makeText(MainActivity.this, "Enter Both Credentials", Toast.LENGTH_SHORT).show();
                    }

                } else if (usergroup.equals("viewers")) {
                    String suid = uid.getText().toString().toLowerCase();
                    String spaasword = password.getText().toString().toLowerCase();

                    if (!suid.isEmpty() && !spaasword.isEmpty()) {
                        Details(suid, spaasword);
                        Intent i = new Intent(MainActivity.this, Dashboard.class);
                        startActivity(i);
                    }
                }
            }

        });
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    //function to login for warden
    private void Warden_Details(final String uid,final String password,final String usergroup){

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String wid = jObj.getString("wid");
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    String contact = jObj.getString("contact");
                    String message = jObj.getString("message");
                    String address = jObj.getString("Address");

                    //writing the value to sharedpreference in phone database
                    AppController.setString(MainActivity.this, "username", name);
                    AppController.setString(MainActivity.this, "loginId", wid);//warden id as loginId


                    Toast.makeText(MainActivity.this,message ,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Warden_DashBoard.class);
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
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", usergroup);
                params.put("uid", uid);
                params.put("password",password);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
        hideDialog();



    }

    //function to login for student
    private void Student_Details(final String uid,final String password,final String usergroup){

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String sid = jObj.getString("sid");
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    String contact = jObj.getString("contact");
                    String message = jObj.getString("message");
                    String sex = jObj.getString("sex");
                    String DOB = jObj.getString("DOB");

                    //writing the value to sharedpreference in phone database
                    //to convert first letter of name as Capital
                    String sname = name.substring(0,1).toUpperCase();
                    String remname = name.substring(1,name.length());
                    name = sname + remname;
                    AppController.setString(MainActivity.this, "username", name);
                    AppController.setString(MainActivity.this, "Student_id", sid);
                    AppController.setString(MainActivity.this, "sex", sex);


                   Toast.makeText(MainActivity.this,message ,Toast.LENGTH_SHORT).show();
                    Get_notification(uid, usergroup);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", usergroup);
                params.put("uid", uid);
                params.put("password",password);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
        hideDialog();

    }


    private void Get_notification(final String uid,final String number){
        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_NOTIFY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String success = jObj.getString("success");
                    String message = jObj.getString("message");
                    String noOfNotification = jObj.getString("noofnotifications");
                    String allocationstdate = jObj.getString("allocationstdate");
                    String allocationnddate = jObj.getString("allocationnddate");
                    String searchallowed = jObj.getString("searchallowed");

                    //writing the value to sharedpreference in phone database
                    AppController.setString(MainActivity.this, "noOfNotify", noOfNotification);
                    AppController.setString(MainActivity.this,"asdate",allocationstdate);//start date of allocation process
                    AppController.setString(MainActivity.this,"andate",allocationnddate);//end date of allocation process

                //    Toast.makeText(MainActivity.this, message ,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Student_Dashboard.class);
                    i.putExtra("searchallowed",searchallowed);
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
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                String getnotification = "number";
                Map<String, String> params = new HashMap<String, String>();
                params.put("usergroup", usergroup);
                params.put("uid", uid);
                params.put("getnotification",getnotification);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void Details(final String uid,final String password){

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest strReq =new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    //Response from server

                    String vid = jObj.getString("vid");
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    String contact = jObj.getString("contact");
                    String message = jObj.getString("message");
                    String department = jObj.getString("department");

                    //writing the value to sharedpreference in phone database
                    AppController.setString(MainActivity.this, "username", name);
                    AppController.setString(MainActivity.this, "other_id", vid);


                    Toast.makeText(MainActivity.this,message ,Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                String usergroup = "med";
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", usergroup);
                params.put("uid", uid);
                params.put("password",password);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
        hideDialog();

    }


    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                System.exit(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
