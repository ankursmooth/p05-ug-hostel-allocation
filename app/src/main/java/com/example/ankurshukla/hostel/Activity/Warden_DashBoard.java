package com.example.ankurshukla.hostel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ankurshukla.hostel.Controller.AppController;
import com.example.ankurshukla.hostel.Dashboard_Activity.Notifications;
import com.example.ankurshukla.hostel.Dashboard_Activity.Preference;
import com.example.ankurshukla.hostel.R;
import com.example.ankurshukla.hostel.Warden_Wing;

public class Warden_DashBoard extends AppCompatActivity {

    Button wing,notification,search,special_req;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_dash_board);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        wing= (Button) findViewById(R.id.btn_warden_wing);
        notification= (Button) findViewById(R.id.btn_warden_notify);
        name=(TextView)findViewById(R.id.display_name);

        String display = AppController.getString(Warden_DashBoard.this ,"username");
        name.setText(display);

        wing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Warden_DashBoard.this, Warden_Wing.class);
                startActivity(i);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Warden_DashBoard.this, Notifications.class);
                startActivity(i);
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
}