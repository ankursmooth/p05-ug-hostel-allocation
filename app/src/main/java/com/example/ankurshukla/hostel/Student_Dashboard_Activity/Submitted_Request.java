package com.example.ankurshukla.hostel.Student_Dashboard_Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ankurshukla.hostel.R;

public class Submitted_Request extends AppCompatActivity {

    TextView message,reqdate,response;
    String gmsg,gdate,gresponse,smsg,sdate,sresponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        gmsg = intent.getStringExtra("msg");
        gdate = intent.getStringExtra("rdate");
        gresponse = intent.getStringExtra("response");
        setContentView(R.layout.activity_submitted__request);

        message = (TextView)findViewById(R.id.request_msg);
        reqdate = (TextView)findViewById(R.id.request_date);
        response = (TextView)findViewById(R.id.request_status);

        sdate = "Request Made on " + gdate;
        sresponse = "Status of Request: " + gresponse;
        message.setText(gmsg);
        reqdate.setText(sdate);
        response.setText(sresponse);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submitted__request, menu);
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
}
