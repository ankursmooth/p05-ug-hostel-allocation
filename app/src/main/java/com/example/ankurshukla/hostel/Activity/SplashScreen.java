package com.example.ankurshukla.hostel.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankurshukla.hostel.ConnectionDetector;
import com.example.ankurshukla.hostel.Controller.AppConfig;
import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cd = new ConnectionDetector(getApplicationContext());
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    // get Internet status
                    isInternetPresent = cd.isConnectingToInternet();
                    // check for Internet status
                    if (isInternetPresent) {
                        // Internet Connection is Present
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(SplashScreen.this, "No Internet Connection",
                                "You don't have internet connection.", false);
                    }

                }
                finish();
            }
        };
        logoTimer.start();
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
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
