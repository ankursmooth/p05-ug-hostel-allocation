package com.example.ankurshukla.hostel.Dashboard_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ankurshukla.hostel.R;


/**
 * Created by Ankur Shukla on 10/17/2015.
 */
public class WingForm extends AppCompatActivity {

    LinearLayout one,two,three,four,five,six,seven,eight;
    String number;
    String hostel[] = new String[]{"",""};
    String floor[] = new String[]{"",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        number=intent.getExtras().getString("number");//getting no of rooms in the wing from prference class
        hostel = intent.getStringArrayExtra("Hostel_type");//getting all hostel types for preferneces
        floor  = intent.getStringArrayExtra("Floor_type");//getting all floor types from preferences
        setContentView(R.layout.wing_form);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        one= (LinearLayout) findViewById(R.id.wfl1);
        two= (LinearLayout) findViewById(R.id.wfl2);
        three= (LinearLayout) findViewById(R.id.wfl3);


        if(number.equals("One")){
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
        }
        else if(number.equals("Two")){
            three.setVisibility(View.GONE);
        }


        String shostel = hostel[0] + hostel[1] +floor[0] + floor[1];
        Toast.makeText(WingForm.this , shostel,Toast.LENGTH_SHORT).show();
    }

   
}
