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
import com.example.ankurshukla.hostel.Dashboard_Activity.Preference;
import com.example.ankurshukla.hostel.R;

public class Student_Dashboard extends AppCompatActivity {

    Button wing,notification,search,special_req;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        wing= (Button) findViewById(R.id.btn_student_wing);
        notification= (Button) findViewById(R.id.btn_student_notify);
        name=(TextView)findViewById(R.id.display_sname);

        String display = AppController.getString(Student_Dashboard.this, "username");
        name.setText(display);

        wing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Student_Dashboard.this, Preference.class);
                startActivity(i);
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
}
